/*
 * Copyright (c) 2023 Amine Zouaoui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import makerspace.MakerSpace;
import makerspace.SuperUser;
import makerspace.User;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Text wrongField;

    @FXML
    public void initialize() {

    }

    public void login() throws Exception {
        if (this.isValidated()) {
            wrongField.setStyle("-fx-fill: green;");
            wrongField.setText("Login Success");
            User user = MakerSpace.getUserByUsername(username.getText());

            if(user!=null && user.getPassword().equals(password.getText())){
                MakerSpace.setActiveUser(user);
                if(user instanceof SuperUser){
                    showSuperUserStage();
                }else{
                    showUserStage();
                }
            }else{
                wrongField.setStyle("-fx-fill: red;");
                wrongField.setText("Username or password is incorrect.");
            }
        }
    }

    private boolean isValidated() {
        if (username.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Username text field cannot be blank.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Password text field cannot be blank.");
            password.requestFocus();
        }  else {
            return true;
        }
        return false;
    }

    public void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.show();
    }

    public void showUserStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Stage");
        stage.show();
    }
    public void showSuperUserStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/SuperUserView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Stage");
        stage.show();
    }

    public void handle(KeyEvent event) throws Exception {
        if(event.getCode()==KeyCode.ENTER){
            login();
        }
    }


}
