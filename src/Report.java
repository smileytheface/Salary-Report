// // Carl Mastny
// ITPRG247
// Lab 4
// This program creates a GUI for creating a salary report based on a CSV text file
// Sources: https://www.geeksforgeeks.org/javafx-filechooser-class/
//          https://docs.oracle.com/javafx/2/get_started/form.htm
//          https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
//          https://stackoverflow.com/questions/32219536/javafx-getting-input-from-textfield
//          https://code.makery.ch/blog/javafx-dialogs-official/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.DirectoryChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import SalaryReportStuff.SalaryReport;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Report extends Application {
	@Override
	public void start(Stage primaryStage) {
		// https://docs.oracle.com/javafx/2/get_started/form.htm
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(20);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Text Title 
		Text sceneTitle = new Text("Report Generator");
		sceneTitle.setFont(Font.font("Calibri", FontWeight.NORMAL, 30));
		grid.add(sceneTitle, 0, 0, 3, 1);
		
		// Name Entry
		Label enterName = new Label("Enter name: ");
		grid.add(enterName, 0, 1);
		
		TextField nameEntry = new TextField();
		grid.add(nameEntry, 1, 1);
		
		//File choice
		Label chooseFile = new Label("Choose file: ");
		grid.add(chooseFile, 0, 2);
		
		TextField fileChosen = new TextField();
		grid.add(fileChosen, 1, 2);
		
		Button fileChoiceButton = new Button("...");
		grid.add(fileChoiceButton, 2, 2);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("TextFiles", "*.txt"));
		
		//Destination choice
		Label chooseDestination = new Label("Choose destination folder: ");
		grid.add(chooseDestination, 0, 3);
		
		TextField destinationChosen = new TextField();
		grid.add(destinationChosen, 1, 3);
		
		Button destinationChoiceButton = new Button("...");
		grid.add(destinationChoiceButton, 2, 3);
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		
		//Submit
		Button submit = new Button("Submit");
		grid.add(submit, 0, 4);
		
		// https://www.geeksforgeeks.org/javafx-filechooser-class/
		//fileChooserButton event handler
		EventHandler<ActionEvent> fileChoice = new EventHandler<ActionEvent>() { 
  
        	public void handle(ActionEvent e) { 
  
                // get the file selected 
                File file = fileChooser.showOpenDialog(primaryStage); 
  
                if (file != null) { 
                      
                    fileChosen.setText(file.getAbsolutePath()); 
                } 
            } 
        };
        
        //destination folder button event handler
        EventHandler<ActionEvent> destinationChoice = new EventHandler<ActionEvent>() { 
  
            public void handle(ActionEvent e) { 
  
                // get the file selected 
                File file = directoryChooser.showDialog(primaryStage); 
  
                if (file != null) { 
                    destinationChosen.setText(file.getAbsolutePath()); 
                } 
            } 
        };
        
        //submit button event handler
        EventHandler<ActionEvent> submitted = new EventHandler<ActionEvent>() { 
	    	 	
	    	// https://stackoverflow.com/questions/32219536/javafx-getting-input-from-textfield
            public void handle(ActionEvent e) { 
            	
            	String[] fileValues = new String[3];
            	fileValues[0] = fileChosen.getText();
            	fileValues[1] = destinationChosen.getText();
            	fileValues[2] = nameEntry.getText();
            	
            	for (int i = 0; i < fileValues.length; i++) {
            		if (fileValues[i].isEmpty() == true) {
            			// https://code.makery.ch/blog/javafx-dialogs-official/
            			Alert alert = new Alert(AlertType.INFORMATION);
            			alert.setTitle("Whoops");
            			alert.setHeaderText(null);
            			alert.setContentText("Please make sure all fields are full");

            			alert.showAndWait();
            			break;
            		} else {
            			SalaryReport.generateReport(fileValues);;
            		}
            	}
            } 
        };
	    
	    fileChoiceButton.setOnAction(fileChoice);
	    destinationChoiceButton.setOnAction(destinationChoice);
	    submit.setOnAction(submitted);
		
		Scene scene = new Scene(grid, 400, 250);
		primaryStage.setTitle("Report Generator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}