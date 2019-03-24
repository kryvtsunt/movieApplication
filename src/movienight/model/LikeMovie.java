package movienight.model;

public class LikeMovie {
	protected int likeMovieId;
	protected int movieId;
	protected String userName;
	
	public LikeMovie(int likeMovieId, int movieId, String userName) {
		this.likeMovieId = likeMovieId;
		this.movieId = movieId;
		this.userName = userName;
	}
	

	public LikeMovie(int movieId, String userName) {
		this.movieId = movieId;
		this.userName = userName;
	}
	
	public LikeMovie(int likeMovieId) {
		this.likeMovieId = likeMovieId;
	}
	
	
	/** Getters and setters. */
	
	public int getLikeMovieId() {
		return likeMovieId;
	}

	public void setLikeMovieId(int likeMovieId) {
		this.likeMovieId = likeMovieId;
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
