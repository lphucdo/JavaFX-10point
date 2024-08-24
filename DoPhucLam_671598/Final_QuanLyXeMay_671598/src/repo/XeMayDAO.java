package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.XeMay;

public class XeMayDAO extends BaseDAO<XeMay>{

	@Override
	public void setModel(XeMay model) {
		this.model = model;
		
	}
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public XeMay mapResultToModel(ResultSet result) throws SQLException {
		XeMay xeMay = new XeMay();
		xeMay.setMaXeMay(result.getString("maXeMay"));
		xeMay.setTenXeMay(result.getString("tenXeMay"));
		xeMay.setDungTich(result.getInt("dungTich"));
		xeMay.setKhoiLuong(result.getInt("khoiLuong"));
		xeMay.setGiaBanLe(result.getInt("giaBanLe"));
		xeMay.setImgPath(result.getString("imgPath"));
		return xeMay;
	}
	@Override
	public boolean add(XeMay xeMay) {
		Boolean row = false;
		try {
			Connection connect = DriverManager.getConnection(dbURL);
			String sql = "INSERT INTO "+tableName+" (maXeMay,tenXeMay,dungTich,khoiLuong,giaBanLe) VALUES(?,?,?,?,?)";
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, xeMay.getMaXeMay());
			statement.setString(2, xeMay.getTenXeMay());
			statement.setInt(3, xeMay.getDungTich());
			statement.setInt(4, xeMay.getKhoiLuong());
			statement.setInt(5, xeMay.getGiaBanLe());
			row = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	@Override
	public boolean update(XeMay xeMay) {
		boolean row = false;
		try {
			Connection connect = DriverManager.getConnection(dbURL);
			String sql = "UPDATE "+ tableName +" SET tenXeMay = ?, dungTich = ?, khoiLuong = ?, giaBanLe = ? WHERE maXeMay = ?";
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, xeMay.getTenXeMay());
			statement.setInt(2, xeMay.getDungTich());
			statement.setInt(3, xeMay.getKhoiLuong());
			statement.setInt(4, xeMay.getGiaBanLe());
			statement.setString(5, xeMay.getMaXeMay());
			row = statement.executeUpdate() > 0;
			statement.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	public boolean addPath(String maXeMay, String imgPath) {
		boolean isInsert = false;
		String sql = "UPDATE tb_bike SET imgPath = (?) WHERE maXeMay = (?)";
		try {
			Connection connect = DriverManager.getConnection("jdbc:ucanaccess://QLXM.accdb");
			PreparedStatement state = connect.prepareStatement(sql);
			state.setString(1, imgPath);
			state.setString(2, maXeMay);
			
			isInsert = state.executeUpdate() > 0;
			state.close();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isInsert;
	
	}
	
}
