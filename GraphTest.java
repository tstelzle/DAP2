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
		//Arrylist mit allen Knoten wird deklariert und initialisert
		ArrayList<Node> node = g.getNodes();
		//Die Matrix zur Berechnung wird deklariert und initialisiert
		double[][] memory = new double[node.size()][node.size()];
		
		//Die erste Zeile wird mit "unendlich" initialisert
		for(int i=0; i<memory[0].length; i++)
		{
			memory[0][i] = Double.POSITIVE_INFINITY;
		}
		
		//der Startknotenabstand wird auf 0 gesetzt
		memory[0][source] = 0;
		
		//die Matrix wird startend mit dem Knoten nach dem Startknoten durchlaufen 
		for(int i=1; i<node.size(); i++)
		{
			//ArrayList mit allen Kanten der Knoten wird deklariert und initialisiert
			ArrayList<Edge> tmp = g.getEdges();
			//alle Knoten in dem Graphen werden durchlaufen
			for(Node v : node)
			{
				//ArrayList edges zur Speicherung der Kanten die auf den Knoten zeigen wird deklariert und initialisiert
				ArrayList<Edge> edges = new ArrayList<Edge>();
				for(int u=0; u<tmp.size(); u++)
				{
					//Falls die Destination der Kante gleich dem Knoten ist wird er edges hinzugefuegt
					if(tmp.get(u).getDest().getID() == v.getID())
					{
						edges.add(tmp.get(u));
					}
				}
				
				//double Wert zum Vergleich fuer das Minium wird deklariert und initialisiert
				double min = Double.POSITIVE_INFINITY;
				
				//alle ausgewaehlten Kanten werden durchlaufen 
				for(int u=0; u<edges.size(); u++)
				{
					//die Kosten der Kante werden zwischengespeichert
					double cost = edges.get(u).getCost();
					//das Minimum wird ausgewaehlt
					double now = Math.min(memory[i-1][v.getID()], memory[i-1][edges.get(u).getSource().getID()] + cost);
					//Falls das neue Minimum kleiner als das aktuelle ist, wird es ersetzt
					if(now < min)
					{
						min = now;
					}
					
					
				}
				
				//Der minimale Kostenaufwand zu dem Knoten wird in der Matrix gespeichert
				memory[i][v.getID()] = min;
			}
		}
		
		//Liste zur Ausgabe wird deklariert und initialisiert
		double[] minCost = new double[node.size()];
		
		//die letzte Zeile der Matrix wird in die Ausgabeliste kopiert
		for(int i=0; i<node.size(); i++)
		{
			minCost[i] = memory[node.size() -1][i];
		}
		
		//die Ausgabe list wird zurueckgegeben
		return minCost;
	}


	/**
	 * Loest das APSP-Problem mit Hilfe des Algorithmus von Floyd-Warshall 
	 * @param g der Graph
	 * @return Matrix mit Weglaengen; Element (i,j) gibt die Laenge eines 
	 * kuerzesten Weges von dem Knoten mit der id i zu dem Knoten mit id j an 
	 */
	public static double[][] apsp(Graph g)
	{
		//eine ArrayList zur Speicherung der Matrizen wird deklariert und initialisiert
		ArrayList<double[][]> matrixList = new ArrayList<double[][]>();
		//die Knoten und Kanter des Graphen werden gespeichert
		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Edge> edges = g.getEdges();
		//die Matrix wird deklariert und initialisiert
		double[][] w = new double[nodes.size()][nodes.size()];

		//die Spalten der Matrix werden durchlaufen 
		for(int i=0; i<nodes.size(); i++)
		{
			//die Zeilen der Matrix werden durchlaufen
			for(int j=0; j<nodes.size(); j++)
			{
				//die Kosten der Kante werden zwischengespeichert
				double tmp = g.getCost(i, j);
				//Falls keine Kante existiert wird der Wert auf "unendlich" gesetzt ansonste auf die Kosten der Kante
				if(tmp == -1)
				{
					w[i][j] = Double.POSITIVE_INFINITY;
				}
				else{
					w[i][j] = g.getCost(j, i); 
				}
				
			}
		}

		//die Matrix wird in der ArrayList zwischengespeichert
		matrixList.add(w);
		
		//Durchlaufen der verschiedenen Matrizen
		for(int k=1; k<nodes.size(); k++)
		{
			//Durchlaufen der Spalten der Matrix
			for(int i=1; i<nodes.size(); i++)
			{
				//Durchlaufend der Zeilen der Matrix
				for(int j=1; j<nodes.size(); j++)
				{
					//die vorherige Matrix wird zwischengespeichert
					double[][] tmp = matrixList.get(k-1);
					//das Minimum der Kantenkosten wird in der neuen Matrix gespeichert
					w[i][j] = Math.min(tmp[i][j], Math.min(tmp[i][k], tmp[k][j]));
				}
			}
			//die Matrix wird in die ArrayList zwischengespeichert
			matrixList.add(w);
		}

		//die letzte ArrayList wird ausgegeben
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
