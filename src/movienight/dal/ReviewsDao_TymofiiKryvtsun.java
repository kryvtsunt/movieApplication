package movienight.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import movienight.model.*;


public class ReviewsDao_TymofiiKryvtsun {
	protected ConnectionManager_TymofiiKryvtsun connectionManager;

	private static ReviewsDao_TymofiiKryvtsun instance = null;
	protected ReviewsDao_TymofiiKryvtsun() {
		connectionManager = new ConnectionManager_TymofiiKryvtsun();
	}
	public static ReviewsDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new ReviewsDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public Reviews_TymofiiKryvtsun create(Reviews_TymofiiKryvtsun review) throws SQLException {
		String insertReviews =
			"INSERT INTO Reviews(Created, Content, Rating, UserName, RestaurantId) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReviews,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, review.getCreated());
			insertStmt.setString(2, review.getContent());
			insertStmt.setFloat(3, review.getRating());
			insertStmt.setString(4, review.getUser().getUserName());
			insertStmt.setInt(5, review.getRestaurant().getRestaurantId());
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

	public Reviews_TymofiiKryvtsun delete(Reviews_TymofiiKryvtsun review) throws SQLException {
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

	/**
	 * Get the Reviews record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Reviews instance.
	 * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Reviews, BlogPosts, BlogUsers tables and then build each object.
	 */
	public Reviews_TymofiiKryvtsun getReviewById(int reviewId) throws SQLException {
		String selectReview =
			"SELECT * " +
			"FROM Reviews " +
			"WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reviews_TymofiiKryvtsun Review = new Reviews_TymofiiKryvtsun(resultReviewId, created, content, rating, users, restaurants);
				return Review;
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

	public List<Reviews_TymofiiKryvtsun> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews_TymofiiKryvtsun> reviews = new ArrayList<Reviews_TymofiiKryvtsun>();
		String selectReviews =
			"SELECT * " +
			"FROM Reviews " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reviews_TymofiiKryvtsun review = new Reviews_TymofiiKryvtsun(resultReviewId, created, content, rating, users, restaurants);
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
	
	public List<Reviews_TymofiiKryvtsun> getReviewsByRestaurantId(int restaurantId) throws SQLException {
		List<Reviews_TymofiiKryvtsun> reviews = new ArrayList<Reviews_TymofiiKryvtsun>();
		String selectReviews =
			"SELECT * " +
			"FROM Reviews " + 
			"WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				String userName = results.getString("UserName");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reviews_TymofiiKryvtsun review = new Reviews_TymofiiKryvtsun(resultReviewId, created, content, rating, users, restaurants);
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
