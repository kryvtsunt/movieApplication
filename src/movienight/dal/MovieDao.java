package movienight.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import movienight.model.*;


public class MovieDao {
	protected ConnectionManager connectionManager;
	
	private static MovieDao instance = null;
	protected MovieDao() {
		connectionManager = new ConnectionManager();
	}
	public static MovieDao getInstance() {
		if(instance == null) {
			instance = new MovieDao();
		}
		return instance;
	}

	public Movie create(Movie movie) throws SQLException {
		String insertMovie = "INSERT INTO Movie(Title, ReleaseYear, Runtime) VALUES(?,?,?);";
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



	public Movie getMovieById(int movieId) throws SQLException {
		String selectMovie = "SELECT * FROM Movie WHERE MovieId=?;";
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
				int releaseYear = results.getInt("RelaseYear");
				int runtime = results.getInt("Runtime");
				Movie movie = new Movie(movieId, title, releaseYear, runtime);
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
	
	
	public Movie delete(Movie movie) throws SQLException {
		String deleteMovie = "DELETE FROM Movie WHERE MovieId=?;";
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
	
	public Movie updateTitle(Movie movie, String title) throws SQLException {
		String updateMovie = "UPDATE Movie SET Title=? WHERE MovieId=?;";
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


}
