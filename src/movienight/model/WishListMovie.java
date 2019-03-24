package movienight.model;

public class WishListMovie {
	protected int wishListMovieId;
	protected int movieId;
	protected String userName;
	
	public WishListMovie(int wishListMovieId, int movieId, String userName) {
		this.wishListMovieId = wishListMovieId;
		this.movieId = movieId;
		this.userName = userName;
	}
	
	public WishListMovie(int movieId, String userName) {
		this.movieId = movieId;
		this.userName = userName;
	}
	
	public WishListMovie(int wishListMovieId) {
		this.wishListMovieId = wishListMovieId;
	}
	
	/** Getters and setters. */
	public int getWishListMovieId() {
		return wishListMovieId;
	}

	public void setWishListMovieId(int wishListMovieId) {
		this.wishListMovieId = wishListMovieId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
