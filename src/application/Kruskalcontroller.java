package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Kruskalcontroller implements Initializable {

    @FXML
    private TextArea text;



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
    KruskalsMST b = null;

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
                i++;
            }


            br.close();

        } catch (Exception e) {

        }


            b = new KruskalsMST(graph.getAdjacencylist(), graph.getNode_num(), graph.totaledges(),graph);
            text.setText(b.kruskalMST());
            ans.setText(b.ans);




    }

    public void agbtn()
    {
        b.getImage();
    }

    public void ogbtn()
    {
        graph.getImage();
    }



}
