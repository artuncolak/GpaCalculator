package GpaCalculator.Models;

import GpaCalculator.Lecture;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GpaCalculatorModel {

    private HashMap<String, Double> letterGrade = new HashMap<>();

    public GpaCalculatorModel(){
        initializeLetterGrades();
    }

    private void initializeLetterGrades() {
        letterGrade.put("AA", 4.0);
        letterGrade.put("BA", 3.5);
        letterGrade.put("BB", 3.0);
        letterGrade.put("CB", 2.5);
        letterGrade.put("CC", 2.0);
        letterGrade.put("DC", 1.5);
        letterGrade.put("DD", 1.0);
        letterGrade.put("FD", 0.5);
        letterGrade.put("FF", 0.0);
    }

    public void calculateGpa(ArrayList<Lecture> lectures, TextField gpaResult){
        double creditSum = 0;
        double gradePoint = 0;

        for (Lecture lecture : lectures) {
            creditSum += lecture.getCredit().getValue();
            gradePoint += lecture.getCredit().getValue() * letterGrade.get(lecture.getLetterGrade().getValue());
        }

        gpaResult.setText(String.format("%.2f", (gradePoint / creditSum)));
    }


    public void calculateCumulativeGpa(ArrayList<Lecture> lectures, TextField gpaResult,
                                       TextField currentCumulativeGpa, TextField currentCreditSum,
                                       TextField cumulativeGpaResult){
        double creditSum = 0;
        double totalCreditSum = 0;
        double gradePoint = 0;
        double totalGradePoint = 0;

        for (Lecture lecture : lectures) {
            creditSum += lecture.getCredit().getValue();
            gradePoint += lecture.getCredit().getValue() * letterGrade.get(lecture.getLetterGrade().getValue());
        }

        totalGradePoint = gradePoint + Double.parseDouble(currentCumulativeGpa.getText()) *
                Double.parseDouble(currentCreditSum.getText());
        totalCreditSum = creditSum + Double.parseDouble(currentCreditSum.getText());

        gpaResult.setText(String.format("%.2f", (gradePoint / creditSum)));
        cumulativeGpaResult.setText(String.format("%.2f", (totalGradePoint / totalCreditSum)));
    }
}
