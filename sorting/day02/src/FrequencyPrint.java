import java.util.*;

public class FrequencyPrint {

    static String frequencyPrint(String s) {

        // Count all the occurrences of each word
        HashMap<String, WordGroup> map = new HashMap<>();
        String[] words = s.split("\\s+");;
        for (String word: words) {
            if (map.containsKey(word)) {
                ++map.get(word).count;
            } else {
                map.put(word, new WordGroup(word));
            }
        }

        // Sort the list by word count
        ArrayList<WordGroup> sortedGroups = new ArrayList<>(map.values());
        Collections.sort(sortedGroups, new Comparator<WordGroup>() {
            @Override
            public int compare(WordGroup o1, WordGroup o2) {
                return o1.count - o2.count;
            }
        });

        // Build the resulting string
        StringBuilder sb = new StringBuilder();
        for (WordGroup word : sortedGroups) {
            for (int i = 0; i < word.count; ++i) {
                sb.append(word.word);
                sb.append(' ');
            }
        }
        // Remove the last space
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }


}
