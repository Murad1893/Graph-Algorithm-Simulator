package application;

import java.text.DecimalFormat;

public class FloydWarshall {

    String output = "";
    double anst = 0;
    String ans = "";
    String floydWarshall(double graph[][], int node_num)
    { 
        double dist[][] = new double[node_num][node_num]; 
        int i, j, k; 
        

        for (i = 0; i < node_num; i++) 
            for (j = 0; j < node_num; j++) 
                dist[i][j] = graph[i][j]; 
  

        for (k = 0; k < node_num; k++) 
        { 

            for (i = 0; i < node_num; i++) 
            { 

                for (j = 0; j < node_num; j++) 
                { 

                    if (dist[i][k] + dist[k][j] < dist[i][j]) 
                        dist[i][j] = dist[i][k] + dist[k][j]; 
                } 
            } 
        } 
  

        printSolution(dist, node_num);
        return output;
    }
    
    void printSolution(double dist[][], int node_num) 
    {
    	DecimalFormat df2 = new DecimalFormat("#.##");

        System.out.println("The following matrix shows the shortest "+ 
                         "distances between every pair of vertices");
        output = output + "The following shows the shortest "+
                "distances between every pair of vertices: " +"\n\n";
        for (int i=0; i<node_num; ++i) 
        {
            output = output + "Vertex "+ i +":\t[\t";
            for (int j=0; j<node_num; ++j) {
                if (dist[i][j] == Double.MAX_VALUE) {
                    System.out.print("   -   ");
                    output = output + "   -   ";

                } else {
                    System.out.printf("%.2f   ", dist[i][j]);
                    anst=anst+dist[i][j];
                    String result = String.format("%.2f   ", dist[i][j]);
                    output = output + result;
                }
            }
            System.out.println();
            output = output + "]"+"\n";
        }
        ans = "Sum of Matrix: " +df2.format(anst) ;
    }
    
}
