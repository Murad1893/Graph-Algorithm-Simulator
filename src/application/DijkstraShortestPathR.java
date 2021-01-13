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

public class DijkstraShortestPathR {

		LinkedList<Edge> [] adjacencylist;
		int node_num;

	    AbstractTypedGraph<String, String> g;
		Map<Integer, Integer> parent = new HashMap<>();

		public DijkstraShortestPathR(LinkedList<Edge>[] adjacencylist, int node_num, GraphR g1, boolean d) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;

			if(d)
			g = new DirectedSparseGraph<>();
	        else g = new UndirectedSparseGraph<>();


			
			for(int i =0; i<node_num ;i++) {
				g.addVertex(Integer.toString(i));
				double x = g1.getNode()[i].getX_cord();
				double y = g1.getNode()[i].getY_cord();
				
				

			}
			
		}

		public void dijkstra_GetMinDistances(int sourceVertex){
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


	    }
		
		public void decreaseKey(MinHeap minHeap, double newKey, int vertex){
			

	        int index = minHeap.indexes[vertex];

	        HeapNode node = minHeap.mH[index];
	        node.key = newKey;
	        minHeap.bubbleUp(index);
	    }
	

		

}
