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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatcherSpeedTest {

	public static final long REPEATS = 100000000;
	
	@Rule 
	public TestName testName = new TestName();
	
	private Date startDate;
	
	private NumberFormat timestampFormatter = new DecimalFormat("###,###");
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@BeforeClass
	public static void setUpBeforeClass() {
		regexStrings = new ArrayList<>(1000);
		regexStrings.add("(Ab\\s+)");
		regexStrings.add("Text.*");
		regexStrings.add(".*");
		regexStrings.add("\\w+12");
		regexStrings.add("\\s+");
		
		comparedStrings = new ArrayList<>(1000);
		comparedStrings.add("Text12");
		comparedStrings.add("cosi12");
		comparedStrings.add("Nejaky nahodny text");
		comparedStrings.add(" \t\r\n");
		
		
		regexPatterns = new ArrayList<>(regexStrings.size());
		for(String s : regexStrings) {
			regexPatterns.add(Pattern.compile(s));
		}

		matcherCache = new HashMap<>(regexPatterns.size());
		for(Pattern p : regexPatterns) {
			matcherCache.put(p, new HashMap<String, Matcher>(16));
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
		log.trace("Running {}", testName.getMethodName());
		startDate = new Date();
	}

	@After
	public void tearDown() {
		Date now = new Date();
		long delta = now.getTime() - startDate.getTime();
		log.trace("{} runs {} ms", testName.getMethodName(), timestampFormatter.format(delta));
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
			Pattern p = getRandomPattern();
			Map<String, Matcher> matcherMap = matcherCache.get(p);
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
	private static Map<Pattern, Map<String, Matcher>> matcherCache;
	
	


}
