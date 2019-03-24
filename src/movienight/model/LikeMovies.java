package movienight.model;

public class LikeMovies {

	protected int likeMovieId;
	protected Movies movie;
	protected Users user;
	
	public LikeMovies(int likeMovieId, Movies movie, Users user) {
		this.likeMovieId = likeMovieId;
		this.movie = movie;
		this.user = user;
	}
	
	// This constructor can be used for creating new records, where likeMovieId may not be
	// assigned yet since it is auto-generated by MySQL.
	public LikeMovies(Movies movie, Users user) {
		this.movie = movie;
		this.user = user;
	}
	
	public LikeMovies(int likeMovieId) {
		this.likeMovieId = likeMovieId;
	}
	
	
	/** Getters and setters. */
	
	public int getLikeMovieId() {
		return likeMovieId;
	}

	public void setLikeMovieId(int likeMovieId) {
		this.likeMovieId = likeMovieId;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
