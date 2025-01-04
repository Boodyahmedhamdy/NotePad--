package notepad;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AboutScreenBase extends BorderPane {

    protected final VBox vBox;
    protected final ImageView imageView;
    protected final Text descriptionText;
    protected final Button goBackBtn;

    public AboutScreenBase() {

        vBox = new VBox();
        imageView = new ImageView();
        descriptionText = new Text();
        goBackBtn = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(418.0);
        setPrefWidth(378.0);

        BorderPane.setAlignment(vBox, javafx.geometry.Pos.CENTER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPrefHeight(367.0);
        vBox.setPrefWidth(268.0);

        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("yellowWhite.jpg").toExternalForm()));

        descriptionText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        descriptionText.setStrokeWidth(0.0);
        descriptionText.setText("this application was made with Love and JavaFx by Abdelrahman Ahmed Hamdy. as a part of learning journey on iti");
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        descriptionText.setWrappingWidth(247.96728515625);
        VBox.setMargin(descriptionText, new Insets(10.0));

        goBackBtn.setMnemonicParsing(false);
        goBackBtn.setText("Go back");
        setCenter(vBox);
        setPadding(new Insets(10.0));

        vBox.getChildren().add(imageView);
        vBox.getChildren().add(descriptionText);
        vBox.getChildren().add(goBackBtn);

    }
}
