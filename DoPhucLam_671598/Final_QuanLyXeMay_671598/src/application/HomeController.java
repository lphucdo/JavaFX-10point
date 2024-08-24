package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import model.XeMay;
import repo.XeMayDAO;

public class HomeController implements Initializable{
	//More
	private User loginedUser;
	private XeMayDAO xeMayDAO = new XeMayDAO();
	
	@FXML
	private Label loginedFullname;
	@FXML
	ImageView imgView;
	@FXML
	private TextField maXeMayTF;
	@FXML
	private TextField tenXeMayTF;
	@FXML
	private TextField dungTichTF;
	@FXML
	private TextField giaBanTF;
	@FXML
	private TextField khoiLuongTF;
	@FXML
	private Label msg;
//	Table view
	@FXML
	private TableView<XeMay> tableView;
	@FXML
	private TableColumn<XeMay, String> maXeMayCol;
	@FXML
	private TableColumn<XeMay, String> tenXeMayCol;
	@FXML
	private TableColumn<XeMay, Integer> dungTichCol;
	@FXML
	private TableColumn<XeMay, Integer> khoiLuongCol;
	@FXML
	private TableColumn<XeMay,Integer> giaBanCol;
//	Button
	@FXML
	Button add;
	@FXML
	Button edit;
	@FXML
	Button delete;
	@FXML
	Button changeBtn;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			loginedFullname.setText("Xin chào: " + loginedUser.getFullname());
			setTimeline(loginedFullname);
			maXeMayCol.setCellValueFactory(new PropertyValueFactory<XeMay, String>("maXeMay"));
			tenXeMayCol.setCellValueFactory(new PropertyValueFactory<XeMay, String>("tenXeMay"));
			dungTichCol.setCellValueFactory(new PropertyValueFactory<XeMay, Integer>("dungTich"));
			khoiLuongCol.setCellValueFactory(new PropertyValueFactory<XeMay, Integer>("khoiLuong"));
			giaBanCol.setCellValueFactory(new PropertyValueFactory<XeMay,Integer>("giaBanLe"));
			xeMayDAO.setModel(new XeMay());
			xeMayDAO.setTableName("tb_bike");
			
			getColsData();
			configRole(loginedUser.getRole());
			
			typingHandle(maXeMayTF);
			typingHandle(tenXeMayTF);
			typingHandle(dungTichTF);
			typingHandle(giaBanTF);
			typingHandle(khoiLuongTF);
			
			setNumFormatter(dungTichTF);
			setNumFormatter(giaBanTF);
			setNumFormatter(khoiLuongTF);
			
		});
	}
	public void setLogined(User loginedUser) {
		this.loginedUser = loginedUser;
	}
	@FXML
	public void onExitBtn() {
		msg.getScene().getWindow().hide();
	}
	@FXML
	public void onChangeBtn(){
		if( !maXeMayTF.getText().isEmpty()) {
			XeMay selectedXeMay = selectionXeMay();
			File fileSelected = fileImgChooser();
			String imgPath = ("./img/" + selectedXeMay.getMaXeMay()+".jpg").replaceAll("\\s", "");
			if(fileSelected != null && selectedXeMay!= null) {
				try {
					Files.copy(Paths.get(fileSelected.getAbsolutePath()), Paths.get(imgPath), StandardCopyOption.REPLACE_EXISTING);
					xeMayDAO.addPath(selectedXeMay.getMaXeMay(), imgPath);
					getColsData();
					tableView.getSelectionModel().select(selectedXeMay); 
					tableView.scrollTo(selectedXeMay); 
					imgView.setImage(new Image("file:" + imgPath));
					showMsg("Chọn ảnh thành công", true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			showMsg("Hãy chọn một Xe Máy", false);
		}	
		
	}
	@FXML
	public void onAddBtn() {
		String maXeMay = maXeMayTF.getText();
		if(!maXeMay.isEmpty()) {
			if(xeMayDAO.find("maXeMay", maXeMay) == null) {
				XeMay xeMay = handleDataFields(maXeMay);
				xeMayDAO.add(xeMay);
				showMsg("Thêm Thành Công!", true);
				getColsData();
				tableView.getSelectionModel().select(xeMay);
				imgView.setImage(null);
				edit.setDisable(false);
			}else {
				showMsg("Thất Bại! Đã tồn tại chiếc xe này", false);
			}
		}else {
			showMsg("Thất bại! Hãy điền dữ liệu", false);
		}
	}

	@FXML
	public void onEditBtn() {
	    String maXeMay = maXeMayTF.getText();
	    if (xeMayDAO.find("maXeMay", maXeMay) != null) {
	        XeMay xeMay = handleDataFields(maXeMay);
	        xeMayDAO.update(xeMay);
	        showMsg("Sửa Thành Công!", true);
	        getColsData();
	        
	        for (XeMay xM : tableView.getItems()) {
	            if (xM.getMaXeMay().equals(maXeMay)) {
	                tableView.getSelectionModel().select(xM);
	                tableView.scrollTo(xM);
	                break;
	            }
	        }
	    } else {
	        showMsg("Sửa thất bại!", false);
	    }
	}

	
	@FXML
	public void onDeleteBtn() throws IOException {
		String maXeMay = maXeMayTF.getText();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Xoá xe");
		alert.setHeaderText("Bạn có muốn xoá chiếc xe " + maXeMay + " này không?");
		Optional<ButtonType> option = alert.showAndWait();		
		if(xeMayDAO.find("maXeMay", maXeMay) != null) {
			if(option.get() == ButtonType.OK) {
				if(xeMayDAO.find("maXeMay", maXeMay).getImgPath() != null) {
					Files.delete(Paths.get(xeMayDAO.find("maXeMay", maXeMay).getImgPath()));					
				};
				xeMayDAO.remove("maXeMay", maXeMay);
				showMsg("Xoá Thành Công!", true);
				clearTextField();
				getColsData();
				edit.setDisable(true);
			}else {
				showMsg("Cancel", true);
			}
			
		}else {
			showMsg("Xoá thất bại! Hãy chọn xe cần xoá", false);
		}
	}
	
	@FXML
	public void onSelectXeMay() {
		 XeMay selectedXeMay = selectionXeMay();
		    if (selectedXeMay != null && selectedXeMay.getImgPath() != null) {
		        imgView.setImage(new Image("file:" + selectedXeMay.getImgPath()));
		    } else {
		        showMsg("Hãy chọn xe máy", false);
		    }
	}
	
	public XeMay selectionXeMay() {
	    XeMay selectedXeMay = (XeMay) tableView.getSelectionModel().getSelectedItem();
	    if (selectedXeMay != null) {
	        maXeMayTF.setText(selectedXeMay.getMaXeMay());
	        tenXeMayTF.setText(selectedXeMay.getTenXeMay());
	        dungTichTF.setText(String.valueOf(selectedXeMay.getDungTich()));
	        khoiLuongTF.setText(String.valueOf(selectedXeMay.getKhoiLuong()));
	        giaBanTF.setText(String.valueOf(selectedXeMay.getGiaBanLe()));
			onChangeValue(maXeMayTF, selectedXeMay.getMaXeMay());

		    edit.setDisable(false);
	    } else {
	        clearTextField();
	    }
	    return selectedXeMay;
	}
	private void getColsData() {
		List<XeMay> listXeMay = xeMayDAO.getAll();
		ObservableList<XeMay> obsList = FXCollections.observableArrayList(listXeMay);
		tableView.setItems(obsList);	
	}
	private void configRole(Boolean role) {
		add.setDisable(!role);
		edit.setDisable(!role);
		delete.setDisable(!role);
		changeBtn.setDisable(!role);
	
	}
	private void setNumFormatter(TextField tf) {
		tf.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
	
	}
	private void onChangeValue(TextField textField, String initValue) {
	    textField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if(!newValue.equals(initValue)) {
	            	edit.setDisable(true);
	            }else {
	            	edit.setDisable(false);
	            }
	        }
	    });
	}
	private XeMay handleDataFields(String maXeMay) {
		String tenXeMay =tenXeMayTF.getText();
		int dungTich = Integer.valueOf(dungTichTF.getText());
		int khoiLuong = Integer.valueOf(khoiLuongTF.getText());
		int giaBan = Integer.valueOf(giaBanTF.getText());
		
		return new XeMay(maXeMay, tenXeMay, dungTich, khoiLuong, giaBan);
	}

	private File fileImgChooser() {
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("Ảnh", "*png", "*jpg"));
		Stage stage = (Stage)imgView.getScene().getWindow();
		return filechooser.showOpenDialog(stage);
	}
	private void typingHandle(TextField t) {
		t.setOnKeyTyped(e -> showMsg(t.getPromptText(), true));
		t.setOnMouseClicked(e -> showMsg(t.getPromptText(), true));
	}
	
	public void showMsg(String mess, boolean status) {
		msg.setText(mess);
		if(status) {
			setTimeline(msg);
		}else {
			msg.setTextFill(Color.RED);
		}
	}
	public void clearTextField() {
		imgView.setImage(null);
		maXeMayTF.setText("");
		tenXeMayTF.setText("");
		dungTichTF.setText("");
		khoiLuongTF.setText("");
		giaBanTF.setText("");
		
	}
	public void setTimeline(Label label) {
		Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    if (label.getTextFill().equals(Color.GREEN)) {
                        label.setTextFill(Color.BLACK);
                    } else {
                        label.setTextFill(Color.GREEN);
                    }
                })
        );
        timeline.setCycleCount(4);
        timeline.play();
	}
	
}
