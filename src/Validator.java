import Exceptions.MaxRowNumberReachedException;

public class Validator {
    private static final int MAX_ROW = 15;

    public static void validateRowNumber(int row) throws MaxRowNumberReachedException {
        if (row >= MAX_ROW)
            throw new MaxRowNumberReachedException();
    }
}
