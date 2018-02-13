import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        int total = 0;
        for (int i1 = 0; i1 < points.length; ++i1) {
            // Keep track of the number of points each distance from the point
            HashMap<Double, Integer> dists = new HashMap<Double, Integer>();
            // Calculate the distance to every other point and increment the counter for that distance in the HM
            for (int i2 = 0; i2 < points.length; ++i2) {
                if (i1 == i2) continue; // Don't calc dist to same point

                double dist = calcEucDist(points[i1], points[i2]);
                // Increment the counter or set it to 1 if it's 0
                dists.merge(dist, 1, Integer::sum);
            }
            // Go through each distance group
            Iterator it = dists.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                int dist = (int)pair.getValue();
                // Increment the number of boomerangs found
                total += dist * (dist-1);
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        return total;
    }

    private static double calcEucDist(int[] p1, int[] p2) {
        return Math.abs(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2));
    }
}

