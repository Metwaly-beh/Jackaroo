package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import View.CardView;
import model.Colour;
import model.card.Card;
import model.card.standard.Standard;
import model.card.wild.Burner;
import model.card.wild.Wild;
import model.player.Marble;
import model.player.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import engine.Game;
import engine.GameManager;
import engine.board.Board;
import exception.ActionException;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class Controller {
	private Game game;
	private Board board;
	private GameManager gameManager;
	private ImageView currentlySelectedCardView = null;

	@FXML private HBox bottomCards;
    @FXML private ImageView bottomCard1, bottomCard2, bottomCard3, bottomCard4;
    @FXML private HBox rightCards;
    @FXML private ImageView rightCard1, rightCard2, rightCard3, rightCard4;
    @FXML private HBox LeftCards;
    @FXML private ImageView leftCard1, leftCard2, leftCard3, leftCard4;
    @FXML private HBox topCards;
    @FXML private ImageView topCard1, topCard2, topCard3, topCard4;
    @FXML private ImageView firePitCard;
    @FXML private GridPane trackGridPane;
    
    
    
    @FXML private HBox CPU3SafeZone;
    @FXML private ImageView CPU3SZ0, CPU3SZ1, CPU3SZ2, CPU3SZ3;
    @FXML private ImageView CPU3SZM0, CPU3SZM1, CPU3SZM2, CPU3SZM3;
    @FXML private HBox CPU1SafeZone;
    @FXML private ImageView CPU1SZ0, CPU1SZ1, CPU1SZ2, CPU1SZ3;
    @FXML private ImageView CPU1SZM0, CPU1SZM1, CPU1SZM2, CPU1SZM3;
    @FXML private VBox PlayerSafeZone;
    @FXML private ImageView PlayerSZ0, PlayerSZ1, PlayerSZ2, PlayerSZ3;
    @FXML private ImageView PlayerSZM0, PlayerSZM1, PlayerSZM2, PlayerSZM3;
    @FXML private VBox CPU2SafeZone;
    @FXML private ImageView CPU2SZ0, CPU2SZ1, CPU2SZ2, CPU2SZ3;
    @FXML private ImageView CPU2SZM0, CPU2SZM1, CPU2SZM2, CPU2SZM3;
    
    
    @FXML private GridPane PlayerHome;
    @FXML private StackPane PlayerHome0,PlayerHome1,PlayerHome2,PlayerHome3;
    @FXML private ImageView PlayerMarble0,PlayerMarble1,PlayerMarble2,PlayerMarble3;
    @FXML private ImageView PlayerBase0,PlayerBase1,PlayerBase2,PlayerBase3;
    @FXML private GridPane CPU1Home;
    @FXML private StackPane CPU1Home0,CPU1Home1,CPU1Home2,CPU1Home3;
    @FXML private ImageView CPU1Base0,CPU1Base1,CPU1Base2,CPU1Base3;
    @FXML private ImageView CPU1Marble0,CPU1Marble1,CPU1Marble2,CPU1Marble3;
    @FXML private GridPane CPU2Home;
    @FXML private StackPane CPU2Home0,CPU2Home1,CPU2Home2,CPU2Home3;
    @FXML private ImageView CPU2Base0,CPU2Base1,CPU2Base2,CPU2Base3;
    @FXML private ImageView CPU2Marble0,CPU2Marble1,CPU2Marble2,CPU2Marble3;
    @FXML private GridPane CPU3Home;
    @FXML private StackPane CPU3Home0,CPU3Home1,CPU3Home2,CPU3Home3;
    @FXML private ImageView CPU3Base0,CPU3Base1,CPU3Base2,CPU3Base3;
    @FXML private ImageView CPU3Marble0,CPU3Marble1,CPU3Marble2,CPU3Marble3;
    
    @FXML private VBox CPU3Area,CPU2Area,CPU1Area,PlayerArea;
    @FXML private Label CPU3Label,CPU2Label,CPU1Label,PlayerName;
    @FXML private ImageView PlayerImg,CPU1Img,CPU2Img,CPU3Img;
    @FXML private Button Button1;
    @FXML private Label cur,next;
    
    private Card selectedCard;
    public void initialize() throws IOException, InvalidCardException {
    	Scanner scanner = new Scanner(System.in);
    	String name = scanner.nextLine();
    	scanner.close();
    	game = new Game(name);
    	gameManager = new Game(name);

    	
    	ArrayList<Colour> colourOrder = new ArrayList<>();
    	for (int i = 0; i < 4; i++) {
    		colourOrder.add(game.getPlayers().get(i).getColour());
    	}
    	board = new Board(colourOrder, gameManager);

    	ArrayList<Player> players = game.getPlayers();

    	ArrayList<Card> bottomHand = players.get(0).getHand();


    	Image image = helper(bottomHand.get(0));
    	bottomCard1.setImage(image);
    	image = helper(bottomHand.get(1));
    	bottomCard2.setImage(image);
    	image = helper(bottomHand.get(2));
    	bottomCard3.setImage(image);
    	image = helper(bottomHand.get(3));
    	bottomCard4.setImage(image);

    	Image imageBack = new Image(getClass().getResourceAsStream("/PlayingCards/card-back1.png"));
    	rightCard1.setImage(imageBack);
    	rightCard2.setImage(imageBack);
    	rightCard3.setImage(imageBack);
    	rightCard4.setImage(imageBack);
    	leftCard1.setImage(imageBack);
    	leftCard2.setImage(imageBack);
    	leftCard3.setImage(imageBack);
    	leftCard4.setImage(imageBack);
    	topCard1.setImage(imageBack);
    	topCard2.setImage(imageBack);
    	topCard3.setImage(imageBack);
    	topCard4.setImage(imageBack);

    	// Using CardView class
    	new CardView(bottomCard1, bottomHand.get(0), game, () -> {
    		if (currentlySelectedCardView != null) currentlySelectedCardView.setEffect(null);
    		currentlySelectedCardView = bottomCard1;
    	});
    	new CardView(bottomCard2, bottomHand.get(1), game, () -> {
    		if (currentlySelectedCardView != null) currentlySelectedCardView.setEffect(null);
    		currentlySelectedCardView = bottomCard2;
    	});
    	new CardView(bottomCard3, bottomHand.get(2), game, () -> {
    		if (currentlySelectedCardView != null) currentlySelectedCardView.setEffect(null);
    		currentlySelectedCardView = bottomCard3;
    	});
    	new CardView(bottomCard4, bottomHand.get(3), game, () -> {
    		if (currentlySelectedCardView != null) currentlySelectedCardView.setEffect(null);
    		currentlySelectedCardView = bottomCard4;
    	});
    	Button1=new Button();

    	setTrack();
    	setHome();
    	setSafeZones();
    	setAreas(name,colourOrder);
    	updateBoard();
    	PlayerMarble0.setOnMouseClicked(e -> handleMarbleClick(0));
    	PlayerMarble1.setOnMouseClicked(e -> handleMarbleClick(1));
    	PlayerMarble2.setOnMouseClicked(e -> handleMarbleClick(2));
    	PlayerMarble3.setOnMouseClicked(e -> handleMarbleClick(3));
    	updatePlayerTurnLabels(game.getPlayers().get(0),game.getPlayers().get(1));
    	
    }
    
    




 
    public void updatePlayerTurnLabels(Player currentPlayer, Player nextPlayer) {
        Platform.runLater(() -> {
            cur.setText("Current: " + getPlayerDisplayName(currentPlayer));
            next.setText("Next: " + getPlayerDisplayName(nextPlayer));
            
            
        });
    }

    private String getPlayerDisplayName(Player player) {
        if (isHumanPlayerTurn()) {
            return player.getName(); 
        } else {
            return "CPU " + (game.getPlayers().indexOf(player)); 
        }
    }

   
    private void handleMarbleClick(int marbleIndex) {
        try {
            // Deselect previous
            resetMarbleHighlights();

            // Get the selected marble
            Marble selectedMarble = game.getPlayers().get(0).getMarbles().get(marbleIndex);

            // Notify the backend
            game.selectMarble(selectedMarble);

            // Highlight in UI
            highlightSelectedMarble(marbleIndex);
        } catch (InvalidMarbleException e) {
            System.out.println("Invalid marble selection: " + e.getMessage());
            // Optional: Show alert to user
        }
    }
    private void highlightSelectedMarble(int marbleIndex) {
        ImageView[] playerMarbles = { PlayerMarble0, PlayerMarble1, PlayerMarble2, PlayerMarble3 };
        playerMarbles[marbleIndex].setStyle("-fx-effect: dropshadow(gaussian, yellow, 15, 0.5, 0, 0);");
    }
    private void resetMarbleHighlights() {
        PlayerMarble0.setStyle("");
        PlayerMarble1.setStyle("");
        PlayerMarble2.setStyle("");
        PlayerMarble3.setStyle("");
    }
    
    
    @FXML
    private void handleDeselectAll(ActionEvent event) {
        game.deselectAll();
        resetMarbleHighlights();
    }
    @FXML
    private ImageView firepitCard; // Linked to FXML
    
    @FXML
    public void handlePlayCard(ActionEvent event)throws GameException{
    	
        game.playPlayerTurn();
    }
    @FXML
    private void handleFieldMarble(ActionEvent event) throws IllegalDestroyException {
    	
    	
    	try {
    	    game.fieldMarble();
    	    updateBoard();
    	    
    	} catch (CannotFieldException e) {
    	    displayAlert("Field error",e.getMessage());
    	}
    }
    private void updateFirePitDisplay() {
        List<Card> firePit = game.getFirePit();
        
        if (!firePit.isEmpty()) {
            Card topCard = firePit.get(firePit.size()-1);
            Image cardImage = helper(topCard);
            
            if (cardImage != null) {
                firepitCard.setImage(cardImage);
            } 
        } else {
            firepitCard.setImage(null); // Clear if fire pit is empty
        }
    }
    
    
    
    private void setAreas(String name,ArrayList<Colour> colourOrder){
    	
    	    Label[] labels = {PlayerName, CPU1Label, CPU2Label, CPU3Label};

    	    for (int i = 0; i < 4; i++) {
    	        Colour color = colourOrder.get(i);
    	        Label currentLabel = labels[i];

    	        switch (color) {
    	            case RED:
    	              
    	                currentLabel.setStyle("-fx-text-fill: red;");
    	                break;
    	            case BLUE:
    	                
    	                currentLabel.setStyle("-fx-text-fill: blue;");
    	                break;
    	            case GREEN:
    	                
    	                currentLabel.setStyle("-fx-text-fill: green;");
    	                break;
    	            case YELLOW:
    	                
    	                currentLabel.setStyle("-fx-text-fill: CCB800;");
    	                break;
    	        }
    	    }
    	Image img=new Image(getClass().getResourceAsStream("/icons/linux-me.png"));
    	PlayerImg.setImage(img);
    	PlayerName.setText(name);
    	 Image noob1 = new Image(getClass().getResourceAsStream("/icons/noob1.png"));
    	    Image noob2 = new Image(getClass().getResourceAsStream("/icons/noob2.png"));
    	    Image noob3 = new Image(getClass().getResourceAsStream("/icons/noob3.png"));

    	    // Add images to list
    	    List<Image> images = new ArrayList<>();
    	    images.add(noob1);
    	    images.add(noob2);
    	    images.add(noob3);

    	    // Add ImageViews to list
    	    List<ImageView> imageViews = new ArrayList<>();
    	    imageViews.add(CPU1Img);
    	    imageViews.add(CPU2Img);
    	    imageViews.add(CPU3Img);

    	    // Shuffle both lists (or just shuffle one)
    	    Collections.shuffle(images);

    	    // Assign shuffled images to ImageViews
    	    for (int i = 0; i < 3; i++) {
    	        imageViews.get(i).setImage(images.get(i));
    	    }
    	
    	
    	
    }
    
    
    
    
    private void setHome() {
		
    		Image image = new Image(getClass().getResourceAsStream("/path/" + game.getPlayers().get(0).getColour().toString().toLowerCase() + "ice.png"));	
			
    		PlayerBase0.setImage(image);
			PlayerBase1.setImage(image);
			PlayerBase2.setImage(image);
			PlayerBase3.setImage(image);
			
			image= new Image(getClass().getResourceAsStream("/path/"+((game.getPlayers().get(1).getColour()).toString().toLowerCase())+"ice.png"));
			CPU1Base0.setImage(image);
			CPU1Base1.setImage(image);
			CPU1Base2.setImage(image);
			CPU1Base3.setImage(image);
			image= new Image(getClass().getResourceAsStream("/path/"+((game.getPlayers().get(2).getColour()).toString().toLowerCase())+"ice.png"));
			CPU2Base0.setImage(image);
			CPU2Base1.setImage(image);
			CPU2Base2.setImage(image);
			CPU2Base3.setImage(image);
			image= new Image(getClass().getResourceAsStream("/path/"+((game.getPlayers().get(3).getColour()).toString().toLowerCase())+"ice.png"));
			CPU3Base0.setImage(image);
			CPU3Base1.setImage(image);
			CPU3Base2.setImage(image);
			CPU3Base3.setImage(image);
	}
    
    
    
    
    
    private void setSafeZones() {
        // Player Safe Zone
        Image image = new Image(getClass().getResourceAsStream("/path/" + game.getPlayers().get(0).getColour().toString().toLowerCase() + "ice.png"));
        PlayerSZ0.setImage(image);
        PlayerSZ1.setImage(image);
        PlayerSZ2.setImage(image);
        PlayerSZ3.setImage(image);

        // CPU1 Safe Zone
        image = new Image(getClass().getResourceAsStream("/path/" + game.getPlayers().get(1).getColour().toString().toLowerCase() + "ice.png"));
        CPU1SZ0.setImage(image);
        CPU1SZ1.setImage(image);
        CPU1SZ2.setImage(image);
        CPU1SZ3.setImage(image);

        // CPU2 Safe Zone
        image = new Image(getClass().getResourceAsStream("/path/" + game.getPlayers().get(2).getColour().toString().toLowerCase() + "ice.png"));
        CPU2SZ0.setImage(image);
        CPU2SZ1.setImage(image);
        CPU2SZ2.setImage(image);
        CPU2SZ3.setImage(image);

        // CPU3 Safe Zone
        image = new Image(getClass().getResourceAsStream("/path/" + game.getPlayers().get(3).getColour().toString().toLowerCase() + "ice.png"));
        CPU3SZ0.setImage(image);
        CPU3SZ1.setImage(image);
        CPU3SZ2.setImage(image);
        CPU3SZ3.setImage(image);
    }

    private void updateBoard() {
        int gridSize = 26;
        int index = 0;

        // Traverse the board edge
        for (int row = gridSize - 1; row >= 0; row--) updateOverlayImage(row, 0, index++);              // Left column
        for (int col = 1; col < gridSize; col++) updateOverlayImage(0, col, index++);                   // Top row
        for (int row = 1; row < gridSize; row++) updateOverlayImage(row, gridSize - 1, index++);        // Right column
        for (int col = gridSize - 2; col > 0; col--) updateOverlayImage(gridSize - 1, col, index++);    // Bottom row

        // Update safe zone marbles and player marbles
        updateSafeZoneMarbles();
        updatePlayerMarbles();
    }
    private void updateSafeZoneMarbles() {
        ImageView[][] safeZoneImages = {
                { PlayerSZM0, PlayerSZM1, PlayerSZM2, PlayerSZM3 },
                { CPU1SZM0, CPU1SZM1, CPU1SZM2, CPU1SZM3 },
                { CPU2SZM0, CPU2SZM1, CPU2SZM2, CPU2SZM3 },
                { CPU3SZM0, CPU3SZM1, CPU3SZM2, CPU3SZM3 }
            };

            for (int zone = 0; zone < 4; zone++) {
                for (int i = 0; i < 4; i++) {
                    Marble marble = board.getSafeZones().get(zone).getCells().get(i).getMarble();
                    safeZoneImages[zone][i].setImage(marble == null ? null : getMarbleImage(marble));
                }
            }
        }
    private void updatePlayerMarbles() {
        ImageView[][] marbleViews = {
            { PlayerMarble0, PlayerMarble1, PlayerMarble2, PlayerMarble3 },
            { CPU1Marble0, CPU1Marble1, CPU1Marble2, CPU1Marble3 },
            { CPU2Marble0, CPU2Marble1, CPU2Marble2, CPU2Marble3 },
            { CPU3Marble0, CPU3Marble1, CPU3Marble2, CPU3Marble3 }
        };

        for (int playerIndex = 0; playerIndex < game.getPlayers().size(); playerIndex++) {
            List<Marble> marbles = game.getPlayers().get(playerIndex).getMarbles();
            for (int i = 0; i < 4; i++) {
                Marble marble = marbles.get(i);
                marbleViews[playerIndex][i].setImage(marble == null ? null : getMarbleImage(marble));
            }
        }
    }


    	
    
    private void updateOverlayImage(int row, int col, int index) {
        for (Node node : trackGridPane.getChildren()) {
            if (node instanceof ImageView && "overlay".equals(node.getId())) {
                Integer r = GridPane.getRowIndex(node);
                Integer c = GridPane.getColumnIndex(node);

                if (r != null && c != null && r == row && c == col) {
                    ImageView overlay = (ImageView) node;

                    Marble marble = board.getTrack().get(index).getMarble();
                    if (marble != null) {
                        overlay.setImage(getMarbleImage(marble));
                    } else {
                        overlay.setImage(null); // Clear if empty
                    }
                    return;
                }
            }
        }
    }

    private Image getMarbleImage(Marble marble) {
        switch (marble.getColour()) {
            case RED:
                return new Image(getClass().getResourceAsStream("/marbles/redpengu.png"));
            case BLUE:
                return new Image(getClass().getResourceAsStream("/marbles/bluepengu.png"));
            case GREEN:
                return new Image(getClass().getResourceAsStream("/marbles/greenpengu.png"));
            case YELLOW:
                return new Image(getClass().getResourceAsStream("/marbles/yellowpengu.png"));
            default:
                return null;
        }
    }
    
    
    
    
    private int selectedRow = -1;
    private int selectedCol = -1;
    private void handleMarbleSelection(int row, int col, ImageView cell) {
        Image marbleImage = cell.getImage();

        if (selectedRow == -1 && marbleImage != null) {
            selectedRow = row;
            selectedCol = col;
            highlightCell(cell, true);
            System.out.println("Selected marble at " + row + "," + col);

        } else if (selectedRow == row && selectedCol == col) {
            highlightCell(cell, false);
            selectedRow = -1;
            selectedCol = -1;
            System.out.println("Deselected marble");

        } else if (selectedRow != -1) {
            ImageView prevCell = findCellByCoords(selectedRow, selectedCol);
            if (prevCell != null) {
                //moveMarble(prevCell, cell);
            }
            highlightCell(prevCell, false);
            selectedRow = -1;
            selectedCol = -1;
        }
        updateBoard();
    }

    private void highlightCell(ImageView cell, boolean highlight) {
        if (cell == null) return;
        if (highlight) {
            cell.setStyle("-fx-effect: dropshadow(three-pass-box, yellow, 10, 0, 0, 0);");
        } else {
            cell.setStyle("");
        }
    }

    private ImageView findCellByCoords(int row, int col) {
        for (Node node : trackGridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof ImageView) {
                    ImageView iv = (ImageView) node;
                    if ("overlay".equals(iv.getId()))
                    return iv;
                }
            }
        }
        return null;
    }

    private void setTrack() {
        int gridSize = 26;
        Image edgeImage = new Image(getClass().getResourceAsStream("/path/iceblock.png"));

        for (int i = 0; i <= gridSize; i++) {
            // Top edge block
            ImageView topEdge = new ImageView(edgeImage);
            topEdge.setFitWidth(27);
            topEdge.setFitHeight(27);
            GridPane.setRowIndex(topEdge, 0);
            GridPane.setColumnIndex(topEdge, i);
            trackGridPane.getChildren().add(topEdge);

            // Top overlay (empty ImageView)
            ImageView topOverlay = new ImageView();
            topOverlay.setId("overlay");
            topOverlay.setFitWidth(23);
            topOverlay.setFitHeight(35);
            GridPane.setRowIndex(topOverlay, 0);
            GridPane.setColumnIndex(topOverlay, i);

            // Center vertically: shift up by 4px (half of height difference)
            topOverlay.setTranslateY(-4);

            // Center horizontally: shift right by (27-23)/2 = 2px
            topOverlay.setTranslateX(2);

            trackGridPane.getChildren().add(topOverlay);

            // Bottom edge block
            ImageView bottomEdge = new ImageView(edgeImage);
            bottomEdge.setFitWidth(27);
            bottomEdge.setFitHeight(27);
            GridPane.setRowIndex(bottomEdge, gridSize);
            GridPane.setColumnIndex(bottomEdge, i);
            trackGridPane.getChildren().add(bottomEdge);

            // Bottom overlay
            ImageView bottomOverlay = new ImageView();
            bottomOverlay.setId("overlay");
            bottomOverlay.setFitWidth(23);
            bottomOverlay.setFitHeight(35);
            GridPane.setRowIndex(bottomOverlay, gridSize);
            GridPane.setColumnIndex(bottomOverlay, i);

            bottomOverlay.setTranslateY(-4);
            bottomOverlay.setTranslateX(2);

            trackGridPane.getChildren().add(bottomOverlay);

            if (i != 0 && i != gridSize) {
                // Left edge block
                ImageView leftEdge = new ImageView(edgeImage);
                leftEdge.setFitWidth(27);
                leftEdge.setFitHeight(27);
                GridPane.setRowIndex(leftEdge, i);
                GridPane.setColumnIndex(leftEdge, 0);
                trackGridPane.getChildren().add(leftEdge);

                // Left overlay
                ImageView leftOverlay = new ImageView();
                leftOverlay.setId("overlay");
                leftOverlay.setFitWidth(23);
                leftOverlay.setFitHeight(35);
                GridPane.setRowIndex(leftOverlay, i);
                GridPane.setColumnIndex(leftOverlay, 0);

                leftOverlay.setTranslateY(-4);
                leftOverlay.setTranslateX(2);

                trackGridPane.getChildren().add(leftOverlay);

                // Right edge block
                ImageView rightEdge = new ImageView(edgeImage);
                rightEdge.setFitWidth(27);
                rightEdge.setFitHeight(27);
                GridPane.setRowIndex(rightEdge, i);
                GridPane.setColumnIndex(rightEdge, gridSize);
                trackGridPane.getChildren().add(rightEdge);

                // Right overlay
                ImageView rightOverlay = new ImageView();
                rightOverlay.setId("overlay");
                rightOverlay.setFitWidth(23);
                rightOverlay.setFitHeight(35);
                GridPane.setRowIndex(rightOverlay, i);
                GridPane.setColumnIndex(rightOverlay, gridSize );

                rightOverlay.setTranslateY(-4);
                rightOverlay.setTranslateX(2);

                trackGridPane.getChildren().add(rightOverlay);
            }
        }
    }



    public Image helper(Card card) {
    	Image image;
    	if (card instanceof Wild) {
    		if (card instanceof Burner) {
    			image = new Image(getClass().getResourceAsStream("/PlayingCards/card-burner.png"));
    		} else {
    			image = new Image(getClass().getResourceAsStream("/PlayingCards/card-saver.png"));
    		}
    	} else {
    		image = new Image(getClass().getResourceAsStream("/PlayingCards/card-" +
    				(((Standard) card).getSuit().toString().toLowerCase()) + "s-" +
    				((Standard) card).getRank() + ".png"));
    	}
    	return image;
    }

    public static void displayAlert(String title, String message) {
        Stage alertStage = new Stage();
        alertStage.setTitle(title);
        alertStage.setMinWidth(300);

        Label label = new Label(message);
        label.setWrapText(true);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(event -> alertStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 150);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }
    
    private void handleKeyPress(KeyEvent event) throws CannotFieldException, IllegalDestroyException {
        if (!isHumanPlayerTurn()) return;
        if (event.getCode() == KeyCode.ENTER) {
            handleFieldMarble(new ActionEvent());
        }
    }

    private boolean isHumanPlayerTurn() {
        return game.getActivePlayerColour() == game.getPlayers().get(0).getColour();
    }
    private void testClick() {
        System.out.println("CLICKED");
    }
}