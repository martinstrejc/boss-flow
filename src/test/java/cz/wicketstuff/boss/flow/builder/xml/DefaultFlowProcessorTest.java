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
package cz.wicketstuff.boss.flow.builder.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.ext.DefaultFlowProcessor;
import cz.wicketstuff.boss.flow.test.AbstractFlowStepTest;
import cz.wicketstuff.boss.flow.test.FlowFileResource;

/**
 * @author Martin Strejc
 *
 */
public class DefaultFlowProcessorTest extends AbstractFlowStepTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	public DefaultFlowProcessorTest() {
		super();
		log.trace("Test complete flow builded by JAXB.");
	}

	@Override
	protected IFlowProcessor<String> createProcessor() throws FlowException {
		log.trace("Creating flow processor");
		DefaultFlowProcessor<String> processor = new DefaultFlowProcessor<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public IFlowStateProcessor<String> defaultFlowStateProcessor(IFlowTree flowTree) {
				return createFlowStateProcessor();
			}
	
		};
		FlowFileResource resourceHelper = new FlowFileResource();
		processor.setFlowInputStream(resourceHelper.getCompleteFlowFileStream());
		processor.setDefaultInitialStateName(S0initialState);
		// not applicable here
		// processor.scanAnnotedBeans(this, this, this, this);
		return processor.initializeProcessor();
	}

}
