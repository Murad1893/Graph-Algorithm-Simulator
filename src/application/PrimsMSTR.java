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
import java.util.LinkedList;

public class PrimsMSTR {

		LinkedList<Edge> [] adjacencylist;
		int node_num;


	    AbstractTypedGraph<String, String> g;

		public PrimsMSTR(LinkedList<Edge>[] adjacencylist, int node_num, GraphR g1) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			
			g = new UndirectedSparseGraph<>();
		        

			
			for(int i =0; i<node_num ;i++) {
				g.addVertex(Integer.toString(i));
				double x = g1.getNode()[i].getX_cord();
				double y = g1.getNode()[i].getY_cord();
				
				

			}
				
		}

		public void primMST(){
			
		     boolean[] inHeap = new boolean[node_num];
		     ResultSet[] resultSet = new ResultSet[node_num];

		     double [] key = new double[node_num];

		     HeapNode [] heapNodes = new HeapNode[node_num];
		     for (int i = 0; i <node_num ; i++) {
		         heapNodes[i] = new HeapNode();
		         heapNodes[i].vertex = i;
		         heapNodes[i].key = Integer.MAX_VALUE;
		         resultSet[i] = new ResultSet();
		         resultSet[i].parent = -1;
		         inHeap[i] = true;
		         key[i] = Integer.MAX_VALUE;
		     }

		     heapNodes[0].key = 0;

		     MinHeap minHeap = new MinHeap(node_num);

		     for (int i = 0; i <node_num ; i++) {
		         minHeap.insert(heapNodes[i]);
		     }
		

		     while(!minHeap.isEmpty()){

		         HeapNode extractedNode = minHeap.extractMin();
		         

		         int extractedVertex = extractedNode.vertex;
		         inHeap[extractedVertex] = false;
		

		         LinkedList<Edge> list = adjacencylist[extractedVertex];
		         for (int i = 0; i <list.size() ; i++) {
		             Edge edge = list.get(i);

		             if(inHeap[edge.destination]) {
		                 int destination = edge.destination;
		                 double newKey = edge.weight;

		                 if(key[destination]>newKey) {
		                	 
		                     decreaseKey(minHeap, newKey, destination);

		                     resultSet[destination].parent = extractedVertex;
		                     resultSet[destination].weight = newKey;
		                     key[destination] = newKey;
		                                
		                 }
		             }
		         }
		     }

		     printMST(resultSet);

		}
		

		
		public void printMST(ResultSet[] resultSet){
		    	

		   
		     double total_min_weight = 0;

		     for (int i = 1; i <node_num ; i++) {

		         g.addEdge("Edge"+Integer.toString(resultSet[i].parent)+"|"+Integer.toString(i),Integer.toString(resultSet[i].parent), Integer.toString(i));
		         
		         total_min_weight += resultSet[i].weight;
		     }


		 }
		 
		public void decreaseKey(MinHeap minHeap, double newKey, int vertex){

		     int index = minHeap.indexes[vertex];

		     HeapNode node = minHeap.mH[index];
		     node.key= newKey;
		     minHeap.bubbleUp(index);
		}
	
}
