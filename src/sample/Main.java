package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.PrintWriter;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("CubingUSA email export parser");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label inlabel = new Label("Raw list from CubingUSA:");
        grid.add(inlabel, 0, 0, 1, 1);

        TextArea input = new TextArea();
        input.setWrapText(true);
        grid.add(input, 0, 1, 4, 4);

        Label filelabel = new Label("(Optional) To output results to a txt file, type a file name here (periods and spaces will be removed)");
        filelabel.setWrapText(true);
        TextField filename = new TextField();
        filename.setMinWidth(250);

        HBox filefield = new HBox(filelabel, filename);
        filefield.setSpacing(20);

        grid.add(filefield, 0, 5);
        Button parse = new Button();
        parse.setMinWidth(120);
        parse.setText("Parse");
        grid.setHalignment(parse, HPos.CENTER);
        grid.add(parse, 0, 6);
        TextArea output = new TextArea();
        grid.add(output, 0, 10, 4, 4);

        parse.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String emails = input.getText();
                String[] people = emails.split(",");
                for(int i = 0; i<people.length; i++) {
                    people[i] = people[i].replaceAll(".+<", "");
                    people[i] = people[i].replaceAll(">", "");
                }

                //parse1
                for(String i:people) {
                    output.appendText(i);
                    output.appendText("\n");
                }

                if(!filename.getText().equals("")){
                    String inputname = filename.getText();
                    String filename = inputname.replaceAll("", "");
                    if (!inputname.equals(filename)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid File Name");
                        alert.setHeaderText("Enter a valid file name");
                        alert.setContentText("No periods or slashes are allowed.");
                        alert.showAndWait();
                    }
                    try{
                        PrintWriter writer = new PrintWriter(filename+".txt", "UTF-8");
                        for(String i: people){
                            writer.println(i);
                            writer.println("\n");
                        }

                        writer.close();
                    } catch (IOException getrekt) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("You dun goofed");
                        alert.setHeaderText("Something went wrong in writing the file");
                        alert.setContentText("Try a different file name");

                        alert.showAndWait();
                    }

                }


            }
        });

        Scene scene = new Scene(grid, 1000, 500);
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
