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


public class LikeMoviesDao {
	protected ConnectionManager connectionManager;

	private static LikeMoviesDao instance = null;
	protected LikeMoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static LikeMoviesDao getInstance() {
		if(instance == null) {
			instance = new LikeMoviesDao();
		}
		return instance;
	}

	public LikeMovies create(LikeMovies likeMovie) throws SQLException {
		String insertLikeMovie =
			"INSERT INTO LikeMovies(MovieId,UserName) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLikeMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, likeMovie.getMovie().getMovieId());
			insertStmt.setString(2, likeMovie.getUser().getUserName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int likeMovieId = -1;
			if(resultKey.next()) {
				likeMovieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			likeMovie.setLikeMovieId(likeMovieId);
			return likeMovie;
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
	 * Delete the LikeMovies instance.
	 * This runs a DELETE statement.
	 */
	public LikeMovies delete(LikeMovies likeMovie) throws SQLException {
		String deleteLikeMovie = "DELETE FROM LikeMovies WHERE LikeMovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLikeMovie);
			deleteStmt.setInt(1, likeMovie.getLikeMovieId());
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

	/**
	 * Get the LikeMovies record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single LikeMovies instance.
	 * Note that we use MoviesDao and UsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the LikeMovies, Movies, Users tables and then build each object.
	 */
	public LikeMovies getLikeMovieById(int likeMovieId) throws SQLException {
		String selectLikeMovie =
			"SELECT LikeMovieId,MovieId,UserName " +
			"FROM LikeMovies " +
			"WHERE LikeMovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLikeMovie);
			selectStmt.setInt(1, likeMovieId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			if(results.next()) {
				int resultLikeMovieId = results.getInt("LikeMovieId");
				int movieId = results.getInt("MovieId");
				String userName = results.getString("UserName");
				
				Movies movie = moviesDao.getMovieById(movieId);
				Users user = usersDao.getUserFromUserName(userName);
				LikeMovies likeMovie = new LikeMovies(resultLikeMovieId, movie, user);
				return likeMovie;
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
	 * Get the all the LikeMovies for a user.
	 */
	public List<LikeMovies> getLikeMoviesForUser(Users user) throws SQLException {
		List<LikeMovies> likeMovies = new ArrayList<LikeMovies>();
		String selectLikeMovies =
			"SELECT LikeMovieId,MovieId,UserName " +
			"FROM LikeMovies " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLikeMovies);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			while(results.next()) {
				int likeMovieId = results.getInt("LikeMovieId");
				int movieId = results.getInt("MovieId");
				
				Movies movie = moviesDao.getMovieById(movieId);
				LikeMovies likeMovie = new LikeMovies(likeMovieId, movie, user);
				likeMovies.add(likeMovie);
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
		return likeMovies;
	}
}

