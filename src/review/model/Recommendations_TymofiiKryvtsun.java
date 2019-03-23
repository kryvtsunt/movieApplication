package review.model;


public class Recommendations_TymofiiKryvtsun {
	
	protected int recommendationId;
	protected Users_TymofiiKryvtsun user;
	protected Restaurants_TymofiiKryvtsun restaurant;
	
	public Recommendations_TymofiiKryvtsun(int recommendationId, Users_TymofiiKryvtsun user, Restaurants_TymofiiKryvtsun restaurant) {
		this.recommendationId = recommendationId;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public Recommendations_TymofiiKryvtsun(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	
	public Recommendations_TymofiiKryvtsun(Users_TymofiiKryvtsun user, Restaurants_TymofiiKryvtsun restaurant) {
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	public Users_TymofiiKryvtsun getUser() {
		return user;
	}
	public void setUser(Users_TymofiiKryvtsun user) {
		this.user = user;
	}
	public Restaurants_TymofiiKryvtsun getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurants_TymofiiKryvtsun restaurant) {
		this.restaurant = restaurant;
	}
	
	
	
	
}
