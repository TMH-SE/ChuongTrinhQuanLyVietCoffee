package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import daos.NhanVienDAO;
import entities.NhanVien;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ui.ThongBao;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class DoiMatKhauController implements Initializable {

    @FXML
    private JFXPasswordField psfMatKhauCu;
    @FXML
    private JFXPasswordField psfMatKhauMoi;
    @FXML
    private JFXPasswordField psfMatKhauMoi2;
    @FXML
    private JFXButton btnLuu;
    @FXML
    private JFXButton btnHuy;
    
    NhanVienDAO dao=new NhanVienDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHuy.setOnAction((e)->{
            ((Stage)btnHuy.getScene().getWindow()).close();
        });
        
        btnLuu.setOnAction((e)->{
            doiMatKhau();
        });
    }    

    private void doiMatKhau() {
        NhanVien nv=MainController.getNhanVien().get();
        if(psfMatKhauCu.getText().isEmpty()){
            ThongBao.ThongBaoLoi("Chưa nhập mật khẩu cũ");
            psfMatKhauCu.requestFocus();
            return;
        }
        if(psfMatKhauMoi.getText().isEmpty()){
            ThongBao.ThongBaoLoi("Chưa nhập mật khẩu mới");
            psfMatKhauMoi.requestFocus();
            return;
        }
        if(psfMatKhauMoi2.getText().isEmpty()){
            ThongBao.ThongBaoLoi("Chưa xác nhận mật khẩu mới");
            psfMatKhauMoi2.requestFocus();
            return;
        }
        if(nv.getMatKhau().equals(psfMatKhauCu.getText())){
            if(psfMatKhauMoi.getText().equals(psfMatKhauMoi2.getText())){
                nv.setMatKhau(psfMatKhauMoi.getText());
                if(dao.update(nv)){
                    ThongBao.ThongBaoThanhCong("Đổi mật khẩu thành công");
                    ((Stage)btnLuu.getScene().getWindow()).close();
                }else{
                    ThongBao.ThongBaoLoi("Không thể đổi mật khẩu");
                }
            }else{
                ThongBao.ThongBaoLoi("Mật khẩu không trùng khớp. Xác nhận lại");
                psfMatKhauMoi2.requestFocus();
            }
        }
        else{
            ThongBao.ThongBaoLoi("Mật khẩu cũ không đúng");
            psfMatKhauCu.requestFocus();
        }
    }
    
}
