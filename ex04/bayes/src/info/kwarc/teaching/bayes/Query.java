package info.kwarc.teaching.bayes;

import java.util.*;


public class Query
{
	public static void main(String args[]){
		boolean test = true;
		System.out.println("Test" + test);
		Network network = new Network();
		Node a = new Node("Node a", new ArrayList<Node>());
		a.setProb(new ArrayList<Boolean>(), 0.001);
		network.addNode(a);

		Node b= new Node("Node b", new ArrayList<Node>());
		b.setProb(new ArrayList<Boolean>(), 0.002);
		network.addNode(b);

		ArrayList<Node> c_par = new ArrayList<Node>() {{
			add(a);
			add(b);
		}};
		Node c = new Node ("Node c", c_par);
		c.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(true), new Boolean(true))), 0.95);
		c.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(true), new Boolean(false))), 0.94);
		c.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(false), new Boolean(true))), 0.29);
		c.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(false), new Boolean(false))), 0.001);
		network.addNode(c);

		ArrayList<Node> d_par = new ArrayList<Node>() {{
			add(c);
		}};
		Node d = new Node("Node d", d_par);
		d.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(true))), 0.9);
		d.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(false))), 0.05);
		network.addNode(d);

		ArrayList<Node> e_par = new ArrayList<Node>() {{
			add(c);
		}};
		Node e = new Node("Node e", e_par);
		e.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(true))), 0.7);
		e.setProb(new ArrayList<Boolean>(Arrays.asList(new Boolean(false))), 0.01);
		network.addNode(e);

		ArrayList<Node> nodes = new ArrayList<Node>() {{
			add(a);
			add(b);
			add(c);
			add(d);
			add(e);
		}};

		ArrayList<Pair<Node,Boolean>> evidence = new ArrayList<Pair<Node, Boolean>>() {{
			add(new Pair<Node,Boolean>(e, new Boolean(true)));
			add(new Pair<Node,Boolean>(d, new Boolean(true)));
		}};

		System.out.println(query(network, a, evidence));
		System.out.println("should be 0.284");
		System.out.println("Yup");
	}

	public static Double query(Network network, Node node, ArrayList<Pair<Node,Boolean>> evidence){
		double[] q = new double[2];
		ArrayList<Node> sorted = topoSort(network);

		ArrayList<Pair<Node,Boolean>> evidence_t = new ArrayList<Pair<Node,Boolean>>(evidence);
		evidence_t.add(new Pair<Node,Boolean>(node, new Boolean(true)));
		q[0] = enum_all(new ArrayList<Node>(sorted), evidence_t);

		ArrayList<Pair<Node,Boolean>> evidence_f = new ArrayList<Pair<Node,Boolean>>(evidence);
		evidence_f.add(new Pair<Node,Boolean>(node, new Boolean(false)));
		q[1] = enum_all(new ArrayList<Node>(sorted), evidence_f);
		System.out.println("Q: " + q[0] + " " + q[1]);
		return normalize2(q);
	}

	private static double enum_all(ArrayList<Node> nodes, ArrayList<Pair<Node,Boolean>> evidence){
		if(nodes.isEmpty()){
			return 1;
		}
		Node y = nodes.remove(0);

		//Get evidences for y's parents
		ArrayList<Node> parents = y.parents;
		ArrayList<Boolean> ev = new ArrayList<Boolean>();
		for(Node i : parents){
			for(Pair<Node,Boolean> j : evidence){
				if(i.equals(j.getLeft()))
					ev.add(j.getRight());
			}
		}

		//Check if y in evidence itself and proceed accordingly
		for(Pair<Node,Boolean> i : evidence){
			if(i.getLeft().equals(y)){
				double one = calculateProb(y, i.getRight(), ev);
				double two = enum_all(nodes, evidence);
				return one*two;
			}
		}

		ArrayList<Pair<Node,Boolean>> evidence_f = new ArrayList<Pair<Node,Boolean>>(evidence);
		evidence_f.add(new Pair<Node, Boolean>(y, false));

		ArrayList<Pair<Node,Boolean>> evidence_t = new ArrayList<Pair<Node,Boolean>>(evidence);
		evidence_t.add(new Pair<Node, Boolean>(y, true));

		double one = calculateProb(y, true, ev) * enum_all(nodes, evidence_t);
		double two = calculateProb(y, false, ev) * enum_all(nodes, evidence_f);

		return one+two;
	}

	private static double calculateProb(Node node, boolean assignment, ArrayList<Boolean> ev){
		return (assignment) ? node.getProb(ev) : 1 - node.getProb(ev);
	}
	private static double normalize2(double[] in){
		assert(in.length == 2);
		return in[0] / (in[0] + in[1]);
	}

	private static ArrayList<Node> topoSort(Network network){
		ArrayList<Node> ret = new ArrayList<Node>();
		ArrayList<Node> toAdd = new ArrayList<Node>();
outerloop:
		for(Node i : network.getNodes()){
			for(Node parent : i.parents){
				if(!ret.contains(parent)){
					if(!toAdd.contains(i))
						toAdd.add(i);
					continue outerloop;
				}
			}
			ret.add(i);
		}
		while(!toAdd.isEmpty()){
			Node n = toAdd.remove(0);
			for(Node i : n.parents){
				if(!ret.contains(i)){
					toAdd.add(n);
					break;
				}
			}
		}
		return ret;
	}
}
