package movienight.model;

public class Movie {
	
	protected int movieId;
	protected String title;
	protected int releaseYear;
	protected int runtime;
	
	public Movie(int movieId, String title, int releaseYear, int runtime) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.releaseYear = releaseYear;
		this.runtime = runtime;
	}
	public Movie(String title, int releaseYear, int runtime) {
		super();
		this.title = title;
		this.releaseYear = releaseYear;
		this.runtime = runtime;
	}
	public Movie(int movieId) {
		super();
		this.movieId = movieId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	

}
