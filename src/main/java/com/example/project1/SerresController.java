package com.example.project1;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SerresController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<DestList> tableCC;
    @FXML
    private TableView<DestList> tableCC1;
    @FXML
    private TableView<DestList> tableCC11;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colName;
    @FXML
    private TableColumn<DestList,SimpleStringProperty> colAdd;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colRat;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colPri;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colFav;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colName1;
    @FXML
    private TableColumn<DestList,SimpleStringProperty> colAdd1;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colRat1;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colPri1;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colFav1;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colName11;
    @FXML
    private TableColumn<DestList,SimpleStringProperty> colAdd11;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colRat11;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colPri11;
    @FXML
    private TableColumn<DestList, SimpleStringProperty> colFav11;
    @FXML
    private ObservableList<DestList> data;
    @FXML
    private ObservableList<DestList> data2;
    @FXML
    private ObservableList<DestList> data3;

    @FXML
    private WebView webview1;

    @FXML
    private WebView webview2;

    @FXML
    private WebView webview3;

    @FXML
    private WebView webview4;
    private mysqlconnect connection;


    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("custom-theme.css")));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFav(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Favourites.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToProf(ActionEvent event) throws IOException {
        if (User.username != null) {
            Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.getDialogPane().setHeaderText("Please Login to check your connection's information!");
            alert.showAndWait();
        }
    }
    public void switchToReg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FORMA_RE.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = connection.ConnectDb();
            data = FXCollections.observableArrayList();
            data2 = FXCollections.observableArrayList();
            data3 = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT name,vicinity,rating,price_level FROM cityguide.places WHERE town_id=1 AND type LIKE '%museum%'");
            while (rs.next()) {
                data.add(new DestList(rs.getString(1), rs.getString(2), rs.getDouble(3),rs.getDouble(4)));
            }
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT name,vicinity,rating,price_level FROM cityguide.places WHERE town_id=1 AND type LIKE '%cafe%'");
            while (rs1.next()) {
                data2.add(new DestList(rs1.getString(1), rs1.getString(2), rs1.getDouble(3),rs1.getDouble(4)));
            }
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT name,vicinity,rating,price_level FROM cityguide.places WHERE town_id=1 AND type LIKE '%restaurant%'");
            while (rs2.next()) {
                data3.add(new DestList(rs2.getString(1), rs2.getString(2), rs2.getDouble(3),rs2.getDouble(4)));
            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRat.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPri.setCellValueFactory(new PropertyValueFactory<>("Price"));

        colName1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAdd1.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRat1.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPri1.setCellValueFactory(new PropertyValueFactory<>("Price"));

        colName11.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAdd11.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRat11.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPri11.setCellValueFactory(new PropertyValueFactory<>("Price"));

        tableCC.setItems(null);
        tableCC.setItems(data);

        tableCC1.setItems(null);
        tableCC1.setItems(data2);

        tableCC11.setItems(null);
        tableCC11.setItems(data3);

        //Loading Webviews
        WebEngine webEngine = webview1.getEngine();
        webEngine.load("https://www.google.com/maps/place/%CE%9A%CE%B1%CE%B2%CE%AC%CE%BB%CE%B1/@40.9368859,24.3869726,14z/data=!3m1!4b1!4m5!3m4!1s0x14aebb729066fc01:0xf567e137446a476c!8m2!3d40.937607!4d24.412866");

        webEngine = webview2.getEngine();
        webEngine.load("https://www.kavalanews.gr/");

        webEngine = webview3.getEngine();
        webEngine.load("https://www.okairos.gr/%CE%BA%CE%B1%CE%B2%CE%AC%CE%BB%CE%B1.html");

        webEngine = webview4.getEngine();
        webEngine.load("https://www.visitkavala.gr/");
    }

    @FXML
    private void loadDataFromDatabase (javafx.event.ActionEvent event){
        try {
            Connection conn = connection.ConnectDb();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT name,vicinity,rating,price_level FROM cityguide.places WHERE town_id=3 AND type LIKE '%museum%'");
            while (rs.next()) {
                data.add(new DestList(rs.getString(1), rs.getString(2), rs.getDouble(3),rs.getDouble(4)));

            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRat.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colPri.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tableCC.setItems(null);
        tableCC.setItems(data);


    }
}
