package com.kazurayam.unittest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSequenceTest {

    @Test
    public void test_indexListOfSubsequence_trivial() {
        StringSequence ss = new StringSequence(Arrays.asList("a", "b", "c", "d"));
        List<Integer> indexList = ss.indexListOfSubsequence(Arrays.asList("b", "c"));
        assertThat(indexList).isNotEmpty();
        assertThat(indexList.get(0)).isEqualTo(1);
    }

    @Test
    public void test_indexListOfSubsequence_mimimal_positive() {
        StringSequence ss = new StringSequence(Arrays.asList("a"));
        List<Integer> indexList = ss.indexListOfSubsequence(Arrays.asList("a"));
        assertThat(indexList).isNotEmpty();
        assertThat(indexList.get(0)).isEqualTo(0);
    }

    @Test
    public void test_indexListOfSubsequence_mimimal_negative() {
        StringSequence ss = new StringSequence(Arrays.asList("a"));
        List<Integer> indexList = ss.indexListOfSubsequence(Arrays.asList("X"));
        assertThat(indexList).isEmpty();
    }

    @Test
    public void test_indexListOfSubsequence_match_at_the_tail() {
        StringSequence ss = new StringSequence(Arrays.asList("a", "b", "c"));
        List<Integer> indexList = ss.indexListOfSubsequence(Arrays.asList("b", "c"));
        assertThat(indexList).isNotEmpty();
        assertThat(indexList.get(0)).isEqualTo(1);
    }

    @Test
    public void test_indexListOfSubsequence_multiple_indices() {
        StringSequence ss = new StringSequence(Arrays.asList("a", "b", "c", "b", "c"));
        List<Integer> indexList = ss.indexListOfSubsequence(Arrays.asList("b", "c"));
        assertThat(indexList).isNotEmpty();
        assertThat(indexList.size()).isEqualTo(2);
        assertThat(indexList.get(0)).isEqualTo(1);
        assertThat(indexList.get(1)).isEqualTo(3);
    }

    @Test
    public void test_indexOfSubsequence_positive() {
        StringSequence ss = new StringSequence(Arrays.asList("a", "b", "c", "b", "c"));
        int index = ss.indexOfSubsequence(Arrays.asList("b", "c"));
        assertThat(index).isEqualTo(1);
    }

    @Test
    public void test_indexOfSubsequence_negative() {
        StringSequence ss = new StringSequence(Arrays.asList("a", "b", "c", "b", "c"));
        int index = ss.indexOfSubsequence(Arrays.asList("x", "y"));
        assertThat(index).isEqualTo(-1);
    }

}
