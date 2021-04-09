/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import com.jfoenix.controls.JFXTextField;
import daos.BanDAO;
import daos.ChiTietHoaDonDAO;
import daos.HoaDonDAO;
import daos.MatHangDAO;
import daos.NhanVienDAO;
import entities.Ban;
import entities.ChiTietHoaDon;
import entities.HoaDon;
import entities.MatHang;
import entities.NhanVien;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import ui.ThongBao;
import ui.viewmodel.HoaDonViewModel;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class DatMonController implements Controller {

    @FXML
    private FlowPane flowpaneBan;
    @FXML
    private DatePicker dpNgayLap;
    @FXML
    private Label lblNhanVienLap;
    @FXML
    private Button btnDatBan;
    @FXML
    private Label lblTongTien;
    @FXML
    private Button btnThanhToan;
    @FXML
    private Spinner<Integer> spnSoLuongHD;
    @FXML
    private Button btnSua;
    @FXML
    private TableView<HoaDonViewModel> tblHoaDon;
    @FXML
    private TableColumn<HoaDonViewModel, Integer> colSTT;
    @FXML
    private TableColumn<HoaDonViewModel, String> colTenHangHD;
    @FXML
    private TableColumn<HoaDonViewModel, Integer> colSoLuong;
    @FXML
    private TableColumn<HoaDonViewModel, Double> colDonGiaHD;
    @FXML
    private TableColumn<HoaDonViewModel, Double> colThanhTien;
    @FXML
    private TextField txtTimMon;
    @FXML
    private TableView<MatHang> tblHang;
    @FXML
    private TableColumn<MatHang, Integer> colMaHang;
    @FXML
    private TableColumn<MatHang, String> colTenHang;
    @FXML
    private TableColumn<MatHang, Double> colDonGiaHang;
    @FXML
    private Button btnThem;
    @FXML
    private Spinner<Integer> spnSoLuong;
    @FXML
    private Button btnHuy;
    @FXML
    private JFXTextField txtMaHang;

    BanDAO daoBan = new BanDAO();
    ChiTietHoaDonDAO daoct = new ChiTietHoaDonDAO();
    HoaDonDAO daohd = new HoaDonDAO();
    MatHangDAO daoha = new MatHangDAO();
    @FXML
    private Label lblBan;
    @FXML
    private Button btnHuyBan;

    ObservableList<HoaDonViewModel> list = FXCollections.observableArrayList();
    ObservableList<MatHang> listHang = FXCollections.observableArrayList();
    List<Button> listBtn = new ArrayList<>();
    @FXML
    private TableColumn<HoaDonViewModel, String> colDVTinh;

    int stt = 0;
    double tongtien = 0;
    Button btn = null;
    HoaDon mainhd = null;
    @FXML
    private MenuItem menuThemBan;
    @FXML
    private MenuItem menuXoaBan;
    @FXML
    private ContextMenu contextmenuBan;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NhanVien nv=MainController.getNhanVien().get();
        if(nv.getLoaiNhanVien().equals("NV")){
            menuThemBan.setVisible(false);
            menuXoaBan.setVisible(false);
        }
        initColHang();
        loadHang();
        loadBan();
        searchMon();

        btnDatBan.setOnAction((e) -> {
            datBan();
        });

        tblHang.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            eventClickTableHang();
        });

        btnThem.setOnAction((e) -> {
            themCTPhieuThue();
        });

        btnHuy.setOnAction((e) -> {
            huyMon();
        });

        btnSua.setOnAction((e) -> {
            suaSoLuong();
        });

        btnThanhToan.setOnAction((e) -> {
            thanhToan();
        });

        btnHuyBan.setOnAction((e) -> {
            huyBan();
        });

        menuThemBan.setOnAction((e) -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Thêm bàn");
            dialog.setHeaderText(null);
            dialog.setContentText("Nhập số lượng bàn cần thêm:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent((r) -> {
                int count=daoBan.getAll(Ban.class).size();
                for (int i = count+1; i <= count+Integer.parseInt(r); i++) {
                    Ban ban = new Ban();
                    ban.setMaBan(i);
                    ban.setTenBan("Bàn " + Integer.toString(i));
                    ban.setTrangThai(false);
                    daoBan.save(ban);
                }
                loadBan();
            });
        });
        
        menuXoaBan.setOnAction((e) -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Xóa bàn");
            dialog.setHeaderText(null);
            dialog.setContentText("Nhập số lượng bàn cần xóa:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent((r) -> {
                int count=daoBan.getAll(Ban.class).size();
                for (int i = count; i > count-Integer.parseInt(r); i--) {
                    Ban ban = daoBan.getBanTheoMa(i);
                    daoBan.delete(ban);
                }
                loadBan();
            });
        });
    }

    // viết hàm 
    public void loadBan() {
        List<Ban> listBan = daoBan.getAll(Ban.class);
        flowpaneBan.getChildren().clear();
        if (!listBan.isEmpty()) {
            listBan.forEach((ban) -> {
                Button btnBan = new Button(ban.getTenBan());
                btnBan.setPrefSize(65, 60);
                btnBan.setId(Integer.toString(ban.getMaBan()));
                btnBan.setGraphic(new ImageView(new Image("/images/table.png")));
                btnBan.setContentDisplay(ContentDisplay.TOP);
                if (!ban.getTrangThai()) {
                    btnBan.setAccessibleText("Trống");
                    btnBan.setStyle("-fx-background-color: lightgreen");
                } else {
                    btnBan.setAccessibleText("Đã đặt");
                    btnBan.setStyle("-fx-background-color: red");
                }
                btnBan.setOnAction((e) -> {
                    eventClickBan(e);
                });
                listBtn.add(btnBan);
                flowpaneBan.getChildren().add(btnBan);
            });
        }
    }

    private void eventClickBan(ActionEvent event) {
        btn = (Button) ((Node) event.getSource());
        String tenBan = daoBan.getBanTheoMa(Integer.parseInt(btn.getId())).getTenBan();
        mainhd = daohd.getHoaDonTheoBan(Integer.parseInt(btn.getId()));
        lblBan.setText(tenBan);
        initColHD();
        loadCTHD();
        List<ChiTietHoaDon> listct = daoct.getCTHoaDonTheoBan(mainhd);
        if (btn.getAccessibleText().equals("Đã đặt") && !listct.isEmpty()) {
            btnDatBan.setDisable(true);
            disableButton(false);
            btnHuyBan.setDisable(true);
            btnThanhToan.setDisable(false);
            dpNgayLap.setValue(LocalDate.now());
            lblNhanVienLap.setText(new NhanVienDAO().getNhanVienTheoTenDangNhap(mainhd.getMaNhanVien().getTenDangNhap()).getHoten());
        } else if (btn.getAccessibleText().equals("Đã đặt")) {
            btnDatBan.setDisable(true);
            disableButton(false);
            btnThanhToan.setDisable(true);
            dpNgayLap.setValue(LocalDate.now());
            lblNhanVienLap.setText(new NhanVienDAO().getNhanVienTheoTenDangNhap(mainhd.getMaNhanVien().getTenDangNhap()).getHoten());
        } else {
            btnDatBan.setDisable(false);
            dpNgayLap.setValue(null);
            lblNhanVienLap.setText("");
            disableButton(true);
            btnThanhToan.setDisable(true);
        }
    }

    public void initColHD() {
        colSTT.setCellValueFactory(new PropertyValueFactory<>("stt"));
        colTenHangHD.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGiaHD.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        colDVTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
    }

    public void initColHang() {
        colMaHang.setCellValueFactory(new PropertyValueFactory<>("maHang"));
        colTenHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
        colDonGiaHang.setCellValueFactory(new PropertyValueFactory<>("donGia"));
    }

    public void loadHang() {
        listHang.clear();
        listHang.addAll(daoha.getAll(MatHang.class));
        tblHang.setItems(listHang);
    }

    public void loadCTHD() {
        list.clear();
        stt = 0;
        tongtien = 0;
        List<ChiTietHoaDon> listct = daoct.getCTHoaDonTheoBan(daohd.getHoaDonTheoBan(Integer.parseInt(btn.getId())));
        listct.forEach((ct) -> {
            HoaDonViewModel vm = new HoaDonViewModel();
            vm.setStt(++stt);
            vm.setMaCTHD(ct.getMaChiTietHoaDon());
            vm.setTenHang(ct.getMaHang().getTenMatHang());
            vm.setSoLuong(ct.getSoLuong());
            vm.setDonGia(ct.getMaHang().getDonGia());
            vm.setThanhTien(vm.getDonGia() * vm.getSoLuong());
            vm.setDonViTinh(ct.getMaHang().getDonViTinh());
            tongtien += vm.getThanhTien();
            mainhd.setTongTien(tongtien);
            daohd.update(mainhd);
            list.add(vm);
        });
        tblHoaDon.setItems(list);
        lblTongTien.setText(Double.toString(tongtien));
        tblHoaDon.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            HoaDonViewModel vm = tblHoaDon.getSelectionModel().getSelectedItem();
            if (vm != null) {
                spnSoLuongHD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, vm.getSoLuong()));
            }
        });
    }

    public void disableButton(boolean b) {
        btnHuy.setDisable(b);
        btnSua.setDisable(b);
        //btnThanhToan.setDisable(b);
        btnThem.setDisable(b);
        btnHuyBan.setDisable(b);
        spnSoLuong.setDisable(b);
        spnSoLuongHD.setDisable(b);
    }

    public void xoaRong() {
        lblNhanVienLap.setText("");
        lblBan.setText("Bàn 00");
        lblTongTien.setText("0.0");
        tblHoaDon.setItems(null);
        dpNgayLap.setValue(null);
    }

    private void searchMon() {
        FilteredList<MatHang> filteredData = new FilteredList<>(listHang, p -> true);
        txtTimMon.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mh -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return mh.getTenMatHang().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<MatHang> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblHang.comparatorProperty());
        tblHang.setItems(sortedData);
    }

    public void datBan() {
        if (btn != null) {
            NhanVien nv = MainController.getNhanVien().getValue();
            Ban ban = daoBan.getBanTheoMa(Integer.parseInt(btn.getId()));
            dpNgayLap.setValue(LocalDate.now());
            lblNhanVienLap.setText(nv.getHoten());
            HoaDon hd = new HoaDon();
            hd.setNgayLap(dpNgayLap.getValue());
            hd.setTrangThaiThanhToan(false);
            hd.setMaBan(ban);
            hd.setMaNhanVien(nv);
            hd.setTongTien(0);
            if (daohd.save(hd)) {
                ban.setTrangThai(true);
                if (daoBan.update(ban)) {
                    loadBan();
                    btnDatBan.setDisable(true);
                    disableButton(false);
                    mainhd = daohd.getHoaDonTheoBan(Integer.parseInt(btn.getId()));
                }
            }
        } else {
            ThongBao.ThongBaoLoi("Chưa chọn bàn");
        }
    }

    public void eventClickTableHang() {
        MatHang mh = tblHang.getSelectionModel().getSelectedItem();
        if (mh != null) {
            txtMaHang.setText(Integer.toString(mh.getMaHang()));
            spnSoLuong.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        }
    }

    public void themCTPhieuThue() {
        MatHang mh = tblHang.getSelectionModel().getSelectedItem();
        if (mh != null) {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setSoLuong(spnSoLuong.getValue());
            ct.setThanhTien(spnSoLuong.getValue() * mh.getDonGia());
            ct.setMaHang(mh);
            ct.setMaHoaDon(mainhd);
            if (daoct.save(ct)) {
                loadCTHD();
                btnHuyBan.setDisable(true);
                btnThanhToan.setDisable(false);
                txtMaHang.setText("");
                txtTimMon.setText("");
                spnSoLuong.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
            }
        } else {
            ThongBao.ThongBaoLoi("Bạn chưa chọn món");
        }
    }

    public void huyMon() {
        HoaDonViewModel vm = tblHoaDon.getSelectionModel().getSelectedItem();
        if (vm != null) {
            if (daoct.delete(daoct.getCTHoaDonTheoMa(vm.getMaCTHD()))) {
                loadCTHD();
            }
        }
    }

    private void suaSoLuong() {
        HoaDonViewModel vm = tblHoaDon.getSelectionModel().getSelectedItem();
        if (vm != null) {
            ChiTietHoaDon ct = daoct.getCTHoaDonTheoMa(vm.getMaCTHD());
            ct.setSoLuong(spnSoLuongHD.getValue());
            if (daoct.update(ct)) {
                loadCTHD();
                spnSoLuongHD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
                tblHoaDon.getSelectionModel().select(null);
            }
        } else {
            ThongBao.ThongBaoLoi("Chưa chọn mục cần sửa");
        }
    }

    private void thanhToan() {
        mainhd.setTrangThaiThanhToan(true);
        if (daohd.update(mainhd)) {
            ThongBao.ThongBaoThanhCong("Thanh toán thành công\n"
                    + "Bàn: " + btn.getText()
                    + "\nTổng cộng: " + tongtien + " VND");
            Ban ban = daoBan.getBanTheoMa(Integer.parseInt(btn.getId()));
            ban.setTrangThai(false);
            if (daoBan.update(ban)) {
                loadBan();
                disableButton(true);
                btnDatBan.setDisable(false);
                btnThanhToan.setDisable(true);
                xoaRong();
            }
        }
    }

    private void huyBan() {
        HoaDon hd = daohd.getHoaDonTheoBan(Integer.parseInt(btn.getId()));
        if (daohd.delete(hd)) {
            Ban ban = daoBan.getBanTheoMa(Integer.parseInt(btn.getId()));
            ban.setTrangThai(false);
            if (daoBan.update(ban)) {
                loadBan();
                xoaRong();
                disableButton(true);
                btnDatBan.setDisable(false);
            }
        }
    }
}
