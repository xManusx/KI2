package info.kwarc.teaching.bayes;

import java.util.*;


public class Network 
{
	private ArrayList<Node> nodes = new ArrayList<Node>();
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
}
