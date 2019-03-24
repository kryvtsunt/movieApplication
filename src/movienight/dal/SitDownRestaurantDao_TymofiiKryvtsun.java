package movienight.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movienight.model.*;



public class SitDownRestaurantDao_TymofiiKryvtsun extends RestaurantsDao_TymofiiKryvtsun {
	
	// Single pattern: instantiation is limited to one object.
	private static SitDownRestaurantDao_TymofiiKryvtsun instance = null;
	protected SitDownRestaurantDao_TymofiiKryvtsun() {
		super();
	}
	public static SitDownRestaurantDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new SitDownRestaurantDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public SitDownRestaurant_TymofiiKryvtsun create(SitDownRestaurant_TymofiiKryvtsun restaurant) throws SQLException {
		Restaurants_TymofiiKryvtsun r = create(new Restaurants_TymofiiKryvtsun(restaurant.getName(), restaurant.getDescription(), restaurant.getMenu(), restaurant.getHours(), restaurant.isActive(), restaurant.getCuisine(), restaurant.getStreet1(), restaurant.getStreet2(), restaurant.getCity(), restaurant.getState(), restaurant.getZip(), restaurant.getCompanyName()));
		String insertPerson = "INSERT INTO SitDownRestaurant(RestaurantId, Capacity) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson);
			insertStmt.setInt(1, r.getRestaurantId());
			insertStmt.setInt(2, restaurant.getCapacity());

			insertStmt.executeUpdate();		
			restaurant.setRestaurantId(r.getRestaurantId());
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
		}
	}
	
	public SitDownRestaurant_TymofiiKryvtsun getSitDownRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT SitDownRestaurant.RestaurantId AS RestaurantId,"
				+ "Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Capacity "
				+ "FROM SitDownRestaurant INNER JOIN Restaurants ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId "
				+ "WHERE SitDownRestaurant.RestaurantId=?;";
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
				String companyName = results.getString("CompanyName");
				int capacity = results.getInt("Capacity");

				SitDownRestaurant_TymofiiKryvtsun restaurant = new SitDownRestaurant_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName, capacity);
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
	
	public List<SitDownRestaurant_TymofiiKryvtsun> getSitDownRestaurantsByCompanyName(String companyName) throws SQLException {
		List<SitDownRestaurant_TymofiiKryvtsun> restaurants = new ArrayList<>();
		String selectRestaurant = "SELECT SitDownRestaurant.RestaurantId AS RestaurantId,"
				+ "Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Capacity "
				+ "FROM SitDownRestaurant INNER JOIN Restaurants ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId "
				+ "WHERE Restaurants.CompanyName=?;";		
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
				int capacity = results.getInt("Capacity");
				SitDownRestaurant_TymofiiKryvtsun restaurant = new SitDownRestaurant_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName, capacity);
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
	


	public SitDownRestaurant_TymofiiKryvtsun delete(SitDownRestaurant_TymofiiKryvtsun restaurant) throws SQLException {
		String deletePerson = "DELETE FROM SitDownRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setInt(1, restaurant.getRestaurantId());
			deleteStmt.executeUpdate();
			super.delete(restaurant);
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
