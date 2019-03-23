package review.model;

public class FoodCartRestaurant_TymofiiKryvtsun extends Restaurants_TymofiiKryvtsun{

	protected boolean licensed;

	public FoodCartRestaurant_TymofiiKryvtsun(int restaurantId, String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, boolean licensed) {
		super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.licensed = licensed;
	}
	
	public FoodCartRestaurant_TymofiiKryvtsun(int restaurantId) {
		super(restaurantId);
	}
	
	public FoodCartRestaurant_TymofiiKryvtsun(String name, String description, String menu, String hours,
			boolean active, CuisineType cuisine, String street1, String street2, String city, String state, int zip,
			String companyName, boolean licensed) {
		super(name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
		this.licensed = licensed;
	}

	public boolean isLicensed() {
		return licensed;
	}

	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}
	
	


	
	
	
	

}
