import java.util.HashMap;

public class DungeonGame {

    public static int minInitialHealth(int[][] map) {
        return minInitialHealth(map, 0, new HashMap<>());
    }

    // Top-down
    private static int minInitialHealth(int[][] map, int index, HashMap<Integer, Integer> DP) {
        if (index == map.length*map[0].length - 1) { // Last square
            return Math.max(1, 1 - map[map.length-1][map[0].length-1]); // HP needed to reach the last square
        }

        if (DP.containsKey(index)) {
            return DP.get(index);
        }

        // Calculate the minimum health needed when leaving this cell to win
        int row = index / map.length;
        int col = index % map.length;
        int minHealthFromHere = 1;
        if (row == map[0].length - 1) { // Check if we're on the last row
            // Only check right
            minHealthFromHere = minInitialHealth(map, index + 1, DP);
        } else if (col == map.length - 1) { // Check if we're on the last column
            // Only check down
            minHealthFromHere = minInitialHealth(map, index + map.length, DP);
        } else {
            minHealthFromHere = Math.min(
                    minInitialHealth(map, index + 1, DP), // Check right
                    minInitialHealth(map, index + map.length, DP) // Check down
            );
        }

        // Determine and save the minimum health needed to travel via this cell
        int cellHPDelta = map[row][col];
        int minHealthToHere = Math.max(minHealthFromHere - cellHPDelta, 1);
        DP.put(index, minHealthToHere);

        return minHealthToHere;

    }
}
