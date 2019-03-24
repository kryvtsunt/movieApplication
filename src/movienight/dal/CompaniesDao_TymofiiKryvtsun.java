package movienight.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import movienight.model.*;


public class CompaniesDao_TymofiiKryvtsun extends UsersDao_TymofiiKryvtsun {
	private static CompaniesDao_TymofiiKryvtsun instance = null;
	protected CompaniesDao_TymofiiKryvtsun() {
		super();
	}
	public static CompaniesDao_TymofiiKryvtsun getInstance() {
		if(instance == null) {
			instance = new CompaniesDao_TymofiiKryvtsun();
		}
		return instance;
	}

	public Companies_TymofiiKryvtsun create(Companies_TymofiiKryvtsun company) throws SQLException {
		String insertBlogUser = "INSERT INTO Companies(CompanyName, About) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBlogUser);
			insertStmt.setString(1, company.getCompanyName());
			insertStmt.setString(2, company.getAbout());
			insertStmt.executeUpdate();
			return company;
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

	public Companies_TymofiiKryvtsun updateAbout(Companies_TymofiiKryvtsun company, String about) throws SQLException {
		String updateBlogPost = "UPDATE Companies SET CompanyName=?,About=? WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBlogPost);
			updateStmt.setString(1, company.getCompanyName());
			updateStmt.setString(2, about);
			updateStmt.setString(3, company.getCompanyName());
			updateStmt.executeUpdate();
			company.setAbout(about);
			return company;
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

	public Companies_TymofiiKryvtsun delete(Companies_TymofiiKryvtsun company) throws SQLException {
		String deleteCompany = "DELETE FROM Companies WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCompany);
			deleteStmt.setString(1, company.getCompanyName());
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

	public Companies_TymofiiKryvtsun getCompanyByCompanyName(String companyName) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectBlogUser =
			"SELECT * " +
			"FROM Companies " +
			"WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogUser);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultCompanyName = results.getString("CompanyName");
				String about = results.getString("About");
				Companies_TymofiiKryvtsun company = new Companies_TymofiiKryvtsun(resultCompanyName, about);
				return company;
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
}
