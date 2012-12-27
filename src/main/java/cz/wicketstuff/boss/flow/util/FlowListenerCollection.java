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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.util.listener.IPriority;

/**
 * Represents a collection of listeners. Facilitates invocation of events on each listener.
 * <p>
 * NOTE: Ordering of listeners is not guaranteed and should not be relied upon
 * </p>
 * 
 * @author ivaynberg (Igor Vaynberg)
 * @author Jonathan Locke
 * 
 * @param <T>
 *            type of listeners
 */
public abstract class FlowListenerCollection<T extends IPriority> implements Serializable, Iterable<T>
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(FlowListenerCollection.class);

	/** list of listeners */
	private final List<T> listeners = new CopyOnWriteArrayList<T>();

	/**
	 * Adds a listener to this set of listeners.
	 * 
	 * @param listener
	 *            The listener to add
	 * @return {@code true} if the listener was added
	 */
	public boolean add(final T listener)
	{
		if ((listener == null) && !isAllowingNulls())
		{
			return false;
		}
		if (!isAllowingDuplicates() && listeners.contains(listener))
		{
			return false;
		}
		listeners.add(listener);
		return true;
	}

	/**
	 * Notifies each listener in this
	 * 
	 * @param notifier
	 *            notifier used to notify each listener
	 */
	protected void notify(final INotifier<T> notifier)
	{
		for (T listener : listeners)
		{
			notifier.notify(listener);
		}
	}

	/**
	 * Notifies each listener in this set ignoring exceptions. Exceptions will be logged.
	 * 
	 * @param notifier
	 *            notifier used to notify each listener
	 */
	protected void notifyIgnoringExceptions(final INotifier<T> notifier)
	{
		for (T listener : listeners)
		{
			try
			{
				notifier.notify(listener);
			}
			catch (Exception e)
			{
				logger.error("Error invoking listener: " + listener, e);
			}
		}
	}

	/**
	 * Notifies each listener in this set in reverse order ignoring exceptions
	 * 
	 * @param notifier
	 *            notifier used to notify each listener
	 */
	protected void reversedNotifyIgnoringExceptions(final INotifier<T> notifier)
	{
		reversedNotify(new INotifier<T>()
		{
			@Override
			public void notify(T listener)
			{
				try
				{
					notifier.notify(listener);
				}
				catch (Exception e)
				{
					logger.error("Error invoking listener: " + listener, e);
				}

			}

		});
	}

	/**
	 * Notifies each listener in this in reversed order
	 * 
	 * @param notifier
	 *            notifier used to notify each listener
	 */
	protected void reversedNotify(final INotifier<T> notifier)
	{
		Iterator<T> it = new ReverseListIterator<T>(listeners);
		while (it.hasNext())
		{
			T listener = it.next();
			notifier.notify(listener);
		}
	}

	/**
	 * Removes a listener from this set.
	 * 
	 * @param listener
	 *            The listener to remove
	 */
	public void remove(final T listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Whether or not added listeners should be checked for duplicates.
	 * 
	 * @return {@code true} to ignore duplicates
	 */
	protected boolean isAllowingDuplicates()
	{
		return true;
	}

	/**
	 * Whether or not to allow {@code null}s in listener collection.
	 * 
	 * @return {@code} true to allow nulls to be added to the collection
	 */
	protected boolean isAllowingNulls()
	{
		return false;
	}

	/**
	 * Used to notify a listener. Usually this method simply forwards the {@link #notify(Object)} to
	 * the proper method on the listener.
	 * 
	 * @author ivaynberg (Igor Vaynberg)
	 * @param <T>
	 */
	protected static interface INotifier<T>
	{
		void notify(T listener);
	}

	/**
	 * Returns an iterator that can iterate the listeners.
	 * 
	 * @return an iterator that can iterate the listeners.
	 */
	@Override
	public Iterator<T> iterator()
	{
		return listeners.iterator();
	}

	@Override
	protected void finalize() throws Throwable {
		if(listeners != null) {
			listeners.clear();
		}
		super.finalize();
	}

	@SuppressWarnings("unchecked")
	public void sort() {
		List<IPriority> list = new ArrayList<IPriority>(listeners);
		Collections.sort(list);
		listeners.clear();
		listeners.addAll((List<T>)list);
	}	
	
	public void sort(Comparator<T> comparator) {
		ArrayList<T> list = new ArrayList<T>(listeners);
		Collections.sort(list, comparator);
		listeners.clear();
		listeners.addAll(list);
	}

}
