package com.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private TextField textField = new TextField();
    private double num1;
    private String operator;
    private boolean start = true;

    public void start(Stage stage) throws Exception{
        textField.setFont(Font.font(20));
        textField.setPrefHeight(20);
        textField.setPrefWidth(165);
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setStyle("-fx-text-fill: blueViolet;");
        textField.setEditable(false);

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10));
        stackPane.getChildren().add(textField);

        Label label = new Label("by Rahul");
        label.setFont(Font.font(10));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.TOP_CENTER);

        StackPane newPane = new StackPane(label);
        newPane.setAlignment(Pos.TOP_CENTER);

        TilePane tile = new TilePane();
        tile.setHgap(10);
        tile.setVgap(10);
        tile.setAlignment(Pos.TOP_CENTER);
        tile.setPadding(new Insets(7));
        tile.getChildren().addAll(
                createButtonForNumber("7"),
                createButtonForNumber("8"),
                createButtonForNumber("9"),
                createButtonForOperator("/"),

                createButtonForNumber("4"),
                createButtonForNumber("5"),
                createButtonForNumber("6"),
                createButtonForOperator("*"),

                createButtonForNumber("1"),
                createButtonForNumber("2"),
                createButtonForNumber("3"),
                createButtonForOperator("-"),

                createButtonForNumber("."),
                createButtonForNumber("0"),
                createButtonForNumber("00"),
                createButtonForOperator("%"),

                createButtonForClear("C"),
                createButtonForDelete("D"),
                createButtonForOperator("="),
                createButtonForOperator("+"),
                newPane
                );

        BorderPane root = new BorderPane();
        root.setTop(stackPane);
        root.setCenter(tile);

        root.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
        Scene scene = new Scene(root,200,310);
        stage.setScene(scene);
        stage.setTitle("Calculator");

        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    // method to create buttons for numbers
    private Button createButtonForNumber(String ch){
        Button button = new Button(ch);
        button.setPrefSize(35,35);
        button.setFont(Font.font(14));
        button.setFont(Font.font("bold"));
        button.setTextFill(Color.BLUEVIOLET);
        button.setOnAction(this::processNumbers);
        return button;
    }
    // method to handle event when number is clicked
    private void processNumbers(ActionEvent e){
        if (start){
            textField.setText("");
            start = false;
        }
        String value = ((Button)e.getSource()).getText();
        if (    textField.getText().equals("+") || textField.getText().equals("-") ||
                textField.getText().equals("*") || textField.getText().equals("/") || textField.getText().equals("%")) {
            textField.setText("");
        }
        textField.setText(textField.getText()+value);
    }

    // method to create Buttons for operators
    private Button createButtonForOperator(String ch){
        Button button = new Button(ch);
        button.setFont(Font.font(14));
        button.setPrefSize(35,35);
        button.setTextFill(Color.BLUEVIOLET);
        button.setOnAction(this::processOperator);
        return button;
    }

    // method to handle event when operator clicked
    private void processOperator(ActionEvent e){

        if (textField.getText().isEmpty() || textField.getText().equals("/") || textField.getText().equals("*") ||
        textField.getText().equals("+") || textField.getText().equals("-") || textField.getText().equals("%")){
            textField.setPromptText("Enter a Number");
            return;
        }

        String value = ((Button)e.getSource()).getText();

        if (value.equals("=")){
            double num2 = Double.parseDouble(textField.getText());
            if (num2 == 0 && (operator.equals("/") || operator.equals("%"))){
                textField.setText("");
                textField.setPromptText("Divide by zero");
                return;
            }
            double result = calculate(num1,num2,operator);
            textField.setText(String.valueOf(result));
            start = true;
            operator = "";
        } else {
            num1 = Double.parseDouble(textField.getText());
            operator = value;
            textField.setText(value);
        }
    }

    //method for Clear button
    private Button createButtonForClear(String ch){
        Button button = new Button(ch);
        button.setFont(Font.font(14));
        button.setMaxSize(35,35);
        button.setTextFill(Color.BLUEVIOLET);
        button.setOnAction(e-> {
            textField.setText(operator = "");
            operator = "";
            start = true;
        });
        return button;
    }

    //method for Delete button
    private Button createButtonForDelete(String ch){
        Button button = new Button(ch);
        button.setFont(Font.font(14));
        button.setMaxSize(35,35);
        button.setTextFill(Color.BLUEVIOLET);
        button.setOnAction(e-> {
            if (textField.getText().equals("")){
                textField.setPromptText("Enter a Number");
                return;
            }
            String newText = textField.getText().substring(0,textField.getText().length()-1);
            textField.setText(newText);
        });
        return button;
    }

    // method to calculate all arithmetic operations
    private double calculate(double num1, double num2, String operator){
        double result = 0;

        switch (operator){
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "%" -> result = num1 % num2;
            case "/" -> result = num1 / num2;
            default -> result = 0;
        }
        return result;
    }

    public static void main(String[] args) {
        launch();
    }
}
