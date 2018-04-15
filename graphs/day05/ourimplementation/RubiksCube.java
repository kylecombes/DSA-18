import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// this is our implementation of a rubiks cube. It is your job to use A* or some other search algorithm to write a
// solve() function
public class RubiksCube {

    public BitSet cube;
    private static final String LOOKUP_TABLE_FILENAME = "LookupTable.dsa";
    private static final char[] POSSIBLE_MOVES  = {'u', 'U', 'r', 'R', 'f', 'F'};
    private static HashMap<BitSet, Short> dists;
    public char moveToHere;
    public int gCost;
    public int fCost;
    public RubiksCube prevState;

    // initialize a solved rubiks cube
    public RubiksCube() {
        // 24 colors to store, each takes 3 bits
        cube = new BitSet(24 * 3);
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 4; i++) {
                setColor(side * 4 + i, side);
            }
        }
    }

    public RubiksCube(HashMap<BitSet, Short> blep) {
        // 24 colors to store, each takes 3 bits
        cube = new BitSet(24 * 3);
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 4; i++) {
                setColor(side * 4 + i, side);
            }
        }
        dists = blep;

    }

    // initialize a rubiks cube with the input bitset
    private RubiksCube(BitSet s) {
        cube = (BitSet) s.clone();
    }

    // creates a copy of the rubics cube
    public RubiksCube(RubiksCube r) {
        cube = (BitSet) r.cube.clone();
    }

    // return true if this rubik's cube is equal to the other rubik's cube
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RubiksCube))
            return false;
        RubiksCube other = (RubiksCube) obj;
        return other.cube.equals(cube);
    }

    /**
     * return a hashCode for this rubik's cube.
     *
     * Your hashCode must follow this specification:
     *   if A.equals(B), then A.hashCode() == B.hashCode()
     *
     * Note that this does NOT mean:
     *   if A.hashCode() == B.hashCode(), then A.equals(B)
     */
    @Override
    public int hashCode() {
        return cube.hashCode();
    }

    public boolean isSolved() {
        return this.equals(new RubiksCube());
    }


    // takes in 3 bits where bitset.get(0) is the MSB, returns the corresponding int
    private static int bitsetToInt(BitSet s) {
        int i = 0;
        if (s.get(0)) i |= 4;
        if (s.get(1)) i |= 2;
        if (s.get(2)) i |= 1;
        return i;
    }

    // takes in a number 0-5, returns a length-3 bitset, where bitset.get(0) is the MSB
    private static BitSet intToBitset(int i) {
        BitSet s = new BitSet(3);
        if (i % 2 == 1) s.set(2, true);
        i /= 2;
        if (i % 2 == 1) s.set(1, true);
        i /= 2;
        if (i % 2 == 1) s.set(0, true);
        return s;
    }

    // index from 0-23, color from 0-5
    private void setColor(int index, int color) {
        BitSet colorBitset = intToBitset(color);
        for (int i = 0; i < 3; i++)
            cube.set(index * 3 + i, colorBitset.get(i));
    }


    // index from 0-23, returns a number from 0-5
    private int getColor(int index) {
        return bitsetToInt(cube.get(index * 3, (index + 1) * 3));
    }

    // given a list of rotations, return a rubik's cube with the rotations applied
    public RubiksCube rotate(List<Character> c) {
        RubiksCube rub = this;
        for (char r : c) {
            rub = rub.rotate(r);
        }
        return rub;
    }


    // Given a character in ['u', 'U', 'r', 'R', 'f', 'F'], return a new rubik's cube with the rotation applied
    // Do not modify this rubik's cube.
    public RubiksCube rotate(char c) {
        int[] faceFrom = null;
        int[] faceTo = null;
        int[] sidesFrom = null;
        int[] sidesTo = null;
        // colors move from the 'from' variable to the 'to' variable
        switch (c) {
            case 'u': // clockwise
            case 'U': // counterclockwise
                faceFrom = new int[]{0, 1, 2, 3};
                faceTo = new int[]{1, 2, 3, 0};
                sidesFrom = new int[]{4, 5, 8, 9, 17, 16, 21, 20};
                sidesTo = new int[]{21, 20, 4, 5, 8, 9, 17, 16};
                break;
            case 'r':
            case 'R':
                faceFrom = new int[]{8, 9, 10, 11};
                faceTo = new int[]{9, 10, 11, 8};
                sidesFrom = new int[]{6, 5, 2, 1, 17, 18, 13, 14};
                sidesTo = new int[]{2, 1, 17, 18, 13, 14, 6, 5};
                break;
            case 'f':
            case 'F':
                faceFrom = new int[]{4, 5, 6, 7};
                faceTo = new int[]{5, 6, 7, 4};
                sidesFrom = new int[]{3, 2, 8, 11, 14, 15, 23, 20};
                sidesTo = new int[]{8, 11, 14, 15, 23, 20, 3, 2};
                break;
            default:
                System.out.println(c);
                assert false;
        }
        // if performing a counter-clockwise rotation, swap from and to
        if (Character.isUpperCase(c)) {
            int[] temp;
            temp = faceFrom;
            faceFrom = faceTo;
            faceTo = temp;
            temp = sidesFrom;
            sidesFrom = sidesTo;
            sidesTo = temp;
        }
        RubiksCube res = new RubiksCube(cube);
        for (int i = 0; i < faceFrom.length; i++) res.setColor(faceTo[i], this.getColor(faceFrom[i]));
        for (int i = 0; i < sidesFrom.length; i++) res.setColor(sidesTo[i], this.getColor(sidesFrom[i]));
        return res;
    }

    // returns a random scrambled rubik's cube by applying random rotations
    public static RubiksCube scrambledCube(int numTurns) {
        RubiksCube r = new RubiksCube();
        char[] listTurns = getScramble(numTurns);
        for (int i = 0; i < numTurns; i++) {
            r= r.rotate(listTurns[i]);
        }
        return r;
    }

    public static char[] getScramble(int size){
        char[] listTurns = new char[size];
        for (int i = 0; i < size; i++) {
            switch (ThreadLocalRandom.current().nextInt(0, 6)) {
                case 0:
                    listTurns[i] = 'u';
                    break;
                case 1:
                    listTurns[i] = 'U';
                    break;
                case 2:
                    listTurns[i] = 'r';
                    break;
                case 3:
                    listTurns[i] = 'R';
                    break;
                case 4:
                    listTurns[i] = 'f';
                    break;
                case 5:
                    listTurns[i] = 'F';
                    break;
            }
        }
        return listTurns;
    }

    public class StateComparator implements Comparator<RubiksCube>
    {
        public int compare( RubiksCube x, RubiksCube y )
        {
            return x.fCost - y.fCost;
        }
    }


    // return the list of rotations needed to solve a rubik's cube
    public List<Character> solve() {
        if (dists == null)
            loadHeuristicLookupTable();
        HashMap<BitSet, Short> dists = RubiksCube.dists;
        StateComparator comparator = new StateComparator();

        PriorityQueue<RubiksCube> open = new PriorityQueue<>(11, comparator);
        open.add(this);

        while (!open.isEmpty()) {
            RubiksCube current = open.poll();

            // Check if we've reached the goal
            if (dists.get(current.cube) == 0) {
                return buildSolution(current);
            }

            // Try each possible move from here
            for (char move : POSSIBLE_MOVES) {
                RubiksCube nextState = current.rotate(move);
                nextState.gCost = current.gCost + 1;
                nextState.fCost = nextState.gCost + dists.get(nextState.cube);

                // Check if we should ignore this state for any reason
                boolean ignore = false;
                for (RubiksCube cube : open)
                    if (cube.cube.equals(nextState.cube))
                        ignore = true;

                if (!ignore) {
                    nextState.moveToHere = move;
                    nextState.prevState = current;
                    open.add(nextState);
                }
            }
        }


        return new ArrayList<>();
    }

    public static void loadHeuristicLookupTable() {
        File file = new File(LOOKUP_TABLE_FILENAME);
        try {
            FileInputStream f = new FileInputStream(file);
            System.out.println("Lookup table file saved. Loading...");
            ObjectInputStream s = new ObjectInputStream(f);
            dists = (HashMap<BitSet, Short>) s.readObject();
            s.close();
            f.close();
            System.out.println("Successfully loaded lookup table from disk.");
        }
        catch (FileNotFoundException e){
            System.out.println("Lookup table not yet generated. Generating...");
            dists = createHeuristicLookupTable(14);
            System.out.println("Lookup table successfully generated.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private List<Character> buildSolution(RubiksCube finalState) {
        List<Character> result = new ArrayList<>();
        while (finalState.prevState != null) {
            result.add(finalState.moveToHere);
            finalState = finalState.prevState;
        }
        Collections.reverse(result);
        return result;

    }
    private static HashMap<BitSet, Short> createHeuristicLookupTable(int levels) {


        HashMap<BitSet, Short> map = new HashMap<>();
        Queue<RubiksCube> open = new LinkedList<>();
        ArrayList<Character> possibleMoves = new ArrayList<>();
        possibleMoves.add('u');
        possibleMoves.add('U');
        possibleMoves.add('r');
        possibleMoves.add('R');
        possibleMoves.add('f');
        possibleMoves.add('F');

        // Add the solved cube state
        RubiksCube solvedCube = new RubiksCube();
        open.add(solvedCube);
        map.put(solvedCube.cube, (short)0);

        // Use BFS to explore all the possible states in 'levels' moves
        while (!open.isEmpty()) {
            RubiksCube cube = open.poll();
            short distToNextState = (short)(map.get(cube.cube) + 1);

            if (distToNextState > levels)
                continue; // Don't go beyond 'levels' deep

            for (char c : possibleMoves) {
                // Load the current cube state into the RubiksCube object
                RubiksCube newCube = cube.rotate(c);
                if (!map.containsKey(newCube.cube)) {
                    map.put(newCube.cube, distToNextState);
                    open.add(newCube);
                }
            }
        }
        FileOutputStream fwriter;
        try {
            fwriter = new FileOutputStream(LOOKUP_TABLE_FILENAME);
            ObjectOutputStream writer = new ObjectOutputStream(fwriter);

            writer.writeObject(map);

            writer.close(); //don't forget to close the writer
            fwriter.close();
        }
        catch (Exception e){
//            fwriter = new FileOutputStream("sample", 1);
        }
        return map;
    }

}