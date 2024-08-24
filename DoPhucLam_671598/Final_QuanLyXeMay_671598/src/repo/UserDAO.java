package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO extends BaseDAO<User>{

	@Override
	public void setModel(User model) {
		this.model = model;
	}
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public User mapResultToModel(ResultSet result) throws SQLException {
		User user = new User();
		user.setFullname(result.getNString("fullName"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setRole(result.getBoolean("role"));
		return user;
	}
	@Override
	public boolean add(User user) {
		Boolean row = false;
		String sql = "INSERT INTO " + tableName + "(fullname,email,password,role)" + " VALUES(?,?,?,?)";
		try {
			Connection connect = DriverManager.getConnection(dbURL);
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setNString(1, user.getFullname());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setBoolean(4, user.getRole());
			row = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	@Override
	public boolean update(User user) {
		return false;
	}

}
