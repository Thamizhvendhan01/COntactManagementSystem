package com.example.cmsui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.regex.Pattern;

public class AddPane {
    DataHandler dataHandler = new DataHandler();
    private static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
    MainScreen ms = new MainScreen();
    TextField name = new TextField();
    TextField number = new TextField();
    TextArea description = new TextArea();
    public void  addPane(Pane commonRightPane){
        Pane basePane = new Pane();
        TextField name = new TextField();
        TextField number = new TextField();
        TextArea description = new TextArea();
        description.setPrefRowCount(5);
        Button saveButton = new Button("SAVE");
        description.setPromptText("Enter the Description........");
        number.setPromptText("Enter the Phone number");
        name.setPromptText("Enter the name :");
        basePane.setStyle("-fx-background-color: E1F8DC;");
        name.setFont(Font.font(20));
        number.setFont(Font.font(20));
        description.setFont(Font.font(20));
        int maxLength = 200;
        description.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                showAlert("Text Limit Reached", "You cannot exceed " + maxLength + " characters.");
                return null;
            }
            return change;
        }));
        DropShadow dropShadow1 = new DropShadow();
        dropShadow1.setRadius(1);
        dropShadow1.setOffsetX(10);
        dropShadow1.setOffsetY(10);
        dropShadow1.setColor(Color.web("#484848"));
        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setRadius(1);
        dropShadow2.setOffsetX(10);
        dropShadow2.setOffsetY(10);
        dropShadow2.setColor(Color.web("E1F8DC"));
        saveButton.setOnMouseEntered(e->{
            saveButton.setEffect(dropShadow1);
        });
        saveButton.setOnMouseExited(e->{
            saveButton.setEffect(dropShadow2);
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.widthProperty().addListener((ol, ov, nv) -> {
            vBox.setPadding(new Insets(100, nv.doubleValue()*0.3D,75, nv.doubleValue()*0.3D));
        });
        vBox.setSpacing(30);
        saveButton.setOnMouseClicked(e->{
            saveButton.setEffect(dropShadow2);
            boolean nameVerify = verifyname(name.getText());
            if (!nameVerify){
                showAlert("Name constrain","Name length should be between 4 to 30");
            }
            boolean numberVerify = isValidMobileNumber(number.getText());
            if (!numberVerify){
                showAlert("Invalid Number","Enter the valid number ");
            }
            numberVerify = dataHandler.isNumberExist(number.getText());
            if (numberVerify){;
                Person person = dataHandler.getDetailsByNumber(number.getText());
                showAlert(person.name,person.numbers,person.description);
            }
            if(nameVerify && !numberVerify && number.getText().length()==10){
                dataHandler.saveNumber(name.getText(),number.getText(),description.getText());
                name.clear();
                number.clear();
                description.clear();
            }
        });
        basePane.heightProperty().addListener((ol,ov,nv)->{
            vBox.setPrefHeight(nv.doubleValue());

        });
        basePane.widthProperty().addListener((ol,ov,nv)->{
            vBox.setPrefWidth(nv.doubleValue());
        });
        basePane.setPrefHeight(commonRightPane.getHeight());
        commonRightPane.heightProperty().addListener((l,o,n)->{
            basePane.setPrefHeight(n.doubleValue());
        });
        basePane.setPrefWidth(commonRightPane.getWidth());
        commonRightPane.widthProperty().addListener((l,o,n)->{
            basePane.setPrefWidth(n.doubleValue());
        });
        commonRightPane.getChildren().add(basePane);
        vBox.getChildren().addAll(name,number,description,saveButton);
        basePane.getChildren().addAll(vBox);
    }
    public String getName(){
        return name.getText();
    }
    public String getNumber(){
        return number.getText();
    }
    public String getDescription(){
        return description.getText();
    }
    public  boolean isValidMobileNumber(String mobileNumber) {
        if (mobileNumber == null) {
            return false;
        }
        return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches();
    }
    public boolean verifyname(String s){
        return s.length()>=4 && s.length()<=30;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlert(String name, List<String> numbers,String desc){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Already exist");
        alert.setHeaderText(null);
        alert.setContentText("This number already mapped with this person in your contact : " + name);
        alert.show();
    }
}
