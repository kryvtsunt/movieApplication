package movienight.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import movienight.model.*;


public class MovieNightsDao {
	protected ConnectionManager connectionManager;
	
	private static MovieNightsDao instance = null;
	protected MovieNightsDao() {
		connectionManager = new ConnectionManager();
	}
	public static MovieNightsDao getInstance() {
		if(instance == null) {
			instance = new MovieNightsDao();
		}
		return instance;
	}

	public MovieNights create(MovieNights movieNight) throws SQLException {
		String insertMovieNight = "INSERT INTO MovieNight(Date, MovieId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			
			// to create a MovieNigh, valid movie is required
			Movies movie = MoviesDao.getInstance().getMovieById(movieNight.getMovie().getMovieId());
			if (movie == null) {
				return null;
			}
			insertStmt = connection.prepareStatement(insertMovieNight, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setDate(1, movieNight.getDate());
			insertStmt.setInt(2, movieNight.getMovie().getMovieId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  movieNightId = -1;
			if(resultKey.next()) {
				movieNightId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			movieNight.setMovieNightId(movieNightId);
			return movieNight;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}



	public MovieNights getMovieNightById(int movieNightId) throws SQLException {
		String selectMovieNight = "SELECT * FROM MovieNight WHERE MovieNightId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovieNight);
			selectStmt.setInt(1, movieNightId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				Date date = results.getDate("Date");
				int movieId = results.getInt("MovieId");
				Movies movie = MoviesDao.getInstance().getMovieById(movieId);
				MovieNights movienight = new MovieNights(movieNightId, date, movie);
				return movienight;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	
	public MovieNights delete(MovieNights movieNight) throws SQLException {
		String deleteMovieNight = "DELETE FROM MovieNight WHERE MovieNightId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMovieNight);
			deleteStmt.setInt(1, movieNight.getMovieNightId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	

}
