package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import daos.NhanVienDAO;
import entities.NhanVien;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ui.ThongBao;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class DangNhapController implements Initializable {

    @FXML
    private JFXTextField txtTenDangNhap;
    @FXML
    private JFXPasswordField psfMatKhau;
    @FXML
    private JFXButton btnDangNhap;
    @FXML
    private JFXButton btnHuy;

    NhanVienDAO dao = new NhanVienDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHuy.setOnAction((e) -> {
            ((Stage) btnHuy.getScene().getWindow()).close();
        });
    }

    @FXML
    private void dangNhap(ActionEvent event) {
        NhanVien nv = dao.getNhanVienTheoTenDangNhap(txtTenDangNhap.getText());
        if (nv != null) {
            if (nv.getMatKhau().equals(psfMatKhau.getText())) {
                MainController.setNhanVien(nv);
                MainController.setLocalContentFXML("/ui/view/DatMonFXML.fxml");
                ((Stage) btnDangNhap.getScene().getWindow()).close();
            } else {
                ThongBao.ThongBaoLoi("Mật khẩu không đúng");
                psfMatKhau.requestFocus();
            }
        } else {
            ThongBao.ThongBaoLoi("Tài khoản này không tồn tại");
            txtTenDangNhap.requestFocus();
        }
    }

}
