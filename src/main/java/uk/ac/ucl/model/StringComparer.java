package uk.ac.ucl.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringComparer {
    private final int DIFFERENCE_THRESHOLD = 2;

    /*
     *Uses levenshtein distance algorithm to calculate the difference between two strings
     *Algorithm source: https://en.wikipedia.org/wiki/Levenshtein_distance
     *Essentially calculates the number of single-character edits (insertions deletions or substitutions) needed
     * to convert one string into another
     */
    public int calculateTextDifference(String s1, String s2) {
        int lenS1 = s1.length();
        int lenS2 = s2.length();

        // dp[i][j] will represent the minimum number of operations required to convert
        // the first i characters of string 1 to the first j characters of string 2
        int[][] dp = new int[lenS1 + 1][lenS2 + 1]; // dp stands for dynamic programming array (its a convention)

        for (int i = 0; i <= lenS1; i++) {
            for (int j = 0; j <= lenS2; j++) {
                if (i == 0) {
                    // Base case: If s1 is empty, the only way to convert it to s2 is by inserting all characters of s2.
                    dp[i][j] = j;
                } else if (j == 0) {
                    // Base case: If s2 is empty, the only way to convert s1 to it is by deleting all characters of s1.
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;

                    int insertionCost = dp[i][j - 1] + 1;
                    int deletionCost = dp[i - 1][j] + 1;
                    int substitutionCost = dp[i - 1][j - 1] + cost;

                    dp[i][j] = Math.min(insertionCost, Math.min(deletionCost, substitutionCost));
                }
            }
        }
        return dp[lenS1][lenS2];
    }

    // Compares every word of the search string with every word of the content being searches
    public boolean perWordComparison(String searchString, String content)
    {
        String[] searchWords = searchString.split("\\s+");
        String[] contentWords = content.split("\\s+");

        for (String searchWord : searchWords) {
            boolean foundMatch = false;

            for (String contentWord : contentWords) {
                if (calculateTextDifference(contentWord, searchWord) <= DIFFERENCE_THRESHOLD) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }

    public boolean containsExactSubstring(String input, String target)
    {
        String regex = "(?i)" + Pattern.quote(input);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}