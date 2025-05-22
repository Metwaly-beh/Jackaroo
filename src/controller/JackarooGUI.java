package controller;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JackarooGUI extends Application{
	
	public void start(Stage primaryStage) {
        try {
            // ===== Load FXML file created with Scene Builder =====
        	System.out.println(getClass().getResource("/Main.fxml"));
        	System.out.print(getClass().getResourceAsStream("/path/"+"red"+"ice.png"));
        	InputStream stream = getClass().getResourceAsStream("/path/redice.png");
        	System.out.println(stream == null ? "Resource not found" : "Resource found");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();	

            // ===== Create and start the scene =====
            Scene scene = new Scene(root);
            primaryStage.setTitle("JACKAROO GAME BY TEAM 250");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	
        launch(args);
    }



}