package GpaCalculator.Controllers;

import GpaCalculator.Exceptions.MaxRowNumberReachedException;
import GpaCalculator.Lecture;
import GpaCalculator.Models.GpaCalculatorModel;
import GpaCalculator.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.beans.EventHandler;
import java.util.ArrayList;

public class Controller {

    @FXML
    private GridPane lecturesPane;
    @FXML
    private TextField gpaTextField;

    private GpaCalculatorModel gpaCalculatorModel = new GpaCalculatorModel();

    private ArrayList<String> letterGradeOptions = new ArrayList<>();
    private ArrayList<Double> creditOptions = new ArrayList<>();
    private ArrayList<Lecture> lectures = new ArrayList<>();

    private EventHandler e;
    private int row = 0;

    public Controller() {
        initializeCreditOptions();
        initializeLetterGradeOptions();
    }

    private void initializeLetterGradeOptions() {
        letterGradeOptions.add("AA");
        letterGradeOptions.add("BA");
        letterGradeOptions.add("BB");
        letterGradeOptions.add("CB");
        letterGradeOptions.add("CC");
        letterGradeOptions.add("DC");
        letterGradeOptions.add("DD");
        letterGradeOptions.add("FD");
        letterGradeOptions.add("FF");
    }
    private void initializeCreditOptions() {
        creditOptions.add(0.5);
        creditOptions.add(1.0);
        creditOptions.add(1.5);
        creditOptions.add(2.0);
        creditOptions.add(2.5);
        creditOptions.add(3.0);
        creditOptions.add(3.5);
        creditOptions.add(4.0);
        creditOptions.add(4.5);
        creditOptions.add(5.0);
        creditOptions.add(5.5);
        creditOptions.add(6.0);
        creditOptions.add(6.5);
        creditOptions.add(7.0);
        creditOptions.add(7.5);
        creditOptions.add(8.0);
        creditOptions.add(8.5);
        creditOptions.add(9.0);
        creditOptions.add(10.0);
        creditOptions.add(11.0);
        creditOptions.add(11.5);
        creditOptions.add(12.0);
        creditOptions.add(12.5);
        creditOptions.add(13.0);
        creditOptions.add(13.5);
        creditOptions.add(14.0);
        creditOptions.add(14.5);
        creditOptions.add(15.0);
        creditOptions.add(15.5);
        creditOptions.add(16.0);
        creditOptions.add(16.5);
        creditOptions.add(17.0);
        creditOptions.add(17.5);
        creditOptions.add(18.0);
        creditOptions.add(18.5);
        creditOptions.add(19.0);
        creditOptions.add(19.5);
        creditOptions.add(20.0);
    }

    @FXML
    private void initialize() {
        //Adding first 3 lectures to lectures pane
        for (int i = 0; i < 3; i++) {
            addLecture();
        }
    }

    private void increaseWindowSize(int size){
        lecturesPane.getScene().getWindow().setHeight(lecturesPane.getScene().getWindow().getHeight() + size);
    }
    private void decreaseWindowSize(int size){
        lecturesPane.getScene().getWindow().setHeight(lecturesPane.getScene().getWindow().getHeight() - size);
    }

    //Button Handlers
    public void addLectureButton() {
        try {
            Validator.validateRowNumber(row);
            addLecture();
            increaseWindowSize(30);
        }catch (MaxRowNumberReachedException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Maksimum ders sayısına ulaşıldı.");
            alert.setContentText("Daha fazla ders ekleyemezsiniz.");
            alert.showAndWait();
        }
    }

    private void removeLectureButton(int row){
        removeLecture(row);
        decreaseWindowSize(30);
    }

    public void calculateGpaButton(){
        try{
            gpaCalculatorModel.calculateGpa(lectures, gpaTextField);
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Ders kredisi ve notu boş bırakılamaz.");
            alert.showAndWait();
        }
    }

    private void addLecture() {
        TextField lectureName = new TextField();
        lectureName.setPromptText("Ders Adı");

        ComboBox<Double> credit = new ComboBox<>();
        credit.setPromptText("Kredi");
        credit.setPrefWidth(100);
        credit.setItems(FXCollections.observableArrayList(creditOptions));

        ComboBox<String> lettergrade = new ComboBox<>();
        lettergrade.setPromptText("Not");
        lettergrade.setPrefWidth(100);
        lettergrade.setItems(FXCollections.observableArrayList(letterGradeOptions));

        Button deleteButton = new Button();
        deleteButton.setText("Sil");
        deleteButton.setPrefWidth(75);

        lecturesPane.add(lectureName, 0, row);
        lecturesPane.add(credit, 1, row);
        lecturesPane.add(lettergrade, 2, row);
        lecturesPane.add(deleteButton, 3, row);

        Lecture lecture = new Lecture(lectureName, credit, lettergrade);
        lectures.add(lecture);

        deleteButton.setOnAction(e -> removeLectureButton(lectures.indexOf(lecture)));
        row++;
    }

    private void removeLecture(int row){
        ArrayList<Node> deleteNodes = new ArrayList<>();

        //Decrease row index of every node but selected node for deletion.
        for (Node node : lecturesPane.getChildren()){
            int rowIndex = GridPane.getRowIndex(node);

            if (rowIndex == row){
                deleteNodes.add(node);
            }else if(rowIndex > row){
                GridPane.setRowIndex(node, (rowIndex - 1));
            }
        }

        lecturesPane.getChildren().removeAll(deleteNodes);
        lectures.remove(row);
        this.row = lectures.size();
    }
}
