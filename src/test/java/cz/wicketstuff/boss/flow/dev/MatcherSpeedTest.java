/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.boss.flow.dev;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class MatcherSpeedTest {

//	private static final long REPEATS = 10000000;
//	private static final long REPEATS = 1000000;
//	private static final long REPEATS = 100000;
//	private static final long REPEATS = 10000;
	private static final long REPEATS = 1000;

	
//	private static boolean USE_LONG_TEXTS = false;
	private static boolean USE_LONG_TEXTS = true;
	
	
	@Rule 
	public TestName testName = new TestName();
	
	private Date startDate;
	
	private NumberFormat timestampFormatter = new DecimalFormat("###,###");
	
	private static final Logger log = LoggerFactory.getLogger(MatcherSpeedTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() {
		log.trace("USE_LONG_TEXTS = {}", USE_LONG_TEXTS);
		log.trace("REPEATS = {}", REPEATS);
		
		regexStrings = new ArrayList<>(1000);
		regexStrings.add("(Ab\\s+)");
		regexStrings.add("Text.*");
		regexStrings.add(".*");
		regexStrings.add("\\w+12");
		regexStrings.add("^[a-z0-9_-]{3,15}$");
		regexStrings.add("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		regexStrings.add("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
		regexStrings.add("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
		regexStrings.add("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");
		regexStrings.add("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|" +
				"25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		regexStrings.add("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
		regexStrings.add("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");
		
		comparedStrings = new ArrayList<>(1000);
		comparedStrings.add("Text12");
		comparedStrings.add("fake12");
		comparedStrings.add("Any random text");
		comparedStrings.add(" \t\r\n");
		comparedStrings.add(" \t\r\n not only white spaces \t\r\n ");
		comparedStrings.add("test@wicketstuff.cz");
		comparedStrings.add("test@wicket@stuff.cz");
		comparedStrings.add("<invalid<tag>");
		comparedStrings.add("<a href=\"anywehere\">");
		comparedStrings.add("2013/07/23");
		if(USE_LONG_TEXTS) {
			comparedStrings.add("CorrectPassword#1234");
			comparedStrings.add("Exmaple text: Regular expression is an art of the programing, " +
					"it’s hard to debug , learn and understand, " +
					"but the powerful features are still " +
					"attract many developers to code regular expression. " +
					"Let’s explore the following 10 practical regular expression ~ enjoy :)");
			comparedStrings.add("Exmaple text: 2013/07/23Pattern Class: A Pattern object is a compiled representation " +
					"of a regular expression. The Pattern class provides no public constructors. " +
					"To create a pattern, you must first invoke one of its public static compile methods, " +
					"which will then return a Pattern object. These methods accept " +
					"a regular expression as the first argument. " +
					"Matcher Class: A Matcher object is the engine that interprets " +
					"the pattern and performs match operations against an input string. " +
					"Like the Pattern class, Matcher defines no public constructors. " +
					"You obtain a Matcher object by invoking the matcher method on a Pattern object. " +
					"PatternSyntaxException: A PatternSyntaxException object is an unchecked exception " +
					"that indicates a syntax error in a regular expression pattern. ");
			comparedStrings.add("Exmaple text: Regular expression is an art of the programing, " +
					"it’s hard to debug , learn and understand, " +
					"but the powerful features are still " +
					"attract many <a href=\"developers\">developers</a> to code regular expression. " +
					"Let’s explore the following 10 practical regular expression ~ enjoy :)");
			comparedStrings.add("Exmaple text: Regular expression is an art of the programing, " +
					"it’s hard to debug , learn and understand, " +
					"but the powerful features are still " +
					"attract many to code regular expression. " +
					"Let’s explore the following 10 practical regular expression ~ enjoy :) " +
					"myemail@mydomain.com");			
		}
		
		regexPatterns = new ArrayList<>(regexStrings.size());
		matcherCache = new ArrayList<>(regexPatterns.size());
		for(String s : regexStrings) {
			regexPatterns.add(Pattern.compile(s));
			matcherCache.add(new HashMap<String, Matcher>(16));
		}

	}

	@AfterClass
	public static void tearDownAfterClass() {
		regexStrings.clear();
		regexStrings = null;
		comparedStrings.clear();
		comparedStrings = null;
		regexPatterns.clear();
		regexPatterns = null;
		if(matcherCache != null) {
			matcherCache.clear();
			matcherCache = null;	
		}		
	}
	
	@Before
	public void setUp() {
		// log.trace("Running {}", testName.getMethodName());
		startDate = new Date();
	}

	@After
	public void tearDown() {
		Date now = new Date();
		long delta = now.getTime() - startDate.getTime();
		log.trace("{} has been running {} ms", testName.getMethodName(), timestampFormatter.format(delta));
	}
	
	public MatcherSpeedTest() {
		super();
	}
	
	@Test
	public void testStringMatcher() throws Exception {
		for(long l = 0; l < REPEATS; l++) {
			getRandomRegex().matches(getRandomRegex());
		}
	}

	@Test
	public void testPatternMatcher() throws Exception {
		for(long l = 0; l < REPEATS; l++) {
			getRandomPattern().matcher(getRandomRegex()).matches();
		}
	}

	@Test
	public void testCachedPatternMatcher() throws Exception {
		for(long l = 0; l < REPEATS; l++) {
			int index = rand(regexPatterns.size());
			Pattern p = regexPatterns.get(index);
			Map<String, Matcher> matcherMap = matcherCache.get(index);
			String text = getRandomRegex();
			Matcher matcher = matcherMap.get(text);
			if(matcher == null) {
				matcher = p.matcher(text);
				matcherMap.put(text, matcher);
			}
			matcher.matches();
		}
	}
		
	public String getRandomRegex() {
		return regexStrings.get(rand(regexStrings.size()));
	}

	public Pattern getRandomPattern() {
		return regexPatterns.get(rand(regexPatterns.size()));
	}

	public String getRandomText() {
		return comparedStrings.get(rand(comparedStrings.size()));
	}
	
	public int rand(int maxValue) {
		return (int)(Math.random() * (double)maxValue);
	}
	
	private static List<String> regexStrings;
	private static List<Pattern> regexPatterns;
	private static List<String> comparedStrings;
	private static List<Map<String, Matcher>> matcherCache;
	
	


}
