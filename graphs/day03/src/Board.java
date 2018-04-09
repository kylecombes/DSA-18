package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;

    //TODO
    // Create a 2D array representing the solved board state
    private int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        n = b.length;
        tiles = new int[n][n];
        for(int i = 0; i < n; i++){
            System.arraycopy(b[i],0, tiles[i], 0,n);
        }
    }

    /*
     * Size of the board
     (equal to 3 for 8 puzzle, 4 for 15 puzzle, 5 for 24 puzzle, etc)
     */
    private int size() {
        return n;
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal
     */
    public int manhattan() {
        int manhattansum = 0;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                int val = tiles[x][y];
                if (val != 0) {
                    int corr_x = (val - 1) / n;
                    int corr_y = (val - 1) % n;
                    manhattansum += Math.abs(corr_x - x) + Math.abs(corr_y - y);
                }
            }
        }
        return manhattansum;
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        return Arrays.deepEquals(tiles, goal);
    }

    /*
     * Returns true if the board is solvable
     * Research how to check this without exploring all states
     */
    public boolean solvable() {
        List<Integer> linearRep = new ArrayList<>();
        for (int[] row: tiles){
            for (int i: row){
                linearRep.add(i);
            }
        }
        int inversions = 0;
        for (int i = 0; i < linearRep.size()-1; i++){
            for(int j = i+1; j < linearRep.size(); j++){
                if (linearRep.get(j) < linearRep.get(i) && linearRep.get(i) != 0 && linearRep.get(j) != 0) inversions++;
            }
        }
        return (inversions % 2 == 0);
    }

    /*
     * Return all neighboring boards in the state tree
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int row = 0;
        int col = 0;
        int dimL = n;
        for (int i = 0; i < dimL; i++){
            for(int j = 0; j < dimL; j++){
                if(tiles[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        if(row < dimL - 1){
            // has room to move up
            int toSwap = tiles[row + 1][col];
            tiles[row][col] = toSwap;
            tiles[row + 1][col] = 0;
            neighbors.add(new Board(tiles));
            tiles[row + 1][col] = toSwap;
            tiles[row][col] = 0;
        }
        if(row > 0){
            // has room to move down
            int toSwap = tiles[row - 1][col];
            tiles[row][col] = toSwap;
            tiles[row - 1][col] = 0;
            neighbors.add(new Board(tiles));
            tiles[row - 1][col] = toSwap;
            tiles[row][col] = 0;
        }
        if(col < dimL - 1){
            // has room to move right
            int toSwap = tiles[row][col + 1];
            tiles[row][col] = toSwap;
            tiles[row][col + 1] = 0;
            neighbors.add(new Board(tiles));
            tiles[row][col + 1] = toSwap;
            tiles[row][col] = 0;
        }
        if(col > 0){
            // has room to move left
            int toSwap = tiles[row][col - 1];
            tiles[row][col] = toSwap;
            tiles[row][col - 1] = 0;
            neighbors.add(new Board(tiles));
            tiles[row][col - 1] = toSwap;
            tiles[row][col] = 0;
        }
        return neighbors;
    }

    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(tiles);
    }


    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(initState);
        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
    }
}
