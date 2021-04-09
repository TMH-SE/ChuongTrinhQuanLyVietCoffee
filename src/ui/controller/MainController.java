package ui.controller;

import entities.NhanVien;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class MainController implements Initializable {

    @FXML
    private ToolBar toolbarQuanLy;
    @FXML
    private StackPane stackMainContain;
    @FXML
    private Button btnDatMon;
    @FXML
    private Button btnThanhToan;
    @FXML
    private Button btnQLMonAn;
    @FXML
    private Button btnQLDoUong;
    @FXML
    private Button btnQLNhanVien;
    @FXML
    private Button btnQLHoaDon;

    private static final StringProperty localContentFXML = new SimpleStringProperty("");
    @FXML
    private MenuItem menuDangNhap;
    @FXML
    private MenuItem menuTTCaNhan;
    @FXML
    private MenuItem menuDoiMatKhau;
    @FXML
    private MenuItem menuDangXuat;
    @FXML
    private ToolBar toolbarThanhToanDatMon;

    private static final ObjectProperty<NhanVien> nhanVien = new SimpleObjectProperty<>();
    @FXML
    private Label lblNhanVien;
    
    private Controller mainController;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventChangeContent();
        eventPersonLogin();
        btnQLHoaDon.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/QLHoaDonFXML.fxml");
        });

        btnQLNhanVien.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/QLNhanVienFXML.fxml");
        });

        btnDatMon.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/DatMonFXML.fxml");
        });
        btnThanhToan.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/DatMonFXML.fxml");
        });

        btnQLDoUong.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/QLMatHangFXML.fxml");
            QuanLyMatHangController controller=(QuanLyMatHangController) mainController;
            controller.setValueOfCombobox("Đồ uống");
        });

        btnQLMonAn.setOnAction((e) -> {
            setLocalContentFXML("/ui/view/QLMatHangFXML.fxml");
            QuanLyMatHangController controller=(QuanLyMatHangController) mainController;
            controller.setValueOfCombobox("Món ăn");
        });

        menuDangNhap.setOnAction((e) -> {
            loadDangNhapDialog();
        });
        
        menuTTCaNhan.setOnAction((e)->{
           loadTTCaNhanDialog();
        });
        
        menuDoiMatKhau.setOnAction((e)->{
            loadDoiMatKhauDialog();
        });
        
        menuDangXuat.setOnAction((e) -> {
            dangXuat();
        });
    }

    public void eventChangeContent() {
        localContentFXML.addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    FXMLLoader loaderContent = new FXMLLoader(getClass().getResource(newValue));
                    final Parent rootContent = loaderContent.load();
                    
                    
                    mainController=loaderContent.getController();
                    stackMainContain.getChildren().setAll(rootContent);
                }
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void eventPersonLogin() {
        nhanVien.addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                if (newValue.getLoaiNhanVien().equals("AD")) {
                    toolbarQuanLy.setDisable(false);
                    toolbarThanhToanDatMon.setDisable(false);
                    lblNhanVien.setText(newValue.getHoten());
                    openMenu(false);
                    menuDangNhap.setDisable(true);
                } else if (newValue.getLoaiNhanVien().equals("NV")) {
                    toolbarQuanLy.setVisible(false);
                    toolbarThanhToanDatMon.setVisible(false);
                    toolbarThanhToanDatMon.setDisable(false);
                    lblNhanVien.setText(newValue.getHoten());
                    openMenu(false);
                    menuDangNhap.setDisable(true);
                }
            }
        });
    }

    public static StringProperty getLocalContentFXML() {
        return localContentFXML;
    }

    public static void setLocalContentFXML(String localContentFXML) {
        MainController.localContentFXML.setValue(localContentFXML);
    }

    public static ObjectProperty<NhanVien> getNhanVien() {
        return nhanVien;
    }

    public static void setNhanVien(NhanVien nhanVien) {
        MainController.nhanVien.setValue(nhanVien);
    }

    public void loadDangNhapDialog() {
        try {
            Stage dialog = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/view/DangNhapFXML.fxml"));
            Parent root = loader.load();
            DangNhapController controller = loader.getController();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTTCaNhanDialog() {
        try {
            Stage dialog = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/view/ThongTinCaNhanFXML.fxml"));
            Parent root = loader.load();
            ThongTinCaNhanController controller = loader.getController();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadDoiMatKhauDialog() {
        try {
            Stage dialog = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/view/DoiMatKhauFXML.fxml"));
            Parent root = loader.load();
            DoiMatKhauController controller = loader.getController();
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dangXuat() {
        toolbarQuanLy.setDisable(true);
        toolbarThanhToanDatMon.setDisable(true);
        toolbarQuanLy.setVisible(true);
        toolbarThanhToanDatMon.setVisible(true);
        lblNhanVien.setText("...");
        setLocalContentFXML("/ui/view/MainContentFXML.fxml");
        setNhanVien(null);
        openMenu(true);
        menuDangNhap.setDisable(false);
    }
    
    public void openMenu(boolean b){
        menuDangXuat.setDisable(b);
        menuDoiMatKhau.setDisable(b);
        menuTTCaNhan.setDisable(b);
    }
}
