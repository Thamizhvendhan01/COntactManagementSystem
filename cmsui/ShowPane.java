package com.example.cmsui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowPane {
    final static ObservableList<Person> olp = FXCollections.observableArrayList();
   public void showPane(Pane commonRightPane){
       VBox vbBase = new VBox();
       vbBase.setAlignment(Pos.CENTER);
       HBox hbSeachBar = new HBox();
       int size = olp.size();
       Label count = new Label(String.valueOf(size));
       hbSeachBar.setMaxWidth(commonRightPane.getWidth()*0.8D);

       Button deleteButton = new Button("Delete");
       Button updateButton = new Button("Update");

       deleteButton.setDisable(true);
       updateButton.setDisable(true);


       ChoiceBox<String> option = new ChoiceBox<>();
       option.getItems().add("Name");
       option.getItems().add("Number");
       TextField searchBar = new TextField();
        option.getSelectionModel().selectFirst();
        option.getSelectionModel().selectedItemProperty().addListener((ol,ov,nv)->{
            searchBar.setText("");
        });


       searchBar.setPromptText("Search here......");

        Button button = new Button("search");

        TableView<Person> tableView = new TableView<>();
        TableColumn<Person,String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setResizable(false);
        column1.setPrefWidth(200);

        TableColumn<Person, ArrayList<String>> column2 = new TableColumn<>("Numbers");
        column2.setCellValueFactory(new PropertyValueFactory<>("numbers"));


        TableColumn<Person,String> column3 = new TableColumn<>("Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Person, LocalDate> column4 = new TableColumn<>("Added Date");
        column4.setCellValueFactory(new PropertyValueFactory<>("addDate"));

       TableColumn<Person, LocalDate> column5 = new TableColumn<>("Last Date Of modified");
       column5.setCellValueFactory(new PropertyValueFactory<>("modifiedDate"));


       TableColumn<Person, Void> actionColumn = new TableColumn<>("Actions");


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(actionColumn);



        searchBar.textProperty().addListener((ol, ov, nv) -> {
            if(option.getSelectionModel().getSelectedItem().equals("Name")) {
                tableView.setItems(FXCollections.observableArrayList(olp.stream().filter(e -> e.getName().contains(nv)).sorted((a, b) -> {
                    if (a.getName().indexOf(nv) < b.getName().indexOf(nv)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }).collect(Collectors.toList())));
                int num = olp.size();
                count.setText(String.valueOf(num));
            } else {
                tableView.setItems(FXCollections.observableArrayList(olp.stream().filter(e -> {
                    List<String> arr = e.getNumbers();
                    for(String s : arr){
                        if(s.contains(nv)) return true;
                    }
                    return false;
                }).sorted((a, b) -> {
                    List<String> num1 = a.getNumbers();
                    for(String s : num1){
                        if(s.startsWith(nv)) return -1;
                    }
                    return 1;
                }).collect(Collectors.toList())));
            }
        });

       tableView.setItems(olp);

       tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           if (newSelection != null) {
               deleteButton.setDisable(false);
               updateButton.setDisable(false);
           } else {
               deleteButton.setDisable(true);
               updateButton.setDisable(true);
           }
       });

       actionColumn.setCellFactory(col -> new TableCell<>() {
           private final HBox buttonContainer = new HBox();
           private final Button deleteButton = new Button("Delete");
           private final Button updateButton = new Button("Update");
           private final Button saveButton = new Button("Update");


           {
               buttonContainer.getChildren().addAll(deleteButton, updateButton,saveButton);
               buttonContainer.setSpacing(5);

               buttonContainer.setVisible(true);

               saveButton.setVisible(false);

               deleteButton.setOnAction(event -> {
                   Person selectedPerson = getTableView().getItems().get(getIndex());
                   getTableView().getItems().remove(selectedPerson);
               });




           }

           @Override
           protected void updateItem(Void item, boolean empty) {
               super.updateItem(item, empty);
               if (empty) {
                   setGraphic(null);
               } else {
                   setGraphic(buttonContainer);
               }
           }
       });


       option.widthProperty().addListener((ol, ov, nv) -> {
           searchBar.setPrefWidth(hbSeachBar.getWidth() - (nv.doubleValue() + button.getWidth()));
       });
       button.widthProperty().addListener((ol, ov, nv) -> {
           searchBar.setPrefWidth(hbSeachBar.getWidth() - (nv.doubleValue() + option.getWidth()));
       });
       hbSeachBar.widthProperty().addListener((ol, ov, nv ) -> {
           searchBar.setPrefWidth(nv.doubleValue() - (option.getWidth() + button.getWidth()));
       });

       hbSeachBar.getChildren().addAll(option,searchBar,button);
       vbBase.setPrefHeight(commonRightPane.getHeight());
       commonRightPane.heightProperty().addListener((l,o,n)->{
           vbBase.setPrefHeight(n.doubleValue());

       });
       vbBase.setPrefWidth(commonRightPane.getWidth());
       commonRightPane.widthProperty().addListener((l,o,n)->{
           vbBase.setPrefWidth(n.doubleValue());
           hbSeachBar.setMaxWidth(n.doubleValue()*0.8D);
           tableView.setPrefWidth(n.doubleValue()*0.8D);
           tableView.setMaxWidth(n.doubleValue()*0.8D);
       });
       tableView.setPrefWidth(commonRightPane.getWidth()*0.8D);
       tableView.setMaxWidth(commonRightPane.getWidth()*0.8D);
       vbBase.setSpacing(25.0D);
       vbBase.getChildren().addAll(hbSeachBar, tableView,count);
        commonRightPane.getChildren().add(vbBase);
   }

}
