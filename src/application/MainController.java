package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Alert error = new Alert(Alert.AlertType.ERROR);

    @FXML
    private Button exit;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;

    @FXML
    private Button Upload;

    @FXML
    private Button Run;

    @FXML
    private Button og;

    @FXML
    private Label path;

    @FXML
    private Label primstime;

    @FXML
    private Label kruskaltime;

    @FXML
    private Label dijkstratime;

    @FXML
    private Label bellmantime;

    @FXML
    private Label floydtime;

    @FXML
    private Label clusteringtime;

    String pathf = null;

    FileChooser filechooser = new FileChooser();
    FileInputStream fs;
    File file;

    GraphR graph = null;

    public void close()
    {

        try
        {
            FileWriter fw = new FileWriter("cache.txt", false);
            fw.write("");
            fw.close();
        }
        catch (IOException e2) {

        }

        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        og.setText("");
        og.setDisable(true);
       // Controller.disable();

        path.setText("");

        Upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                file = filechooser.showOpenDialog(Upload.getScene().getWindow());
                try {
                    filechooser.setInitialDirectory(file.getParentFile());
                }
                catch(Exception e){
                    error.setContentText("No file chosen!");
                    error.showAndWait();
                }
                if (file != null) {
                    path.setText(file.getPath());
                    pathf=file.getPath();
                    try
                    {
                        FileWriter fw = new FileWriter("cache.txt", false);
                        fw.write(pathf);
                        fw.close();
                        //Controller.enable();
                    }
                    catch (IOException e2) {

                    }
                }
            }
        });

    }

    public void runbtn(ActionEvent actionEvent) throws InterruptedException {
        b1.setText("Inactive");
        b1.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        b2.setText("Inactive");
        b2.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        b3.setText("Inactive");
        b3.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        b4.setText("Inactive");
        b4.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        b5.setText("Inactive");
        b5.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        b6.setText("Inactive");
        b6.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #2A73FF;");
        Circle[] nodes = null;

        try {
            System.out.println(pathf);
            File file = new File(pathf);
        }

        catch(Exception e){
            error.setContentText("No file chosen!");
            error.showAndWait();
        }
        String st, last = null;
        int i = 1;
        int vertices = 0;
        String[] arr;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));


            while ((st = br.readLine()) != null) {

                if(i == 3) {
                    vertices = Integer.parseInt(st);
                    graph = new GraphR(vertices);

                    nodes=  new Circle[vertices];
                }
                if(i < vertices+5 && i > 4) {
                    arr = st.split("\t");
                    graph.setCoords(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]));

                }
                if(i > vertices+5) {
                    arr = st.split("\t");

                    for(int j=1; j<arr.length; j = j+4) {
                        if(!arr[j].equals(arr[0])) {
                            graph.UaddEdge(Integer.parseInt(arr[0]), Integer.parseInt(arr[j]), Double.parseDouble(arr[j+2])/10000000);

                        }
                    }
                }
                last = st;
                i++;
            }


            br.close();

        } catch (Exception e) {

        }

        long startTime = 0;
        long endTime = 0;
        long duration = 0;

        graph.convertIntoMatrix();

        b1.setText("Active");
        b1.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        b2.setText("Active");
        b2.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        b3.setText("Active");
        b3.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        b4.setText("Active");
        b4.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        b5.setText("Active");
        b5.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        b6.setText("Active");
        b6.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #f6b93b;");
        PrimsMSTR pmst = new PrimsMSTR(graph.getAdjacencylist(), graph.getNode_num(), graph);
        startTime = System.nanoTime();
        pmst.primMST();
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        primstime.setText(Long.toString(duration));
        b1.setText("Done");
        b1.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");



        KruskalsMSTR kmst = new KruskalsMSTR(graph.getAdjacencylist(), graph.getNode_num(), graph.totaledges(),graph);
        startTime = System.nanoTime();
        kmst.kruskalMST();
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        kruskaltime.setText(Long.toString(duration));
        b2.setText("Done");
        b2.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");



        DijkstraShortestPathR d = new DijkstraShortestPathR(graph.getAdjacencylist(), graph.getNode_num(),graph, false);
        startTime = System.nanoTime();
        d.dijkstra_GetMinDistances(Integer.parseInt(last));
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        dijkstratime.setText(Long.toString(duration));
        b3.setText("Done");
        b3.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");



        BellmanFordShortestPathR b = new BellmanFordShortestPathR(graph.getAdjacencylist(), graph.getNode_num(), graph.totaledges(), graph, false);
        startTime = System.nanoTime();
        b.BellmanFord(Integer.parseInt(last));
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        bellmantime.setText(Long.toString(duration));
        b4.setText("Done");
        b4.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");



        FloydWarshallR f = new FloydWarshallR();
        startTime = System.nanoTime();
        f.floydWarshall(graph.convertIntoMatrix(),graph.getNode_num());
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        floydtime.setText(Long.toString(duration));
        b5.setText("Done");
        b5.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");


        startTime = System.nanoTime();
        graph.clusteringCoefficient();
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000;
        clusteringtime.setText(Long.toString(duration));
        b6.setText("Done");
        b6.setStyle("-fx-border-radius :  20; -fx-background-color :  transparent; -fx-border-color : #28b351;");

        og.setText("Graph");
        og.setDisable(false);
    }
    public void ogbtn()
    {
        graph.getImage();
    }
}
