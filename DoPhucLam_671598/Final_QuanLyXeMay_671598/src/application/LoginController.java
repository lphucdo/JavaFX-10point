package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;
import repo.UserDAO;

public class LoginController{
	@FXML
	private TextField emailTF;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorLabel;
	@FXML
	private CheckBox myCheckBox;
	@FXML
	private TextField passwordTF;
	@FXML
	public void toogleShowPassword(ActionEvent event) {
		if(!myCheckBox.isSelected()) {
			passwordTF.setVisible(false);
			passwordField.setText(passwordTF.getText());
			passwordField.setVisible(true);
		}else {
			passwordTF.setVisible(true);
			passwordTF.setText(passwordField.getText());
			passwordField.setVisible(false);
		}
	}
	@FXML
	public void onLogBtn() {
		UserDAO users = new UserDAO();
		String email = emailTF.getText();
		String password = !myCheckBox.isSelected() ? passwordField.getText() :  passwordTF.getText();
		
		User user = new User(email, password);
		users.setModel(user);
		users.setTableName("tb_users");
		if(user != null) {
			for(User us: users.getAll()) {
				if(us.getEmail().equals(user.getEmail()) && us.getPassword().equals(user.getPassword())) {
					emailTF.getScene().getWindow().hide();
					goHome(us);
				}else {
					if(us.getEmail().equals(email)) {
						emailTF.setStyle("-fx-border-color: #0598FF;");				
						errorLabel.setText("Mật khẩu sai!");
						break;
					}else {
						emailTF.setStyle("-fx-border-color: red;");	
						errorLabel.setText("Đăng nhập thất bại! Sai email và mật khẩu");
					}
					passwordTF.setStyle("-fx-border-color: red;");
					passwordTF.setText("");
					passwordField.setStyle("-fx-border-color: red;");
					passwordField.setText("");
				}
			}
		}
	}
	public void goHome(User user) {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("./Home.fxml"));
		
		try {
			Parent root = (Parent)fxmlloader.load();
			HomeController controller = fxmlloader.getController();
			controller.setLogined(user);
			Stage homeStage = new Stage();
			Scene scene = new Scene(root);
			
			homeStage.setTitle("Trang Chủ Quản Lý Xe Máy");
			homeStage.getIcons().add(new Image("file:./icon/favicon.png"));
			scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
			homeStage.setScene(scene);
			homeStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Không thể mở trang chủ.");
		}
	}
	public void onRegister() {
		emailTF.getScene().getWindow().hide();
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("./Register.fxml"));
		try {
			Parent root = (Parent)fxml.load();
			Stage regStage = new Stage();
			Scene scene = new Scene(root);
			regStage.setTitle("Trang Đăng kí người dùng");
			regStage.getIcons().add(new Image("file:./icon/favicon.png"));
			scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
			regStage.setScene(scene);
			regStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void onKeyDown(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			onLogBtn();
		}
	}
	
}
