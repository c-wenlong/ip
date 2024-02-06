package duke.kbot;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Main class where we enter the program.
 * 
 * @author: CHEN WENLONG
 * @version: CS2103T AY23/24 Semester 2
 */
public class Main extends Application {
    /** Image used to represent user. */
    private Image user = new Image(this.getClass().getResourceAsStream("/images/aaron.jpg"));

    /** Image used to represent chatbot. */
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/halim.png"));

    /** Text input by user. */
    private TextField userInput; // Declare userInput as an instance variable

    /** Contains the dialogue between the user and chatbot. */
    private VBox dialogContainer; // Declare dialogContainer as an instance variable

    /**
     * Starts the JavaFx and launches the GUI.
     * 
     * @param stage Main container of all the nodes of graphical components.
     * @throws Exception Exceptions thrown when setting up the JavaFx program.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // set up main diaplay
        ScrollPane scrollPane = new ScrollPane();
        dialogContainer = new VBox();

        // Anchoring the main layout
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        Button sendButton = new Button("Send");
        Button closeButton = new Button("Close");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton, closeButton);

        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();

        // Formatting the window
        stage.setTitle("Kaipybara Bot");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(280.0);
        sendButton.setPrefWidth(55.0);
        closeButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 60.0);
        AnchorPane.setBottomAnchor(closeButton, 1.0);
        AnchorPane.setRightAnchor(closeButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Add functionality to the buttons
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        closeButton.setOnAction((event) -> {
            Platform.exit(); // Close the JavaFX application
        });

        // Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        if (userInput.getText().equals("")) {
            Platform.exit(); // alternative method to exit besides the Close button
        }
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        userText.setFont(Font.font("Verdana", FontWeight.NORMAL, 8)); // Set font type and size
        dukeText.setFont(Font.font("Verdana", FontWeight.NORMAL, 8)); // Set font type and size
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, new ImageView(user)),
                DialogBox.getDukeDialog(dukeText, new ImageView(duke)));
        userInput.clear();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input) {
        return KBot.simulate(input);
    }

}
