/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Pruebas unitarias para la clase Floyd.
 *  Fecha de creación: 21/05/2025
 *  Fecha de última modificación: 21/05/2025
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TestFloyd {
    
    @Test
    public void testSimpleGraph() {
        int[][] graph = {
            {0, 5, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 0, 3},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        assertEquals(0, floyd.getDistance(0, 0));
        assertEquals(5, floyd.getDistance(0, 1));
        assertEquals(8, floyd.getDistance(0, 2));
        assertEquals(Integer.MAX_VALUE, floyd.getDistance(1, 0));
        assertEquals(0, floyd.getDistance(1, 1));
        assertEquals(3, floyd.getDistance(1, 2));
    }
    
    @Test
    public void testPathReconstruction() {
        int[][] graph = {
            {0, 2, Integer.MAX_VALUE, 5},
            {Integer.MAX_VALUE, 0, 3, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        List<Integer> path = floyd.getPath(0, 3);
        assertNotNull(path);
        assertEquals(0, path.get(0));
        assertEquals(3, path.get(path.size() - 1));
        
        assertNull(floyd.getPath(3, 0));
    }
    
    @Test
    public void testSameVertex() {
        int[][] graph = {
            {0, 1},
            {1, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        List<Integer> path = floyd.getPath(0, 0);
        assertNotNull(path);
        assertEquals(1, path.size());
        assertEquals(0, path.get(0));
    }
    
    
    @Test
    public void testGraphCenter() {
        int[][] graph = {
            {0, 1, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {1, 0, 1, 1},
            {Integer.MAX_VALUE, 1, 0, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 1, Integer.MAX_VALUE, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        String[] names = {"A", "B", "C", "D"};
        String center = floyd.findGraphCenter(names);
        
        assertEquals("B", center);
    }
    
    @Test
    public void testGraphCenterWithDisconnectedVertices() {
        int[][] graph = {
            {0, 1, Integer.MAX_VALUE},
            {1, 0, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        String[] names = {"A", "B", "C"};
        String center = floyd.findGraphCenter(names);
        
        assertTrue(center.equals("A") || center.equals("B"));
    }
    
    @Test
    public void testLargeDistances() {
        int[][] graph = {
            {0, 1000000, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, 0, 1000000},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        };
        
        Floyd floyd = new Floyd(graph);
        floyd.compute();
        
        assertEquals(2000000, floyd.getDistance(0, 2));
    }
    
   
}