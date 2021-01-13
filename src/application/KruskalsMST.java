package application;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class KruskalsMST {

		String output = "";
		String ans = "";
		LinkedList<Edge> [] adjacencylist;
		int node_num;
		int edge_count;
		VisualizationImageServer<String, String> vv;
	    Layout<String, String> layout;
	    AbstractTypedGraph<String, String> g;
		
		public KruskalsMST(LinkedList<Edge>[] adjacencylist, int node_num, int edge_count, Graph g1) {
			super();
			this.adjacencylist = adjacencylist;
			this.node_num = node_num;
			this.edge_count = edge_count;
			
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

		public String kruskalMST(){
		
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
		            //ignore, will create cycle
		        }else {

		            mst.add(edge);
		            index++;
		            union(parent,x_set,y_set);
		        }
		    }

		    System.out.println("Minimum Spanning Tree: ");
			output = output + "Minimum Spanning Tree: " +"\n";
		    printGraph(mst);
		    return output;
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
		
		public void printGraph(ArrayList<Edge> edgeList){

			DecimalFormat df2 = new DecimalFormat("#.##");
		    	
		    double total_min_weight = 0;
			
			for (int i = 0; i <edgeList.size() ; i++) {
                Edge edge = edgeList.get(i);
                System.out.println("Edge: source: " + edge.source +
                        " destination: " + edge.destination +
                        " weight: " + edge.weight);

				String result = String.format("%.2f   ",edge.weight);

				output = output + "Edge: " + edge.source +
						" - " + edge.destination +
						" weight: " + result +"\n";
                
                g.addEdge("Edge"+Integer.toString(edge.source)+"|"+Integer.toString(edge.destination), Integer.toString(edge.source),Integer.toString(edge.destination));
                
                total_min_weight += edge.weight;
            }

			String result = String.format("%.2f   ", total_min_weight);

			System.out.println("Total minimum key: " + df2.format(total_min_weight));
			output = output + "Total minimum key: " + result +"\n";
			ans = "Total minimum key: " + result;
        }
		
}
