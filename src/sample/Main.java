package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.Observable;

public class Main extends Application {

    @FXML
    private ComboBox<?> cbMode;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Лабораторная по ТИ №3");
        primaryStage.setScene(new Scene(root, 640, 380));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
