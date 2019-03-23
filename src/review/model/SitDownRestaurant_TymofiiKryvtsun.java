package review.model;

public class SitDownRestaurant_TymofiiKryvtsun extends Restaurants_TymofiiKryvtsun{

	protected int capacity;

	public SitDownRestaurant_TymofiiKryvtsun(int restaurantId, String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, int capacity) {
		super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.capacity = capacity;
	}
	
	public SitDownRestaurant_TymofiiKryvtsun(int restaurantId) {
		super(restaurantId);
	}
	
	
	public SitDownRestaurant_TymofiiKryvtsun(String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, int capacity) {
		super(name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.capacity = capacity;
	}
	
	

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
	
	

}
