package movienight.model;

import java.util.Date;

public class Review {
	protected int reviewId;
	protected Date created;
	protected String content;
	protected double rating;
	protected String userName;
	protected int movieId;
	
	public Review(int reviewId, Date created, String content, double rating, String userName, int movieId) {
		this.reviewId = reviewId;
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.userName = userName;
		this.movieId = movieId;
	}
	
	public Review(String content, double rating, String userName, int movieId) {
		this.content = content;
		this.rating = rating;
		this.userName = userName;
		this.movieId = movieId;
	}
	
	public Review(int reviewId) {
		this.reviewId = reviewId;
	}
	
	
	/** Getters and setters. */
	
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

}