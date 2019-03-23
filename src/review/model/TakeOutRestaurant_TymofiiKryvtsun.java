package review.model;

public class TakeOutRestaurant_TymofiiKryvtsun extends Restaurants_TymofiiKryvtsun{

	protected int maxWaitTime;

	public TakeOutRestaurant_TymofiiKryvtsun(int restaurantId, String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, int maxWaitTime) {
		super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.maxWaitTime = maxWaitTime;
	}
	
	public TakeOutRestaurant_TymofiiKryvtsun(int restaurantId) {
		super(restaurantId);
	}
	
	public TakeOutRestaurant_TymofiiKryvtsun(String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, int maxWaitTime) {
		super(name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.maxWaitTime = maxWaitTime;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}


	

}
