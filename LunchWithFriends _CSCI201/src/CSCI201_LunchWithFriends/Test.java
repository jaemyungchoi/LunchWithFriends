package CSCI201_LunchWithFriends;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public List<Business> getB(List<Business> businesses) {
		return businesses;
	}
	
	public static void main(String[] args) {
		Location loc = new Location(37.7, -122.4);

		List<Business> businesses = YelpAPIParser.getBusiness(" ", loc);
		for (Business bus : businesses) {
			System.out.println("Name: " + bus.getName());
			System.out.println("Rating: " + bus.getRating());
			System.out.println("Price: " + bus.getPrice());
			System.out.println("Closed: " + bus.getClosed());
			System.out.println("URL: " + bus.getUrl());
			System.out.println("Coordinates: " + bus.getLatitude() + ", " + bus.getLongitude());
			System.out.println("Address: " + bus.getAddress());
			System.out.println();
		}
	}
}
