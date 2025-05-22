/**
 *  @author José Manuel Sanchez Hernández - 24092
 *  @version 1.0
 *  Descripción: Clase principal que implementa un sistema de modelado de red de rutas
 *  Fecha de creación: 21/05/2025
 *  Fecha de última modificación: 21/05/2025
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphMatrixDirected<String, Integer> graph = new GraphMatrixDirected<>(5);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "A", 0);
        graph.addEdge("A", "B", 3);
        graph.addEdge("A", "D", 7);
        graph.addEdge("B", "B", 0);
        graph.addEdge("B", "C", 1);
        graph.addEdge("B", "E", 8);
        graph.addEdge("C", "C", 0);
        graph.addEdge("C", "D", 2);
        graph.addEdge("D", "D", 0);
        graph.addEdge("D", "E", 3);
        graph.addEdge("E", "E", 0);
        graph.addEdge("E", "A", 4);

        int n = graph.vertices().size();
        int[][] initialDist = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(initialDist[i], Integer.MAX_VALUE);
            initialDist[i][i] = 0;
        }
        
        String[] vertexNames = graph.vertices().toArray(new String[0]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Integer weight = graph.getEdge(vertexNames[i], vertexNames[j]);
                if (weight != null) {
                    initialDist[i][j] = weight;
                }
            }
        }

        Floyd floyd = new Floyd(initialDist);
        floyd.compute();

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 4) {

            System.out.println("\nSistema de modelado de red de rutas\n");
            System.out.println("1. Encontrar el camino más corto entre dos estaciones");
            System.out.println("2. Calcular el centro del grafo");
            System.out.println("3. Mostrar la matriz de distancias");
            System.out.println("4. Salir\n");
            System.out.print("Ingrese la opción deseada: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("\nEstaciones disponibles:");
                    System.out.println("A: Ciudad de Guatemala");
                    System.out.println("B: Zacapa");
                    System.out.println("C: Chiquimula");
                    System.out.println("D: Quetzaltenango");
                    System.out.println("E: Cobán\n");

                    System.out.print("Ingrese la estación de origen (A, B, C, D, E): ");
                    String origin = scanner.next();
                    System.out.print("Ingrese la estación de destino (A, B, C, D, E): ");
                    String destination = scanner.next();

                    int originIndex = Arrays.asList(vertexNames).indexOf(origin);
                    int destinationIndex = Arrays.asList(vertexNames).indexOf(destination);

                    if (originIndex == -1 || destinationIndex == -1) {
                        System.out.println("Estación no válida.");
                        return;
                    }

                    List<Integer> path = floyd.getPath(originIndex, destinationIndex);
                    if (path != null) {
                        System.out.print("Camino más corto: ");
                        for (int i : path) {
                            System.out.print(vertexNames[i] + " ");
                        }
                        System.out.println("\nDistancia: " + floyd.getDistance(originIndex, destinationIndex));
                    } 
                    
                    else {
                        System.out.println("No hay camino entre las estaciones seleccionadas.");
                    }

                    break;

                case 2:
                    String center = floyd.findGraphCenter(vertexNames);
                    System.out.println("El centro del grafo es: " + center);
                    break;

                case 3:
                    System.out.println("\nMatriz de distancias más cortas:");
                    System.out.print("\t");
                    for (String vertex : vertexNames) {
                        System.out.print(vertex + "\t");
                    }
                    System.out.println();
                    
                    for (int i = 0; i < n; i++) {
                        System.out.print(vertexNames[i] + "\t");
                        
                        for (int j = 0; j < n; j++) {
                            int distance = floyd.getDistance(i, j);
                            System.out.print((distance == Integer.MAX_VALUE ? "∞" : distance) + "\t");
                        }
                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}