package net.umbriel.kryolib;

/**
 *  (c) 2018 Daniel Jameson
 *     
 *  This program is free software: you can redistribute it and/or modify
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

/**
 * @author daniel
 * Out Of Band Block
 * 
 */

import java.util.*;

public class OOBBlock {
	
	public static final int INVALID = 0;
	public static final int READ = 1;
	public static final int INDEX = 2;
	public static final int END = 3;
	public static final int INFO = 4;
	public static final int EOF = 0xD;
	
	private int type; //OOB type
 	private int size; //OOB header
 	private ArrayList<Integer> data; //Data as array of bytes
 	private long strPos; //stream position
 	
 	public OOBBlock(int type) {
 		this.setType(type);
 	}

	/**
	 * @return block type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param set block type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return data size in bytes
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param set data size in bytes
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the data as an arraylist (each int is one byte value)
	 */
	public ArrayList<Integer> getData() {
		return data;
	}

	/**
	 * @param set the data arrayList
	 */
	public void setData(ArrayList<Integer> data) {
		this.data = data;
	}

	/**
	 * @return the stream position
	 */
	public long getStrPos() {
		return strPos;
	}

	/**
	 * @param strPos set stream position
	 */
	public void setStrPos(long strPos) {
		this.strPos = strPos;
	}
 	
 	
	
	
}
