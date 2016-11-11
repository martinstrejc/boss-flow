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
package cz.wicketstuff.boss.flow;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.processor.IFlowProcessor;

/**
 * Flow processor factory interface. The processor is responsible of correct flow processing.
 * The factory just can create or get important processing classes.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowProcessorFactory {

	/**
	 * Return the default flow processor
	 * 
	 * @return flow processor
	 */
	public <T extends Serializable> IFlowProcessor<T> getFlowProcessor();

	/**
	 * Return the flow processor associated to the specified flow identified by its name.
	 * 
	 * @param flowName
	 * @return flow processor
	 */
	public <T extends Serializable> IFlowProcessor<T> getFlowProcessor(String flowName);
	
}
