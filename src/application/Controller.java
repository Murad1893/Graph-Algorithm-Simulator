package application;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Alert error = new Alert(Alert.AlertType.ERROR);

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnbf;

    @FXML
    private Button btnk;

    @FXML
    private Button btnd;

    @FXML
    private Button btnp;

    @FXML
    private Button btnf;

    @FXML
    private Button btncc;

    @FXML
    private Button exit;


    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @FXML
    private BorderPane view;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadPane("Main");

    }

    public void close()
    {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public void LoadPane(String ui) {

        Parent root = null;

        try {

            root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));

        }

        catch(Exception ex) {
            error.setContentText("No file chosen!");
            error.showAndWait();
        }

        view.setCenter(root);
        new FadeIn(root).play();
    }

    public void handleClicks(ActionEvent actionEvent) {

    	if (actionEvent.getSource() == btnk) {
            LoadPane("Kruskal");
        }
    	if (actionEvent.getSource() == btnd) {
    	    LoadPane("Dijkstra");
       }
        if (actionEvent.getSource() == btnOverview) {
            LoadPane("Main");
        }
        if(actionEvent.getSource()==btnbf)
        {
            LoadPane("BellmanFord");
        }
        if(actionEvent.getSource()==btnp) {
            LoadPane("Prims");
        }
        if(actionEvent.getSource()==btnf) {
            LoadPane("Floyd");
        }
        if(actionEvent.getSource()==btncc) {
            LoadPane("ClusteringCoefficient");
        }
    }

}
