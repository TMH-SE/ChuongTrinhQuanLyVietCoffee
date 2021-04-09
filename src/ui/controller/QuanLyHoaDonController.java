/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import com.jfoenix.controls.JFXDatePicker;
import daos.HoaDonDAO;
import entities.HoaDon;
import entities.NhanVien;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author minhh
 */
public class QuanLyHoaDonController implements Controller {

    @FXML
    private TableView<HoaDon> tblHoaDon;
    @FXML
    private TableColumn<HoaDon, Integer> colMaHoaDon;
    @FXML
    private TableColumn<HoaDon, NhanVien> colNhanVien;
    @FXML
    private TableColumn<HoaDon, LocalDate> colNgayLap;
    @FXML
    private TableColumn<HoaDon, Double> colTongTien;
    @FXML
    private Label lblTongDoanhThu;
    @FXML
    private JFXDatePicker dpNgayBD;
    @FXML
    private JFXDatePicker dpNgayKT;
    
    ObservableList<HoaDon> list=FXCollections.observableArrayList();
    double tongDoanhThu=0;
    
    HoaDonDAO dao=new HoaDonDAO();
    @FXML
    private Button btnXuat;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpNgayKT.setDayCellFactory((DatePicker param) -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(item.isAfter(LocalDate.now())){
                    setDisable(true);
                }
            }
        });
        dpNgayBD.setDayCellFactory((DatePicker param) -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(item.isAfter(LocalDate.now())){
                    setDisable(true);
                }
            }
        });
        btnXuat.setOnAction((e)->{
            initCol();
            loadHoaDon();
        });
    }
    
    public void initCol(){
        colMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
        colNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colNgayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
    }
    
    public void loadHoaDon(){
        list.clear();
        tongDoanhThu=0;
        list.addAll(dao.getHoaDonTheoNgay(dpNgayBD.getValue(), dpNgayKT.getValue()));
        list.forEach((l)->{
            tongDoanhThu+=l.getTongTien();
        });
        lblTongDoanhThu.setText(Double.toString(tongDoanhThu));
        tblHoaDon.setItems(list);
    }
}
