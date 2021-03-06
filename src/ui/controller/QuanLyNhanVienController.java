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
                alert.setContentText("*B???t bu???c nh???p h??? ng?????i d??ng");
                alert.showAndWait();
                txtHoTen.requestFocus();
                return;
            }
            //^[a-zA-Z]+$
            if (!ho.matches("[a-zA-Za??????????????????????????????????????????????e??????????????????????????????i????????????o??????????????????????????????????????????????u?????????????????????????????"
                    + "y????????????????A??????????????????????????????????????????????E??????????????????????????????I????????????O??????????????????????????????????????????????U?????????????????????????????Y????????????????\\s\\']+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("H??? ng?????i d??ng ch??? ch???a ch???");
                alert.showAndWait();
                txtHoTen.requestFocus();
                return;
            }
            if (soCMND.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*B???t bu???c nh???p s??? CMND");
                alert.showAndWait();
                txtSoCMND.requestFocus();
                return;
            }
            if (!soCMND.matches("\\d{9,12}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("S??? CMND l?? d??y s??? t??? 9 ?????n 12 ch??? s???");
                alert.showAndWait();
                txtSoCMND.selectAll();
                txtSoCMND.requestFocus();
                return;
            }
            if (ngaySinh == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*B???t bu???c ch???n ng??y sinh");
                alert.showAndWait();
                dpNgaySinh.requestFocus();
                return;
            }
            if (Period.between(ngaySinh, LocalDate.now()).getYears() < 12) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Th??nh vi??n ph???i 12 tu???i tr??? l??n");
                alert.showAndWait();
                dpNgaySinh.requestFocus();
                return;
            }
            if (diaChi.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*B???t bu???c nh???p ?????a ch??? ng?????i d??ng");
                alert.showAndWait();
                txtDiaChi.requestFocus();
                return;
            }
            if (soDienThoai.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*B???t bu???c nh???p s??? ??i???n tho???i ng?????i d??ng");
                alert.showAndWait();
                txtSoDienThoai.requestFocus();
                return;
            }
            if (!soDienThoai.matches("^0\\d{9,10}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("S??? ??i???n tho???i b???t ?????u b???i ch??? s??? 0 v?? c?? ????? d??i t??? 10-11 ch??? s???");
                alert.showAndWait();
                txtSoDienThoai.selectAll();
                txtSoDienThoai.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("*B???t bu???c nh???p email ng?????i d??ng");
                alert.showAndWait();
                txtEmail.requestFocus();
                return;
            }
            if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Email kh??ng ????ng ?????nh d???ng (VD: admin@gmail.com)");
                alert.showAndWait();
                txtEmail.selectAll();
                txtEmail.requestFocus();
                return;
            }
            if (btnSuaNV.getText().equals("H???y")) {
                NhanVien nv = new NhanVien(manv, ho, soCMND, ngaySinh, diaChi, soDienThoai, email, "NV", tendn, mk);
                if (dao.update(nv)) {
                    ThongBao.ThongBaoThanhCong("C???p nh???t th??nh vi??n th??nh c??ng");
                    loadNV();
                    suaAction();
                    searchNhanVien();
                } else {
                    ThongBao.ThongBaoLoi("Kh??ng th??? ch???nh s???a th??ng tin nh??n vi??n");
                }
            }
            if (btnThemNV.getText().equals("H???y")) {
//                if (bll.NguoiDungBLL.checkSoCMND(soCMND) == false) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText(null);
//                    alert.setContentText("S??? CMND n??y ???? ????ng k?? th??nh vi??n");
//                    alert.showAndWait();
//                    txtSoCMND.selectAll();
//                    txtSoCMND.requestFocus();
//                    return;
//                }
                if (tendn.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Vui l??ng nh???p t??n ????ng nh???p");
                    alert.showAndWait();
                    txtTenDangNhap.requestFocus();
                    return;
                }
                if (!tendn.matches("[a-zA-Z\\d]{4,}")) {
                    ThongBao.ThongBaoLoi("T??n ????ng nh???p ch??? ch???a ch??? ho???c s??? v?? t???i thi???u 4 k?? t???");
                    txtTenDangNhap.requestFocus();
                    return;
                }
//                if (!bll.TaiKhoanBLL.checkTenDangNhap(tendn)) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText(null);
//                    alert.setContentText("T??n ????ng nh???p ???? c?? ng?????i s??? d???ng");
//                    alert.showAndWait();
//                    txtTenDN.requestFocus();
//                    return;
//                }
                if (mk.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Vui l??ng nh???p m???t kh???u");
                    alert.showAndWait();
                    psfMatKhau.requestFocus();
                    return;
                }
                if (!mk.matches(".{6,}")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("M???t kh???u c?? ????? d??i t??? 6 k?? t??? tr??? l??n");
                    alert.showAndWait();
                    psfMatKhau.selectAll();
                    psfMatKhau.requestFocus();
                    return;
                } else {
                    NhanVien nv = new NhanVien(ho, soCMND, ngaySinh, diaChi, soDienThoai, email, "NV", tendn, mk);
                    if (dao.save(nv)) {                       
                        ThongBao.ThongBaoThanhCong("Th??m th??nh vi??n th??nh c??ng");
                        loadNV();
                        themAction();
                        searchNhanVien();
                    } else {
                        ThongBao.ThongBaoLoi("Kh??ng th??? th??m nh??n vi??n");
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
        if (btnThemNV.getText().equals("Th??m Nh??n Vi??n")) {
            moTextFields(true);
            xoaRongTextFields();
            int manv = dao.getIdentityCurrent()+1;
            txtMaNV.setText(Integer.toString(manv));
            txtTenDangNhap.setEditable(true);
            psfMatKhau.setEditable(true);
            moButton(false);
            btnSuaNV.setDisable(true);
            btnXoaNV.setDisable(true);
            btnThemNV.setText("H???y");
            tblNV.setDisable(true);
            txtTim.setDisable(true);
            imgThemNV.setImage(new Image("/images/delete.png"));
            txtHoTen.requestFocus();
        } else if (btnThemNV.getText().equals("H???y")) {
            xoaRongTextFields();
            moTextFields(false);
            txtTenDangNhap.setEditable(false);
            psfMatKhau.setEditable(false);
            moButton(true);
            btnThemNV.setDisable(false);
            btnXoaNV.setDisable(false);
            btnSuaNV.setDisable(false);
            btnThemNV.setText("Th??m Nh??n Vi??n");
            tblNV.setDisable(false);
            txtTim.setDisable(false);
            imgThemNV.setImage(new Image("/images/themnv.png"));
        }
    }

    public void suaAction() {
        if (btnSuaNV.getText().equals("S???a Nh??n Vi??n")) {
            if (tblNV.getSelectionModel().getSelectedItem() == null) {
                ThongBao.ThongBaoLoi("B???n ch??a ch???n th??nh vi??n mu???n s???a");
                return;
            }
            moTextFields(true);
            moButton(false);
            btnThemNV.setDisable(true);
            btnXoaNV.setDisable(true);
            btnSuaNV.setText("H???y");
            imgSuaNV.setImage(new Image("/images/delete.png"));
            txtHoTen.requestFocus();
            txtTenDangNhap.setDisable(true);
            psfMatKhau.setDisable(true);
            txtTim.setDisable(true);
        } else if (btnSuaNV.getText().equals("H???y")) {
            moTextFields(false);
            moButton(true);
            btnThemNV.setDisable(false);
            btnXoaNV.setDisable(false);
            btnSuaNV.setDisable(false);
            btnSuaNV.setText("S???a Nh??n Vi??n");
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
            ThongBao.ThongBaoLoi("B???n ch??a ch???n th??nh vi??n mu???n x??a");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("B???n c?? ch???c ch???n s??? x??a b??? nh??n vi??n n??y?");
            Optional<ButtonType> opt = alert.showAndWait();
            if (opt.get().equals(ButtonType.OK)) {
                if (dao.delete(nv)) {
                    ThongBao.ThongBaoThanhCong("X??a nh??n vi??n th??nh c??ng");
                    loadNV();
                    searchNhanVien();
                } else {
                    ThongBao.ThongBaoLoi("Kh??ng th??? x??a nh??n vi??n n??y");
                }
            }
        }
    }

    private void searchNhanVien() {
        //- X??? l?? s??? ki???n cho n??t t??m-
        //*1. B???c ObservableList trong m???t FilteredList (ban ?????u hi???n th??? t???t c??? d??? li???u)
        FilteredList<NhanVien> filteredData = new FilteredList<>(list, p -> true);
        //*2. ?????t b??? l???c Predicate b???t c??? khi n??o b??? l???c thay ?????i
        txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nhanvien -> {
                // N???u l???c v??n b???n r???ng, hi???n th??? t???t c??? m???i ng?????i
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();// So s??nh t??n v?? h??? c???a m???i ng?????i v???i v??n b???n l???c.
                if (nhanvien.getHoten().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // B??? l???c kh???p v???i h???.
                }

                if (nhanvien.getSoCMND().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // B??? l???c kh???p v???i cmnd
                }
                if (nhanvien.getDiaChi().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // B??? l???c kh???p v???i dia chi
                }
                if (nhanvien.getSoDienThoai().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // B??? l???c kh???p v???i sdt
                } else if (nhanvien.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // B??? l???c kh???p v???i email
                }
                return false; // khong khop 
            });
        });
        SortedList<NhanVien> sortedData = new SortedList<>(filteredData);// 3.Wrap c??c FilteredList trong m???t SortedList.
        sortedData.comparatorProperty().bind(tblNV.comparatorProperty());// 4. Li??n k???t b??? so s??nh SortedList v???i b??? so s??nh TableView.
        tblNV.setItems(sortedData);// 5. Th??m d??? li???u ???????c s???p x???p (v?? l???c) v??o b???ng.
    }
}
