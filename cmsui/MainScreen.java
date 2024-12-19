package com.example.cmsui;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainScreen extends Application {
    public double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();

    @Override
    public void start(Stage primaryStage) {
        Pane basePane1 = new Pane();
        Pane basePane2 = new Pane();
        Pane commonRightPane = new Pane();
        basePane1.getStylesheets().add(Objects.requireNonNull(MainScreen.class.getResource("Style.css")).toExternalForm());
        ListView<String> listView = new ListView<>();
        listView.getItems().add("ADD CONTACT");
        listView.getItems().add("VIEW CONTACTS");
        listView.getItems().add("UPDATE");
        listView.getItems().add("DELETE");
        listView.getItems().add("ABOUT");
        listView.getItems().add("HELP");
        listView.getItems().add("SETTINGS");
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectedItemProperty().addListener((ol, ov, nv) -> {
            commonRightPane.getChildren().clear();
            switch (nv) {
                case "ADD CONTACT" -> {
                    AddPane ap = new AddPane();
                    ap.addPane(commonRightPane);
                }
                case "VIEW CONTACTS" ->{
                    ShowPane sp = new ShowPane();
                    sp.showPane(commonRightPane);
                }
                default -> {
                    commonRightPane.getChildren().clear();
                }
            }
        });
        commonRightPane.setStyle("-fx-background-color: CAF1DE;");
        basePane2.setStyle("-fx-background-color: violet;");
        basePane2.heightProperty().addListener((ol,ov,nv)->{
            listView.setPrefHeight(nv.doubleValue());
            commonRightPane.setPrefHeight(nv.doubleValue());
        });
        basePane2.widthProperty().addListener((ol,ov,nv)->{
            final double w = Math.min(nv.doubleValue()*0.3,250);
            listView.setPrefWidth(w);
            commonRightPane.setPrefWidth(nv.doubleValue()-w);
            commonRightPane.setLayoutX(w);
        });
        basePane1.heightProperty().addListener((ol,ov,nv)->{
            basePane2.setPrefHeight(nv.doubleValue());
//            basePane2.setLayoutY(nv.doubleValue()*0);
        });
        basePane1.widthProperty().addListener((ol,ov,nv)->{
            basePane2.setPrefWidth(nv.doubleValue());
        });
        basePane2.getChildren().addAll(listView,commonRightPane);
        basePane1.getChildren().add(basePane2);
        Scene scene = new Scene(basePane1);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(SCREEN_WIDTH*0.60D);
        primaryStage.setMinHeight(SCREEN_HEIGHT*0.50);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
