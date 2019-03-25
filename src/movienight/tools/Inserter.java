package movienight.tools;

import java.sql.SQLException;
import java.sql.Timestamp;

import movienight.dal.*;
import movienight.model.*;

import java.sql.Date;


public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances
		MoviesDao moviesDao = MoviesDao.getInstance();



		// INSERT
		Date date =  new Date(System.currentTimeMillis());
		Timestamp time = new Timestamp(date.getTime());
		Movies movie = new Movies("Title A", 1996, 120);
		moviesDao.create(movie);

		

		// READ
		Movies m1 = moviesDao.getMoviesByTitle("Title A").get(0);
		System.out.format("Reading movie: id:%s, title:%s, releaseYear:%s, runtime:%s\n",
			m1.getMovieId(), m1.getTitle(), m1.getReleaseYear(), m1.getRuntime());
		

		//UPDATE
		moviesDao.updateTitle(m1, "Title B");
		
		
		//DELETE
		moviesDao.delete(m1);
	}
}
