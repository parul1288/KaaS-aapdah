package tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import tasks.CSVReader;

public class ClusterBasedonAgency {
	public static void main(String args[]) throws IOException {
		String abspath = "/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb/";

		String datafile1 = abspath + "/data/crimedataTest1.csv";
		// String datafile2 = abspath + "/data/PaloAlto.csv";
		// String datafile3 = abspath + "/data/SanMateo.csv";
		// String datafile4 = abspath + "/data/SFO.csv";
		// String datafile5 = abspath + "/data/Sunnyvale.csv";

		String[] row = null;
		CSVReader csvReader = new CSVReader(new FileReader(datafile1));
		FileWriter writerMV = new FileWriter(abspath
				+ "datafiles/mountainview.csv");
		FileWriter writerPa = new FileWriter(abspath + "datafiles/paloalto.csv");
		FileWriter writerMP = new FileWriter(abspath
				+ "datafiles/menlopark.csv");
		FileWriter writerSC = new FileWriter(abspath
				+ "datafiles/santaclara.csv");
		FileWriter writerSM = new FileWriter(abspath + "datafiles/sanmateo.csv");
		FileWriter writerSu = new FileWriter(abspath
				+ "datafiles/sunnyvale.csv");
		FileWriter writerSF = new FileWriter(abspath + "datafiles/sfo.csv");
		FileWriter writerSJ = new FileWriter(abspath + "datafiles/sanjose.csv");

		String header[] = csvReader.readNext();
		while ((row = csvReader.readNext()) != null) {
			System.out.println((row[4]).substring(0, 4));
			if ((row[4].substring(0, 4).contains("Moun"))) {
				writerMV.append(row[0]);
				writerMV.append(",");

				writerMV.append(row[1]);
				writerMV.append(",");

				writerMV.append(row[2]);
				writerMV.append(",");

				writerMV.append(row[3]);
				writerMV.append(",");
				writerMV.append(row[4]);
				writerMV.append(",");

				writerMV.append(row[5]);
				writerMV.append(",");
				writerMV.append(row[6]);

				writerMV.append("\n");
			}

			else if (row[4].substring(0, 4).contains("Palo")
					|| (row[4].substring(0, 4).contains("East"))) {
				writerPa.append(row[0]);
				writerPa.append(",");
				writerPa.append(row[1]);
				writerPa.append(",");
				writerPa.append(row[2]);
				writerPa.append(",");
				writerPa.append(row[3]);
				writerPa.append(",");

				writerPa.append(row[4]);
				writerPa.append(",");

				writerPa.append(row[5]);
				writerPa.append(",");
				writerPa.append(row[6]);
				writerPa.append("\n");

			} else if ((row[4].substring(0, 4).contains("Menl"))) {
				writerMP.append(row[0]);
				writerMP.append(",");
				writerMP.append(row[1]);
				writerMP.append(",");
				writerMP.append(row[2]);
				writerMP.append(",");
				writerMP.append(row[3]);
				writerMP.append(",");

				writerMP.append(row[4]);
				writerMP.append(",");

				writerMP.append(row[5]);
				writerMP.append(",");
				writerMP.append(row[6]);
				writerMP.append("\n");

			} else if ((row[4].substring(0, 4).contains("Sant"))) {
				writerSC.append(row[0]);
				writerSC.append(",");
				writerSC.append(row[1]);
				writerSC.append(",");
				writerSC.append(row[2]);
				writerSC.append(",");
				writerSC.append(row[3]);
				writerSC.append(",");
				writerSC.append(row[4]);
				writerSC.append(",");

				writerSC.append(row[5]);
				writerSC.append(",");
				writerSC.append(row[6]);
				writerSC.append("\n");
			}

			else if ((row[4].substring(0, 5).contains("San M"))) {
				writerSM.append(row[0]);
				writerSM.append(",");
				writerSM.append(row[1]);
				writerSM.append(",");
				writerSM.append(row[2]);
				writerSM.append(",");
				writerSM.append(row[3]);
				writerSM.append(",");
				writerSM.append(row[4]);
				writerSM.append(",");

				writerSM.append(row[5]);
				writerSM.append(",");
				writerSM.append(row[6]);
				writerSM.append("\n");
			}

			else if ((row[4].substring(0, 5).contains("San F"))) {
				writerSF.append(row[0]);
				writerSF.append(",");
				writerSF.append(row[1]);
				writerSF.append(",");
				writerSF.append(row[2]);
				writerSF.append(",");
				writerSF.append(row[3]);
				writerSF.append(",");
				writerSF.append(row[4]);
				writerSF.append(",");

				writerSF.append(row[5]);
				writerSF.append(",");
				writerSF.append(row[6]);
				writerSF.append("\n");
			}

			else if ((row[4].substring(0, 4).contains("Sunn"))) {
				writerSu.append(row[0]);
				writerSu.append(",");
				writerSu.append(row[1]);
				writerSu.append(",");
				writerSu.append(row[2]);
				writerSu.append(",");
				writerSu.append(row[3]);
				writerSu.append(",");
				writerSu.append(row[4]);
				writerSu.append(",");

				writerSu.append(row[5]);
				writerSu.append(",");
				writerSu.append(row[6]);
				writerSu.append("\n");
			}
			else if ((row[4].substring(0, 5).contains("San J"))) {
				writerSJ.append(row[0]);
				writerSJ.append(",");
				writerSJ.append(row[1]);
				writerSJ.append(",");
				writerSJ.append(row[2]);
				writerSJ.append(",");
				writerSJ.append(row[3]);
				writerSJ.append(",");
				writerSJ.append(row[4]);
				writerSJ.append(",");

				writerSJ.append(row[5]);
				writerSJ.append(",");
				writerSJ.append(row[6]);
				writerSJ.append("\n");
			}

		}
		writerMV.close();
		writerPa.close();
		writerMP.close();
		writerSC.close();
		writerSM.close();
		writerSF.close();
		writerSu.close();
		csvReader.close();

	}

}
