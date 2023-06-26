package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao ;
	private Graph<Album, DefaultEdge> grafo ;
	private List<Album> vertici;
	private Map<Integer, Album> idMapAlbum;
	
	
	public Model() {
		
		this.dao= new ItunesDAO();
		this.grafo = new SimpleGraph<Album, DefaultEdge>(DefaultEdge.class);
		this.idMapAlbum = new HashMap<Integer, Album>();
	}
	
	public void loadNodes(double d) {
		
		if (this.vertici.isEmpty()) {
			this.vertici = this.dao.getVertici(d);
		}
	}
	
	public void clearGraph() {
		
		this.grafo = new SimpleGraph<Album, DefaultEdge>(DefaultEdge.class);
		this.vertici = new ArrayList<Album>();
	}
	
	public void creaGrafo(double d) {
		
		clearGraph();
		loadNodes(d);
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		
		for (Album a : this.grafo.vertexSet()) {
			if (idMapAlbum.get(a.getAlbumId())==null) {
				idMapAlbum.put(a.getAlbumId(), a);
			}
		}
		
		
		List<Arco> allArchi = this.dao.getAllArchi();
		
		for (Arco arco : allArchi) {
			int id1 = arco.getIdAlbum1();
			int id2 = arco.getIdAlbum2();
			
			//se questi nodi sono presenti tra i vertici del grafo allora connetterli
			if (idMapAlbum.get(id1)!= null && idMapAlbum.get(id2)!= null) {
				Album a1 = idMapAlbum.get(id1);
				Album a2 = idMapAlbum.get(id2);
				this.grafo.addEdge(a1, a2);
			}
		}
		
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Album> getVertici() {
		return this.vertici;
	}
}
