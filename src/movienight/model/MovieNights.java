package movienight.model;

import java.util.Date;

public class MovieNights {
	protected int movieNightId;
	protected Date date;
	protected Movies movie;
	
	public MovieNights(int movieNightId, Date date, Movies movie) {
		this.movieNightId = movieNightId;
		this.date = date;
		this.movie = movie;
	}
	
	public MovieNights(Date date, Movies movie) {
		this.date = date;
		this.movie = movie;
	}
	public MovieNights(int movieNightId) {
		this.movieNightId = movieNightId;
	}

	public int getMovieNightId() {
		return movieNightId;
	}

	public void setMovieNightId(int movieNightId) {
		this.movieNightId = movieNightId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}



}
