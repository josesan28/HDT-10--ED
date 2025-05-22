/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Clase que implementa un grafo dirigido utilizando una matriz de adyacencia.
 *  Fecha de creación: 20/05/2025
 *  Fecha de última modificación: 22/05/2025
 *  Fuentes: implementación basada en el libro Java Structures de Duane A. Bailey
 */

import java.util.*;

/**
 * * Clase que implementa un grafo dirigido utilizando una matriz de adyacencia.
 */
public class GraphMatrixDirected<V, E> {
    protected int size;
    protected Object[][] data;
    protected Map<V, Vertex<V>> dict;
    protected List<Integer> freeList;
    protected boolean directed;

    /**
     * Constructor que inicializa el grafo con un tamaño específico.
     *
     * @param size Tamaño del grafo.
     */
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

    /**
     * Clase interna que representa un vértice del grafo.
     */
    public static class Vertex<V> {
        private V label;
        private int index;

        /**
         * Constructor que inicializa un vértice.
         *
         * @param label Etiqueta del vértice.
         * @param index Índice del vértice.
         */
        public Vertex(V label, int index) {
            this.label = label;
            this.index = index;
        }

        /**
         * Método que devuelve la etiqueta del vértice.
         * @return Etiqueta del vértice.
         */
        public V label() {
            return label;
        }

        /**
         * Método que devuelve el índice del vértice.
         * @return Índice del vértice.
         */ 
        public int index() {
            return index;
        }
    }

    /**
     * Clase interna que representa una arista del grafo.
     */
    public static class Edge<V, E> {
        private V from;
        private V to;
        private E label;
        private boolean directed;

        /**
         * Constructor que inicializa una arista.
         *
         * @param from Vértice de origen.
         * @param to Vértice de destino.
         * @param label Etiqueta de la arista.
         * @param directed Indica si la arista es dirigida o no.
         */
        public Edge(V from, V to, E label, boolean directed) {
            this.from = from;
            this.to = to;
            this.label = label;
            this.directed = directed;
        }

        /**
         * Método que devuelve la etiqueta de la arista.
         * @return Etiqueta de la arista.
         */
        public E label() {
            return label;
        }
    }

    /**
     * Método que añade un vértice al grafo.
     * @param label Etiqueta del vértice.
     */
    public void addVertex(V label) {
        if (dict.containsKey(label) || freeList.isEmpty()) return;
        
        int index = freeList.remove(0);
        Vertex<V> vertex = new Vertex<>(label, index);
        dict.put(label, vertex);
    }

    /**
     * Método que elimina un vértice del grafo.
     * @param label Etiqueta del vértice a eliminar.
     */
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

    /**
     * Método que añade una arista al grafo.
     * @param from Vértice de origen.
     * @param to Vértice de destino.
     * @param label Etiqueta de la arista.
     */
    public void addEdge(V from, V to, E label) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return;
        
        Edge<V, E> edge = new Edge<>(from, to, label, true);
        data[vFrom.index()][vTo.index()] = edge;
    }

    /**
     * Método que elimina una arista del grafo.
     * @param from Vértice de origen.
     * @param to Vértice de destino.
     */
    public E removeEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return null;
        
        Edge<V, E> edge = (Edge<V, E>) data[vFrom.index()][vTo.index()];
        data[vFrom.index()][vTo.index()] = null;
        
        return edge != null ? edge.label() : null;
    }

    /**
     * Método que verifica si el grafo contiene un vértice.
     * @param label Etiqueta del vértice.
     * @return true si el vértice existe, false en caso contrario.
     */
    public boolean contains(V label) {
        return dict.containsKey(label);
    }

    /**
     * Método que verifica si el grafo contiene una arista.
     * @param from Vértice de origen.
     * @param to Vértice de destino.
     * @return true si la arista existe, false en caso contrario.
     */
    public boolean containsEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return false;
        
        return data[vFrom.index()][vTo.index()] != null;
    }

    /**
     * Método que obtiene una arista del grafo.
     * @param from Vértice de origen.
     * @param to Vértice de destino.
     */
    public E getEdge(V from, V to) {
        Vertex<V> vFrom = dict.get(from);
        Vertex<V> vTo = dict.get(to);
        
        if (vFrom == null || vTo == null) return null;
        
        Edge<V, E> edge = (Edge<V, E>) data[vFrom.index()][vTo.index()];
        return edge != null ? edge.label() : null;
    }

    /**
     * Método que obtiene el índice de un vértice.
     * @param label Etiqueta del vértice.
     */
    public int getIndex(V label) {
        Vertex<V> vertex = dict.get(label);
        return vertex != null ? vertex.index() : -1;
    }

    public Set<V> vertices() {
        return dict.keySet();
    }
}