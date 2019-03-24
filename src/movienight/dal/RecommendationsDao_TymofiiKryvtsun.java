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


public class RecommendationsDao_TymofiiKryvtsun {
	protected ConnectionManager connectionManager;

	private static RecommendationsDao_TymofiiKryvtsun instance = null;
	protected RecommendationsDao_TymofiiKryvtsun() {
		connectionManager = new ConnectionManager();
	}
	public static RecommendationsDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new RecommendationsDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public Recommendations_TymofiiKryvtsun create(Recommendations_TymofiiKryvtsun recommendation) throws SQLException {
		String insertRecommendations =
			"INSERT INTO Recommendations(UserName, RestaurantId) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendations,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, recommendation.getUser().getUserName());
			insertStmt.setInt(2, recommendation.getRestaurant().getRestaurantId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if(resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(recommendationId);
			return recommendation;
			
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

	public Recommendations_TymofiiKryvtsun delete(Recommendations_TymofiiKryvtsun recommendation) throws SQLException {
		String deleterecommendation = "DELETE FROM Recommendations WHERE recommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleterecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
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
	 * Get the Recommendations record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Recommendations instance.
	 * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Recommendations, BlogPosts, BlogUsers tables and then build each object.
	 */
	public Recommendations_TymofiiKryvtsun getrecommendationById(int recommendationId) throws SQLException {
		String selectrecommendation =
			"SELECT * " +
			"FROM Recommendations " +
			"WHERE recommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectrecommendation);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			MovieDao usersDao = MovieDao.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();
			if(results.next()) {
				int resultrecommendationId = results.getInt("recommendationId");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Recommendations_TymofiiKryvtsun recommendation = new Recommendations_TymofiiKryvtsun(resultrecommendationId, users, restaurants);
				return recommendation;
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

	public List<Recommendations_TymofiiKryvtsun> getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations_TymofiiKryvtsun> Recommendations = new ArrayList<Recommendations_TymofiiKryvtsun>();
		String selectRecommendations =
			"SELECT * " +
			"FROM Recommendations " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			MovieDao usersDao = MovieDao.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultrecommendationId = results.getInt("recommendationId");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Recommendations_TymofiiKryvtsun recommendation = new Recommendations_TymofiiKryvtsun(resultrecommendationId, users, restaurants);
				Recommendations.add(recommendation);
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
		return Recommendations;
	}
	
	public List<Recommendations_TymofiiKryvtsun> getRecommendationsByRestaurantId(int restaurantId) throws SQLException {
		List<Recommendations_TymofiiKryvtsun> Recommendations = new ArrayList<Recommendations_TymofiiKryvtsun>();
		String selectRecommendations =
			"SELECT * " +
			"FROM Recommendations " + 
			"WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			MovieDao usersDao = MovieDao.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultrecommendationId = results.getInt("recommendationId");
				String userName = results.getString("UserName");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Recommendations_TymofiiKryvtsun recommendation = new Recommendations_TymofiiKryvtsun(resultrecommendationId, users, restaurants);
				Recommendations.add(recommendation);
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
		return Recommendations;
	}
}
