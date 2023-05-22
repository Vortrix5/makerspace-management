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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import makerspace.MakerSpace;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RegisterController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Text wrongField;

    @FXML
    private Button registerButton;

    public void register() throws IOException {
        if (this.isValidated()) {
            wrongField.setStyle("-fx-fill: green;");
            wrongField.setText("Registration Success");
            MakerSpace.createUser(username.getText(), email.getText(), password.getText());
            MakerSpace.setActiveUser(MakerSpace.getUserByUsername(username.getText()));
            clearForm();
            showUserStage();
        }
    }

    private boolean isValidated() {

        if (firstName.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("First name text field cannot be blank.");
            firstName.requestFocus();
        } else if (firstName.getText().length() < 2 || firstName.getText().length() > 25) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("First name text field cannot be less than 2 and greator than 25 characters.");
            firstName.requestFocus();
        } else if (lastName.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Last name text field cannot be blank.");
            lastName.requestFocus();
        } else if (lastName.getText().length() < 2 || lastName.getText().length() > 25) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Last name text field cannot be less than 2 and greator than 25 characters.");
            lastName.requestFocus();
        } else if (email.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Email text field cannot be blank.");
            email.requestFocus();
        } else if (email.getText().length() < 5 || email.getText().length() > 45) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Email text field cannot be less than 5 and greator than 45 characters.");
            email.requestFocus();
        } else if (username.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else if (confirmPassword.getText().equals("")) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Confirm password text field cannot be blank.");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Confirm password text field cannot be less than 5 and greator than 25 characters.");
            confirmPassword.requestFocus();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Password and confirm password text fields does not match.");
            password.requestFocus();
        } else if (isAlreadyRegistered()) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("The email is already taken by someone else.");
            username.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private boolean clearForm() {
        firstName.clear();
        lastName.clear();
        email.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
        return true;
    }

    private boolean isAlreadyRegistered() {
        if (MakerSpace.getUserByEmail(email.getText()) != null) {
            return true;
        }
        return false;
    }

    public void showLoginStage() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();
    }

    public void showUserStage() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Stage");
        stage.show();
    }

}
