public class FirstFailingVersion {

    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        return locateBadVersion(0, n, isBadVersion);
    }

    private static long locateBadVersion(long lower, long upper, IsFailingVersion checker) {
        if (lower == upper) // Found first bad version
            return lower;

        long mid = (upper - lower) / 2 + lower;

        if (checker.isFailingVersion(mid)) {
            return locateBadVersion(lower, mid, checker);
        } else {
            return locateBadVersion(mid + 1, upper, checker);
        }
    }
}
