package application;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class PrimsMST {
		String output = "";
		String ans = "";
		LinkedList<Edge> [] adjacencylist;
		int node_num;
		VisualizationImageServer<String, String> vv;
	    Layout<String, String> layout;
	    AbstractTypedGraph<String, String> g;
		
		public PrimsMST(LinkedList<Edge>[] adjacencylist, int node_num, Graph g1) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			
			g = new UndirectedSparseGraph<>();
		        
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

		public String primMST(){
			
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
		
			JFrame frame = new JFrame("Prims Graph");

			frame.getContentPane().add(vv);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		
			System.out.println(g.toString());
			output = output + g.toString() +"\n";
	    }
		
		public void printMST(ResultSet[] resultSet){
		    	
		     DecimalFormat df2 = new DecimalFormat("#.##");
		   
		     double total_min_weight = 0;
		     System.out.println("Minimum Spanning Tree: ");
		     output = output + "Minimum Spanning Tree: " +"\n";
		     for (int i = 1; i <node_num ; i++) {
		         System.out.println("Edge: " + i + " - " + resultSet[i].parent +
		                 " weight: " + resultSet[i].weight);

				 String result = String.format("%.2f   ",resultSet[i].weight);
				 output = output + "Edge: " + i + " - " + resultSet[i].parent +
						 " weight: " + result +"\n";
		         g.addEdge("Edge"+Integer.toString(resultSet[i].parent)+"|"+Integer.toString(i),Integer.toString(resultSet[i].parent), Integer.toString(i));
		         
		         total_min_weight += resultSet[i].weight;
		     }

			String result = String.format("%.2f   ",total_min_weight);

		     System.out.println("Total minimum key: " + df2.format(total_min_weight));
		     output = output + "Total minimum weight: " + result +"\n";
		     ans = "Total minimum weight: " + result;
		 }
		 
		public void decreaseKey(MinHeap minHeap, double newKey, int vertex){

		     int index = minHeap.indexes[vertex];

		     HeapNode node = minHeap.mH[index];
		     node.key= newKey;
		     minHeap.bubbleUp(index);
		}
	
}
