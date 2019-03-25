package movienight.dal;

import movienight.model.*;
import movienight.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Data access object (DAO) class to interact with the underlying Review table in your
 * MySQL instance. 
 */

public class ReviewsDao {

	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	/**
	 * Save the Reviews instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Reviews create(Reviews review) throws SQLException {
		String insertReview = "INSERT INTO Reviews(Created,Content,Rating,UserName,MovieId) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, new Timestamp(review.getCreated().getTime()));
			insertStmt.setString(2, review.getContent());
			insertStmt.setDouble(3, review.getRating());
			insertStmt.setString(4, review.getUser().getUserName());
			insertStmt.setInt(5, review.getMovie().getMovieId());
		
			insertStmt.executeUpdate();
			
			
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
	
			return review;
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
	 * Update the content of the Reviews instance.
	 * This runs a UPDATE statement.
	 */
	public Reviews updateContent(Reviews review, String newContent) throws SQLException {
		String updateReview = "UPDATE Reviews SET Content=?,Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, newContent);
			// Sets the Created timestamp to the current time.
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, review.getReviewId());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			review.setContent(newContent);
			review.setCreated(newCreatedTimestamp);
			return review;
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

	
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
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
	
	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview = "SELECT ReviewId,Created,Content,Rating,UserName,MovieId FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
	
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			if(results.next()) {

				int resultReviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				String userName = results.getString("UserName");
				int movieId = results.getInt("MovieId");
				
				Users user = usersDao.getUserFromUserName(userName);
				Movies movie = moviesDao.getMovieById(movieId);
			
				Reviews review = new Reviews(resultReviewId, created, content, rating, user, movie);
				return review;
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
	
	public List<Reviews> getReviewsForUser(Users user) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews = "SELECT ReviewId,Created,Content,Rating,UserName,MovieId FROM Reviews WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, user.getUserName());
	
			results = selectStmt.executeQuery();
			
			MoviesDao moviesDao = MoviesDao.getInstance();
			
			while(results.next()) {

				int reviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				int movieId = results.getInt("MovieId");
				
				Movies movie = moviesDao.getMovieById(movieId);
			
				Reviews review = new Reviews(reviewId, created, content, rating, user, movie);
				reviews.add(review);
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
		return reviews;
	}
	
	public List<Reviews> getReviewsByMovieId(Movies movie) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews = "SELECT ReviewId,Created,Content,Rating,UserName,MovieId FROM Reviews WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1,movie.getMovieId());
	
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {

				int reviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				String userName = results.getString("UserName");
				
				Users user = usersDao.getUserFromUserName(userName);
			
				Reviews review = new Reviews(reviewId, created, content, rating, user, movie);
				reviews.add(review);
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
		return reviews;
	}

}
