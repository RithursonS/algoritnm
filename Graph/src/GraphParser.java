import java.io.File;
import java.util.Scanner;

public class GraphParser {
    private String filename;
    private int numVertices;
    private int[][] edges;

    public GraphParser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Text file: ");
        filename = sc.nextLine();
        sc.close();
    }

    public GraphLinkedList parseFile() {
        GraphLinkedList glist = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int maxVertex = 0;
            while (sc.hasNextInt()) {
                int to = sc.nextInt();
                int from = sc.nextInt();
                maxVertex = Math.max(maxVertex, Math.max(from, to));
            }
            numVertices = maxVertex;
            glist = new GraphLinkedList(numVertices);
            edges = new int[numVertices][numVertices];
            sc.close();
            sc = new Scanner(new File(filename));
            while (sc.hasNextInt()) {
                int to = sc.nextInt();
                int from = sc.nextInt();
                glist.setEdge(to, from);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return glist;
    }

    public int[][] getEdges() {
        return edges;
    }

    public int getNumVertices() {
        return numVertices;
    }
}
