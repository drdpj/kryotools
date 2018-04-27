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
 * A flux
 */
public class Flux {
	
	private long time;
	private long streamPos;
	private double nanoSecondTime;
	
	public Flux (long time,long streamPos) {
		this.time=time;
		this.streamPos=streamPos;
	}
	/**
	 * @return the time between this and previous flux
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param set flux time
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * @return the stream position
	 */
	public long getStreamPos() {
		return streamPos;
	}
	/**
	 * @param streamPos the stream position to set
	 */
	public void setStreamPos(long streamPos) {
		this.streamPos = streamPos;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getNanoSecondTime() {
		return nanoSecondTime;
	}
	
	/**
	 * 
	 * @param microSecondTime time from preceeding flux in us.
	 */
	public void setNanoSecondTime(Double sampleClock) {
		this.nanoSecondTime = ((this.getTime()/sampleClock) * 1000000000);
		if (Utils.DEBUG) {
			//System.out.println(this.nanoSecondTime);
		}
	}
	

}
