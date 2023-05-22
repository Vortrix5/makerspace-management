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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import makerspace.*;

import java.io.IOException;

import static java.lang.Integer.parseInt;


public class SuperUserController {

    @FXML
    private TableView<Booking> bookingsLogTable;
    @FXML
    private TableView<Booking> pendingBookingsTable;
    @FXML
    private TableView<Component> inventoryTable;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TextField componentAddName;
    @FXML
    private TextField componentAddQty;
    @FXML
    private Text wrongField;

    TableColumn<Booking, Integer> bookingIdColumn = new TableColumn<>("Booking ID");
    TableColumn<Booking, String> bookingDateColumn = new TableColumn<>("Date");
    TableColumn<Booking, String> bookingTimeColumn = new TableColumn<>("Time");
    TableColumn<Booking, String> bookingDurationColumn = new TableColumn<>("Duration");
    TableColumn<Booking, String> bookingStatusColumn = new TableColumn<>("Status");

    TableColumn<Component, Integer> componentIdColumn = new TableColumn<>("Component ID");
    TableColumn<Component, String> componentNameColumn = new TableColumn<>("Component Name");
    TableColumn<Component, Integer> componentQuantityColumn = new TableColumn<>("Quantity");

    TableColumn<User,Integer> userIdColumn = new TableColumn<>("User ID");
    TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
    TableColumn<User, String> userEmailColumn = new TableColumn<>("Email");

    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));

        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        bookingDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        bookingStatusColumn.setCellValueFactory(data -> {
            Booking booking = data.getValue();
            String status = booking.getStatus() ? "Accepted" : "Pending";
            return new SimpleStringProperty(status);
        });

        componentIdColumn.setCellValueFactory(new PropertyValueFactory<>("componentId"));
        componentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        componentQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        bookingsLogTable.getColumns().addAll(bookingIdColumn, bookingDateColumn, bookingTimeColumn, bookingDurationColumn, bookingStatusColumn);
        pendingBookingsTable.getColumns().addAll(bookingIdColumn, bookingDateColumn, bookingTimeColumn, bookingDurationColumn, bookingStatusColumn);
        inventoryTable.getColumns().addAll(componentIdColumn, componentNameColumn, componentQuantityColumn);
        usersTable.getColumns().addAll(userIdColumn,userNameColumn,userEmailColumn);

        bookingsLogTable.getItems().addAll(MakerSpace.getBookings());
        pendingBookingsTable.getItems().addAll(MakerSpace.getPendingBookings());
        inventoryTable.getItems().addAll(MakerSpace.getComponents());
        usersTable.getItems().addAll(MakerSpace.getUsers());

        inventoryTable.setEditable(true);
        componentQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        componentQuantityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Component, Integer> t) -> {
                    ((Component) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setQuantity(t.getNewValue());
                });


        addButtonToTable();
    }

    private void addButtonToTable() {
        TableColumn<Booking, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>> cellFactory = new Callback<TableColumn<Booking, Void>, TableCell<Booking, Void>>() {
            @Override
            public TableCell<Booking, Void> call(final TableColumn<Booking, Void> param) {
                final TableCell<Booking, Void> cell = new TableCell<Booking, Void>() {

                    private final Button btn = new Button("Accept");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Booking booking = getTableView().getItems().get(getIndex());
                            SuperUser superUser = (SuperUser) MakerSpace.getActiveUser();
                            superUser.approveBooking(booking);
                            pendingBookingsTable.refresh();
                            pendingBookingsTable.layout();
                            bookingsLogTable.refresh();

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        pendingBookingsTable.getColumns().add(colBtn);

    }

    public void handle(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ESCAPE) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You are about to log out. Do you want to proceed?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText("Logging out");
            alert.setTitle("Log out");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                MakerSpace.setActiveUser(null);
                Stage stage = (Stage) inventoryTable.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("User Login");
                stage.show();

            }
        }
    }

    public void add(){
        if(componentAddName.getText()==null || componentAddQty.getText()==null){
            wrongField.setStyle("-fx-fill: red;");
            wrongField.setText("Please fill all fields");
            return;
        }

        String name = componentAddName.getText();
        int qty = parseInt(componentAddQty.getText());

        //Check if component exists
        wrongField.setStyle("-fx-fill: green;");
        wrongField.setText("Component added successfully");
        Component newComponent = new Component(name,qty);
        MakerSpace.addComponent(name,qty);
        inventoryTable.getItems().add(newComponent);
        inventoryTable.refresh();


    }


}
