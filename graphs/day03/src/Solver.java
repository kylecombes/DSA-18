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

    class CostSort implements Comparator<State>
    {
        // Used for sorting in descending order of cost
        public int compare(State a, State b)
        {
            return a.cost - b.cost;
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
        if(isSolvable()){
            Iterable<Board> solution = solution();
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
        PriorityQueue<State> open = new PriorityQueue<>(new CostSort());
        Set<Board> openSet = new HashSet<>();
        HashMap<Board, Integer> visited = new HashMap<>();
        State initialState = new State(board, 0, null);
        open.add(initialState);
        Set<State> closed = new HashSet<>();
        while (open.size() > 0) {
            State currState = open.poll(); // Get state with lowest cost
            if(currState.board.isGoal()){
                solved = true;
                minMoves = currState.moves;
                return buildSolution(currState);
            }
            for(Board nb : currState.board.neighbors()){
                State ns = new State(nb, currState.moves + 1, currState);
                visited.put(ns.board, ns.cost);
                boolean ignore = false;
                if(openSet.contains(nb)){
                    if(visited.get(nb) < ns.cost) {
                        ignore = true;
                    }
                }
                if(closed.contains(nb)){
                    if(visited.get(nb) < ns.cost) {
                        ignore = true;
                    }
                }
                if(!ignore){
                    openSet.add(ns.board);
                    open.add(ns);
                }
            }
            closed.add(currState);
        }
        return null;
    }

    private Iterable<Board> buildSolution(State solutionState) {
        Stack<Board> temp = new Stack<>();

        // Add all the boards to a temporary stack (to reverse the order)
        while (solutionState.prev != null) {
            temp.add(solutionState.board);
            solutionState = solutionState.prev;
        }

        // Now pop every element onto a list
        List<Board> result = new LinkedList<>();
        while (!temp.empty()) {
            result.add(temp.pop());
        }

        // Return the list
        return result;
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
