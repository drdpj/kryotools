package net.umbriel.imageformat;

import java.util.ArrayList;

public class Sector implements Comparable<Sector> {

	private Integer number;
	private Integer headerChecksum;
	private Integer dataChecksum;
	private ArrayList<Integer> data;
	
	public Sector() {
		data = new ArrayList<Integer>();
		number = 0xff;
		headerChecksum=0xffff;
		dataChecksum=0xffff;
	}

	/**
	 * @return the Sector number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number the Sector Number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return the headerChecksum
	 */
	public Integer getHeaderChecksum() {
		return headerChecksum;
	}

	/**
	 * @param headerChecksum the headerChecksum to set
	 */
	public void setHeaderChecksum(Integer headerChecksum) {
		this.headerChecksum = headerChecksum;
	}

	/**
	 * @return the dataChecksum
	 */
	public Integer getDataChecksum() {
		return dataChecksum;
	}

	/**
	 * @param dataChecksum the dataChecksum to set
	 */
	public void setDataChecksum(Integer dataChecksum) {
		this.dataChecksum = dataChecksum;
	}

	/**
	 * @return the data
	 */
	public ArrayList<Integer> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<Integer> data) {
		this.data = data;
	}



	@Override
	public int compareTo(Sector o) {
		return (this.getNumber() < o.getNumber()) ? -1 : ((this.getNumber() == o.getNumber()) ? 0 : 1);

	}



}
