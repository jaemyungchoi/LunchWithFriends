package CSCI201_LunchWithFriends;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Businesses {
	@SerializedName("businesses")
	private List<Business> businesses;

	public Businesses(List<Business> businesses) {
		this.businesses = businesses;
	}

	public List<Business> getBusinesses() {
		return businesses;
	}
}
