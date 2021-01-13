package application;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class BellmanFordShortestPathR {


		LinkedList<Edge> [] adjacencylist;
		int node_num;
		int edges;


	    AbstractTypedGraph<String, String> g;
		Map<Integer, Integer> parent = new HashMap<>();

		public BellmanFordShortestPathR(LinkedList<Edge>[] adjacencylist, int node_num, int edges, GraphR g1, boolean d) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			this.edges = edges;

			if(d)
			g = new DirectedSparseGraph<>();
	        else g = new UndirectedSparseGraph<>();


			
			for(int i =0; i<node_num ;i++) {
				g.addVertex(Integer.toString(i));
				double x = g1.getNode()[i].getX_cord();
				double y = g1.getNode()[i].getY_cord();
				
				

			}
		}	

		void BellmanFord(int src) {
	        double[] dist = new double[node_num];


	        for (int i = 0; i < node_num; ++i)
	            dist[i] = Double.MAX_VALUE;
	        dist[src] = 0;

	        for (int i = 0; i < node_num; i++) {
	            for (int j = 0; j < adjacencylist[i].size(); j++) {
	                int u = adjacencylist[i].get(j).getSource();
	                int v = adjacencylist[i].get(j).getDestination();

	                double weight = adjacencylist[i].get(j).getWeight();
	                if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
	                	dist[v] = dist[u] + weight;
	                	parent.put(v, u);
	                }

	            }
	        }

	        for(Integer keys: parent.keySet()) {
	        	g.addEdge("Edge"+Integer.toString(keys)+"|"+Integer.toString(parent.get(keys)), Integer.toString(parent.get(keys)), Integer.toString(keys));
	        }


	        for (int i = 0; i < node_num; i++) {
		        for (int j = 0; j < adjacencylist[i].size(); j++) {
		        	int u = adjacencylist[i].get(j).getSource();
	                int v = adjacencylist[i].get(j).getDestination();
	                double weight = adjacencylist[i].get(j).getWeight();

		        }
	        }



	    }



	
}
