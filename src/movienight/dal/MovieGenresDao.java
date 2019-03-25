package movienight.dal;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movienight.model.*;


public class MovieGenresDao {
	protected ConnectionManager connectionManager;
	
	private static MovieGenresDao instance = null;
	protected MovieGenresDao() {
		connectionManager = new ConnectionManager();
	}
	public static MovieGenresDao getInstance() {
		if(instance == null) {
			instance = new MovieGenresDao();
		}
		return instance;
	}

	public MovieGenres create(MovieGenres movieGenre) throws SQLException {
		String insertMovieGenre = "INSERT INTO MovieGenres(MovieId, GenreId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			// to create MovieGenre, a valid movie-person required;
			Movies movie = MoviesDao.getInstance().getMovieById(movieGenre.getMovie().getMovieId());
			Genres genre = GenresDao.getInstance().getGenreById(movieGenre.getGenre().getGenreId());
			if (movie == null | genre == null) {
				return null;
			}
			insertStmt = connection.prepareStatement(insertMovieGenre,  Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, movieGenre.getMovie().getMovieId());
			insertStmt.setInt(2, movieGenre.getGenre().getGenreId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  MovieGenreId = -1;
			if(resultKey.next()) {
				MovieGenreId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			movieGenre.setMovieGenreId(MovieGenreId);
			return movieGenre;
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

	public MovieGenres delete(MovieGenres movieGenre) throws SQLException {
		String deleteMovieGenre = "DELETE FROM MovieGenres WHERE MovieGenreId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMovieGenre);
			deleteStmt.setInt(1, movieGenre.getMovieGenreId());
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


	public MovieGenres getMovieGenreById(int movieGenreId) throws SQLException {
		String selectMovieGenre = "SELECT * FROM MovieGenres WHERE MovieGenreId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovieGenre);
			selectStmt.setInt(1, movieGenreId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int mid = results.getInt("MovieId");
				int gid = results.getInt("GenreId");
				Movies movie = MoviesDao.getInstance().getMovieById(mid);
				Genres genre = GenresDao.getInstance().getGenreById(gid);
				MovieGenres MovieGenre = new MovieGenres(movieGenreId, genre, movie);
				return MovieGenre;
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

	
}
