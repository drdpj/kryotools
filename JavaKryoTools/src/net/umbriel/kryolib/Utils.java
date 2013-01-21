/**
 *     Copyright (C) 2013  Daniel Jameson

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */
package net.umbriel.kryolib;

/**
 * 
 * Utilities
 * @author daniel
 *
 */

import java.util.*;



final class Utils {

	final static Boolean DEBUG = true;
	
	final static Integer maxIntValue(ArrayList<Integer> ints) {
		Integer maxValue = Integer.MIN_VALUE;
		Iterator<Integer> iter = ints.iterator();
		while (iter.hasNext()) {
			maxValue = Math.max(iter.next(),maxValue);
		}
		
		return maxValue;
	}
	
	final static Integer minIntValue(ArrayList<Integer> ints) {
		Integer minValue = Integer.MAX_VALUE;
		Iterator<Integer> iter = ints.iterator();
		while (iter.hasNext()) {
			minValue = Math.min(iter.next(), minValue);
		}
		
		return minValue;
	}
	
	final static Double maxDoubleValue(ArrayList<Double> doubles) {
		Double maxValue = Double.MIN_VALUE;
		Iterator<Double> iter = doubles.iterator();
		while (iter.hasNext()) {
			maxValue = Math.max(iter.next(), maxValue);
		}
		
		return maxValue;
	}
	
	final static Double minDoubleValue(ArrayList<Double> doubles) {
		Double minValue = Double.MIN_VALUE;
		Iterator<Double> iter = doubles.iterator();
		while (iter.hasNext()) {
			minValue = Math.min(iter.next(), minValue);
		}
		
		return minValue;
	}
	
	
}
