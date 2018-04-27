package net.umbriel.kryotest;

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

import net.umbriel.kryolib.*;
import net.umbriel.kryolib.discstructure.Disk;
import net.umbriel.kryolib.discstructure.Sector;
import net.umbriel.kryolib.discstructure.Surface;
import net.umbriel.kryolib.discstructure.Track;


import java.io.*;
import java.util.ArrayList;


public class KryoTest {

	//static String hugeString="FFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF57EAEEAAAAAFFAAAAAEBEAAFFFAFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF56FAAAAEAAEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEFAEAAAAAAAAAAAAAAAEAAAAAAAAAAAAFFFAAAAAAAAAAAAAAAAEAAAAAAAAAAAAFBEAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAFEAAABAAAAAAAAAAAEAAAAAAAAAAAAFAFAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEFAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEBBAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAEFAAAABAAAAAAAAAAAEAAAAAAAAAAAAEBFEAAAAAAAAAAAAAAAEAAAAAAAAAAAAFAFAAAABAAAAAAAAAAAEAAAAAAAAAAAAFAFEAAABAAAAAAAAAAAEAAAAAAAAAAAAEBBAAAABAAAAAAAAAAAEAAAAAAAAAAAAEBAEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEAAAAAABAAAAAAAAAAAEAAAAAAAAAAAAEEFEAAABAAAAAAAAAAAEAAAAAAAAAAAAEEEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFFEAAABAAAAAAAAAAAEAAAAAAAAAAAAFEFEAAABAAAAAAAAAAAEAAAAAAAAAAAABFAAAAABAAAAAAAAAAAEAAAAAAAAAAAAEEEEAAAAAAAAAAAAAAAEAAAAAAAAAAAAAEBAAAABAAAAAAAAAAAEAAAAAAAAAAAAFAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAFAFEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEFFEAAAAAAAAAAAAAAAEAAAAAAAAAAAABFAEAAABAAAAAAAAAAAEAAAAAAAAAAAAEBEAAAABAAAAAAAAAAAEAAAAAAAAAAAAFAEAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEAEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFBEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEAFEAAABAAAAAAAAAAAEAAAAAAAAAAAAEBBEAAABAAAAAAAAAAAEAAAAAAAAAAAAEAEEAAAAAAAAAAAAAAAEAAAAAAAAAAAAABFEAAABAAAAAAAAAAAEAAAAAAAAAAAAFBBEAAAAAAAAAAAAAAAEAAAAAAAAAAAAFBFAAAAAAAAAAAAAAAAEAAAAAAAAAAAAFFBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFABAAAABAAAAAAAAAAAEAAAAAAAAAAAAFBEAAAABAAAAAAAAAAAEAAAAAAAAAAAAFFAEAAAAAAAAAAAAAAAEAAAAAAAAAAAABBBAAAABAAAAAAAAAAAEAAAAAAAAAAAAAEAEAAABAAAAAAAAAAAEAAAAAAAAAAAAABFAAAABAAAAAAAAAAAEAAAAAAAAAAAAEABAAAABAAAAAAAAAAAEAAAAAAAAAAAAABEAAAABAAAAAAAAAAAEAAAAAAAAAAAAABAAAAABAAAAAAAAAAAEAAAAAAAAAAAAFEFEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEBBEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEFBAAAAAAAAAAAAAAAAEAAAAAAAAAAAABEAAAAABAAAAAAAAAAAEAAAAAAAAAAAAFEEEAAABAAAAAAAAAAAEAAAAAAAAAAAAABBAAAABAAAAAAAAAAAEAAAAAAAAAAAAFFFEAAAAAAAAAAAAAAAEAAAAAAAAAAAABFBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFEEEAAAAAAAAAAAAAAAEAAAAAAAAAAAAFFBEAAAAAAAAAAAAAAAEAAAAAAAAAAAABFBAAAABAAAAAAAAAAAEAAAAAAAAAAAAFAEEAAABAAAAAAAAAAAEAAAAAAAAAAAABEBEAAABAAAAAAAAAAAEAAAAAAAAAAAAEEEEAAABAAAAAAAAAAAEAAAAAAAAAAAABBEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEEFEAAAAAAAAAAAAAAAEAAAAAAAAAAAAAABAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFEAAAAAAAAAAAAAAAAEAAAAAAAAAEABAAEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF57EAEEAAAAAFFABAAAEBBAFFAFBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF56FFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBBBFBAFAAFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF57EAEEAAAAAFFAEAAAEAABEEBFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF56FAAAAEAFEAAAAAAAAAAAAAAAEAAAAAAAAAAAAAFBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFFAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAFFBAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEEBAAAABAAAAAAAAAAAEAAAAAAAAAAAABEEEAAABAAAAAAAAAAAEAAAAAAAAAAAAEABEAAABAAAAAAAAAAAEAAAAAAAAAAAAFAAAAAABAAAAAAAAAAAEAAAAAAAAAAAABAAEAAABAAAAAAAAAAAEAAAAAAAAAAAAEFBEAAABAAAAAAAAAAAEAAAAAAAAAAAABBAAAAABAAAAAAAAAAAEAAAAAAAAAAAAFBFAAAABAAAAAAAAAAAEAAAAAAAAAAAAAEEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEBFAAAABAAAAAAAAAAAEAAAAAAAAAAAABBFAAAABAAAAAAAAAAAEAAAAAAAAAAAABAEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEAEAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEBAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAFAAEAAAAAAAAAAAAAAAEAAAAAAAAAAAABFFEAAABAAAAAAAAAAAEAAAAAAAAAAAAAAFAAAABAAAAAAAAAAAEAAAAAAAAAAAAFFEAAAABAAAAAAAAAAAEAAAAAAAAAAAAFFEAAAAAAAAAAAAAAAAEAAAAAAAAAAAABAEEAAABAAAAAAAAAAAEAAAAAAAAAAAAEBFEAAABAAAAAAAAAAAEAAAAAAAAAAAAFAAEAAABAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAEAAAAAAAAAAAAEAFAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAEEAAABAAAAAAAAAAAEAAAAAAAAAAAAEEBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFABEAAAAAAAAAAAAAAAEAAAAAAAAAAAAABAEAAABAAAAAAAAAAAEAAAAAAAAAAAABFEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEBEEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEAEEAAABAAAAAAAAAAAEAAAAAAAAAAAAAABEAAABAAAAAAAAAAAEAAAAAAAAAAAAFBAEAAAAAAAAAAAAAAAEAAAAAAAAAAAAFBAEAAABAAAAAAAAAAAEAAAAAAAAAAAAEBFAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEEBEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEBEEAAABAAAAAAAAAAAEAAAAAAAAAAAAAFEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFEEAAAAAAAAAAAAAAAEAAAAAAAAAAAAEEAEAAAAAAAAAAAAAAAEAAAAAAAAAAAABAFAAAABAAAAAAAAAAAEAAAAAAAAAAAAEEEAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAFAAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFFAAAABAAAAAAAAAAAEAAAAAAAAAAAABFFAAAABAAAAAAAAAAAEAAAAAAAAAAAAAAEAAAABAAAAAAAAAAAEAAAAAAAAAAAAEFBAAAABAAAAAAAAAAAEAAAAAAAAAAAAFEBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFEAEAAABAAAAAAAAAAAEAAAAAAAAAAAAEEFAAAABAAAAAAAAAAAEAAAAAAAAAAAAEABAAAAAAAAAAAAAAAAEAAAAAAAAAAAAEAFAAAABAAAAAAAAAAAEAAAAAAAAAAAABBAEAAABAAAAAAAAAAAEAAAAAAAAAAAABBEEAAABAAAAAAAAAAAEAAAAAAAAAAAAFBFEAAABAAAAAAAAAAAEAAAAAAAAAAAAAEBEAAABAAAAAAAAAAAEAAAAAAAAAAAAFFAEAAABAAAAAAAAAAAEAAAAAAAAAAAABABAAAABAAAAAAAAAAAEAAAAAAAAAAAAFBBEAAABAAAAAAAAAAAEAAAAAAAAFABEAAFBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF57EAEEAAAAAFFAFAAAEAFBBEEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFAAAAAAAAAAAAAAAAAAAAAAAAF56FFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBFEBBBBFBAFAAFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*//Turn huge string into binary...
		StringBuilder binaryString = new StringBuilder();
		for (int i=0; i<hugeString.length(); i+=2) {
			String tempString=Integer.toBinaryString(Integer.parseInt(hugeString.substring(i, i+2),16));
			if (tempString.length()<8) {
				for (int j=tempString.length();j<8;j++);
				binaryString.append("0");
			} 
			binaryString.append(tempString);

		}
		//Then into booleans..
		String finalTrackString=binaryString.toString();
		ArrayList<Boolean> finalTrack = new ArrayList<Boolean>();
		for (int i=0;i<finalTrackString.length();i++) {
			if (finalTrackString.charAt(i)=='1') {
				finalTrack.add(true);
			} else {
				finalTrack.add(false);
			}
		}*/

		Disk disk = new Disk();
		// TODO Auto-generated method stub

		CRCCalculator c = new CRCCalculator();

		FMDecoder decoder = new FMDecoder();
		
		try {

			disk.getSurface()[0]=new Surface(); //surface 0...
			ArrayList<Track> tracks = new ArrayList<Track>();
			StreamReader sr = new StreamReader(new File("literature"));
			/*
			FDCEmulator fdc = new FDCEmulator(sr.getTrack(10, 0));
			fdc.setClockCentre(4000);
			fdc.processTrack();
			Track track = decoder.decode(fdc.getBinaryList());
			//Track track = decoder.decode(finalTrack);
			//System.out.println(track.getSectors().size());

			//for (int i=0; i<track.getSectors().size();i++) {
			//	Sector currentSector = track.getSectors().get(i);
			//}
			*/
			System.out.println("There are "+sr.getNumberOfTracks()+" tracks.");
			for (int i=0; i<sr.getNumberOfTracks(); i++) {
				for (int j=0; j<2; j++) {
				FDCEmulator fdc = new FDCEmulator(sr.getTrack(i, j));
				//System.out.println(fdc.getBinaryString());
				fdc.setClockCentre(4000);
				//fdc.setRpm(360.0);
				fdc.processTrack();
				tracks.add(decoder.decode(fdc.getBinaryList()));
				//System.out.println(fdc.getClockCentre());
				}
			}
			disk.getSurface()[0].setTracks(tracks);
			/*
			FileOutputStream fo = new FileOutputStream("zap.ssd");
			for (int t=0; t<tracks.size();t++) {
				ArrayList<Sector> sectors=tracks.get(t).getSortedSectors();
				for (int s=0; s<sectors.size(); s++) {
					ArrayList<Integer> data=sectors.get(s).getData();
					for (int d=0; d<data.size();d++) {
						fo.write(data.get(d).intValue());
					}
				}
			}
			
			fo.close();*/
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
			//} catch (IOException e) {
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
