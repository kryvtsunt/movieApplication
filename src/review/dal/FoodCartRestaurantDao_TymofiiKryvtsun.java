package review.dal;

import review.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class FoodCartRestaurantDao_TymofiiKryvtsun extends RestaurantsDao_TymofiiKryvtsun {
	
	// Single pattern: instantiation is limited to one object.
	private static FoodCartRestaurantDao_TymofiiKryvtsun instance = null;
	protected FoodCartRestaurantDao_TymofiiKryvtsun() {
		super();
	}
	public static FoodCartRestaurantDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new FoodCartRestaurantDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public FoodCartRestaurant_TymofiiKryvtsun create(FoodCartRestaurant_TymofiiKryvtsun restaurant) throws SQLException {
		Restaurants_TymofiiKryvtsun r = create(new Restaurants_TymofiiKryvtsun(restaurant.getName(), restaurant.getDescription(), restaurant.getMenu(), restaurant.getHours(), restaurant.isActive(), restaurant.getCuisine(), restaurant.getStreet1(), restaurant.getStreet2(), restaurant.getCity(), restaurant.getState(), restaurant.getZip(), restaurant.getCompanyName()));
		String insertPerson = "INSERT INTO FoodCartRestaurant(RestaurantId, Licensed) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson);
			insertStmt.setInt(1, r.getRestaurantId());
			insertStmt.setBoolean(2, restaurant.isLicensed());

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
	
	public FoodCartRestaurant_TymofiiKryvtsun getFoodCartRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT FoodCartRestaurant.RestaurantId AS RestaurantId,"
				+ "Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Licensed "
				+ "FROM FoodCartRestaurant INNER JOIN Restaurants ON FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId "
				+ "WHERE FoodCartRestaurant.RestaurantId=?;";
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
				boolean Licensed = results.getBoolean("Licensed");

				FoodCartRestaurant_TymofiiKryvtsun restaurant = new FoodCartRestaurant_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName, Licensed);
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
	
	public List<FoodCartRestaurant_TymofiiKryvtsun> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException {
		List<FoodCartRestaurant_TymofiiKryvtsun> restaurants = new ArrayList<>();
		String selectRestaurant = "SELECT FoodCartRestaurant.RestaurantId AS RestaurantId,"
				+ "Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Licensed "
				+ "FROM FoodCartRestaurant INNER JOIN Restaurants ON FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId "
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
				boolean Licensed = results.getBoolean("Licensed");
				FoodCartRestaurant_TymofiiKryvtsun restaurant = new FoodCartRestaurant_TymofiiKryvtsun(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zip, companyName, Licensed);
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
	


	public FoodCartRestaurant_TymofiiKryvtsun delete(FoodCartRestaurant_TymofiiKryvtsun restaurant) throws SQLException {
		String deletePerson = "DELETE FROM FoodCartRestaurant WHERE RestaurantId=?;";
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
