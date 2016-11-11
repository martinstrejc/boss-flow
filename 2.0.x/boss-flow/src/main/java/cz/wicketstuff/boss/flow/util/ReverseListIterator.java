/*
 * Et netera, http://boss.etnetera.cz - Copyright (C) 2012 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 *
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
package cz.wicketstuff.boss.flow.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An iterator over {@link List} which goes from the end to the start
 * 
 * @param <E>
 */
public class ReverseListIterator<E> implements Iterator<E>, Iterable<E>
{
	private final ListIterator<E> delegateIterator;

	/**
	 * Construct.
	 * 
	 * @param list
	 *            the list which will be iterated in reverse order
	 */
	public ReverseListIterator(final List<E> list)
	{
		int start = list.size();
		this.delegateIterator = list.listIterator(start);
	}

	@Override
	public boolean hasNext()
	{
		return delegateIterator.hasPrevious();
	}

	@Override
	public E next()
	{
		return delegateIterator.previous();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<E> iterator()
	{
		return this;
	}


}