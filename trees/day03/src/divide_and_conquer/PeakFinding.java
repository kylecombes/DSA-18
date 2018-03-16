package divide_and_conquer;

public class PeakFinding {

    // Return -1 if left is higher, 1 if right is higher, 0 if peak
    private static int peakOneD(int i, int[] nums) {
        if (i > 0 && nums[i] < nums[i - 1])
            return -1;
        if (i < nums.length - 1 && nums[i] < nums[i + 1])
            return 1;
        return 0;
    }

    // Return -1 if left is higher, 1 if right is higher, 0 if peak
    private static int peakX(int x, int y, int[][] nums) {
        if (x > 0 && nums[y][x] < nums[y][x - 1])
            return -1;
        if (x < nums[0].length - 1 && nums[y][x] < nums[y][x + 1])
            return 1;
        return 0;
    }

    // Return -1 if up is higher, 1 if down is higher, 0 if peak
    private static int peakY(int x, int y, int[][] nums) {
        if (y > 0 && nums[y][x] < nums[y - 1][x])
            return -1;
        if (y < nums.length - 1 && nums[y][x] < nums[y + 1][x])
            return 1;
        return 0;
    }

    // These two functions return the index of the highest value along the X or Y axis, with the given
    // value for the other axis. Searches between hi (exclusive) and lo (inclusive)
    private static int maxXIndex(int y, int lo, int hi, int[][] nums) {
        int maxIndex = -1;
        for (int x = lo; x < hi; x++) {
            if (maxIndex == -1 || nums[y][x] > nums[y][maxIndex])
                maxIndex = x;
        }
        return maxIndex;
    }

    private static int maxYIndex(int x, int lo, int hi, int[][] nums) {
        int maxIndex = -1;
        for (int y = lo; y < hi; y++) {
            if (maxIndex == -1 || nums[y][x] > nums[maxIndex][x])
                maxIndex = y;
        }
        return maxIndex;
    }


    public static int findOneDPeak(int[] nums) {
        return findOneDPeak(nums, 0, nums.length-1);
    }

    private static int findOneDPeak(int[] nums, int lo, int hi) {
        if (lo == hi) { // Looking at single elem (base case)
            return isLocalPeak(nums, lo) ? lo : -1;
        }
        int midIdx = (hi - lo + 1) / 2 + lo;
        if (nums[midIdx-1] > nums[midIdx]) { // Left sibling is larger, so look in the left half
            int res = findOneDPeak(nums, lo, midIdx-1);
            if (res >= 0)
                return res;
        }
        // No peak on the left side, so try looking on the right side.
        int res = findOneDPeak(nums, midIdx, hi);
        return res >= 0 ? res : -1;
    }

    private static boolean isLocalPeak(int[] nums, int index) {
        // Check if left sibling is greater than or equal to element
        if (index > 0 && nums[index-1] > nums[index])
            return false;
        // Check if right sibling is less than or equal to element
        if (index+1 < nums.length && nums[index] < nums[index+1])
            return false;
        return true;
    }

    public static int[] findTwoDPeak(int[][] nums) {
        int rows = nums.length;
        int cols = nums[0].length;
        int middleRowMaxColIdx = maxXIndex(rows/2, 0, rows, nums);
        return findTwoDPeak(nums, 0, rows, 0, cols, rows/2, middleRowMaxColIdx);
    }

    private static int[] findTwoDPeak(int[][] nums, int minRow, int maxRow, int minCol, int maxCol, int curRow, int curCol) {
        int yIdx = maxYIndex(curCol, minRow, maxRow, nums);
        // Check if this point is a local max
        if (isLocalPeak(nums, curCol, yIdx))
            return new int[]{yIdx, curCol};
        // Find the max column element index
        int xIdx = maxXIndex(yIdx, minCol, maxCol, nums);
        // Shift the bounds for the next search
        if (yIdx < curRow) {
            maxRow = yIdx;
        } else {
            minRow = yIdx + 1;
        }
        if (xIdx < curCol) {
            maxCol = xIdx;
        } else {
            minRow = xIdx + 1;
        }
        return findTwoDPeak(nums, minRow, maxRow, minCol, maxCol, yIdx, xIdx);
    }

    private static boolean isLocalPeak(int[][] nums, int x, int y) {
        if (x+1 < nums[0].length && nums[y][x] < nums[y][x+1])
            return false;
        if (x-1 >= 0 && nums[y][x] < nums[y][x-1])
            return false;
        if (y+1 < nums[1].length && nums[y][x] < nums[y+1][x])
            return false;
        if (y-1 >= 0 && nums[y][x] < nums[y-1][x])
            return false;
        return true;
    }

}
