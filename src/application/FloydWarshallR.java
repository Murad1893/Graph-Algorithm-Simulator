package application;


public class FloydWarshallR {


    void floydWarshall(double graph[][], int node_num)
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

    }
    

    
}
