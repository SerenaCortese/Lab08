package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private List<Parola> parole; //mi serve elenco degli oggetti
	private Graph<Parola,DefaultEdge> graph; //poi costruisco grafo con quelli
	

	public void createGraph(int numeroLettere) {
		//1) leggo lista oggetti da DB 
		WordDAO dao = new WordDAO();
		this.parole = dao.getAllWordsFixedLength(numeroLettere);
		
		//2) creo oggetto grafo (ogni volta che chiamo metodo crea grafo nuovo)
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		//3) aggiungo vertici ( = tutti gli oggetti)
		Graphs.addAllVertices(this.graph, this.parole);

		//4) aggiungo archi
		for(Parola p : this.parole) {
			for(Parola c: this.parole) {
				if(!c.equals(p) & this.sonoVicini(p,c)==true) {
					Graphs.addEdgeWithVertices(this.graph, p, c);
				}
			}
		}

	}

	private boolean sonoVicini(Parola p, Parola c) {
		int diff= 0;
		for(int i=0; i<p.getNome().length();i++) {
			if(p.getNome().charAt(i)!=c.getNome().charAt(i)) {
				diff++;
			}
		}
		if(diff==1)
			return true;
		return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		List<Parola> vicini = new ArrayList<Parola>();
		List<String> viciniString = new ArrayList<String>();
		
		for(Parola p : this.parole) {
			if(p.getNome().equals(parolaInserita)) {
				vicini.addAll(Graphs.neighborListOf(this.graph, p));
			}
		}
		
		for(Parola p : vicini) {
			viciniString.add(p.getNome());
		}
		
		return viciniString;
	}

	public String findMaxDegree() {
		int max = -1;
		Parola verticeMax = null;
		String res = "";
		for(Parola p : graph.vertexSet()) {
			if(this.graph.degreeOf(p)>max) {
				max = this.graph.degreeOf(p);
				verticeMax = p;
			}
		}
		List<String> vicini = this.displayNeighbours(verticeMax.getNome());
		res= "Grado massimo: "+max + "\nVertice col grado massimo: "+verticeMax.getNome()+"\nLista vicini: \n";
		for (String s : vicini) {
			res+= s+"\n";
		}
		return res;
	}

	public boolean parolaPresente(String parola) {
		for(Parola p : parole) {
			if(p.getNome().equals(parola))
				return true;
		}
		
		return false;
	}

	public Graph<Parola, DefaultEdge> getGraph() {
		return graph;
	}
	public void clear() {
		graph = new SimpleGraph<>(DefaultEdge.class);
	}
	
	
}
