package movienight.model;

import java.sql.Date;

public class MovieNight {
	
protected int movieNightId;
protected Date date;
protected Movie movie;

public MovieNight(int movieNightId, Date date, Movie movie) {
	super();
	this.movieNightId = movieNightId;
	this.date = date;
	this.movie = movie;
}
public MovieNight(Date date, Movie movie) {
	super();
	this.date = date;
	this.movie = movie;
}
public MovieNight(int movieNightId) {
	super();
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
public Movie getMovie() {
	return movie;
}
public void setMovie(Movie movie) {
	this.movie = movie;
}



}
