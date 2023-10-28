package com.kazurayam.unittest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * StringSequence.indexOfSubsequence() will return the index value at which the subsequence is
 * found in the sequence.
 *
 * Given the sequence is ["Users", "me", "tmp", "selenium-webdriver-java", "selenium-webdriver-junit4", "build", "classes", "java", "test", "com", "kazurayam", "webdriver", "TestHelper.class"],
 * and the subsequence is ["build", "classes", "java", "test"].
 * Then StringSequence.indexOfSubsequence(sequence, subsequence) should return 5.
 * The value 5 is the index value of the string "build" in the given sequence.
 * The value "build" the head of the subsequence.
 *
 * Provided the returned value of indexOfSubsequence, I can get the sublist out of the sequence:
 *     ["Users", "me", "tmp", "selenium-webdriver-java", "selenium-webdriver-junit4"].
 * I can convert this string list into a valid Path:
 *     /Users/me/tmp/selenium-webdriver-java/selenium-webdriver-junit
 * which is the absolute Path of the subproject's project directory.
 *
 * Provided with this result, I can create a directory into which my test classes write output files.
 *     /Users/me/tmp/selenium-webdriver-java/selenium-webdriver-junit/test-output
 */
public class StringSequence {

    List<String> sequence;

    /**
     *
     * @param sequence List of Strings as target to scan
     */
    public StringSequence(List<String> sequence) {
        Objects.requireNonNull(sequence);
        if (sequence.isEmpty())
            throw new IllegalArgumentException("sequence must not be empty");
        this.sequence = sequence;
    }

    public Integer indexOfSubsequence(List<String> subsequence) {
        Objects.requireNonNull(subsequence);
        if (sequence.isEmpty())
            throw new IllegalArgumentException("subsequence must not be empty");

        List<Integer> indexList = this.indexListOfSubsequence(subsequence);
        if (indexList.isEmpty()) {
            return -1;
        } else {
            return indexList.get(0);
        }
    }

    /**
     * Provided that the sequence = ["a", "b", "c", "d"] and the subsequence = ["b", "c"],
     * then indexOfSubsequence(sequence, subsequence) returns 1, which is the index of "b"
     * in the sequence List.
     *
     * Provided that the sequence = ["a", "b", "c", "d"] and the subsequence = ["e"],
     * then indexOfSubsequence(sequence, subsequence) returns -1 as "e" in not found
     * in the sequence List.
     *
     * Provided that the sequence is
     * @param subsequence List of String as marker to look for
     * @return the index in the sequence where the subsequence is found. return -1 if not found.
     */
    public List<Integer> indexListOfSubsequence(List<String> subsequence) {
        Objects.requireNonNull(subsequence);
        if (sequence.isEmpty())
            throw new IllegalArgumentException("subsequence must not be empty");
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < sequence.size(); i++) {
            if (sequence.get(i).equals(subsequence.get(0))) {
                if (subsequence.size() <= (sequence.size() - i)) {
                    boolean matched = true;
                    for (int j = 0; j < subsequence.size(); j++) {
                        if (subsequence.get(j).equals(sequence.get(i + j))) {
                            ;
                        } else {
                            matched = false;
                        }
                    }
                    if (matched) {
                        indexList.add(i);
                    }
                }
            }
        }
        return indexList;
    }
}
