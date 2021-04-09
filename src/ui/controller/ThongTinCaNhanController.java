package ui.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.NhanVien;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class ThongTinCaNhanController implements Initializable {

    @FXML
    private JFXDatePicker dpNgaySinh;
    @FXML
    private JFXTextField txtSoCMND;
    @FXML
    private JFXTextField txtDiaChi;
    @FXML
    private JFXTextField txtSoDT;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtTenDN;
    @FXML
    private JFXPasswordField psfMatKhau;
    @FXML
    private JFXTextField txtHoTen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTT();
    }    
    
    public void loadTT(){
        NhanVien nv=MainController.getNhanVien().get();
        txtHoTen.setText(nv.getHoten());
        txtSoCMND.setText(nv.getSoCMND());
        dpNgaySinh.setValue(nv.getNgaySinh());
        txtDiaChi.setText(nv.getDiaChi());
        txtSoDT.setText(nv.getSoDienThoai());
        txtEmail.setText(nv.getEmail());
        txtTenDN.setText(nv.getTenDangNhap());
        psfMatKhau.setText(nv.getMatKhau());
   }
}
