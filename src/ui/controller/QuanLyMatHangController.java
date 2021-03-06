/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import daos.MatHangDAO;
import entities.MatHang;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ui.ThongBao;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class QuanLyMatHangController implements Controller {

    @FXML
    private TableView<MatHang> tblMatHang;
    @FXML
    private TableColumn<MatHang, Integer> colMaHang;
    @FXML
    private TableColumn<MatHang, String> colTenHang;
    @FXML
    private TableColumn<MatHang, String> colLoaiHang;
    @FXML
    private TableColumn<MatHang, String> colDonViTinh;
    @FXML
    private TableColumn<MatHang, Double> colDonGia;
    @FXML
    private JFXTextField txtMaHang;
    @FXML
    private JFXTextField txtTenHang;
    @FXML
    private JFXButton btnThem;
    @FXML
    private ImageView imgThemNV;
    @FXML
    private JFXButton btnLuu;
    @FXML
    private JFXButton btnSua;
    @FXML
    private ImageView imgSuaNV;
    @FXML
    private JFXButton btnXoa;
    @FXML
    private ComboBox<String> cbbLoaiHang;
    @FXML
    private JFXTextField txtDonViTinh;
    @FXML
    private JFXTextField txtDonGia;
    @FXML
    private TextField txtTim;

    private final StringProperty valueOfCombobox;
    @FXML
    private Label lblQuanLyMatHang;

    ObservableList<MatHang> list = FXCollections.observableArrayList();
    MatHangDAO dao = new MatHangDAO();

    public QuanLyMatHangController() {
        this.valueOfCombobox = new SimpleStringProperty();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbbLoaiHang.getItems().setAll("M??n ??n", "????? u???ng");
        valueOfCombobox.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbbLoaiHang.getSelectionModel().select(newValue);
                lblQuanLyMatHang.setText("QU???N L?? " + newValue.toUpperCase());
                initCol();
                loadHang();
                searchMon();
            }
        });

        txtDonGia.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d")) {
                txtDonGia.setText(newValue.replaceAll("[^\\d]*", ""));
            }
        });

        btnThem.setOnAction((e) -> {
            themAction();
        });

        btnLuu.setOnAction((e) -> {
            luuActions();
        });

        btnSua.setOnAction((e) -> {
            suaAction();
        });

        btnXoa.setOnAction((e) -> {
            xoaAction();
        });
    }

    public void initCol() {
        colMaHang.setCellValueFactory(new PropertyValueFactory<>("maHang"));
        colTenHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
        colLoaiHang.setCellValueFactory(new PropertyValueFactory<>("loaiMatHang"));
        colDonViTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
    }

    public void loadHang() {
        list.clear();
        list.addAll(dao.getMatHangTheoLoaiHang(cbbLoaiHang.getValue()));
        tblMatHang.setItems(list);
        tblMatHang.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            MatHang mh = tblMatHang.getSelectionModel().getSelectedItem();
            if (mh != null) {
                txtMaHang.setText(Integer.toString(mh.getMaHang()));
                txtTenHang.setText(mh.getTenMatHang());
                txtDonViTinh.setText(mh.getDonViTinh());
                txtDonGia.setText(Double.toString(mh.getDonGia()));
            }
        });
    }

    public StringProperty getValueOfCombobox() {
        return valueOfCombobox;
    }

    public void setValueOfCombobox(String valueOfCombobox) {
        this.valueOfCombobox.setValue(valueOfCombobox);
    }

    public void themAction() {
        if (btnThem.getText().equals("Th??m")) {
            xoaRongTextfields();
            int mahang = dao.getIdentityCurrent();
            if (mahang != dao.getIdentitySeed() || !dao.getAll(MatHang.class).isEmpty()) {
                mahang++;
            }
            txtMaHang.setText(Integer.toString(mahang));
            txtTenHang.requestFocus();
            setEditableTextfields(true);
            btnThem.setText("H???y");
            disableButton(true);
            btnThem.setDisable(false);
            btnLuu.setDisable(false);
        } else if (btnThem.getText().equals("H???y")) {
            btnThem.setText("Th??m");
            setEditableTextfields(false);
            xoaRongTextfields();
            disableButton(false);
            btnLuu.setDisable(true);
        }
    }

    public void luuActions() {
        String tenMatHang = txtTenHang.getText();
        String donViTinh = txtDonViTinh.getText();
        String loaiMatHang = cbbLoaiHang.getValue();
        String donGia = txtDonGia.getText();
        if (txtTenHang.getText().isEmpty()) {
            ThongBao.ThongBaoLoi("Ch??a nh???p t??n h??ng");
            txtTenHang.requestFocus();
            return;
        }
        if (txtDonViTinh.getText().isEmpty()) {
            ThongBao.ThongBaoLoi("Ch??a nh???p ????n v??? t??nh");
            txtDonViTinh.requestFocus();
            return;
        }
        if (!txtDonViTinh.getText().matches("[a-zA-Za??????????????????????????????????????????????e??????????????????????????????i????????????o??????????????????????????????????????????????u?????????????????????????????"
                + "y????????????????A??????????????????????????????????????????????E??????????????????????????????I????????????O??????????????????????????????????????????????U?????????????????????????????Y????????????????\\s\\']+")) {
            ThongBao.ThongBaoLoi("????n v??? t??nh ch??? ch???a ch???");
            txtDonViTinh.requestFocus();
            return;
        }
        if (txtDonGia.getText().isEmpty()) {
            ThongBao.ThongBaoLoi("Ch??a nh???p ????n gi??");
            txtDonGia.requestFocus();
            return;
        }
        if (Double.parseDouble(donGia) <= 0) {
            ThongBao.ThongBaoLoi("????n gi?? ph???i l???n h??n b???ng 0");
            txtDonGia.requestFocus();
            return;
        }
        if (btnThem.getText().equals("H???y")) {
            MatHang mh = new MatHang(tenMatHang, loaiMatHang, donViTinh, Double.parseDouble(donGia));
            if (dao.save(mh)) {
                ThongBao.ThongBaoThanhCong("Th??m " + getValueOfCombobox().get() + " th??nh c??ng");
                loadHang();
                themAction();
                searchMon();
            }
        } else if (btnSua.getText().equals("H???y")) {
            MatHang mh = tblMatHang.getSelectionModel().getSelectedItem();
            if (mh != null) {
                mh.setTenMatHang(tenMatHang);
                mh.setDonViTinh(donViTinh);
                mh.setDonGia(Double.parseDouble(donGia));
                if (dao.update(mh)) {
                    ThongBao.ThongBaoThanhCong("Ch???nh s???a th??nh c??ng");
                    loadHang();
                    suaAction();
                    searchMon();
                }
            }
        }
    }

    public void setEditableTextfields(boolean b) {
        txtTenHang.setEditable(b);
        txtDonViTinh.setEditable(b);
        txtDonGia.setEditable(b);
    }

    public void xoaRongTextfields() {
        txtMaHang.setText("");
        txtTenHang.setText("");
        txtDonViTinh.setText("");
        txtDonGia.setText("");
        txtTim.setText("");
    }

    public void disableButton(boolean b) {
        btnThem.setDisable(b);
        btnLuu.setDisable(b);
        btnSua.setDisable(b);
        btnXoa.setDisable(b);
        txtTim.setDisable(b);
    }

    private void searchMon() {
        FilteredList<MatHang> filteredData = new FilteredList<>(list, p -> true);
        txtTim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mh -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return mh.getTenMatHang().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<MatHang> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblMatHang.comparatorProperty());
        tblMatHang.setItems(sortedData);
    }

    private void suaAction() {
        MatHang mh = tblMatHang.getSelectionModel().getSelectedItem();
        if (btnSua.getText().equals("S???a")) {
            if (mh == null) {
                ThongBao.ThongBaoLoi("Ch??a ch???n m???c c???n s???a");
                return;
            }
            disableButton(true);
            setEditableTextfields(true);
            btnSua.setText("H???y");
            btnSua.setDisable(false);
            btnLuu.setDisable(false);
        } else if (btnSua.getText().equals("H???y")) {
            disableButton(false);
            setEditableTextfields(false);
            btnSua.setText("S???a");
            btnLuu.setDisable(true);
            tblMatHang.getSelectionModel().select(null);
            xoaRongTextfields();
        }
    }

    private void xoaAction() {
        MatHang mh = tblMatHang.getSelectionModel().getSelectedItem();
        if (mh != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("B???n c?? mu???n x??a " + getValueOfCombobox().get() + " n??y?");
            Optional<ButtonType> opt = alert.showAndWait();
            if (opt.get() == ButtonType.OK) {
                if (dao.delete(mh)) {
                    ThongBao.ThongBaoThanhCong("X??a " + getValueOfCombobox().get() + " th??nh c??ng");
                    loadHang();
                    xoaRongTextfields();
                    searchMon();
                } else {
                    ThongBao.ThongBaoLoi("Kh??ng th??? x??a");
                }
            }
        } else {
            ThongBao.ThongBaoLoi("Ch??a ch???n m???c c???n x??a");
        }
    }
}
