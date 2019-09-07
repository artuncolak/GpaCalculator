package GpaCalculator.Controllers;

import GpaCalculator.Exceptions.MaxRowNumberReachedException;
import GpaCalculator.Lecture;
import GpaCalculator.Models.GpaCalculatorModel;
import GpaCalculator.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.beans.EventHandler;
import java.util.ArrayList;

public class Controller {

    @FXML
    private GridPane lecturesPane;
    @FXML
    private TextField gpaResultTextField;
    @FXML
    private TextField currentCumulativeGpaTextField;
    @FXML
    private TextField currentCreditSumTextField;
    @FXML
    private CheckBox calculateCumulativeGpaCheckBox;
    @FXML
    private Text currentCumulativeGpaText;
    @FXML
    private Text currentCreditSumText;
    @FXML
    private Text cumulativeGpaResultText;
    @FXML
    private TextField cumulativeGpaResultTextField;

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
            if (calculateCumulativeGpaCheckBox.isSelected()){

                gpaCalculatorModel.calculateCumulativeGpa(lectures, gpaResultTextField, currentCumulativeGpaTextField,
                        currentCreditSumTextField, cumulativeGpaResultTextField);
            }else{
                gpaCalculatorModel.calculateGpa(lectures, gpaResultTextField);
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Ders kredisi ve notu boş bırakılamaz.");
            alert.showAndWait();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Mevcut ortalama veya mevcut toplam kredi boş bırakılamaz veya sayı dışında bir şey girilemez.");
            alert.showAndWait();
        }
    }

    //CheckBox Handler
    public void calculateCumulativeGpaCheckBoxChecked(){
        if (calculateCumulativeGpaCheckBox.isSelected()){
            currentCreditSumTextField.setDisable(false);
            currentCumulativeGpaTextField.setDisable(false);
            cumulativeGpaResultTextField.setDisable(false);
            currentCumulativeGpaText.setOpacity(1);
            currentCreditSumText.setOpacity(1);
            cumulativeGpaResultText.setOpacity(1);
        }else{
            currentCreditSumTextField.setDisable(true);
            currentCumulativeGpaTextField.setDisable(true);
            cumulativeGpaResultTextField.setDisable(true);
            currentCumulativeGpaText.setOpacity(0.5);
            currentCreditSumText.setOpacity(0.5);
            cumulativeGpaResultText.setOpacity(0.5);
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

        for (Node node : lecturesPane.getChildren()){
            int rowIndex = GridPane.getRowIndex(node);
            //If rowIndex of node is equals to rowIndex of wanted node for deletion add it to deleteNodes list
            //else decrement rowIndex of node for 1.
            if (rowIndex == row){
                deleteNodes.add(node);
            }else if(rowIndex > row){
                GridPane.setRowIndex(node, (rowIndex - 1));
            }
        }

        //Destroy the nodes ( •̀ᴗ•́ )
        lecturesPane.getChildren().removeAll(deleteNodes);
        lectures.remove(row);
        this.row = lectures.size();
    }
}
