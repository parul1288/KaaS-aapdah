package tasks;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterRealTimeStream {

	public static ConfigurationBuilder cb;
	public static DB db;
	private static DBCollection items;
	private static DBCollection crimetweets;

	public static void initMongoDB() throws MongoException {
		try {
			System.out.println("Connecting to Mongo DB..");
			Mongo mongo;
			mongo = new Mongo("54.186.122.31:27017");
			//mongo = new Mongo("127.0.0.1");
			db = mongo.getDB("RealTimeTweets");
			System.out.println("Success..");
		} catch (UnknownHostException ex) {
			System.out.println("MongoDB Connection Error :" + ex.getMessage());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("AFXP3neBClzSBHHwGK6Xg");
		cb.setOAuthConsumerSecret("AjcMZRKKmMqTC1ShdEFGw4uqeRckw9XDOiCfmcu2nGY");
		cb.setOAuthAccessToken("2335580155-Hm6g7QzfLNGSZ4lNSyaBC4FifiLlAPSs5CbxROa");
		cb.setOAuthAccessTokenSecret("VNkdcvq87GbZNCHp8j3WHUrXE1HY9EIs34EeAYQ1euavF");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
		.getInstance();
		initMongoDB();

		BufferedReader brCrimeType;
		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				//System.out.println(arg0);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				//	System.out.println(arg0);

			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatus(Status status) {

				items = db.getCollection("tweets");
				User user = status.getUser();
				String username = status.getUser().getScreenName();
				// System.out.println(username);
				String profileLocation = user.getLocation();
				System.out.println(profileLocation);
				long tweetId = status.getId();
				// System.out.println(tweetId);
				String content = status.getText();
				System.out.println(content + "\n");
				String crimeFileData;
				String cityFileData;
				BufferedReader brCity = null;

				try {
					brCity = new BufferedReader(
							new FileReader(
									"/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb/data/Cities.txt"));

					//					while ((crimeFileData = brCrimeType.readLine()) != null) {
					//						if (status.getText().contains(crimeFileData)) {
					while ((cityFileData = brCity.readLine()) != null) {
						if(status.getUser().getLocation().contains(cityFileData))
						{
							BasicDBObject basicObj = new BasicDBObject();
							basicObj.put("user_name", status.getUser().getScreenName());
							basicObj.put("Location", status.getUser().getLocation());
							basicObj.put("tweet_text", status.getText());

							try {
								items.insert(basicObj);
							} catch (Exception e) {
								System.out.println("MongoDB Connection Error : "
										+ e.getMessage());
							}

						}
					}
					//						}
					//					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

		};
		//		brCrimeType = new BufferedReader(
		//				new FileReader(
		//						"/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb/data/crimetypes.txt"));
		//		String crimeFileData;
		//		String[] keywords = new String[20];
		//		int i=0;
		//		try {
		//			while ((crimeFileData = brCrimeType.readLine()) != null) {
		//				
		//				keywords[i]=crimeFileData.toString();
		//				i++;
		//			}
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		FilterQuery fq = new FilterQuery();
		String keywords[] = { "sanjosepd", "breaking and entering",
				"assault", "sfpd", "robbery", "SanMateoPD", "MountainViewPD",
				"PaloAltoPolice","Homicide","theft","sexual offense","property crime" };

		fq.track(keywords);
		twitterStream.addListener(listener);
		twitterStream.filter(fq);

	}
}