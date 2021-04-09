/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import daos.BanDAO;
import daos.MyEntityManagerFactory;
import daos.NhanVienDAO;
import entities.Ban;
import entities.NhanVien;
import java.time.LocalDate;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.controller.MainController;

/**
 *
 * @author minhh
 */
public class Starting extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage=primaryStage;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/view/MainFXML.fxml"));
        Parent root=loader.load();
        MainController controller=loader.getController();
        MainController.setLocalContentFXML("/ui/view/MainContentFXML.fxml");
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("CHƯƠNG TRÌNH VIETCOFFEE");
        mainStage.getIcons().add(new Image("/images/cafe.png"));
        mainStage.setResizable(false);
        mainStage.show();
        mainStage.setOnCloseRequest((e)->{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thoát chương trình?");
            Optional<ButtonType> opt=alert.showAndWait();
            if(opt.get()==ButtonType.CANCEL){
                e.consume();
            }else{
                MyEntityManagerFactory.getInstance().getEntityManager().close();
            }            
        });
    }
    public static void main(String[] args) { 
        NhanVienDAO daoNV=new NhanVienDAO();
        if(daoNV.getNhanVienTheoTenDangNhap("admin")==null){
            daoNV.save(new NhanVien("Trần Minh Hiếu", "123456789", LocalDate.of(1998, 11, 20), "Bà Rịa Vũng Tàu", "0123456789", "hieu@gmail.com", "AD", "admin", "123456"));
        }
        BanDAO daoBan=new BanDAO();
        if(daoBan.getAll(Ban.class).isEmpty()){
            for(int i=1; i<=15; i++){
                Ban ban=new Ban();
                ban.setMaBan(i);
                ban.setTenBan("Bàn "+Integer.toString(i));
                ban.setTrangThai(false);
                daoBan.save(ban);
            }
        }
        launch(args);
    }
    
    public static Stage mainStage;
}
