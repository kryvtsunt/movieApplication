package review.dal;

import review.model.*;
import review.model.Restaurants_TymofiiKryvtsun.CuisineType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;



public class RestaurantsDao_TymofiiKryvtsun {
	protected ConnectionManager_TymofiiKryvtsun connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static RestaurantsDao_TymofiiKryvtsun instance = null;
	protected RestaurantsDao_TymofiiKryvtsun() {
		connectionManager = new ConnectionManager_TymofiiKryvtsun();
	}
	public static RestaurantsDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public Restaurants_TymofiiKryvtsun create(Restaurants_TymofiiKryvtsun restaurant) throws SQLException {
		String insertPerson = "INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, restaurant.getName());
			insertStmt.setString(2, restaurant.getDescription());

			insertStmt.setString(3, restaurant.getMenu());
			insertStmt.setString(4, restaurant.getHours());
			insertStmt.setBoolean(5, restaurant.isActive());
			insertStmt.setString(6, restaurant.getCuisine().name());
			insertStmt.setString(7, restaurant.getStreet1());
			insertStmt.setString(8, restaurant.getStreet2());
			insertStmt.setString(9, restaurant.getCity());
			insertStmt.setString(10, restaurant.getState());
			insertStmt.setInt(11, restaurant.getZip());
			insertStmt.setString(12, restaurant.getCompanyName());

			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int restaurantId = -1;
			
			if(resultKey.next()) {
				restaurantId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			restaurant.setRestaurantId(restaurantId);
			return restaurant;

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
	
	public Restaurants_TymofiiKryvtsun getRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT * FROM restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants_TymofiiKryvtsun.CuisineType cuisine = Restaurants_TymofiiKryvtsun.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("companyName");
				Restaurants_TymofiiKryvtsun restaurant = new Restaurants_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
				return restaurant;
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
	
	public List<Restaurants_TymofiiKryvtsun> getRestaurantsByCompanyName(String companyName) throws SQLException {
		List<Restaurants_TymofiiKryvtsun> restaurants = new ArrayList<>();
		String selectRestaurant = "SELECT * FROM restaurants WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants_TymofiiKryvtsun.CuisineType cuisine = Restaurants_TymofiiKryvtsun.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				Restaurants_TymofiiKryvtsun restaurant = new Restaurants_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
				restaurants.add(restaurant);
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
		return restaurants;
	}
	
	public List<Restaurants_TymofiiKryvtsun> getRestaurantsByCuisine(Restaurants_TymofiiKryvtsun.CuisineType cuisine) throws SQLException {
		List<Restaurants_TymofiiKryvtsun> restaurants = new ArrayList<>();
		String selectRestaurant = "SELECT * FROM Restaurants WHERE CuisineType=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setString(1, cuisine.toString());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("companyName");
				Restaurants_TymofiiKryvtsun restaurant = new Restaurants_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName);
				restaurants.add(restaurant);
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
		return restaurants;
	}


	public Restaurants_TymofiiKryvtsun delete(Restaurants_TymofiiKryvtsun restaurant) throws SQLException {
		String deletePerson = "DELETE FROM Restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setInt(1, restaurant.getRestaurantId());
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
