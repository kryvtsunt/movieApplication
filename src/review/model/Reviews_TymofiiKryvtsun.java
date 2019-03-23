package review.model;

import java.sql.Timestamp;

public class Reviews_TymofiiKryvtsun {
	
	protected int reviewId;
	protected Timestamp created;
	protected String content;
	protected float rating; 
	protected Users_TymofiiKryvtsun user;
	protected Restaurants_TymofiiKryvtsun restaurant;
	
	public Reviews_TymofiiKryvtsun(int reviewId, Timestamp created, String content, float rating, Users_TymofiiKryvtsun user, Restaurants_TymofiiKryvtsun restaurant) {
		this.reviewId = reviewId;
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public Reviews_TymofiiKryvtsun(int reviewId) {
		this.reviewId = reviewId;
	}
	
	public Reviews_TymofiiKryvtsun(Timestamp created, String content, float rating, Users_TymofiiKryvtsun user, Restaurants_TymofiiKryvtsun restaurant) {
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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
