/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import daos.NhanVienDAO;
import entities.NhanVien;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ui.ThongBao;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class QuanLyNhanVienController implements Controller {

    @FXML
    private TableView<NhanVien> tblNV;
    @FXML
    private TableColumn<NhanVien, Integer> colMaNV;
    @FXML
    private TableColumn<NhanVien, String> colHoTen;
    @FXML
    private TableColumn<NhanVien, String> colSoCMND;
    @FXML
    private TableColumn<NhanVien, LocalDate> colNgaySinh;
    @FXML
    private TableColumn<NhanVien, String> colDiaChi;
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    @FXML
    private TableColumn<NhanVien, String> colEmail;
    @FXML
    private JFXTextField txtMaNV;
    @FXML
    private JFXTextField txtSoCMND;
    @FXML
    private JFXDatePicker dpNgaySinh;
    @FXML
    private JFXTextField txtDiaChi;
    @FXML
    private JFXTextField txtSoDienThoai;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtTenDangNhap;
    @FXML
    private JFXPasswordField psfMatKhau;
    @FXML
    private JFXButton btnThemNV;
    @FXML
    private ImageView imgThemNV;
    @FXML
    private JFXButton btnLuu;
    @FXML
    private JFXButton btnSuaNV;
    @FXML
    private ImageView imgSuaNV;
    @FXML
    private JFXButton btnXoaNV;
    @FXML
    private TextField txtTim;
    @FXML
    private JFXTextField txtHoTen;

    NhanVienDAO dao = new NhanVienDAO();
    ObservableList<NhanVien> list = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSoCMND.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d")) {
                txtSoCMND.setText(newValue.replaceAll("[^\\d]*", ""));
            }
        });
        txtSoDienThoai.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d")) {
                txtSoDienThoai.setText(newValue.replaceAll("[^\\d]*", ""));
            }
        });
        
        initCol();
        loadNV();
        btnThemNV.setOnAction((e) -> {
            themAction();
        });

        btnSuaNV.setOnAction((e) -> {
            suaAction();
        });
        
        tblNV.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            loadDuLieuLenTextField();
        });
        
        btnLuu.setOnAction((e) -> {
            int manv = Integer.parseInt(txtMaNV.getText());
            String ho = txtHoTen.getText();
            String soCMND = txtSoCMND.getText();
            LocalDate ngaySinh = dpNgaySinh.getValue();
            String diaChi = txtDiaChi.getText();
            String soDienThoai = txtSoDienThoai.getText();
            String email = txtEmail.getText();
            String tendn = txtTenDangNhap.getText();
            String mk = psfMatKhau.getText();
            if (ho.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc nhập họ người dùng");
                alert.showAndWait();
                txtHoTen.requestFocus();
                return;
            }
            //^[a-zA-Z]+$
            if (!ho.matches("[a-zA-Zaáạàảãăắặằẳẵâấậầẩẫeéẹèẻẽêếệềểễiíịìỉĩoóọòỏõôốộồổỗơớợờởỡuúụùủũưứựừửữ"
                    + "yýỵỳỷỹđAÁẠÀẢÃĂẮẶẰẲẴÂẤẬẦẨẪEÉẸÈẺẼÊẾỆỀỂỄIÍỊÌỈĨOÓỌÒỎÕÔỐỘỒỔỖƠỚỢỜỞỠUÚỤÙỦŨƯỨỰỪỬỮYÝỴỲỶỸĐ\\s\\']+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Họ người dùng chỉ chứa chữ");
                alert.showAndWait();
                txtHoTen.requestFocus();
                return;
            }
            if (soCMND.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc nhập số CMND");
                alert.showAndWait();
                txtSoCMND.requestFocus();
                return;
            }
            if (!soCMND.matches("\\d{9,12}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Số CMND là dãy số từ 9 đến 12 chữ số");
                alert.showAndWait();
                txtSoCMND.selectAll();
                txtSoCMND.requestFocus();
                return;
            }
            if (ngaySinh == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc chọn ngày sinh");
                alert.showAndWait();
                dpNgaySinh.requestFocus();
                return;
            }
            if (Period.between(ngaySinh, LocalDate.now()).getYears() < 12) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Thành viên phải 12 tuổi trở lên");
                alert.showAndWait();
                dpNgaySinh.requestFocus();
                return;
            }
            if (diaChi.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc nhập địa chỉ người dùng");
                alert.showAndWait();
                txtDiaChi.requestFocus();
                return;
            }
            if (soDienThoai.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc nhập số điện thoại người dùng");
                alert.showAndWait();
                txtSoDienThoai.requestFocus();
                return;
            }
            if (!soDienThoai.matches("^0\\d{9,10}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Số điện thoại bắt đầu bởi chữ số 0 và có độ dài từ 10-11 chữ số");
                alert.showAndWait();
                txtSoDienThoai.selectAll();
                txtSoDienThoai.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*Bắt buộc nhập email người dùng");
                alert.showAndWait();
                txtEmail.requestFocus();
                return;
            }
            if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Email không đúng định dạng (VD: admin@gmail.com)");
                alert.showAndWait();
                txtEmail.selectAll();
                txtEmail.requestFocus();
                return;
            }
            if (btnSuaNV.getText().equals("Hủy")) {
                NhanVien nv = new NhanVien(manv, ho, soCMND, ngaySinh, diaChi, soDienThoai, email, "NV", tendn, mk);
                if (dao.update(nv)) {
                    ThongBao.ThongBaoThanhCong("Cập nhật thành viên thành công");
                    loadNV();
                    suaAction();
                    searchNhanVien();
                } else {
                    ThongBao.ThongBaoLoi("Không thể chỉnh sửa thông tin nhân viên");
                }
            }
            if (btnThemNV.getText().equals("Hủy")) {
//                if (bll.NguoiDungBLL.checkSoCMND(soCMND) == false) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText(null);
//                    alert.setContentText("Số CMND này đã đăng ký thành viên");
//                    alert.showAndWait();
//                    txtSoCMND.selectAll();
//                    txtSoCMND.requestFocus();
//                    return;
//                }
                if (tendn.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng nhập tên đăng nhập");
                    alert.showAndWait();
                    txtTenDangNhap.requestFocus();
                    return;
                }
                if (!tendn.matches("[a-zA-Z\\d]{4,}")) {
                    ThongBao.ThongBaoLoi("Tên đăng nhập chỉ chứa chữ hoặc số và tối thiểu 4 ký tự");
                    txtTenDangNhap.requestFocus();
                    return;
                }
//                if (!bll.TaiKhoanBLL.checkTenDangNhap(tendn)) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText(null);
//                    alert.setContentText("Tên đăng nhập đã có người sử dụng");
//                    alert.showAndWait();
//                    txtTenDN.requestFocus();
//                    return;
//                }
                if (mk.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng nhập mật khẩu");
                    alert.showAndWait();
                    psfMatKhau.requestFocus();
                    return;
                }
                if (!mk.matches(".{6,}")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Mật khẩu có độ dài từ 6 ký tự trở lên");
                    alert.showAndWait();
                    psfMatKhau.selectAll();
                    psfMatKhau.requestFocus();
                    return;
                } else {
                    NhanVien nv = new NhanVien(ho, soCMND, ngaySinh, diaChi, soDienThoai, email, "NV", tendn, mk);
                    if (dao.save(nv)) {                       
                        ThongBao.ThongBaoThanhCong("Thêm thành viên thành công");
                        loadNV();
                        themAction();
                        searchNhanVien();
                    } else {
                        ThongBao.ThongBaoLoi("Không thể thêm nhân viên");
                    }
                }
            }
            searchNhanVien();
        });
        btnXoaNV.setOnAction((e)->{
            xoaAction();
        });
        searchNhanVien();
    }

    private void initCol() {
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        colSoCMND.setCellValueFactory(new PropertyValueFactory<>("soCMND"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadNV() {
        list.clear();
        list.addAll(dao.getAll(NhanVien.class));
        tblNV.setItems(list);
    }

    private void loadDuLieuLenTextField() {
        NhanVien nv = tblNV.getSelectionModel().getSelectedItem();
        if (nv != null) {
            txtMaNV.setText(Integer.toString(nv.getMaNhanVien()));
            txtHoTen.setText(nv.getHoten());
            txtSoCMND.setText(nv.getSoCMND());
            txtDiaChi.setText(nv.getDiaChi());
            txtSoDienThoai.setText(nv.getSoDienThoai());
            txtEmail.setText(nv.getEmail());
            dpNgaySinh.setValue((nv.getNgaySinh()));
            txtTenDangNhap.setText(nv.getTenDangNhap());
            psfMatKhau.setText(nv.getMatKhau());
        }
    }

    public void moButton(boolean b) {
        btnThemNV.setDisable(b);
        btnXoaNV.setDisable(b);
        btnSuaNV.setDisable(b);
        btnLuu.setDisable(b);
        dpNgaySinh.setDisable(b);
    }

    public void moTextFields(boolean b) {
        txtHoTen.setEditable(b);
        txtSoCMND.setEditable(b);
        txtDiaChi.setEditable(b);
        txtSoDienThoai.setEditable(b);
        txtEmail.setEditable(b);
    }

    public void xoaRongTextFields() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtSoCMND.setText("");
        dpNgaySinh.setValue(null);
        txtTenDangNhap.setText("");
        txtDiaChi.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        psfMatKhau.setText("");
        tblNV.getSelectionModel().select(null);
    }

    public void themAction() {
        if (btnThemNV.getText().equals("Thêm Nhân Viên")) {
            moTextFields(true);
            xoaRongTextFields();
            int manv = dao.getIdentityCurrent()+1;
            txtMaNV.setText(Integer.toString(manv));
            txtTenDangNhap.setEditable(true);
            psfMatKhau.setEditable(true);
            moButton(false);
            btnSuaNV.setDisable(true);
            btnXoaNV.setDisable(true);
            btnThemNV.setText("Hủy");
            tblNV.setDisable(true);
            txtTim.setDisable(true);
            imgThemNV.setImage(new Image("/images/delete.png"));
            txtHoTen.requestFocus();
        } else if (btnThemNV.getText().equals("Hủy")) {
            xoaRongTextFields();
            moTextFields(false);
            txtTenDangNhap.setEditable(false);
            psfMatKhau.setEditable(false);
            moButton(true);
            btnThemNV.setDisable(false);
            btnXoaNV.setDisable(false);
            btnSuaNV.setDisable(false);
            btnThemNV.setText("Thêm Nhân Viên");
            tblNV.setDisable(false);
            txtTim.setDisable(false);
            imgThemNV.setImage(new Image("/images/themnv.png"));
        }
    }

    public void suaAction() {
        if (btnSuaNV.getText().equals("Sửa Nhân Viên")) {
            if (tblNV.getSelectionModel().getSelectedItem() == null) {
                ThongBao.ThongBaoLoi("Bạn chưa chọn thành viên muốn sửa");
                return;
            }
            moTextFields(true);
            moButton(false);
            btnThemNV.setDisable(true);
            btnXoaNV.setDisable(true);
            btnSuaNV.setText("Hủy");
            imgSuaNV.setImage(new Image("/images/delete.png"));
            txtHoTen.requestFocus();
            txtTenDangNhap.setDisable(true);
            psfMatKhau.setDisable(true);
            txtTim.setDisable(true);
        } else if (btnSuaNV.getText().equals("Hủy")) {
            moTextFields(false);
            moButton(true);
            btnThemNV.setDisable(false);
            btnXoaNV.setDisable(false);
            btnSuaNV.setDisable(false);
            btnSuaNV.setText("Sửa Nhân Viên");
            imgSuaNV.setImage(new Image("/images/suanv.png"));
            txtTenDangNhap.setDisable(false);
            psfMatKhau.setDisable(false);
            txtTim.setDisable(false);
            xoaRongTextFields();
        }
    }

    public void xoaAction() {
        NhanVien nv = tblNV.getSelectionModel().getSelectedItem();
        if (nv == null) {
            ThongBao.ThongBaoLoi("Bạn chưa chọn thành viên muốn xóa");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn sẽ xóa bỏ nhân viên này?");
            Optional<ButtonType> opt = alert.showAndWait();
            if (opt.get().equals(ButtonType.OK)) {
                if (dao.delete(nv)) {
                    ThongBao.ThongBaoThanhCong("Xóa nhân viên thành công");
                    loadNV();
                    searchNhanVien();
                } else {
                    ThongBao.ThongBaoLoi("Không thể xóa nhân viên này");
                }
            }
        }
    }

    private void searchNhanVien() {
        //- Xử lý sự kiện cho nút tìm-
        //*1. Bọc ObservableList trong một FilteredList (ban đầu hiển thị tất cả dữ liệu)
        FilteredList<NhanVien> filteredData = new FilteredList<>(list, p -> true);
        //*2. Đặt bộ lọc Predicate bất cứ khi nào bộ lọc thay đổi
        txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nhanvien -> {
                // Nếu lọc văn bản rỗng, hiển thị tất cả mọi người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();// So sánh tên và họ của mỗi người với văn bản lọc.
                if (nhanvien.getHoten().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Bộ lọc khớp với họ.
                }

                if (nhanvien.getSoCMND().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Bộ lọc khớp với cmnd
                }
                if (nhanvien.getDiaChi().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Bộ lọc khớp với dia chi
                }
                if (nhanvien.getSoDienThoai().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Bộ lọc khớp với sdt
                } else if (nhanvien.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Bộ lọc khớp với email
                }
                return false; // khong khop 
            });
        });
        SortedList<NhanVien> sortedData = new SortedList<>(filteredData);// 3.Wrap các FilteredList trong một SortedList.
        sortedData.comparatorProperty().bind(tblNV.comparatorProperty());// 4. Liên kết bộ so sánh SortedList với bộ so sánh TableView.
        tblNV.setItems(sortedData);// 5. Thêm dữ liệu được sắp xếp (và lọc) vào bảng.
    }
}
