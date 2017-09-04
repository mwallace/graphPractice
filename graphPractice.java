package graphPractice;

import java.util.ArrayList;

public class graphPractice {

	public static void main(String[] args) {		
		// Create a Graph
		Graph g = new CyclicGraph();
		Node a = new GraphNode("a");
		Node b = new GraphNode("b");
		Node c = new GraphNode("c");
		Node d = new GraphNode("d");
		Node e = new GraphNode("e");
		Node f = new GraphNode("f");
		g.insert(a, b);
		g.insert(a, c);
		g.insert(a, e);
		g.insert(b, d);
		g.insert(b, f);
		g.insert(d, c);
		g.insert(f, d);
		g.print();
		g.remove(b);
		g.print();
	}

}

abstract class Graph {
	// Initialize list of Nodes
	public Graph() {
		nodes = new ArrayList<Node>();
	}

	public abstract Boolean insert(Node src, Node dst);
	
	public abstract void remove(Node del);
	
	// Print out the complete adjacency list
	public void print() {
		for (Node n: nodes) {
			n.print();
			System.out.println();
		}
	}
	
	protected ArrayList<Node> nodes;
}

class CyclicGraph extends Graph {
	
	// Create a new connection between src and dst. Fails if
	// either node doesn't exist. Creates a one-way link.
	// If this returns false, then an edge between these nodes
	// was found and nothing was inserted.
	public Boolean insert(Node src, Node dst) {
		// If the node isn't in the graph yet, add it.
		if (!super.nodes.contains(src))
			nodes.add(src);
		if (!nodes.contains(dst))
			nodes.add(dst);
		// Connect nodes together
		return src.createEdge(dst);
	}
	
	// Remove node "del"
	// Regardless of where this returns, the remove() operation will
	// result in there being no link between src and dst.
	public void remove(Node del) {
		// If node doesn't exist, do nothing
		if (!nodes.contains(del))
			return;
		// Remove all edges that point to "del"
		for (Node n: nodes)
			n.removeEdge(del);
		// Remove "del"
		nodes.remove(del);
	}
}

class GraphNode extends Node {
	
	GraphNode(String value) {
		super(value);
		adjList = new ArrayList<Node>();
	}
	
	// Create an edge between this node and destination node
	public Boolean createEdge(Node dst) {
		// Determine if edge already exists; if so, return false
		if (adjList.contains(dst))
			return false;
		adjList.add(dst);
		return true;
	}
	
	// Remove an edge between this node and destination node
	public void removeEdge(Node dst) {
		// Determine if edge doesn't exist
		if (!adjList.contains(dst))
			return;
		adjList.remove(dst);
		return;
	}
	
	// Print out adjacency list for this node
	public void print() {
		System.out.print(this.value + ": ");
		if (adjList.isEmpty())
			return;
		for (Node neighbor : adjList) {
			System.out.print(neighbor.value + " ");
		}
	}
	
	private ArrayList<Node> adjList;
}

abstract class Node {
	
	Node(String value) {
		this.value = value;
	}
	
	public abstract Boolean createEdge(Node dst);
	
	public abstract void removeEdge(Node dst);
	
	public abstract void print();
	
	protected String value;
}