package movienight.model;

public class MovieGenres {
	protected int movieGenreId;
	protected Genres genre;
	protected Movies movie;
	public MovieGenres(int movieGenreId, Genres genre, Movies movie) {
		super();
		this.movieGenreId = movieGenreId;
		this.genre = genre;
		this.movie = movie;
	}
	public MovieGenres(Genres genre, Movies movie) {
		super();
		this.genre = genre;
		this.movie = movie;
	}
	public MovieGenres(int movieGenreId) {
		super();
		this.movieGenreId = movieGenreId;
	}
	public int getMovieGenreId() {
		return movieGenreId;
	}
	public void setMovieGenreId(int movieGenreId) {
		this.movieGenreId = movieGenreId;
	}
	public Genres getGenre() {
		return genre;
	}
	public void setGenre(Genres genre) {
		this.genre = genre;
	}
	public Movies getMovie() {
		return movie;
	}
	public void setMovie(Movies movie) {
		this.movie = movie;
	}
	
	

}
