package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JackarooGUI extends Application {
	
	public void start(Stage primaryStage) {
        try {
            // ===== Load FXML file created with Scene Builder =====
            var fxmlUrl = getClass().getResource("/Main.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("Cannot find Main.fxml in classpath. Make sure resources are copied to output directory.");
            }
            System.out.println("Loading FXML from: " + fxmlUrl);
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();	

            // ===== Create and start the scene =====
            Scene scene = new Scene(root);
            primaryStage.setTitle("JACKAROO GAME BY TEAM 250");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Failed to start game", e.getMessage());
        }
    }
    
    private void showErrorDialog(String title, String message) {
        Stage dialog = new Stage();
        dialog.setTitle(title);
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        
        javafx.scene.control.Label label = new javafx.scene.control.Label(message);
        label.setWrapText(true);
        javafx.scene.control.Button closeButton = new javafx.scene.control.Button("OK");
        closeButton.setOnAction(e -> dialog.close());
        
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10, label, closeButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));
        
        Scene scene = new Scene(layout, 400, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public static void main(String[] args) {
    	
        launch(args);
    }
}