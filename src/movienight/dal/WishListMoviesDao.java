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


public class WishListMoviesDao {
	protected ConnectionManager connectionManager;

	private static WishListMoviesDao instance = null;
	protected WishListMoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static WishListMoviesDao getInstance() {
		if(instance == null) {
			instance = new WishListMoviesDao();
		}
		return instance;
	}

	public WishListMovies create(WishListMovies wishListMovie) throws SQLException {
		String insertWishListMovie =
			"INSERT INTO WishListMovies(movie,user) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertWishListMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, wishListMovie.getMovie().getMovieId());
			insertStmt.setString(2, wishListMovie.getUser().getUserName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int wishListMovieId = -1;
			if(resultKey.next()) {
				wishListMovieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			wishListMovie.setWishListMovieId(wishListMovieId);
			return wishListMovie;
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
	 * Delete the WishListMovies instance.
	 * This runs a DELETE statement.
	 */
	public WishListMovies delete(WishListMovies wishListMovie) throws SQLException {
		String deleteWishListMovie = "DELETE FROM WishListMovies WHERE wishListMovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteWishListMovie);
			deleteStmt.setInt(1, wishListMovie.getWishListMovieId());
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

	public WishListMovies getWishListMovieById(int wishListMovieId) throws SQLException {
		String selectWishListMovie =
			"SELECT WishListMovieId,MovieId,UserName" +
			"FROM WishListMovies " +
			"WHERE WishListMovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWishListMovie);
			selectStmt.setInt(1, wishListMovieId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			if(results.next()) {
				int resultWishListMovieId = results.getInt("WishListMovieId");
				int movieId = results.getInt("MovieId");
				String userName = results.getString("UserName");
				
				Movies movie = moviesDao.getMovieById(movieId);
				Users user = usersDao.getUserFromUserName(userName);
				WishListMovies wishListMovie = new WishListMovies(resultWishListMovieId, movie, user);
				return wishListMovie;
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
	 * Get the all the WishListMovies for a user.
	 */
	public List<WishListMovies> getWishListMoviesForUser(Users user) throws SQLException {
		List<WishListMovies> wishListMovies = new ArrayList<WishListMovies>();
		String selectWishListMovies =
			"SELECT WishListMovieId,MovieId,UserName" +
			"FROM WishListMovies" + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWishListMovies);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			while(results.next()) {
				int wishListMovieId = results.getInt("WishListMovieId");
				int movieId = results.getInt("MovieId");
				
				Movies movie = moviesDao.getMovieById(movieId);
				WishListMovies wishListMovie = new WishListMovies(wishListMovieId, movie, user);
				wishListMovies.add(wishListMovie);
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
		return wishListMovies;
	}
}
