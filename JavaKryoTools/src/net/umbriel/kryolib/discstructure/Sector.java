package net.umbriel.kryolib.discstructure;

import java.util.ArrayList;

public class Sector implements Comparable<Sector> {
	
	final static Integer FM = 0;
	final static Integer MFM = 1;
	
	private Integer encoding;
	private Integer number;
	private Integer headerChecksum;
	private Integer dataChecksum;
	private Integer trackNumber;
	private Integer size;
	private ArrayList<Integer> data;
	
	public Sector() {
		data = new ArrayList<Integer>();
		number = 0xff;
		headerChecksum=0xffff;
		dataChecksum=0xffff;
		encoding = Sector.FM; //default is going to be FM
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

	/**
	 * @return the encoding
	 */
	public Integer getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(Integer encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the trackNumber
	 */
	public Integer getTrackNumber() {
		return trackNumber;
	}

	/**
	 * @param trackNumber the trackNumber to set
	 */
	public void setTrackNumber(Integer trackNumber) {
		this.trackNumber = trackNumber;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}



}
