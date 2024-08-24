package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.getIcons().add(new Image("file:./icon/favicon.png"));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			scene.setOnKeyPressed(e -> {
				if(e.getCode() == KeyCode.ENTER) {
					System.out.println("ENTER");
					
				}
			});
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Đăng nhập");
			
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
