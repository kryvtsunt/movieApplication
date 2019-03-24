package movienight.model;

import java.util.Date;

public class MovieNights {
	protected int movieNightId;
	protected Date date;
	protected Movies movie;
	
	public MovieNights(int movieNightId, Date date, Movies movie) {
		super();
		this.movieNightId = movieNightId;
		this.date = date;
		this.movie = movie;
	}
	
	public MovieNights(Date date, Movies movie) {
		super();
		this.date = date;
		this.movie = movie;
	}
	public MovieNights(int movieNightId) {
		super();
		this.movieNightId = movieNightId;
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

	public void setMovieId(Movies movie) {
		this.movie = movie;
	}

}
