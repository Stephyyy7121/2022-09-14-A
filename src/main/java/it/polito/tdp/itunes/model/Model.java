package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;
import javafx.util.Pair;

public class Model {
	
	private Graph<Album, DefaultEdge> graph;
	private List<Album> vertici;
	private ItunesDAO dao ;
	
	
	public Model() {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.vertici = new ArrayList<>();
		dao = new ItunesDAO();
		
	}
	
	
	//aggiungere veritici e archi
	public void loadNodes(double d) {
		
		if (this.vertici.isEmpty()) {
			this.vertici = dao.getFilteredAlbum(d);
		}
	}
	
	public void clearGraph() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.vertici = new ArrayList<>();
	}
	
	public void buildGraph(double d) {
		
		this.clearGraph();
		this.loadNodes(d);
		
		//aggiugnere i vertici
		Graphs.addAllVertices(this.graph, this.vertici);
		
		//aggiungere gli archi 
		//due nodi sono connessi se almeno una canzone si trova nella stessa playlist
		
		//creare una lista di archi 
		List<org.jgrapht.alg.util.Pair<Integer, Integer>> archi  = dao.getAlbumConnessi();
		//in questo modo si ottengono le coppie di nodi che sono connessi
		//ora bisogna aggiungere il tutto nel grafo
		
		//idMap
		Map<Integer, Album> idMapAlbum = new HashMap<Integer, Album>();
		
		for (org.jgrapht.alg.util.Pair<Integer, Integer> arco : archi ) {
			
			//associare id con l'oggetto Album --> necessario un IdMap
			if (idMapAlbum.containsKey(arco.getFirst()) && idMapAlbum.containsKey(arco.getSecond())) {
				
				//se sono presenti, allora aggiungere
				this.graph.addEdge(idMapAlbum.get(arco.getFirst()), idMapAlbum.get(arco.getSecond()));
			}
		}
	
		
	}
	
	//metodo per ottenere tutti i nodi adiacenti --> restituisce l'insieme dei nodi adiacenti
	public Set<Album> getComponente(Album a1) {
		ConnectivityInspector<Album, DefaultEdge> ci =
				new ConnectivityInspector<>(this.graph) ;
		return ci.connectedSetOf(a1) ;
	}
	
	
	public int verticiSize() {
		return this.graph.vertexSet().size();
	}
	
	public int archiSize() {
		return this.graph.edgeSet().size();
	}
	
	public List<Album> getVertici() {
		return this.vertici;
	}

	
}
