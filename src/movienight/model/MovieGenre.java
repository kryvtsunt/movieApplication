package movienight.model;

public class MovieGenre {
	protected int movieGenreId;
	protected Genre genre;
	protected Movie movie;
	public MovieGenre(int movieGenreId, Genre genre, Movie movie) {
		super();
		this.movieGenreId = movieGenreId;
		this.genre = genre;
		this.movie = movie;
	}
	public MovieGenre(Genre genre, Movie movie) {
		super();
		this.genre = genre;
		this.movie = movie;
	}
	public MovieGenre(int movieGenreId) {
		super();
		this.movieGenreId = movieGenreId;
	}
	public int getMovieGenreId() {
		return movieGenreId;
	}
	public void setMovieGenreId(int movieGenreId) {
		this.movieGenreId = movieGenreId;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	

}
