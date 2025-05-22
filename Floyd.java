/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Clase que implementa el algoritmo de Floyd.
 *  Fecha de creación: 20/05/2025
 *  Fecha de última modificación: 22/05/2025
 */

import java.util.*;

public class Floyd {
    private int[][] dist;
    private int[][] next;
    private int V;

    /**
     * Constructor que inicializa el algoritmo de Floyd con una matriz de distancias inicial.
     * @param initialDist Matriz de distancias inicial entre los vértices.
     */
    public Floyd(int[][] initialDist) {
        this.V = initialDist.length;
        this.dist = new int[V][V];
        this.next = new int[V][V];
        
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = initialDist[i][j];
                next[i][j] = (initialDist[i][j] != Integer.MAX_VALUE && i != j) ? j : -1;
            }
        }
    }

    /**
     * Método que ejecuta el algoritmo de Floyd para calcular las distancias más cortas entre todos los pares de vértices.
     */
    public void compute() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && 
                        dist[k][j] != Integer.MAX_VALUE &&
                        dist[i][k] <= Integer.MAX_VALUE - dist[k][j]) {
                        
                        int newDist = dist[i][k] + dist[k][j];
                        if (dist[i][j] == Integer.MAX_VALUE || newDist < dist[i][j]) {
                            dist[i][j] = newDist;
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Método que obtiene la distancia más corta entre dos vértices.
     * @param i Índice del primer vértice.
     * @param j Índice del segundo vértice.
     * @return Distancia más corta entre los dos vértices.
     */
    public int getDistance(int i, int j) {
        return dist[i][j];
    }
    
    /**
     * Método que obtiene el camino más corto entre dos vértices.
     * @param i Índice del primer vértice.
     * @param j Índice del segundo vértice.
     * @return Lista de índices que representan el camino más corto entre los dos vértices.
     */
    public List<Integer> getPath(int i, int j) {
        if (i == j) {
            List<Integer> path = new ArrayList<>();
            path.add(i);
            return path;
        }
        if (dist[i][j] == Integer.MAX_VALUE || next[i][j] == -1) {
            return null;
        }
        
        List<Integer> path = new ArrayList<>();
        path.add(i);
        while (i != j) {
            i = next[i][j];
            path.add(i);
        }
        return path;
    }

    /**
     * Método que encuentra el centro del grafo.
     * @param vertexNames Array de nombres de los vértices.
     * @return Nombre del vértice central.
     */
    public String findGraphCenter(String[] vertexNames) {
        if (vertexNames == null || vertexNames.length != V) {
            throw new IllegalArgumentException("El array de nombres no coincide con el tamaño del grafo");
        }
        
        int[] eccentricities = new int[V];
        
        for (int i = 0; i < V; i++) {
            int maxDist = 0;
            boolean hasReachableVertex = false;
            
            for (int j = 0; j < V; j++) {
                if (i != j && dist[i][j] != Integer.MAX_VALUE) {
                    maxDist = Math.max(maxDist, dist[i][j]);
                    hasReachableVertex = true;
                }
            }
            
            eccentricities[i] = hasReachableVertex ? maxDist : Integer.MAX_VALUE;
        }
        
        int minEccentricity = Integer.MAX_VALUE;
        String center = null;
        for (int i = 0; i < V; i++) {
            if (eccentricities[i] < minEccentricity) {
                minEccentricity = eccentricities[i];
                center = vertexNames[i];
            }
        }
        
        return center;
    }
}