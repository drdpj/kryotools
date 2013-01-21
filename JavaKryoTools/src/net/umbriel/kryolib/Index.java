package net.umbriel.kryolib;

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

/**
 * @author daniel
 * Class to represent kryoflux index OOB blocks
 *
 */


public class Index {
	private int sPos;
	private int timer;
	private int sysTime;
	
	public Index() {
		setSPos(0);
		setTimer(0);
		setSysTime(0);
	}

	/**
	 * @return stream position
	 */
	public int getSPos() {
		return sPos;
	}

	/**
	 * @param set stream position
	 */
	public void setSPos(int sPos) {
		this.sPos = sPos;
	}

	/**
	 * @return Timer value
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * @param set Timer value
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}

	/**
	 * @return the system time when index detected
	 */
	public int getSysTime() {
		return sysTime;
	}

	/**
	 * @param set system time when index detected
	 */
	public void setSysTime(int sysTime) {
		this.sysTime = sysTime;
	}
	
}
