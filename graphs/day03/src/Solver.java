package src;
import java.util.*;

/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */
public class Solver {

    public int minMoves = -1;
    private State solutionState;
    private boolean solved = false;
    private Board board;

    /**
     * ][\-
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State {
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves; // equal to g-cost in A*
        public int cost; // equal to f-cost in A*
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            cost = moves + board.manhattan();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }
    }

    class costSort implements Comparator<State>
    {
        // Used for sorting in descending order of cost
        public int compare(State a, State b)
        {
            return b.cost - a.cost;
        }
    }

    /*
     * Return the root state of a given state
     */
    private State root(State state) {
        if (state == null)
            return null;

        while (state.prev != null) {
            state = state.prev;
        }
        return state;
    }

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) {
        board = initial;
        PriorityQueue<State> open = new PriorityQueue<State>(new costSort());
        Set<State> closed = new HashSet<State>();
        while (open.size() > 0) {
            State curr_state = open.poll(); //Get state with lowest cost
            while (curr_state.board.neighbors().hasNext()) { //Look through it's neighbors
                Board nb = curr_state.board.neighbors().next();
                if (nb.isGoal()) {
                    return new State(nb, moves + 1, curr_state); // Found the goal
                }
                State ns = new State(nb, state.moves + 1, curr_state);

                Iterator<State> it = open.iterator();
                while (it.hasNext()) {
                    State os = it.next();
                    if (os.next().board.equals(nb) && os.cost < ns.cost) {
                        continue;
                    }
                }

                Iterator<State> iter = closed.iterator();
                while(iter.hasNext()){
                    cs = iter.next();
                    if (cs.board.equals(nb) && cs.cost < ns.cost) {
                        continue;
                    }
                }
                open.add(ns);
                //ns.prev = curr_board;

            }
            closed.add(state);
        }
    }


    /*
     * Is the input board a solvable state
     * Research how to check this without exploring all states
     */
    public boolean isSolvable() {
        return this.board.solvable();
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    public Iterable<Board> solution() {
        // TODO: Your code here
        return null;
    }

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space
     */
    public static void main(String[] args) {
        int[][] initState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initial = new Board(initState);

        Solver solver = new Solver(initial);
    }


}
