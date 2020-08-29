package sxa190016;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import idsa.*;
import idsa.Graph.Edge;
import idsa.Graph.Vertex;

/**
 * @author sxa190016
 * @author bsv180000
 * @version 1.0 Odd Length Cycle: Short project 6
 * 				A BFS algorithm to return an odd length
 * 				cycle in an undirected graph, if present, else null
 */
public class SP6 {
	/**
	 * Finds if the undirected graph contains a cycle of odd length
	 * 
	 * @param g		The undirected graph
	 * @return		The list of vertices of odd length cycle if present else null
	 */
	public List<Vertex> oddCycle(Graph g)
	{
		/**
		 * The source vertex for BFS
		 */
		int s = 1;

		/**
		 * Object for calling BFS on graph g with s as start vertex
		 */
		BFSOO b = idsa.BFSOO.breadthFirstSearch(g, s);
		
		/**
		 * List of vertices for storing the left part of the cycle
		 */
		List<Vertex> left=new ArrayList<Vertex>();
		
		/**
		 * List of vertices for storing the right part of the cycle
		 */
		List<Vertex> right=new ArrayList<Vertex>();
		
		/**
		 * List of vertices for returning the final result
		 */
		List<Vertex> result=new ArrayList<Vertex>();
		
		//For each vertex do
		for(Vertex u: g) {
			
			//Call BFS from the current vertex if it unreachable from the original source
		    if(b.getDistance(u) == idsa.BFSOO.INFINITY) 
		    {
		    	b = idsa.BFSOO.breadthFirstSearch(g, u);
		    } 
		    else 
		    {
		    	//For each edge do
				for(Edge e: g.incident(u))
			    {
					//Check if there is an edge between vertices on the same level of BFS
					//If exists then store the vertices and break
			    	if(b.getDistance(e.fromVertex()) == b.getDistance(e.toVertex()))
			    	{
			    		left.add(e.fromVertex());
			    		right.add(e.toVertex());
			    		break;
			    	}
			    }
				
				//If we found any edge in the previous loop
				if(left.size()>0 && right.size()>0)
				{
					//Backtrack from both vertices till the common ancestor
					while(!left.get(left.size()-1).equals(right.get(right.size()-1)))
		    		{
		    			left.add(b.getParent(left.get(left.size()-1)));
		    			right.add(b.getParent(right.get(right.size()-1)));
		    		}
					
					//Add vertices on the left part of the cycle to the result
		    		for(int i=left.size()-1; i>=0; i--)
		    		{
		    			result.add(left.get(i));
		    		}
		    		
		    		//Add vertices from the right part of the cycle to the result
		    		for(Vertex x : right)
		    		{
		    			result.add(x);
		    		}
		    		break;
				}    		
		    }		    
		}
		
		//Return result if odd cycle exists else null
		return result.size()>0 ? result : null;
	}
	
	/**
	 * Main function to test the SP6 class
	 * 
	 * @param args		Arguments to be passed to the main function
	 * @throws 			FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		String string = "7 7   1 2 2   1 3 3   2 4 5   3 4 4   5 6 3   5 7 3   7 6 -7   6 7 -1 1";
		
		/**
		 * Scanner to read standard input
		 */
		Scanner in;
		
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		
		/**
		 * Read undirected graph from input
		 */
        Graph g = Graph.readGraph(in, false);

        //If graph is not null
		if(g.size()>0)
		{
			//Print the cycle if it exists
			System.out.println(new SP6().oddCycle(g));
		}
	}
}
