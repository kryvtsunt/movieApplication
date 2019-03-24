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


public class ReservationsDao_TymofiiKryvtsun {
	protected ConnectionManager_TymofiiKryvtsun connectionManager;

	private static ReservationsDao_TymofiiKryvtsun instance = null;
	protected ReservationsDao_TymofiiKryvtsun() {
		connectionManager = new ConnectionManager_TymofiiKryvtsun();
	}
	public static ReservationsDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new ReservationsDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public Reservations_TymofiiKryvtsun create(Reservations_TymofiiKryvtsun reservation) throws SQLException {
		String insertReservations =
			"INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReservations,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, reservation.getStart());
			insertStmt.setTimestamp(2, reservation.getEnd());
			insertStmt.setInt(3, reservation.getSize());
			insertStmt.setString(4, reservation.getUser().getUserName());
			insertStmt.setInt(5, reservation.getRestaurant().getRestaurantId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int ReservationId = -1;
			if(resultKey.next()) {
				ReservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reservation.setReservationId(ReservationId);
			return reservation;
			
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

	public Reservations_TymofiiKryvtsun delete(Reservations_TymofiiKryvtsun reservation) throws SQLException {
		String deleteReservation = "DELETE FROM Reservations WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReservation);
			deleteStmt.setInt(1, reservation.getReservationId());
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
	 * Get the Reservations record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Reservations instance.
	 * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Reservations, BlogPosts, BlogUsers tables and then build each object.
	 */
	public Reservations_TymofiiKryvtsun getReservationById(int reservationId) throws SQLException {
		String selectReservation =
			"SELECT * " +
			"FROM Reservations " +
			"WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservation);
			selectStmt.setInt(1, reservationId);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();
			if(results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reservations_TymofiiKryvtsun Reservation = new Reservations_TymofiiKryvtsun(resultReservationId, start, end, size, users, restaurants);
				return Reservation;
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

	public List<Reservations_TymofiiKryvtsun> getReservationsByUserName(String userName) throws SQLException {
		List<Reservations_TymofiiKryvtsun> Reservations = new ArrayList<Reservations_TymofiiKryvtsun>();
		String selectReservations =
			"SELECT * " +
			"FROM Reservations " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				int restaurantId = results.getInt("RestaurantId");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reservations_TymofiiKryvtsun Reservation = new Reservations_TymofiiKryvtsun(resultReservationId, start, end, size, users, restaurants);
				Reservations.add(Reservation);
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
		return Reservations;
	}
	
	public List<Reservations_TymofiiKryvtsun> getReservationsByRestaurantId(int restaurantId) throws SQLException {
		List<Reservations_TymofiiKryvtsun> Reservations = new ArrayList<Reservations_TymofiiKryvtsun>();
		String selectReservations =
			"SELECT * " +
			"FROM Reservations " + 
			"WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			UsersDao_TymofiiKryvtsun usersDao = UsersDao_TymofiiKryvtsun.getInstance();
			RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();			
			while(results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Timestamp start = results.getTimestamp("Start");
				Timestamp end = results.getTimestamp("End");
				int size = results.getInt("Size");
				String userName = results.getString("UserName");
				Users_TymofiiKryvtsun users = usersDao.getUserByUserName(userName);
				Restaurants_TymofiiKryvtsun restaurants = restaurantsDao.getRestaurantById(restaurantId);
				Reservations_TymofiiKryvtsun Reservation = new Reservations_TymofiiKryvtsun(resultReservationId, start, end, size, users, restaurants);
				Reservations.add(Reservation);
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
		return Reservations;
	}
}
