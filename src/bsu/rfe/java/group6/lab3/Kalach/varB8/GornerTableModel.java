package bsu.rfe.java.group6.lab3.Kalach.varB8;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return (int) Math.ceil((to - from) / step) + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        if (col == 0) {
            return roundToTwoDecimalPlaces(x);
        } else {
            double result = 0.0;
            for (int i = 0; i < coefficients.length; ++i) {
                result += coefficients[i] * Math.pow(x, coefficients.length - i - 1);
            }

            // Округляем результат
            double roundedResult = roundToTwoDecimalPlaces(result);

            if (col == 1) {
                return roundedResult;
            } else {
                // Проверяем наличие двух пар подряд стоящих одинаковых цифр
                return isDiverse(String.valueOf(roundedResult));
            }
        }
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private boolean isDiverse(String value) {
        // Убираем знак "-" из числа
        String[] parts = value.replace("-", "").split("\\.");
        if (parts.length != 2) return false; // Если нет дробной части

        String integerPart = parts[0]; // Целая часть
        String fractionalPart = parts[1]; // Дробная часть

        boolean integerEven = areAllDigitsEven(integerPart);     // Чётность целой части
        boolean fractionalOdd = areAllDigitsOdd(fractionalPart); // Нечётность дробной части

        boolean integerOdd = areAllDigitsOdd(integerPart);       // Нечётность целой части
        boolean fractionalEven = areAllDigitsEven(fractionalPart); // Чётность дробной части

        return (integerEven && fractionalOdd) || (integerOdd && fractionalEven);
    }

    // Проверка, все ли цифры чётные
    private boolean areAllDigitsEven(String digits) {
        for (char c : digits.toCharArray()) {
            if ((c - '0') % 2 != 0) { // Не чётная цифра
                return false;
            }
        }
        return true;
    }

    // Проверка, все ли цифры нечётные
    private boolean areAllDigitsOdd(String digits) {
        for (char c : digits.toCharArray()) {
            if ((c - '0') % 2 == 0) { // Не нечётная цифра
                return false;
            }
        }
        return true;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            default:
                return "Разностороннее";
        }
    }

    public Class<?> getColumnClass(int col) {
        return col == 2 ? Boolean.class : Double.class;
    }
}
