package movienight.dal;
import movienight.model.*;

import movienight.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class WatchedMoviesDao {
	protected ConnectionManager connectionManager;

	private static WatchedMoviesDao instance = null;
	protected WatchedMoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static WatchedMoviesDao getInstance() {
		if(instance == null) {
			instance = new WatchedMoviesDao();
		}
		return instance;
	}

	public WatchedMovies create(WatchedMovies watchedMovie) throws SQLException {
		String insertWatchedMovie =
			"INSERT INTO WatchedMovies(movie,user) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertWatchedMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, watchedMovie.getMovie().getMovieId());
			insertStmt.setString(2, watchedMovie.getUser().getUserName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int watchedMovieId = -1;
			if(resultKey.next()) {
				watchedMovieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			watchedMovie.setWatchedMovieId(watchedMovieId);
			return watchedMovie;
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

	/**
	 * Delete the WatchedMovies instance.
	 * This runs a DELETE statement.
	 */
	public WatchedMovies delete(WatchedMovies watchedMovie) throws SQLException {
		String deleteWatchedMovie = "DELETE FROM WatchedMovies WHERE WatchedMovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteWatchedMovie);
			deleteStmt.setInt(1, watchedMovie.getWatchedMovieId());
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

	public WatchedMovies getWatchedMovieById(int watchedMovieId) throws SQLException {
		String selectWatchedMovie =
			"SELECT WatchedMovieId,MovieId,UserId " +
			"FROM WatchedMovies " +
			"WHERE WatchedMovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWatchedMovie);
			selectStmt.setInt(1, watchedMovieId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			if(results.next()) {
				int resultWatchedMovieId = results.getInt("WatchedMovieId");
				int movieId = results.getInt("MovieId");
				String userName = results.getString("UserName");

				Movies movie = moviesDao.getMovieById(movieId);
				Users user = usersDao.getUserFromUserName(userName);
				WatchedMovies watchedMovie = new WatchedMovies(resultWatchedMovieId, movie, user);
				return watchedMovie;
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

	/**
	 * Get the all the WatchedMovies for a user.
	 */
	public List<WatchedMovies> getWatchedMoviesForUser(Users user) throws SQLException {
		List<WatchedMovies> watchedMovies = new ArrayList<WatchedMovies>();
		String selectWatchedMovies =
			"SELECT WatchedMovieId,MovieId,UserName" +
			"FROM WatchedMovies" + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWatchedMovies);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			while(results.next()) {
				int watchedMovieId = results.getInt("WatchedMovieId");
				int movieId = results.getInt("MovieId");
				
				Movies movie = moviesDao.getMovieById(movieId);
				WatchedMovies watchedMovie = new WatchedMovies(watchedMovieId, movie, user);
				watchedMovies.add(watchedMovie);
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
		return watchedMovies;
	}
}

