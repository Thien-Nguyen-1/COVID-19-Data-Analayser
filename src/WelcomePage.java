import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WelcomePage extends StackPane {
    public WelcomePage() {
        createWelcomePage();
    }
    
    public void createWelcomePage() {
        // Welcome Messages
        VBox welcomeMessages = new VBox(10); // Spacing between nodes
        welcomeMessages.setAlignment(Pos.TOP_CENTER); // Aligning to the top center

        Label welcomeLabel = new Label("Welcome to the Covid Statistics Viewer!");
        welcomeLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 30));

        welcomeMessages.getChildren().addAll(welcomeLabel);

        // Adding padding to center the VBox
        welcomeMessages.setPadding(new Insets(100, 0, 0, 0)); // Adjust top padding as needed
        
        
        // Middle Text
        Label middleText = new Label("The application consists of three main slides, accessible through the navigation buttons provided.");
        middleText.setFont(Font.font("Calibri", 20));
        middleText.setWrapText(true); // Enable text wrapping

        // Additional Text Below Middle Text
        Label additionalText = new Label("The first slide serves as a welcome screen and provides instructions on the basic use of the application. Here, you'll find guidance on navigating between slides and utilising the features available.");
        additionalText.setFont(Font.font("Calibri", 20));
        additionalText.setWrapText(true); // Enable text wrapping

        // Additional Paragraph Below Additional Text
        Label additionalParagraph1 = new Label("The second slide displays a map outlining visual information about COVID death rates in different London boroughs. Use this slide to gain insights into how the pandemic has affected various areas within London. Tap on specific boroughs to view detailed information about death rates.");
        additionalParagraph1.setFont(Font.font("Calibri", 20));
        additionalParagraph1.setWrapText(true); // Enable text wrapping

        // Additional Paragraph Below Additional Paragraph 1
        Label additionalParagraph2 = new Label("The third slide highlights essential information including retail traffic, workplace traffic, total number of deaths, and average total cases. This slide provides a comprehensive overview of COVID-19 statistics and trends.");
        additionalParagraph2.setFont(Font.font("Calibri", 20));
        additionalParagraph2.setWrapText(true); // Enable text wrapping

        // Additional Paragraph Below Additional Paragraph 2
        Label additionalParagraph3 = new Label("To get started, please select a valid start and end date.");
        additionalParagraph3.setFont(Font.font("Calibri", 20));
        additionalParagraph3.setWrapText(true); // Enable text wrapping

        // Adding icons
        Image leftIcon = new Image("coronavirus-icon.png"); // Assuming the icon image file is named left_icon.png
        ImageView leftIconView = new ImageView(leftIcon);
        leftIconView.setFitHeight(100); // Adjust the height of the icon as needed
        leftIconView.setFitWidth(100); // Adjust the width of the icon as needed
        StackPane.setAlignment(leftIconView, Pos.TOP_LEFT); // Aligning the icon to the top left corner

        Image rightIcon = new Image("coronavirus-icon.png"); // Assuming the icon image file is named right_icon.png
        ImageView rightIconView = new ImageView(rightIcon);
        rightIconView.setFitHeight(100); // Adjust the height of the icon as needed
        rightIconView.setFitWidth(100); // Adjust the width of the icon as needed
        StackPane.setAlignment(rightIconView, Pos.TOP_RIGHT); // Aligning the icon to the top right corner

        // Bottom Image
        Image bottomImage = new Image("download.png"); // Assuming the bottom image file is named bottom_image.png
        ImageView bottomImageView = new ImageView(bottomImage);
        bottomImageView.setFitHeight(120); // Adjust the height of the image as needed
        bottomImageView.setFitWidth(120); // Adjust the width of the image as needed
        StackPane.setAlignment(bottomImageView, Pos.BOTTOM_CENTER); // Aligning the image to the bottom center

        // VBox to center additionalText and additionalParagraph vertically
        VBox additionalTextContainer = new VBox(20); // Spacing between nodes
        additionalTextContainer.getChildren().addAll(additionalText, additionalParagraph1, additionalParagraph2, additionalParagraph3);
        additionalTextContainer.setAlignment(Pos.CENTER); // Centering the additionalText vertically

        // VBox to hold all the text
        VBox textContainer = new VBox(20); // Spacing between nodes
        textContainer.getChildren().addAll(middleText, additionalTextContainer);
        textContainer.setAlignment(Pos.CENTER); // Aligning the textContainer
        textContainer.setPadding(new Insets(0, 70, 0, 70)); // Adjust left and right padding as needed


        // StackPane to overlay the textContainer on top of the welcome messages
        //StackPane stackPane = new StackPane();
        this.getChildren().addAll(welcomeMessages, textContainer, leftIconView, rightIconView, bottomImageView);
        this.setAlignment(Pos.CENTER); // Aligning the stack pane content

        //return stackPane;
    }
}

