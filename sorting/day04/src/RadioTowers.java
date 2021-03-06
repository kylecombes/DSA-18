import java.util.ArrayList;
import java.util.List;

public class RadioTowers {
    static class Tower {
        double x, y;
        Tower(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double getDist(Tower t1, Tower t2) {
        double xDiff = t1.x - t2.x;
        double yDiff = t1.y - t2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private static boolean isWithin(Tower t1, Tower t2, int dist) {
        return getDist(t1, t2) <= dist;
    }

    // Strip contains a list of Towers sorted by x-coordinate, whose y-coordinates all fall in a 2-mile strip
    // Return true if no two towers are within 1 mile
    public static boolean checkStrip(List<Tower> strip) {
        // Maybe need to sort -- but constant time because only sorting 8 elements
        // Could break if you had a bunch of towers with the same y coordinate
        for (int i = 0; i < strip.size(); ++i) {
            for (int j = i+1; j < i+8 && j < strip.size(); ++j) {
                if (isWithin(strip.get(i), strip.get(j), 1))
                    return false;
            }
        }
        return true;
    }

    private static boolean checkSection(List<Tower> Ly, int lo, int hi) {
        if (hi - lo < 9)
            return checkStrip(Ly.subList(lo, hi));

        int midIdx = (hi - lo) / 2 + lo;
        // Check bottom half
        if (!checkSection(Ly, lo, midIdx))
            return false;
        // Check top half
        if (!checkSection(Ly, midIdx, hi))
            return false;
        // Check 8 points along boundary
        return checkSection(Ly, midIdx - 4, midIdx + 4);
    }

    // Return true if no two towers are within distance 1 of each other
    public static boolean validTowers(List<Tower> Lx, List<Tower> Ly) {
        assert Lx.size() == Ly.size();
        return checkSection(Ly, 0, Ly.size());
    }
}
