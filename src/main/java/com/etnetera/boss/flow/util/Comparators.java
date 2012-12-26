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
 */
package com.etnetera.boss.flow.util;

/**
 * @author Martin Strejc
 *
 */
public class Comparators {

	public Comparators() {
	}
	
	public static int compareInts(int i1, int i2) {
		if(i1 == i2) {
			return 0;
		}
		if(i1 > i2) {
			return 1;
		}
		return -1;
	}

}
