package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import makerspace.MakerSpace;

import java.io.IOException;

public class MainController extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Fill Makerspace
        MakerSpace.createUser("user","user","user");
        MakerSpace.createSuperUser("admin","admin","admin");
        MakerSpace.addComponent("Arduino",20);
        MakerSpace.addComponent("Cable",20);
        MakerSpace.addComponent("Raspberry Pi",20);
        MakerSpace.addBooking(MakerSpace.getUserById(1),"2021-05-01","10:00",2);

        launch();
    }
}