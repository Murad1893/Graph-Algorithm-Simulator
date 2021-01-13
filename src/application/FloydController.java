package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FloydController implements Initializable {

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
    private Label ans;

    @FXML
    private Button exit;
    String pathf = null;
    Graph graph = null;
    Graph graph1 = null;
    FloydWarshall b = null;

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
        String st;
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
                i++;
            }


            br.close();

        } catch (Exception e) {

        }

        if(r1.isSelected())
        {
            b = new FloydWarshall();
            text.setText(b.floydWarshall(graph.convertIntoMatrix(),graph.getNode_num()));
            ans.setText(b.ans);
        }

        if(r2.isSelected())
        {
            b = new FloydWarshall();
            text.setText(b.floydWarshall(graph1.convertIntoMatrix(),graph1.getNode_num()));
            ans.setText(b.ans);
        }

    }


    public void ogbtn()
    {
        if(r1.isSelected())
            graph.getImage();
        if(r2.isSelected())
            graph1.getImage();
    }



}
