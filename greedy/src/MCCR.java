import java.util.HashSet;
import java.util.PriorityQueue;

public class MCCR {

    public static int MCCR(EdgeWeightedGraph G) {

        int cost = 0;
        PriorityQueue<Move> open = new PriorityQueue<>();
        HashSet<Integer> visited = new HashSet<>();

        // Add the first vertex to our open queue
        open.add(new Move(0, 0));

        while (visited.size() < G.numberOfV()) { // While we haven't visited all vertices
            Move m = open.poll();

            if (visited.contains(m.vertex))
                continue; // We've already been here

            // Mark this vertex as visited
            visited.add(m.vertex);

            // Increment our build cost
            cost += m.cost;

            // Add all the neighboring vertices to the open list
            for (Edge e : G.edges(m.vertex)) {
                open.add(new Move(e.other(m.vertex), e.weight()));
            }
        }

        return cost;
    }

}


class Move implements Comparable {
    public int vertex;
    public int cost;

    public Move(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        return cost - ((Move)o).cost;
    }
}