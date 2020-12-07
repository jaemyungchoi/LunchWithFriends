package CSCI201_LunchWithFriends;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YelpAPIParser {
	public static List<Business> getBusiness(String term, Location location) {
		List<Business> busList = new ArrayList<Business>();
		try {
		  for(int i = 1;i < 5; i ++) {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + term + "&latitude="
					+ location.getLatitude() + "&longitude=" + location.getLongitude() + "&limit=50&offset=" + i*50;
			String apiKey = "BEARER Q33lviU83bjK-P3T6lvt8UU2ozR4DEk-XmGJzEQbAOmGINEJbXXcDEUJW0V88HY"
					+ "UQcVaA8KgIBM4AH5tbJ0aY0VjDko0cDdPoWK1cJtKFGpFuM8wvyz5eyBGPySxX3Yx";
			Request request = new Request.Builder().url(builder).method("GET", null).addHeader("Authorization", apiKey)
					.build();
			Response response = client.newCall(request).execute();

			Gson gson = new Gson();
			Businesses businesses = gson.fromJson((response.body()).string(), new TypeToken<Businesses>() {
			}.getType());
			List<Business> testBus = businesses.getBusinesses();
			for(Business b: testBus) {
				busList.add(b);
			}
		  }
		  return busList;

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
}