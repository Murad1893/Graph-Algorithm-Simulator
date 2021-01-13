package application;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;

import javafx.fxml.FXML;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;


public class BellmanFordShortestPath {

		String output = "";
		LinkedList<Edge> [] adjacencylist;
		double anst = 0;
		String ans = "";
		int node_num;
		int edges;
		VisualizationImageServer<String, String> vv;
	    Layout<String, String> layout;
	    AbstractTypedGraph<String, String> g;
		Map<Integer, Integer> parent = new HashMap<>();
		
		public BellmanFordShortestPath(LinkedList<Edge>[] adjacencylist, int node_num, int edges, Graph g1, boolean d) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			this.edges = edges;

			if(d)
			g = new DirectedSparseGraph<>();
	        else g = new UndirectedSparseGraph<>();

	        vv = new VisualizationImageServer<>(new StaticLayout<>(g), new Dimension(1850, 1000));
	        layout = vv.getGraphLayout();
			
			for(int i =0; i<node_num ;i++) {
				g.addVertex(Integer.toString(i));
				double x = g1.getNode()[i].getX_cord();
				double y = g1.getNode()[i].getY_cord();
				
				
				Point2D vp = layout.transform(Integer.toString(i));
		    	vp.setLocation(x*1500, y*1500);
		    	layout.setLocation(Integer.toString(i), vp);
			}
		}	

		String BellmanFord(int src) {
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
		            if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) { 
		                System.out.println("Graph contains negative weight cycle");
						output = output + "Graph contains negative weight cycle" +"\n";
						return output;
		            } 
		        } 
	        }

	        printBellman(dist, src);
			return output;
	    } 
	   
		public void getImage() {
			    	
		  	Transformer<String, String> transformer = new Transformer<String, String>() {
			    @Override
			    public String transform(String arg0) {
			    return arg0;
			    }
			};
			vv.getRenderContext().setVertexLabelTransformer(transformer);
		
			JFrame frame = new JFrame("My Graph");


			frame.getContentPane().add(vv);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		
			System.out.println(g.toString());
			output = output + g.toString() +"\n";
		}
		
		public void printBellman(double[] dist, int sourceVertex){
	    	
	    	DecimalFormat df2 = new DecimalFormat("#.##");
	    	
	        System.out.println("Bellman Ford Algorithm:");
			output = output + "Bellman Ford Algorithm:" +"\n";
	        for (int i = 0; i <node_num ; i++) {
	        	if(sourceVertex!=i) {
					System.out.println("Source Vertex: " + sourceVertex + " to vertex " + +i +
							" Distance: " + df2.format(dist[i]));
					String result = String.format("%.2f   ", dist[i]);
					if(dist[i] == Double.MAX_VALUE)
					output = output + "Source Vertex: " + sourceVertex + " to vertex " + i +
							" Distance: " + "INF" + "\n";
					else {
						output = output + "Source Vertex: " + sourceVertex + " to vertex " + i +
								" Distance: " + result + "\n";
						anst=anst+dist[i];
					}
				}
	        }
	        ans = "Vertex " + sourceVertex + " To All Vertex: " +df2.format(anst) ;
	    }
	
}
