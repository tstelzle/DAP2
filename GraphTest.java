import java.io.IOException;
import java.util.ArrayList;

public class GraphTest {

	/**
	 * Loest das SSSP-Proplem mit Hilfe des Algortihmus von Bellman-Ford.
	 * @param g der Graph
	 * @param source id des Startknotes
	 * @return Array mit Weglaengen; Element i gibt die Laenge eines kuerzesten
	 * Weges von dem Knoten mit der id source zu dem Knoten mit id i an
	 */
	public static double[] sssp(Graph g, int source) 
	{	
		ArrayList<Node> node = g.getNodes();
		double[][] memory = new double[node.size()][node.size()];
		
		for(int i=0; i<memory[0].length; i++)
		{
			memory[0][i] = Double.POSITIVE_INFINITY;
		}
		
		memory[0][source] = 0;
		
		for(int i=1; i<node.size(); i++)
		{
			ArrayList<Edge> tmp = g.getEdges();
			for(Node v : node)
			{
				ArrayList<Edge> edges = new ArrayList<Edge>();
				for(int u=0; u<tmp.size(); u++)
				{
					if(tmp.get(u).getDest().getID() == v.getID())
					{
						edges.add(tmp.get(u));
					}
				}
				
				/*
				System.out.println("--------------------------------------------------");
				for(int u=0; u<edges.size(); u++)
				{
					System.out.println("Source: " + edges.get(u).getSource().getID() + ", Dest: " + edges.get(u).getDest().getID() + ", Cost: " + edges.get(u).getCost());
				}
				System.out.println("--------------------------------------------------");
				*/

				double min = Double.POSITIVE_INFINITY;
				
				for(int u=0; u<edges.size(); u++)
				{
					double cost = edges.get(u).getCost();
					//System.out.println("Test: Source: " + edges.get(u).getSource().getID() + ", Dest: " + edges.get(u).getDest().getID() + ", v: " + v.getID() + ", Cost: " + g.getCost(edges.get(u).getSource().getID(), v.getID()));
					double now = Math.min(memory[i-1][v.getID()], memory[i-1][edges.get(u).getSource().getID()] + cost);
					if(now < min)
					{
						min = now;
					}
					
					
				}
				
				memory[i][v.getID()] = min;
			}
		}
		
		/*
		for(int x=0; x<node.size(); x++)
		{
			for(int y=0; y<node.size(); y++)
			{
				System.out.print(memory[x][y] + " ");
			}
			System.out.println("");
		}
		*/
		
		double[] minCost = new double[node.size()];
		
		for(int i=0; i<node.size(); i++)
		{
			minCost[i] = memory[node.size() -1][i];
		}
		
		return minCost;
	}


	/**
	 * Loest das APSP-Problem mit Hilfe des Algorithmus von Floyd-Warshall 
	 * @param g der Graph
	 * @return Matrix mit Weglaengen; Element (i,j) gibt die Laenge eines 
	 * kuerzesten Weges von dem Knoten mit der id i zu dem Knoten mit id j an 
	 */
	public static double[][] apsp(Graph g) {
		//double[][] kosten = g.costs;
		ArrayList<double[][]> matrixList = new ArrayList<double[][]>();
		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Edge> edges = g.getEdges();
		double[][] w = new double[nodes.size()][nodes.size()];

		/*
		for(int i=0; i<kosten.length; i++)
		{
			for(int j=0; j<kosten[i].length; j++)
			{
				System.out.println("i, " + i + ", j, " + j + " : " + kosten[i][j]); 
			}
		}
		*/

		for(int i=0; i<nodes.size(); i++)
		{
			for(int j=0; j<nodes.size(); j++)
			{
				/*
				for(Edge v : edges)
				{
					if(j == v.getDest().getID())
					{
						w[i][j] = v.getCost();
					}
				}
				*/
				
				
				double tmp = g.getCost(i, j);
				System.out.println(tmp);
				/*
				tmp = g.costs[i][j];
				System.out.println(tmp);
				tmp = g.getCost(j,i);
				System.out.println(tmp);
				System.out.println("-----------------------");	
				*/
				if(tmp == -1)
				{
					w[i][j] = Double.POSITIVE_INFINITY;
				}
				else{
					w[i][j] = g.getCost(j, i); 
				}
				
			}
		}

		matrixList.add(w);
		
		/*
		for(int i=0; i<w.length; i++)
		{
			for(int j=0; j<w[i].length; j++)
			{
				w[i][j] = 0;
			}
		}
		*/

		for(int k=1; k<nodes.size(); k++)
		{
			for(int i=1; i<nodes.size(); i++)
			{
				for(int j=1; j<nodes.size(); j++)
				{
					double[][] tmp = matrixList.get(k-1);
					w[i][j] = Math.min(tmp[i][j], Math.min(tmp[i][k], tmp[k][j]));
				}
			}
			matrixList.add(w);
		}

		return matrixList.get(matrixList.size()-1);
	}

	/**
 	 * Realisiert einen APSP-Algorithmus, indem fuer alle Knoten das 
 	 * SSSP-Problem mittels Bellman-Ford geloest wird.
 	 * @param g der Graph
	 * @return Matrix mit Weglaengen; Element (i,j) gibt die Laenge eines 
	 * kuerzesten Weges von dem Knoten mit der id i zu dem Knoten mit id j an 
	 */
	public static double[][] apspBellmanFord(Graph g) {
		// Knoten holen
		ArrayList<Node> nodes = g.getNodes();
		// Tabelle anlegen
		double[][] result = new double[nodes.get(nodes.size()-1).getID()+1][];
		for (Node n : nodes){
			// Bellman-Ford fuer jeden Knoten
			result[n.getID()] = sssp(g, n.getID());
		}
		return result;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length < 1) {
			System.err.println("Syntax: java GraphTest <filename> [<nodenumber>]");
			System.exit(-1);
		}
		
		Graph g = Graph.fromFile(args[0]);
		if (g==null) {
			System.err.println("Konnte Datei "+args[0]+ " nicht oeffnen oder enthaelt keinen Graphen");
			System.exit(1);
		}
		if (g.getNodes().isEmpty()) {
			System.err.println("Leerer Graph.");
			System.exit(2);
		}
		
		if (args.length == 2) {
			// Fuehre Bellman-Ford-Algorithmus aus
			int nodenumber=-1;
			try {
				nodenumber=Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
			}
			if (g.getNode(nodenumber) == null) {
				System.err.println("Ungueltiger Startknoten angegeben: "+args[1]);
				System.exit(1);
			}
			double[] minCost = sssp(g, nodenumber);
			ArrayList<Node> nodes = g.getNodes();
			Node s = g.getNode(nodenumber), e = s;
			double maxDist = 0d;
			for (Node n : nodes){
				if (nodes.size()<= 20){
					System.out.println("Abstand von Knoten " + n.getID() + " zu Knoten " + s.getID() + ": " + minCost[n.getID()]);
				}
				if (minCost[n.getID()] != Double.POSITIVE_INFINITY && minCost[n.getID()] > maxDist){
					maxDist = minCost[n.getID()];
					e = n;
				}
			}
			System.out.println("Der maximale Abstand ist " + maxDist + " fuer Knoten " + e.getID());
		} else {
			// Fuehre Floyd-Warshall-Algorithmus aus
			double[][] minCost=apsp(g);
			ArrayList<Node> nodes = g.getNodes();
			Node s = nodes.get(0), e = s;
			double maxDist = 0d;
			for (Node u : nodes){
				for (Node v : nodes){
					if (nodes.size()<= 10){
						System.out.print((minCost[u.getID()][v.getID()] == Double.POSITIVE_INFINITY? "\u221E": "" +minCost[u.getID()][v.getID()]) + "\t");
					}
					if (minCost[u.getID()][v.getID()] != Double.POSITIVE_INFINITY && minCost[u.getID()][v.getID()] > maxDist){
						maxDist = minCost[u.getID()][v.getID()];
						s = u;
						e = v;
					}
				}
				if (nodes.size()<= 10) System.out.println();
			}
			System.out.println("Der maximale Abstand ist " + maxDist + " fuer das Knotenpaar (" + s.getID() + ", " + e.getID() + ")");
		}
	}
}
