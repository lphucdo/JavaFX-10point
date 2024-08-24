package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import repo.UserDAO;

public class RegisterController {
	@FXML
	TextField fullnameTF;
	@FXML
	TextField emailTF;
	@FXML
	TextField passwordTF;
	@FXML
	TextField rePasswordTF;
	@FXML
	Button chooserBtn;
	@FXML
	ToggleGroup role;
	@FXML
	RadioButton userToogle;
	@FXML
	RadioButton adminToogle;
	@FXML
	Button regBtn;
	@FXML
	Label msg;
	
	public void onRegBtn(){
		UserDAO userdao = new UserDAO();
		userdao.setModel(new User());
		userdao.setTableName("tb_users");
		
		String emailRegex = "^(.+)@(.+)$";
		String passwordRegex = "^.+$";
		String fullnameRegex = "[\\p{L}\\p{N}\\s]+";
		
		String fullname = fullnameTF.getText();
		String email = emailTF.getText();
		String password = passwordTF.getText();
		String password_ = rePasswordTF.getText();
		
		boolean isEmail = false;
		boolean isFullname = false;
		boolean isPassword = false;
		
		User user = userdao.find("email", email);
		
		if(user == null) {
			isFullname = checkRegex(fullname, fullnameRegex);
			isEmail = checkRegex(email, emailRegex);
			isPassword = checkRegex(password, passwordRegex);
			
			if(isFullname) {
				if(isEmail) {
					if(isPassword) {
						if(password.equals(password_)) {
							userdao.add(new User(fullname, email, password_, checkRole()));
							
					        Alert alert = new Alert(Alert.AlertType.INFORMATION);
					        alert.setTitle("Thông báo");
					        alert.setHeaderText("Đăng kí thành công.");
					        
					        alert.showAndWait();
							goLogin();
						}else {
							rePasswordTF.setText("");
							showMsg("Mật khẩu không giống nhau", false);
							passwordTF.requestFocus();
						}
					}else {
						passwordTF.requestFocus();
						showMsg("Hãy nhập mật khẩu", false);
					}
				}else {
					emailTF.requestFocus();
					showMsg("Email không đúng định dạng(example@domain.io)", false);
				}
			}else {
				fullnameTF.requestFocus();
				showMsg("Định dạng Fullname không chính xác", false);
			}
			
		}else {
			emailTF.setText("");
			showMsg("Đã tồn tại email này", false);
		}
	}
	public void goLogin() {
		emailTF.getScene().getWindow().hide();
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("./Login.fxml"));
		
		try {
			Parent root = (Parent)fxmlloader.load();
			Stage logStage = new Stage();
			Scene scene = new Scene(root);
			
			logStage.setTitle("Trang Đăng nhập phần mềm quản lý xe máy");
			logStage.getIcons().add(new Image("file:./icon/favicon.png"));
			scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
			logStage.setScene(scene);
			logStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Không thể mở trang chủ.");
		}
		
	}
	public void onKeyDown(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			onRegBtn();
		}
	}
	private boolean checkRole() {
		if(role.getSelectedToggle().equals(adminToogle)) {
			return true;
		}
		else {
			return false;
		}
	}
	private boolean checkRegex(String s, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	public void showMsg(String mess, boolean bool) {
		msg.setText(mess);
		if(bool) {
			msg.setTextFill(Color.GREEN);
		}else {
			msg.setTextFill(Color.RED);
		}
	}
	
}
