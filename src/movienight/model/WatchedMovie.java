package movienight.model;

public class WatchedMovie {
	
	protected int watchedMovieId;
	protected int movieId;
	protected String userName;
	
	public WatchedMovie(int watchedMovieId, int movieId, String userName) {
		this.watchedMovieId = watchedMovieId;
		this.movieId = movieId;
		this.userName = userName;
	}
	
	public WatchedMovie(int movieId, String userName) {
		this.movieId = movieId;
		this.userName = userName;
	}
	
	public WatchedMovie(int watchedMovieId) {
		this.watchedMovieId = watchedMovieId;
	}
	
	/** Getters and setters. */

	public int getWatchedMovieId() {
		return watchedMovieId;
	}

	public void setWatchedMovieId(int watchedMovieId) {
		this.watchedMovieId = watchedMovieId;
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
