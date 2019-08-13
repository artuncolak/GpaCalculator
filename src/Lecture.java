import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Lecture {
    private TextField name;
    private ComboBox<String> letterGrade;
    private ComboBox<Double> credit;

    public Lecture(TextField name, ComboBox<Double> credit, ComboBox<String> letterGrade) {
        this.name = name;
        this.credit = credit;
        this.letterGrade = letterGrade;
    }

    public TextField getName() {
        return name;
    }

    public ComboBox<String> getLetterGrade() {
        return letterGrade;
    }

    public ComboBox<Double> getCredit() {
        return credit;
    }
}