// Import required libraries
import java.util.*;





class GraphLinkedList {
    private Map<Integer, List<Integer>> adjacencyList;

    public GraphLinkedList(int vertices) {
        adjacencyList = new HashMap<>();
        for (int i = 1; i <= vertices; i++)
            adjacencyList.put(i, new LinkedList<>());
    }

    public void setEdge(int from, int to) {
        if (to > adjacencyList.size() || from > adjacencyList.size())
            System.out.println("The vertices does not exist");
        List<Integer> dls = adjacencyList.get(from);
        dls.add(to);
    }

    public List<Integer> getEdge(int to) {
        if (to > adjacencyList.size()) {
            System.out.println("The vertices does not exist");
            return null;
        }
        return adjacencyList.get(to);
    }

    public boolean containsEdge(int source, int destination) {
        List<Integer> edges = getEdge(source);
        if (edges == null)
            return false;
        return edges.contains(destination);
    }

    public boolean checkGraph() {
        // counter for number of nodes with no outgoing edges
        Integer count = 0;
        // iterator over the nodes in the graph
        Iterator<Integer> iteratorI = this.adjacencyList.keySet().iterator();
        // calculate the expected number of nodes with no outgoing edges
        Integer size = this.adjacencyList.size() - 1;
        // iterate over the nodes in the graph
        while (iteratorI.hasNext()) {
            Integer i = iteratorI.next();
            List<Integer> adjList = this.adjacencyList.get(i);
            // if all nodes except the last one have no outgoing edges, it is a DAG
            if (count == size) {
                System.out.println("This graph doesn't contain a cycle");
                return true;
            }
            // if a node has no outgoing edges, remove it and its incoming edges
            if (adjList.size() == 0) {
                count++;
                // print the sink node being removed
                System.out.println("\nSink Node - " + i);
                // iterate over the nodes in the graph
                Iterator<Integer> iteratorJ = this.adjacencyList.keySet().iterator();
                while (iteratorJ.hasNext()) {
                    Integer j = iteratorJ.next();
                    List<Integer> li = this.adjacencyList.get(j);
                    // if the current node's incoming edges include the sink node,
                    // remove the edge and print a message
                    if (li.contains(i)) {
                        li.remove(i);
                        System.out.println("Deleting edge between sink node "
                                + i + " - " + j);
                    }
                }
                // remove the sink node from the graph
                this.adjacencyList.remove(i);
                // reset the iterator to the beginning of the graph
                iteratorI = this.adjacencyList.keySet().iterator();
            }
        }
        // if all nodes have been removed without detecting a cycle, it is a DAG
        System.out.println("This graph contains a cycle");

        return false;
    }

    public List<List<Integer>> findCycles() {
        List<List<Integer>> cycles = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for (int i = 1; i <= this.adjacencyList.size(); i++) {
            if (!visited.contains(i)) {
                List<Integer> path = new ArrayList<>();
                path.add(i);
                findCyclesHelper(i, visited, path, cycles);
            }
        }
        return cycles;
    }


    private void findCyclesHelper(int node, Set<Integer> visited, List<Integer> path, List<List<Integer>> cycles) {
        visited.add(node);
        path.add(node);
        int startIndex = path.indexOf(node);

        for (int neighbor : adjacencyList.get(node)) {
            if (neighbor == path.get(startIndex + 1)) {
                // Found a cycle
                List<Integer> cycle = new ArrayList<>(path.subList(startIndex, path.size()));
                cycles.add(cycle);
            } else if (!visited.contains(neighbor)) {
                // Recurse on unvisited neighbors
                findCyclesHelper(neighbor, visited, path, cycles);
            }
        }

        // Remove the current node from the path and visited set to backtrack
        path.remove(path.size() - 1);
        visited.remove(node);
    }

    public void printGraph() {
        // Print initial message
        System.out.println("The Graph is: ");
        // Iterate through nodes and their edge lists
        for (int i = 1; i <= this.adjacencyList.size(); i++)
        {
            List<Integer> edgeList = this.getEdge(i);

            // If the node has at least one neighbor, print it
            if (edgeList.size() != 0)
            {
                // Print the node number
                System.out.print(i);

                // Print all of its neighbors
                for (int j = 0; j < edgeList.size(); j++)
                {
                    System.out.print(" -> " + edgeList.get(j));
                }

                // Move to a new line
                System.out.println();
            }
        }
    }
}


// Define a class GraphLinkedList to represent a directed graph using an adjacency list


// This class checks whether the given graph is an Acyclic Graph or not.
public class AcyclicGraphChecker {

    public static void main(String[] args) {
        GraphParser parser = new GraphParser();

        // Create a new GraphLinkedList object.
        GraphLinkedList glist = parser.parseFile();

        int numVertices = parser.getNumVertices();
        System.out.println("Number of Vertices: "+numVertices+"\n");

        // Print the graph
        glist.printGraph();

        // Check whether the graph is a DAG or not
        if (glist.checkGraph()) {
            System.out.println("\n Is this  an Acyclic Graph: Yes, ");
        } else {
            System.out.println("\nIs this  an Acyclic Graph: No,");
        }



    }

}
