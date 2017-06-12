package info.kwarc.teaching.bayes;

import java.util.ArrayList;

public static void main(String args[]){
	System.out.println("Test");
}

public abstract class Query 
{
	public abstract Double query(Network network, Node node, ArrayList<Pair<Node,Boolean>> evidence);
}
