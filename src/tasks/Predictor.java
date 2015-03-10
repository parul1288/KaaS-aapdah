package tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.clustering.topdown.postprocessor.ClusterOutputPostProcessorDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class Predictor {
	static String abspath = "C:/Users/parul_000/Downloads/Project/Aapdah/resources/testData/";
	static String abspath1 = "C:/Users/parul_000/Downloads/Project/Aapdah/resources/";
	
	public Map<String, Double> predictCrime(String city) throws Exception {
		List<Vector> finalPoints = new ArrayList<Vector>();
		String datafile = null;
		List<String> crimeTime = new ArrayList<String>();
		int k = 60;
		
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	String time= sdf.format(cal.getTime());
    	
    	if (city.equalsIgnoreCase("Mountain View"))
			datafile = abspath1 + "mountainview.csv";

		else if (city.equalsIgnoreCase("Palo Alto"))
			datafile = abspath1 + "paloalto.csv";
		else if (city.equalsIgnoreCase("Santa Clara"))
			datafile = abspath1 + "santaclara.csv";
		else if (city.equalsIgnoreCase("Sunnyvale"))
			datafile = abspath1 + "sunnyvale.csv";
		else if (city.equalsIgnoreCase("San Francisco"))
			datafile = abspath1 + "sfo.csv";
		else if (city.equalsIgnoreCase("San Mateo"))
			datafile = abspath1 + "sanmateo.csv";
		else if (city.equalsIgnoreCase("menlo park"))
			datafile = abspath1 + "menlopark.csv";
		else if (city.equalsIgnoreCase("san jose")){
			datafile = abspath1 + "sanjose.csv";
}
		
		List<Vector> vectors = getVectorPoints(datafile);
		
		File testData = new File(abspath);
		
		File outputDir = new File(abspath + "/output");
		
		File kmeansDir = new File(abspath + "/output/");
		if (!testData.exists()) {
			testData.mkdir();

		}
		testData = new File(abspath + "/points");
		if (!testData.exists()) {
			testData.mkdir();
		}

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		writePointsToFile(vectors, abspath + "/points/file1", fs, conf);

		Path path = new Path(abspath + "/clusters/part-00000");
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path,
				Text.class, Kluster.class);// );

		for (int i = 0; i < k; i++) {
			Vector vec = vectors.get(i);
			Kluster cluster = new Kluster(vec, i,
					new EuclideanDistanceMeasure());
			writer.append(new Text(cluster.getIdentifier()), cluster);
		}
		writer.close();
		Path output = null;
		if (!kmeansDir.exists()) {

			KMeansDriver.run(conf, new Path(abspath + "/points"), new Path(
					abspath + "/clusters"), new Path(abspath + "/output"),
					new EuclideanDistanceMeasure(), 0.001, 4, true, 0.0, false);
			Path input = new Path(abspath + "/output");
			output = new Path(abspath + "/postoutput");
			ClusterOutputPostProcessorDriver.run(input, output, true);
		}
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
				abspath + "/output/" + Cluster.CLUSTERED_POINTS_DIR
						+ "/part-m-00000"), conf);
		
		Vector newTimeVec = getPoints(time);

		int nearestPos = findNearestCluster(fs, new Path(abspath
				+ "/output/clusters-4-final/part-r-00000"), conf, newTimeVec);

		FileSystem fso = output.getFileSystem(conf);
		SequenceFile.Reader reader2 = new SequenceFile.Reader(fso, new Path(
				abspath + "/postoutput/" + nearestPos + "/part-m-0"), conf);
		LongWritable key2 = new LongWritable();
		VectorWritable value2 = new VectorWritable();

		while (reader2.next(key2, value2)) {
			
			finalPoints.add(value2.get());

		}
		reader2.close();
		for (int t = 0; t < finalPoints.size(); t++) {
			String[] xy = finalPoints.get(t).toString().split(":");

			crimeTime.add(xy[1]);//.replaceAll(".0}", ""));

		}
		int a = 0;
		int b = 0;
		int r = 0;
		int o = 0;
		int t = 0;
		int oc =0;
		
		String[] data = null;
		
		HashMap<String, Integer> crimeMap = new HashMap<String, Integer>();
		HashMap<String, Double> crimeMapProb = new HashMap<String, Double>();

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(datafile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int j = 0; j < finalPoints.size(); j++) {

			try {
				while ((data = csvReader.readNext()) != null) {

					
					if (data[6].substring(0, 2).replaceAll(":", "")
							.contains(crimeTime.get(j).substring(0, 2))) {
						if (data[1].contains("Assault")) {
							crimeMap.put("Assault", ++a);
						} else if (data[1].contains("Theft"))
							crimeMap.put("Theft", ++t);
						else if (data[1].contains("Robbery"))
							crimeMap.put("Robbery", ++r);
						else if (data[1].contains("Breaking & Entering"))
							crimeMap.put("Breaking & Entering", ++b);
						else if (data[1].contains("Other sexual Offenses"))
							crimeMap.put("Other sexual Offenses", ++o);
						else
							crimeMap.put("Other Crimes", ++oc);
						

					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println();
		double probA = 0;
		double probB = 0;
		double probT = 0;
		double probR = 0;
		double probO = 0;
		double probOC=0;
		double ctr = 0;
		
		
		if (crimeMap.get("Assault") != null) {
			probA = ((crimeMap.get("Assault") * 100) / finalPoints.size());
			if (probA > 15) {
				crimeMapProb.put("Assault", probA);
				ctr++;
			}
		}

		if (crimeMap.get("Breaking & Entering") != null) {
			probB = ((crimeMap.get("Breaking & Entering") * 100) / finalPoints
					.size());
			if (probB > 15) {
				crimeMapProb.put("Breaking and Entering", probB);
				ctr++;
			}
		}

		if (crimeMap.get("Theft") != null) {
			probT = ((crimeMap.get("Theft") * 100) / finalPoints.size());
			 if (probT > 15) {
			crimeMapProb.put("Theft", probT);
			ctr++;
			}
		}

		if (crimeMap.get("Robbery") != null) {
			probR = ((crimeMap.get("Robbery") * 100) / finalPoints.size());
			if (probR > 15) {
				crimeMapProb.put("Robbery", probR);
				ctr++;
			}
		}

		if (crimeMap.get("Other sexual Offenses") != null) {
			probO = ((crimeMap.get("Other Sexual offenses") * 100) / finalPoints
					.size());
			if (probO > 15) {
				crimeMapProb.put("Other Sexual offenses", probO);

				ctr++;
			}
		}
		if(crimeMap.get("Other Crimes") != null) {
			probOC = ((crimeMap.get("Other Crimes") * 100) / finalPoints
					.size());
			if (probO > 15) {
				crimeMapProb.put("Petty Crimes", probOC);

				ctr++;
			}
		}
		if (ctr == 0)
			crimeMapProb = null;
		 try {
			 FileUtils.deleteDirectory(new File(abspath + "/clusters/"));
			 FileUtils.deleteDirectory(new File(abspath + "/output/"));
			 FileUtils.deleteDirectory(new File(abspath + "/points/"));
			FileUtils.deleteDirectory(new File(abspath + "/postoutput/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return crimeMapProb;
	}

	private static int findNearestCluster(FileSystem fs, Path path,
			Configuration conf, Vector newTimeVec) throws IOException {

		SequenceFile.Reader reader = null;

		try {
			reader = new SequenceFile.Reader(fs, path, conf);
		} catch (IOException e) {
			e.printStackTrace();
		}

		IntWritable key1 = new IntWritable();
		int nearestPos = 0;
		double smallestDist = 0;
		int clusterPos = -1;
		double dist = 0;
		IntWritable nearestCluster = null;

		ClusterWritable value1 = new ClusterWritable();
		while (reader.next(key1, value1)) {
			
			dist = value1.getValue().getCenter().getDistanceSquared(newTimeVec);
			
			if (smallestDist == 0) {
				smallestDist = dist;
				nearestCluster = key1;
				clusterPos++;
			} else if (dist <= smallestDist) {
				smallestDist = dist;
				nearestCluster = key1;
				clusterPos++;
				nearestPos = clusterPos;
			} else {
				clusterPos++;
			}

		}
		
		reader.close();
		return nearestPos;
	}
	private static Vector getPoints(String time) {
		String d = time.replaceAll(":", "");
		double[] dc = new double[2];
		dc[0] = Double.parseDouble(d);
		Vector vec = new RandomAccessSparseVector(dc.length);
		vec.assign(dc);
		return vec;
	}


	public static void writePointsToFile(List<Vector> points, String fileName,
			FileSystem fs, Configuration conf) throws IOException {
		Path path = new Path(fileName);
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path,
				LongWritable.class, VectorWritable.class);
		long recNum = 0;
		VectorWritable vec = new VectorWritable();
		for (Vector point : points) {
			vec.set(point);
			writer.append(new LongWritable(recNum++), vec);
		}
		writer.close();
	}


	public static List<Vector> getVectorPoints(String inputFile)
			throws Exception {

		List<Vector> points = new ArrayList<Vector>();
		CSVReader csvReader = new CSVReader(new FileReader(inputFile));
		String[] row = null;

		while ((row = csvReader.readNext()) != null)

		{

			String d = row[6].replaceAll(":", "");
			double[] dc = new double[2];
			dc[0] = Double.parseDouble(d);
			Vector vec = new RandomAccessSparseVector(dc.length);
			vec.assign(dc);
			points.add(vec);

		}

		csvReader.close();
		return points;
	}

}