package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
	protected String dbURL = "jdbc:ucanaccess://QLXM.accdb";
	protected T model;
	protected String tableName;
	
	public abstract void setModel(T model);
	public abstract void setTableName(String tableName);
	public abstract T mapResultToModel(ResultSet result) throws SQLException;
	public abstract boolean add(T model);
	public abstract boolean update(T model);
	
	public ResultSet executeQuery(String sql) {
		try {
			Connection connect = DriverManager.getConnection(dbURL);
			Statement state = connect.createStatement();
			return state.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int executeUpdate(String sql) {
		try {
			Connection connect = DriverManager.getConnection(dbURL);
			Statement state = connect.createStatement();
			return state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<T> getAll(){
		
		String sql = "SELECT * FROM " + this.tableName;
		ResultSet result = executeQuery(sql);
		List<T> myList = new ArrayList<T>();
		try {
			while(result.next()) {
				T data = mapResultToModel(result);
				myList.add(data);	
			}
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myList;
		
	}
	
	public T find(String field, String value) {
		String sql = "SELECT * FROM " + tableName + " WHERE " + field + " = '" + value + "'";
		ResultSet result = executeQuery(sql);
		T data = null;
		try {
			while(result.next()) {
				data = mapResultToModel(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	public boolean remove(String field, String value) {
		String sql = "DELETE FROM " + tableName + " WHERE " + field + " = '" + value + "'";
		Boolean isDel = false;
		try {
			isDel = executeUpdate(sql) > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDel;
	}
}
