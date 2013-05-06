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

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Strejc
 *
 */
public class FlowFileResource {

	public static final String COMPLETE_FLOW_FILE = "test-flow-complete.xml";
	public static final String FLOW_FILE_MISSING_STATE = "test-flow-missing-state.xml";
	public static final String FLOW_FILE_MISSING_TRANSITION = "test-flow-missing-transition.xml";
	public static final String FLOW_FILE_DUPLICATED_STATE = "test-flow-duplicated-state.xml";
	public static final String FLOW_FILE_DUPLICATED_TRANSITION = "test-flow-duplicated-transition.xml";
	public static final String FLOW_FILE_INVALID_PREVNEXT1 = "test-flow-invalid-prevnext1.xml";
	public static final String FLOW_FILE_INVALID_PREVNEXT2 = "test-flow-invalid-prevnext2.xml";
	public static final String FLOW_FILE_INVALID_PREVNEXT3 = "test-flow-invalid-prevnext3.xml";
	
	public FlowFileResource() {
	}

	private Logger log = LoggerFactory.getLogger(getClass().getName());
	
	protected InputStream getResourceAsStream(String name) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(name);
		if(is == null) {
			log.error("Required tested resource '" + name + "' doesn't exist! Check the resource location!");
			throw new IllegalStateException("Required tested resource '" + name + "' doesn't exist! Check the resource location!");
		}
		return is;
	}

	public String getCompleteFlowFileName() {
		return COMPLETE_FLOW_FILE;
	}
	
	public InputStream getCompleteFlowFileStream() {
		return getResourceAsStream(getCompleteFlowFileName());
	}

	public String getFlowMissingStateFileName() {
		return FLOW_FILE_MISSING_STATE;
	}
	
	public InputStream getFlowMissingStateFileStream() {
		return getResourceAsStream(getFlowMissingStateFileName());
	}

	public String getFlowMissingTransitionFileName() {
		return FLOW_FILE_MISSING_TRANSITION;
	}
	
	public InputStream getFlowMissingTransitionFileStream() {
		return getResourceAsStream(getFlowMissingTransitionFileName());
	}

	public String getFlowDuplicatedStateFileName() {
		return FLOW_FILE_DUPLICATED_STATE;
	}
	
	public InputStream getFlowDuplicatedStateFileStream() {
		return getResourceAsStream(getFlowDuplicatedStateFileName());
	}

	public String getFlowDuplicatedTransitionFileName() {
		return FLOW_FILE_DUPLICATED_TRANSITION;
	}
	
	public InputStream getFlowDuplicatedTransitionFileStream() {
		return getResourceAsStream(getFlowDuplicatedTransitionFileName());
	}

	public String getFlowInvalidPrevNext1FileName() {
		return FLOW_FILE_INVALID_PREVNEXT1;
	}
	
	public InputStream getFlowInvalidPrevNext1FileStream() {
		return getResourceAsStream(getFlowInvalidPrevNext1FileName());
	}

	public String getFlowInvalidPrevNext2FileName() {
		return FLOW_FILE_INVALID_PREVNEXT2;
	}
	
	public InputStream getFlowInvalidPrevNext2FileStream() {
		return getResourceAsStream(getFlowInvalidPrevNext2FileName());
	}

	public String getFlowInvalidPrevNext3FileName() {
		return FLOW_FILE_INVALID_PREVNEXT3;
	}
	
	public InputStream getFlowInvalidPrevNext3FileStream() {
		return getResourceAsStream(getFlowInvalidPrevNext3FileName());
	}
}
