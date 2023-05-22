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

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import makerspace.Booking;
import makerspace.Component;
import makerspace.MakerSpace;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;

public class UserController {
    @FXML
    private ComboBox<String> componentNameSelect;
    @FXML
    private Slider componentQtySelect;
    @FXML
    private Text componentQtyText;
    @FXML
    private Button borrowButton;
    @FXML
    private Text wrongField;


    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<String> timeField;
    @FXML
    private TextField durationField;
    @FXML
    private Text bookField;
    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableView<Component> componentTable;

    TableColumn<Booking, Integer> bookingIdColumn = new TableColumn<>("ID");
    TableColumn<Booking, String> bookingDateColumn = new TableColumn<>("Date");
    TableColumn<Booking, String> bookingStatusColumn = new TableColumn<>("Status");

    TableColumn<Component, Integer> componentIdColumn = new TableColumn<>("ID");
    TableColumn<Component, String> componentNameColumn = new TableColumn<>("Name");


    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookingStatusColumn.setCellValueFactory(data -> {
            Booking booking = data.getValue();
            String status = booking.getStatus() ? "Accepted" : "Pending";
            return new SimpleStringProperty(status);
        });
        bookingIdColumn.prefWidthProperty().bind(bookingTable.widthProperty().divide(3));
        bookingDateColumn.prefWidthProperty().bind(bookingTable.widthProperty().divide(3));
        bookingStatusColumn.prefWidthProperty().bind(bookingTable.widthProperty().divide(3));
        bookingTable.setPlaceholder(new Label("No bookings made yet."));

        componentIdColumn.setCellValueFactory(new PropertyValueFactory<>("componentId"));
        componentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        componentIdColumn.prefWidthProperty().bind(componentTable.widthProperty().divide(2));
        componentNameColumn.prefWidthProperty().bind(componentTable.widthProperty().divide(2));
        componentTable.setPlaceholder(new Label("No components borrowed yet."));


        bookingTable.getColumns().addAll(bookingIdColumn, bookingDateColumn, bookingStatusColumn);
        componentTable.getColumns().addAll(componentIdColumn, componentNameColumn);
        bookingTable.getItems().addAll(MakerSpace.getActiveUser().getBookings());
        componentTable.getItems().addAll(MakerSpace.getActiveUser().getComponents());


        for (Component component : MakerSpace.getComponents()) {
            componentNameSelect.getItems().add(component.getName());
        }

        timeField.getItems().addAll("10:00", "11:00", "12:00", "13:00", "14:00", "15:00");

        componentQtySelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            componentQtyText.setText(String.valueOf(newValue.intValue()));
        });

        componentNameSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Component component = MakerSpace.getComponentByName(newValue);
            componentQtySelect.setMax(component.getQuantity());
        });

    }

    public void borrow() {
        String name = componentNameSelect.getValue();
        int qty = (int) componentQtySelect.getValue();
        Component component = MakerSpace.getComponentByName(name);
        if ((component.getQuantity()-qty) < 0) {
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Component is not available at that quantity");
        } else {
            MakerSpace.getActiveUser().borrowComponent(component,qty);
            componentTable.getItems().add(component);
            componentTable.refresh();
            wrongField.setStyle("-fx-fill: green;");
            wrongField.setText("Component borrowed successfully");
            componentQtySelect.setMax(component.getQuantity());
            componentQtySelect.setValue(0);
        }
    }

    public void book(){
        if(dateField.getValue()==null || timeField.getValue()==null || durationField.getText()==""){
            bookField.setStyle("-fx-fill: red;");
            bookField.setText("Please fill all fields");
            return;
        }
        String date = dateField.getValue().toString();
        String time = timeField.getValue();
        int duration = Integer.parseInt(durationField.getText());

        if(duration > 2){
            bookField.setStyle("-fx-fill: red;");
            bookField.setText("Duration cannot be more than 2 hours");
            return;
        }
        Booking booking = new Booking(MakerSpace.getActiveUser(),date, time, duration);
        MakerSpace.getBookings().add(booking);
        MakerSpace.getActiveUser().bookSpace(booking);
        bookingTable.getItems().add(booking);
        bookingTable.refresh();
        bookField.setStyle("-fx-fill: green;");
        bookField.setText("Booking made successfully");
        durationField.clear();
    }

    public void handle(KeyEvent event) throws Exception {
        if(event.getCode()== KeyCode.ESCAPE){
            Alert alert = new Alert(Alert.AlertType.WARNING, "You are about to log out. Do you want to proceed?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText("Logging out");
            alert.setTitle("Log out");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                MakerSpace.setActiveUser(null);
                Stage stage = (Stage) borrowButton.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("User Login");
                stage.getIcons().add(new Image("/asset/icon.png"));
                stage.show();

            }
        }
    }


}
