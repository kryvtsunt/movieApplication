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


public class MoviesDao {
	protected ConnectionManager connectionManager;
	
	private static MoviesDao instance = null;
	protected MoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static MoviesDao getInstance() {
		if(instance == null) {
			instance = new MoviesDao();
		}
		return instance;
	}

	public Movies create(Movies movie) throws SQLException {
		String insertMovie = "INSERT INTO Movies(Title, ReleaseYear, Runtime) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, movie.getTitle());
			insertStmt.setInt(2, movie.getReleaseYear());
			insertStmt.setInt(3, movie.getRuntime());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  movieId = -1;
			if(resultKey.next()) {
				movieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			movie.setMovieId(movieId);
			return movie;
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

	public Movies getMovieById(int movieId) throws SQLException {
		String selectMovie = "SELECT * FROM Movies WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovie);
			selectStmt.setInt(1, movieId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String title = results.getString("Title");
				int releaseYear = results.getInt("ReleaseYear");
				int runtime = results.getInt("Runtime");
				Movies movie = new Movies(movieId, title, releaseYear, runtime);
				return movie;
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
	
	
	public Movies delete(Movies movie) throws SQLException {
		String deleteMovie = "DELETE FROM Movies WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMovie);
			deleteStmt.setInt(1, movie.getMovieId());
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
	
	public Movies updateTitle(Movies movie, String title) throws SQLException {
		String updateMovie = "UPDATE Movies SET Title=? WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateMovie);
			updateStmt.setString(1, title);
			updateStmt.setInt(2, movie.getMovieId());
			updateStmt.executeUpdate();
			movie.setTitle(title);
			return movie;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public List<Movies> getMoviesByTitle(String title) throws SQLException {
		List<Movies> movies = new ArrayList<Movies>();
		String selectMovies =
			"SELECT * FROM Movies WHERE Title LIKE ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovies);
			selectStmt.setString(1, "%" + title + "%");
			results = selectStmt.executeQuery();
			while(results.next()) {
				int movieId = results.getInt("MovieId");
				String resultTitle = results.getString("Title");
				int releaseYear = results.getInt("ReleaseYear");
				int runtime = results.getInt("Runtime");
				Movies movie = new Movies(movieId, resultTitle, releaseYear, runtime);
				movies.add(movie);
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
		return movies;
	}


}
