package application;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class BellmanFordController implements Initializable {

    @FXML
    private TextArea text;

    @FXML
    private RadioButton r1;

    @FXML
    private RadioButton r2;

    @FXML
    private Button og;

    @FXML
    private Button ag;

    @FXML
    private Button exit;
    @FXML
    private Label ans;
    String pathf = null;
    Graph graph = null;
    Graph graph1 = null;
    BellmanFordShortestPath b = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
            BufferedReader fd = new BufferedReader(new FileReader("cache.txt"));
            pathf = fd.readLine();
            fd.close();
        }
        catch (IOException e2) {

        }
        r1.setSelected(true);
        run();

    }

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

    public void run ()
    {
        Circle[] nodes = null;

        File file = new File(pathf);
        String st, last = null;
        int i = 1;
        int vertices = 0;
        String[] arr;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));


            while ((st = br.readLine()) != null) {

                if(i == 3) {
                    vertices = Integer.parseInt(st);
                    graph = new Graph(vertices);
                    graph1 = new Graph(vertices,"m");
                    nodes=  new Circle[vertices];
                }
                if(i < vertices+5 && i > 4) {
                    arr = st.split("\t");
                    graph.setCoords(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]));
                    graph1.setCoords(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]));
                }
                if(i > vertices+5) {
                    arr = st.split("\t");

                    for(int j=1; j<arr.length; j = j+4) {
                        if(!arr[j].equals(arr[0])) {
                            graph.UaddEdge(Integer.parseInt(arr[0]), Integer.parseInt(arr[j]), Double.parseDouble(arr[j+2])/10000000);
                            graph1.DaddEdge(Integer.parseInt(arr[0]), Integer.parseInt(arr[j]), Double.parseDouble(arr[j+2])/10000000);
                        }
                    }
                }

                last = st;
                i++;
            }


            br.close();

        } catch (Exception e) {

        }

        if(r1.isSelected())
        {
            b = new BellmanFordShortestPath(graph.getAdjacencylist(), graph.getNode_num(), graph.totaledges(), graph, false);
            text.setText(b.BellmanFord(Integer.parseInt(last)));
            ans.setText(b.ans);

        }

        if(r2.isSelected())
        {
            b = new BellmanFordShortestPath(graph1.getAdjacencylist(), graph1.getNode_num(), graph1.totaledges(), graph1, true);
            text.setText(b.BellmanFord(Integer.parseInt(last)));
            ans.setText(b.ans);
        }


    }

    public void agbtn()
    {
        b.getImage();
    }

    public void ogbtn()
    {
        if(r1.isSelected())
             graph.getImage();
        if(r2.isSelected())
            graph1.getImage();
    }



}
