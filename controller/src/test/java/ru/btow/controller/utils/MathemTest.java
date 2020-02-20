package ru.btow.controller.utils;

import javafx.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MathemTest extends Mathem {

    @Test
    public void testRound() {

        System.out.println("\\=========== testRound() ===========\\");

        List<Pair<Pair<Float, Integer>, Float>> testCasesAndExpextedValues = new ArrayList<>();

        Pair<Float, Integer> testCase1 = new Pair<>(1.2345f, 3);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue1 = new Pair<>(testCase1, 1.235f);
        testCasesAndExpextedValues.add(testCaseAndExpValue1);

        Pair<Float, Integer> testCase2 = new Pair<>(12.34567f, 2);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue2 = new Pair<>(testCase2, 12.35f);
        testCasesAndExpextedValues.add(testCaseAndExpValue2);

        Pair<Float, Integer> testCase3 = new Pair<>(123.45123f, 3);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue3 = new Pair<>(testCase3, 123.451f);
        testCasesAndExpextedValues.add(testCaseAndExpValue3);

        Pair<Float, Integer> testCase4 = new Pair<>(1234.5432f, 2);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue4 = new Pair<>(testCase4, 1234.54f);
        testCasesAndExpextedValues.add(testCaseAndExpValue4);

        Pair<Float, Integer> testCase5 = new Pair<>(-1.2345f, 3);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue5 = new Pair<>(testCase5, -1.235f);
        testCasesAndExpextedValues.add(testCaseAndExpValue5);

        Pair<Float, Integer> testCase6 = new Pair<>(-12.34567f, 2);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue6 = new Pair<>(testCase6, -12.35f);
        testCasesAndExpextedValues.add(testCaseAndExpValue6);

        Pair<Float, Integer> testCase7 = new Pair<>(-123.45123f, 3);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue7 = new Pair<>(testCase7, -123.451f);
        testCasesAndExpextedValues.add(testCaseAndExpValue7);

        Pair<Float, Integer> testCase8 = new Pair<>(-1234.5432f, 2);
        Pair<Pair<Float, Integer>, Float> testCaseAndExpValue8 = new Pair<>(testCase8, -1234.54f);
        testCasesAndExpextedValues.add(testCaseAndExpValue8);

        testCasesAndExpextedValues.forEach(testCaseAndExpValue -> {
            System.out.println("Test case: value = "
                    + testCaseAndExpValue.getKey().getKey()
                    + ", accuracy = " + testCaseAndExpValue.getKey().getValue()
            );
            System.out.println("Expected result: " + testCaseAndExpValue.getValue());
            float actResult = Mathem.round(testCaseAndExpValue.getKey().getKey(),
                    testCaseAndExpValue.getKey().getValue());
            System.out.println("\\=========== VS ===========\\");
            System.out.println("Actual result: " + actResult);
            System.out.println();
            assertEquals(testCaseAndExpValue.getValue(), actResult, 0.0f);
        });
    }
}