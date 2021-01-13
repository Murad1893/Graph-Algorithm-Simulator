package application;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

class ResultSet {
    int parent;
    double weight;
}

public class Graph {
		String output = "";
		String ans = "";
		int node_num;
		Node[] node;
	    LinkedList<Edge> [] adjacencylist;
	    VisualizationImageServer<String, String> vv;
	    Layout<String, String> layout;
	    AbstractTypedGraph<String, String> g;
	    
	    public AbstractTypedGraph<String, String> getG() {
			return g;
		}
	    
	   
		public VisualizationImageServer<String, String> getVv() {
			return vv;
		}


		Graph(int vertices, String type) {
			this.node_num = vertices;
			node = new Node[vertices];
			adjacencylist = new LinkedList[vertices];

			g = new DirectedSparseGraph<>();

			vv = new VisualizationImageServer<>(new StaticLayout<>(g), new Dimension(1850, 1000));
			layout = vv.getGraphLayout();


			for (int i = 0; i <vertices ; i++) {
				node[i] = new Node();
				adjacencylist[i] = new LinkedList<>();
			}
		}


		Graph(int vertices) {
	        this.node_num = vertices;
	        node = new Node[vertices];
	        adjacencylist = new LinkedList[vertices];
	        
	        g = new UndirectedSparseGraph<>();
	        
	        vv = new VisualizationImageServer<>(new StaticLayout<>(g), new Dimension(1850, 1000));
	        layout = vv.getGraphLayout();
	        

	        for (int i = 0; i <vertices ; i++) {
	        	node[i] = new Node();
	            adjacencylist[i] = new LinkedList<>();
	        }
	    }
	
		public int getNode_num() {
			return node_num;
		}

		public void setNode_num(int node_num) {
			this.node_num = node_num;
		}

		public Node[] getNode() {
			return node;
		}

		public void setNode(Node[] node) {
			this.node = node;
		}

		public LinkedList<Edge>[] getAdjacencylist() {
			return adjacencylist;
		}

		public void setAdjacencylist(LinkedList<Edge>[] adjacencylist) {
			this.adjacencylist = adjacencylist;
		}
			
		public void setCoords(int num, double x, double y) {
	    	node[num].setX_cord(x);
	    	node[num].setY_cord(y);
	    	g.addVertex(Integer.toString(num));
	    	
	    	Point2D vp = layout.transform(Integer.toString(num));
	    	vp.setLocation(x*1500, y*1500);
	    	layout.setLocation(Integer.toString(num), vp);
	    	
	    }
	    
		public int totaledges() {
			int edge_count = 0;
			for(int i = 0; i<node_num; i++) {
				for(int j = 0; j<adjacencylist[i].size(); j++)
					edge_count++;
			}
			return edge_count;
		}

	    public void DaddEdge(int source, int destination, double weight) {
	    		
		    	Edge edge = new Edge(source, destination, weight);
		    	boolean check = false;
		    	ListIterator<Edge> j = adjacencylist[source].listIterator();
		    	
		    	while(j.hasNext()) {
		    		Edge e = j.next();
		    		
		    		if(e.getDestination() == edge.destination) {
		    			check = true;
		    		
		    			if(e.getWeight() > edge.weight) {
		    				j.remove();
		    				j.add(edge);
		    			}
		    		}
		    		 
		    	}	
		    	if(!check) {
		    		
		    		adjacencylist[source].addFirst(edge); //for directed graph
		    		node[source].setDegree(node[source].getDegree()+1);
		    		g.addEdge("Edge"+Integer.toString(source)+"|"+Integer.toString(destination), Integer.toString(source),Integer.toString(destination));
		    	}
		 
	    }	
	    
	    public void UaddEdge(int source, int destination, double weight) {
		    	DaddEdge(source,destination,weight);
		    	DaddEdge(destination,source,weight);
		    	
		    	g.addEdge("Edge"+Integer.toString(source)+"|"+Integer.toString(destination), Integer.toString(source),Integer.toString(destination));
		    	
	    }
	   
	    public double[][] convertIntoMatrix(){
	    	
	    	double[][] distance = new double[node_num][node_num];
	    	
	    	for(int i = 0; i < node_num; i++){
	    		for(int j = 0; j < node_num; j++){
	    			if(i == j)
	    				distance[i][j] = 0;
	    			else distance[i][j] = Double.MAX_VALUE;
	    		}
	    	}
	    	
	    	for(int i = 0; i < node_num; i++){
	    		for(int j = 0; j < adjacencylist[i].size(); j++){
	    				distance[i][adjacencylist[i].get(j).getDestination()] = adjacencylist[i].get(j).getWeight();
	    		}
	    	}
	    
	    	return distance;
	    	
	    }
	    
	    public void printGraph(){
	        for (int i = 0; i <node_num ; i++) {
	            for (int j = adjacencylist[i].size()-1; j >= 0 ; j--) {
	                System.out.println("vertex-" + i + " is connected to " +
	                		adjacencylist[i].get(j).destination + " with weight " +  adjacencylist[i].get(j).weight + " with coords: ("+ node[i].getX_cord() + "," + node[i].getY_cord() + ")");
	            }
	        }
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
	    }
	    
	    public boolean isEdge(int vertex1, int vertex2)
		{
	                int i = vertex1;
	                if(i == -1)
	                {
	                       
	                        return false;
	                }

	                int j = vertex2;
	                if(j == -1)
	                {
	                      
	                        return false;
	                }

	        for(int k = 0; k<adjacencylist[i].size() ; k++) {   
	        	if(adjacencylist[i].get(k).getDestination() == j) {
	        		return true;
	        	}
	        }
			return false;
		}
	    
	    public double clusteringCoefficient(int i)
		{

			DecimalFormat df2 = new DecimalFormat("#.####");

	    	int[] temp = new int[adjacencylist[i].size()];
	    	for(int k = 0 ; k < adjacencylist[i].size(); k++) {
				temp[k] = adjacencylist[i].get(k).getDestination();
			}


			int edgesInNbd = 0;


			for(int j = 0; j < temp.length; j++)
				for(int k = 0; k < j; k++)
					if(isEdge(temp[j], temp[k]))
						edgesInNbd++;


			if(temp.length <= 1) {
				output = output + "Clustering Coefficient for Vertex " + i + ": " + 1 +"\n\n";
				return 1;
			}
			else {
				output = output + "Clustering Coefficient for Vertex " + i + ": " + df2.format((edgesInNbd * 2.0 / (temp.length * (temp.length - 1)))) +"\n\n";
				return edgesInNbd * 2.0 / (temp.length * (temp.length - 1));
			}

		}

	public String getOutput() {
		return output;
	}

	public double clusteringCoefficient()
		{
			DecimalFormat df2 = new DecimalFormat("#.####");
			double sum = 0;
			for(int i = 0; i < node_num; i++)
				sum =  sum + clusteringCoefficient(i);
			output = output + "Clustering Coefficient for whole graph: " + df2.format(sum/node_num) ;
			ans="Clustering Coefficient: " + df2.format(sum/node_num);

			return sum/node_num;
		}
	    
	    public void printAdjacency() {
	    	for(int v = 0; v < node_num; v++) 
	        { 
	            System.out.println("Adjacency list of vertex "+ v + " Degree: " + node[v].getDegree());

	            System.out.print("head");

	            for(Edge pCrawl: adjacencylist[v]){ 
	                System.out.print(" -> "+pCrawl.getDestination()+"("+pCrawl.getWeight()+")");

	            } 
	            System.out.println("\n");

	        } 
	    }
	    
}
