package application;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class DijkstraShortestPath {
		String output = "";
		LinkedList<Edge> [] adjacencylist;
		double anst = 0;
		String ans = "";
		int node_num;
		VisualizationImageServer<String, String> vv;
	    Layout<String, String> layout;
	    AbstractTypedGraph<String, String> g;
		Map<Integer, Integer> parent = new HashMap<>();
	    
		public DijkstraShortestPath(LinkedList<Edge>[] adjacencylist, int node_num, Graph g1, boolean d) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;

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

		public String dijkstra_GetMinDistances(int sourceVertex){
	        int INFINITY = Integer.MAX_VALUE;
	          

	        HeapNode [] heapNodes = new HeapNode[node_num];
	        for (int i = 0; i <node_num ; i++) {
	            heapNodes[i] = new HeapNode();
	            heapNodes[i].vertex = i;
	            heapNodes[i].key = INFINITY;
	        }
	

	        heapNodes[sourceVertex].key = 0;
	

	        MinHeap minHeap = new MinHeap(node_num);
	        for (int i = 0; i <node_num ; i++) {
	            minHeap.insert(heapNodes[i]);
	        }

	        while(!minHeap.isEmpty()){

	            HeapNode extractedNode = minHeap.extractMin();

	            int extractedVertex = extractedNode.vertex;

	            LinkedList<Edge> list = adjacencylist[extractedVertex];
	            for (int i = 0; i <list.size() ; i++) {
	            	
	                Edge edge = list.get(i);
	                int destination = edge.destination;
	                
	               if(!minHeap.contains(destination)) {
	            	   continue;
	               }

	                double newKey =  heapNodes[extractedVertex].key + edge.weight ;
	                double currentKey = heapNodes[destination].key;
	                if(currentKey>newKey){ // relaxation!
	                    decreaseKey(minHeap, newKey, destination);
	                    heapNodes[destination].key = newKey;
	                    parent.put(destination, extractedVertex);

	                }
	                
	            }
	        }
	        
	        for(Integer keys: parent.keySet()) {
	        	g.addEdge("Edge"+Integer.toString(keys)+"|"+Integer.toString(parent.get(keys)),Integer.toString(parent.get(keys)), Integer.toString(keys));
	        }
	        
	        //print SPT
	        printDijkstra(heapNodes, sourceVertex);
			return output;
	    }
		
		public void decreaseKey(MinHeap minHeap, double newKey, int vertex){
			

	        int index = minHeap.indexes[vertex];

	        HeapNode node = minHeap.mH[index];
	        node.key = newKey;
	        minHeap.bubbleUp(index);
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
		
	    public void printDijkstra(HeapNode[] resultSet, int sourceVertex){
	    	
	    	DecimalFormat df2 = new DecimalFormat("#.##");
	    	
	        System.out.println("Dijkstra Algorithm: (Adjacency List + Min Heap)");
			output = output + "Dijkstra Algorithm: (Adjacency List + Min Heap)" +"\n";
	        for (int i = 0; i <node_num ; i++) {
	        	if(sourceVertex!=i) {
	            System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
	                    " Distance: " + df2.format(resultSet[i].key));
					String result = String.format("%.2f   ", resultSet[i].key);
					if(resultSet[i].key == Integer.MAX_VALUE)
					output = output + "Source Vertex: " + sourceVertex + " to vertex " +   + i +
							" Distance: " + "INF" +"\n";
					else{
						output = output + "Source Vertex: " + sourceVertex + " to vertex " +   + i +
							" Distance: " + result +"\n";
						anst=anst+resultSet[i].key;
					}
	        	} 	
	        }
			ans = "Vertex " + sourceVertex + " To All Vertex: " +df2.format(anst) ;
	    }
}
