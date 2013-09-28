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
package cz.wicketstuff.boss.flow.test;

/**
 * Interface that define mixed strings from 'test-flow-complete.xml'
 * for directly testing inside classes.
 * 
 * @author Martin Strejc
 *
 */
public interface ICompleteFlowTest {

	public static final String S0initialState = "S0initialState";
	public static final String S1realState = "S1realState";
	public static final String S2ifState = "S2ifState";
	public static final String S3viewState = "S3viewState";
	public static final String S4viewState = "S4viewState";
	public static final String S5switchState = "S5switchState";
	public static final String S6viewStateInitial = "S6viewStateInitial";
	public static final String S8joinState = "S8joinState";
	public static final String S9finalState = "S9finalState";
	
	public static final String T01 = "t01";
	public static final String T12 = "t12";
	public static final String T15 = "t15";
	public static final String T23 = "t23";
	public static final String T24 = "t24";
	public static final String T38 = "t38";
	public static final String T45 = "t45";
	public static final String T52 = "t52";
	public static final String T56 = "t56";
	public static final String T58 = "t58";
	public static final String T68 = "t68";
	public static final String T78 = "t78";
	public static final String T89 = "t89";
	public static final String T96 = "t96";
	
	public static final String EX_isState2 = "isState2";
	public static final String EX_switchS5 = "switchS5";
	public static final String CASE_toViewS6 = "toViewS6";
	public static final String CASE_toFinal = "toFinal";

}
