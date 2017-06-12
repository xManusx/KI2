package info.kwarc.teaching.bayes;

import java.util.*;

public class Node 
{
	private HashMap<ArrayList<Boolean>, Double> probabilities = new HashMap<ArrayList<Boolean>, Double>();
	public ArrayList<Node> parents = new ArrayList<Node>();
	public String id;
	
	Node(String id, ArrayList<Node> parents)
	{
		this.parents.addAll(parents);
		this.id=id;
	}
	
	void setProb(ArrayList<Boolean> given, double value)
	{
		assert(given.size() == parents.size()) : "Node " + id + ": number of premises doesn't match number of parents (in setProp)";
		assert(value >= 0 && value <= 1.0) : "Node " + id + ": value must be between 0 and 1";
		probabilities.put(given,value);
	}
	
	public double getProb(ArrayList<Boolean> given)
	{
		assert(given.size() == parents.size()) : "Node " + id + ": number of premises doesn't match number of parents (in getProp)";
	    Double val=probabilities.get(given);
	    if(val==null)
	    {
	    	System.out.println("Node " + id + ": No probability for " + given + " given; using 0.5 as default");
	    	val=0.5;
	    }
	    return val;
	}
	
	public boolean isCauseOf(Node n)
	{
		if(n.parents.contains(this))
		{
			return true;
		}
		else
		{
			for(Node parent : n.parents)
			{
				if(this.isCauseOf(parent))
				{
					return true;
				}
			}
		}
		return false;
	}
}
