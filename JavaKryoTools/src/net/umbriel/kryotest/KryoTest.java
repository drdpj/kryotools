package net.umbriel.kryotest;

import net.umbriel.kryolib.*;


import java.io.*;
import java.util.ArrayList;


public class KryoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[][][] ssd = new Integer[40][][];
		// TODO Auto-generated method stub
		
		CRCCalculator c = new CRCCalculator();
		
		SSDWriter decoder = new SSDWriter();
		try {


			StreamReader sr = new StreamReader(new File("zap!"));
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			for (int i=0; i<80; i+=2) {
				FDCEmulator fdc = new FDCEmulator(sr.getTrack(i, 0));
				//System.out.println(fdc.getBinaryString());
				fdc.setClockCentre(4000);
				fdc.setRpm(360.0);
				fdc.processTrack();
				ssd[i/2]=decoder.decode(fdc.getBinaryList());
				//System.out.println(fdc.getClockCentre());
			}
			FileOutputStream fo = new FileOutputStream("zap.ssd");
			for (int t=0; t<40; t++) {
				for (int s=0; s<10; s++) {
					for (int b=0; b<256; b++) {
						fo.write(ssd[t][s][b]);
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
