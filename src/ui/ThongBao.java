package ui;

import javafx.scene.control.Alert;

/**
 * Mô tả: dùng để tạo thông báo
 * @author Trần Minh Hiếu
 */
public class ThongBao {

    public static void ThongBaoLoi(String content) {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setHeaderText(null);
        alert1.setContentText(content);
        alert1.showAndWait();
    }
    public static void ThongBaoThanhCong(String content) {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setHeaderText(null);
        alert1.setContentText(content);
        alert1.showAndWait();
    }
}
