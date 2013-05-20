package net.umbriel.kryotest;

import net.umbriel.imageformat.Disk;
import net.umbriel.imageformat.Sector;
import net.umbriel.imageformat.Surface;
import net.umbriel.imageformat.Track;
import net.umbriel.kryolib.*;


import java.io.*;
import java.util.ArrayList;


public class KryoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Disk disk = new Disk();
		// TODO Auto-generated method stub
		
		CRCCalculator c = new CRCCalculator();
		
		SSDWriter decoder = new SSDWriter();
		try {

			disk.getSurface()[0]=new Surface(); //surface 0...
			ArrayList<Track> tracks = new ArrayList<Track>();
			StreamReader sr = new StreamReader(new File("zap!"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			for (int i=0; i<80; i+=2) {
				FDCEmulator fdc = new FDCEmulator(sr.getTrack(i, 0));
				//System.out.println(fdc.getBinaryString());
				fdc.setClockCentre(4000);
				fdc.setRpm(360.0);
				fdc.processTrack();
				tracks.add(decoder.decode(fdc.getBinaryList()));
				//System.out.println(fdc.getClockCentre());
			}
			disk.getSurface()[0].setTracks(tracks);
			FileOutputStream fo = new FileOutputStream("zap.ssd");
			for (int t=0; t<tracks.size();t++) {
				ArrayList<Sector> sectors=tracks.get(t).getSectors();
				for (int s=0; s<sectors.size(); s++) {
					ArrayList<Integer> data=sectors.get(s).getData();
					for (int d=0; d<data.size();d++) {
						fo.write(data.get(d).intValue());
					}
				}
			}
			
			fo.close();
			/*FileWriter fw = new FileWriter(new File("fluxes.csv"));
			ArrayList<Flux> fluxes = sr.getTrack(0, 0).getFluxes();
			for (int i=0; i<fluxes.size();i++) {
				double time = fluxes.get(i).getNanoSecondTime();
				if ((time < 3600) | ((time > 4400) && (time <7200)) | (time >8800)) {
					fw.write(time+"\n");
				}
			}
			fw.close();*/
		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//APDWriter.createAPD(new File("zarch"));
		/**try {
			FileOutputStream fo = new FileOutputStream("binarytrackdump");
			StreamReader sr = new StreamReader(new File("zarch"));
			FDCEmulator fdc = new FDCEmulator(sr.getTrack(40, 0));
			fdc.setClockCentre(4000);
			fdc.processTrack();
			fo.write(fdc.getTrackByteArray(0));
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/

	}

}
