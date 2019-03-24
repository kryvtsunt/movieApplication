package movienight.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movienight.model.*;

import java.sql.Date;


public class CreditCardsDao_TymofiiKryvtsun {
	protected ConnectionManager_TymofiiKryvtsun connectionManager;

	private static CreditCardsDao_TymofiiKryvtsun instance = null;
	protected CreditCardsDao_TymofiiKryvtsun() {
		connectionManager = new ConnectionManager_TymofiiKryvtsun();
	}
	public static CreditCardsDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new CreditCardsDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public CreditCards_TymofiiKryvtsun create(CreditCards_TymofiiKryvtsun card) throws SQLException {
		String insertCards =
			"INSERT INTO CreditCards(CardNumber, Expiration, Username) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCards);
			insertStmt.setLong(1, card.getCardNumer());
			insertStmt.setDate(2, card.getExpiration());
			insertStmt.setString(3, card.getUser().getUserName());
			insertStmt.executeUpdate();
			
			return card;
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
	
	public CreditCards_TymofiiKryvtsun updateExpiration(CreditCards_TymofiiKryvtsun card, Date newExpiration) throws SQLException {
		String updateBlogPost = "UPDATE CreditCards SET CardNumber=?,Expiration=?,UserName=? WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBlogPost);
			updateStmt.setLong(1, card.getCardNumer());
			updateStmt.setDate(2, newExpiration);
			updateStmt.setString(3, card.getUser().getUserName());
			updateStmt.setLong(4, card.getCardNumer());

			updateStmt.executeUpdate();
			card.setExpiration(newExpiration);
			return card;
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

	public CreditCards_TymofiiKryvtsun delete(CreditCards_TymofiiKryvtsun card) throws SQLException {
		String deleteReshare = "DELETE FROM CreditCards WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReshare);
			deleteStmt.setLong(1, card.getCardNumer());
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

	public CreditCards_TymofiiKryvtsun getCreditCardByCardNumber(long cardNumber) throws SQLException {
		String selectCard =
			"SELECT * " +
			"FROM CreditCards " +
			"WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCard);
			selectStmt.setLong(1, cardNumber);
			results = selectStmt.executeQuery();
			CompaniesDao_TymofiiKryvtsun UsersDao = CompaniesDao_TymofiiKryvtsun.getInstance();
			if(results.next()) {
				String userName = results.getString("UserName");				
				Users_TymofiiKryvtsun user = UsersDao.getUserByUserName(userName);
				CreditCards_TymofiiKryvtsun card = new CreditCards_TymofiiKryvtsun(cardNumber, results.getDate("Expiration"), user);
				return card;
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

	public List<CreditCards_TymofiiKryvtsun> getCreditCarsdByUserName(String userName) throws SQLException {
		List<CreditCards_TymofiiKryvtsun> cards = new ArrayList<CreditCards_TymofiiKryvtsun>();
		String selectCards =
			"SELECT * " +
			"FROM CreditCards " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCards);
			selectStmt.setString(1,userName);
			results = selectStmt.executeQuery();
			CompaniesDao_TymofiiKryvtsun UsersDao = CompaniesDao_TymofiiKryvtsun.getInstance();
			while(results.next()) {
				Users_TymofiiKryvtsun user = UsersDao.getUserByUserName(userName);
				CreditCards_TymofiiKryvtsun card = new CreditCards_TymofiiKryvtsun(results.getLong("CardNumber"), results.getDate("Expiration"), user);
				cards.add(card);
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
		return cards;
	}
}
