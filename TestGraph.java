/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Pruebas unitarias para la implementación del grafo.
 *  Fecha de creación: 21/05/2025
 *  Fecha de última modificación: 21/05/2025
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGraph {
    private GraphMatrixDirected<String, Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphMatrixDirected<>(5);
    }

    @Test
    void testAddVertex() {
        assertTrue(graph.vertices().isEmpty());
        
        graph.addVertex("A");
        graph.addVertex("B");
        
        assertEquals(2, graph.vertices().size());
        assertTrue(graph.contains("A"));
        assertTrue(graph.contains("B"));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        
        assertEquals("B", graph.removeVertex("B"));
        assertFalse(graph.contains("B"));
        assertEquals(1, graph.vertices().size());
    }

    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        
        graph.addEdge("A", "B", 5);
        
        assertTrue(graph.containsEdge("A", "B"));
        assertEquals(5, graph.getEdge("A", "B"));
        assertFalse(graph.containsEdge("B", "A"));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 5);
        
        assertEquals(5, graph.removeEdge("A", "B"));
        assertNull(graph.getEdge("A", "B"));
    }
}