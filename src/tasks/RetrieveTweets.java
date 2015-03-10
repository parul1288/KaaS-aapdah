package tasks;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class RetrieveTweets {
	public static DB db;
	public static DBCollection collection;

	public static void initMongoDB() throws MongoException {
		try {
			System.out.println("Connecting to Mongo DB..");
			Mongo mongo;
			mongo = new Mongo("54.186.122.31:27017");
			// mongo = new Mongo("127.0.0.1");
			db = mongo.getDB("RealTimeTweets");
			System.out.println("Success..");
		} catch (UnknownHostException ex) {
			System.out.println("MongoDB Connection Error :" + ex.getMessage());
		}
	}

	public List<String> retrieveRealTimeTweets(){ 
		List<String> list = null;
		initMongoDB();

		collection = db.getCollection("tweets");
		BasicDBObject breakingTweets = new BasicDBObject();
		//breakingTweets.put("tweet_text", java.util.regex.Pattern.compile("#BREAKING"));
		DBCursor cursor = collection.find().sort(new BasicDBObject("_id",-1)).limit(8);
		DBObject cur1;

		list = new LinkedList<String>();
		//		while (true) {
		while(cursor.hasNext())
		{
			cur1 = cursor.next();
			String tweet = cur1.get("tweet_text").toString();
			//System.out.println("Tweet: " +tweet);
			if (!tweet.startsWith("RT")) {
				//					System.out.println("Reported by: " + cur1.get("user_name"));
				//
				//					System.out.println(tweet);
				//					System.out.println();

				list.add(tweet);					
			} 
		}
		//		}

		return list;

	}
}