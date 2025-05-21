/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Clase que implementa el algoritmo de Floyd.
 *  Fecha de creación: 20/05/2025
 *  Fecha de última modificación: 20/05/2025
 *  Fuentes: implementación basada en el libro Java Structures de Duane A. Bailey
 */

import java.util.*;

public class Floyd {
    private int[][] dist;
    private int[][] next;
    private int V;

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

    public void compute() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && 
                        dist[k][j] != Integer.MAX_VALUE &&
                        dist[i][k] + dist[k][j] < dist[i][j]) {
                        
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }
    
    public int getDistance(int i, int j) {
        return dist[i][j];
    }
    
    public List<Integer> getPath(int i, int j) {
        if (next[i][j] == -1) return null;
        
        List<Integer> path = new ArrayList<>();
        path.add(i);
        while (i != j) {
            i = next[i][j];
            path.add(i);
        }
        return path;
    }
}