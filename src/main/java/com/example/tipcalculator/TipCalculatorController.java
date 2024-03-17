package com.example.tipcalculator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class TipCalculatorController {
    // formatters for currency and percentages
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent = NumberFormat.getPercentInstance();

    // GUI controls defined in FXML and used by the controller's code
    @FXML
    private TextField amountTextField;

    @FXML
    private Label tipPercentageLabel;

    @FXML
    private Slider tipPercentageSlider;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    // Create properties for bill amount and tip percentage
    private DoubleProperty billAmount = new SimpleDoubleProperty(0);
    private DoubleProperty tipPercentage = new SimpleDoubleProperty(0.15); // 15% default

    // Called by FXMLLoader to initialize the controller
    // Called by FXMLLoader to initialize the controller
    public void initialize() {
        // 0-4 rounds down, 5-9 rounds up
        currency.setRoundingMode(RoundingMode.HALF_UP);

        // Bind the tip percentage label to the tip percentage property
        tipPercentageLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format("%.0f%%", tipPercentage.multiply(100)),
                        " Tip"
                )
        );

        // Bind the bill amount text field to the bill amount property
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amountTextField.setText(newValue.replaceAll("[^\\d]", ""));
                return;
            }
            billAmount.set(Double.parseDouble(newValue));
        });

        // Bind the tip text field to the calculated tip amount
        tipTextField.textProperty().bind(
                Bindings.createStringBinding(() -> currency.format(billAmount.getValue() * tipPercentage.getValue()), billAmount, tipPercentage)
        );

        // Bind the total text field to the calculated total amount
        totalTextField.textProperty().bind(
                Bindings.createStringBinding(() -> currency.format(billAmount.getValue() + billAmount.getValue() * tipPercentage.getValue()), billAmount, tipPercentage)
        );

        // Bind the tip percentage property to the slider value divided by 100
        tipPercentage.bind(tipPercentageSlider.valueProperty().divide(100));
    }


    // Method to handle the calculate button press (if needed)
    @FXML
    private void calculateButtonPressed() {
        // Implementation if needed
    }
}
