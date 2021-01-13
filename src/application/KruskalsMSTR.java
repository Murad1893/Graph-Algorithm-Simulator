package application;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class KruskalsMSTR {


		LinkedList<Edge> [] adjacencylist;
		int node_num;
		int edge_count;


	    AbstractTypedGraph<String, String> g;

		public KruskalsMSTR(LinkedList<Edge>[] adjacencylist, int node_num, int edge_count, GraphR g1) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			this.edge_count = edge_count;
			
			g = new UndirectedSparseGraph<>();
	        

			
			for(int i =0; i<node_num ;i++) {
				g.addVertex(Integer.toString(i));
				double x = g1.getNode()[i].getX_cord();
				double y = g1.getNode()[i].getY_cord();
				

			}
		}

		public void kruskalMST(){
		
		    PriorityQueue<Edge> pq = new PriorityQueue<>(edge_count, Comparator.comparingDouble(o -> o.weight));
		

		    for (int i = 0; i <node_num ; i++) {
		        for(int j = 0; j < adjacencylist[i].size(); j++)
		        		pq.add(adjacencylist[i].get(j));
		    }

		    int [] parent = new int[node_num];

		    makeSet(parent);
		
		    ArrayList<Edge> mst = new ArrayList<>();
		

		    int index = 0;
		    while(index<node_num-1){
		        Edge edge = pq.remove();

		        int x_set = find(parent, edge.source);
		        int y_set = find(parent, edge.destination);
		
		        if(x_set==y_set){

		        }else {

		            mst.add(edge);
		            index++;
		            union(parent,x_set,y_set);
		        }
		    }

		}
	
		public void makeSet(int [] parent){

            for (int i = 0; i <node_num ; i++) {
                parent[i] = i;
            }
        }
		
		public int find(int [] parent, int vertex){
            if(parent[vertex]!=vertex)
                return find(parent, parent[vertex]);;
            return vertex;
        }
		
		public void union(int [] parent, int x, int y){
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);

            parent[y_set_parent] = x_set_parent;
        }
}
