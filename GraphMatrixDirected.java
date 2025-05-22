/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Clase que implementa un grafo dirigido utilizando una matriz de adyacencia.
 *  Fecha de creación: 20/05/2025
 *  Fecha de última modificación: 21/05/2025
 *  Fuentes: implementación basada en el libro Java Structures de Duane A. Bailey
 */

import java.util.*;

public class GraphMatrixDirected<V, E> {
    protected int size;
    protected Object[][] data;
    protected Map<V, Vertex<V>> dict;
    protected List<Integer> freeList;
    protected boolean directed;

    public GraphMatrixDirected(int size) {
        this.size = size;
        this.directed = true;
        this.data = new Object[size][size];
        this.dict = new HashMap<>();
        this.freeList = new LinkedList<>();
        
        for (int i = size-1; i >= 0; i--) {
            freeList.add(i);
        }
    }

    public static class Vertex<V> {
        private V label;
        private int index;

        public Vertex(V label, int index) {
            this.label = label;
            this.index = index;
        }

        public V label() {
            return label;
        }

        public int index() {
            return index;
        }
    }

    public static class Edge<V, E> {
        private V from;
        private V to;
        private E label;
        private boolean directed;

        public Edge(V from, V to, E label, boolean directed) {
            this.from = from;
            this.to = to;
            this.label = label;
            this.directed = directed;
        }

        public E label() {
            return label;
        }
    }

    public void addVertex(V label) {
        if (dict.containsKey(label) || freeList.isEmpty()) return;
        
        int index = freeList.remove(0);
        Vertex<V> vertex = new Vertex<>(label, index);
        dict.put(label, vertex);
    }

    public V removeVertex(V label) {
        Vertex<V> vertex = dict.remove(label);
        if (vertex == null) return null;
        
        int index = vertex.index();
        for (int i = 0; i < size; i++) {
            data[i][index] = null;
            data[index][i] = null;
        }
        
        freeList.add(index);
        return vertex.label();
    }

    public void addEdge(V from, V to, E label) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return;
        
        Edge<V, E> edge = new Edge<>(from, to, label, true);
        data[vFrom.index()][vTo.index()] = edge;
    }

    public E removeEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return null;
        
        Edge<V, E> edge = (Edge<V, E>) data[vFrom.index()][vTo.index()];
        data[vFrom.index()][vTo.index()] = null;
        
        return edge != null ? edge.label() : null;
    }

    public boolean contains(V label) {
        return dict.containsKey(label);
    }

    public boolean containsEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return false;
        
        return data[vFrom.index()][vTo.index()] != null;
    }

    public E getEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return null;
        
        Edge<V, E> edge = (Edge<V, E>) data[vFrom.index()][vTo.index()];
        return edge != null ? edge.label() : null;
    }

    public int getIndex(V label) {
        Vertex<V> vertex = dict.get(label);
        return vertex != null ? vertex.index() : -1;
    }

    public Set<V> vertices() {
        return dict.keySet();
    }
}