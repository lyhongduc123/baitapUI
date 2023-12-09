package client.baitapui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HelloController implements Initializable {
    @FXML
    public Button backButton;

    @FXML
    public Button callButton;

    @FXML
    public Button videoButton;

    @FXML
    public Button imageButton;

    @FXML
    public Button recordButton;

    @FXML
    public Button sendButton;

    @FXML
    public HBox messageBox;

    @FXML
    public VBox textBox;

    @FXML
    public VBox chatBox;

    @FXML
    public HBox topBox;

    @FXML
    public HBox bottomBox;

    @FXML
    public Circle statusIcon;

    @FXML
    public ScrollPane scrollChat;

    @FXML
    public ImageView avatar;

    private static TextField currentLine;
    private TextFlow currentMessage;

    private int count = 0;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        AnchorPane.setTopAnchor(scrollChat, topBox.getPrefHeight() + 10);
        AnchorPane.setBottomAnchor(scrollChat, bottomBox.getPrefHeight() + 10);

        Circle clip = new Circle(avatar.getFitWidth() / 2,
                                avatar.getFitHeight() / 2,
                            avatar.getFitWidth() / 2);
        avatar.setClip(clip);

        scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollChat.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        bottomBox.setMinHeight(40);

        currentLine = (TextField) textBox.getChildren().get(0);

        textBox.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sendButton.fire();
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                if (currentLine.getText().length() == 0 && textBox.getChildren().size() > 1) {
                    bottomBox.setPrefHeight(bottomBox.getPrefHeight() - 30);
                    textBox.getChildren().remove(textBox.getChildren().size() - 1);
                    currentLine = (TextField) textBox.getChildren().get(textBox.getChildren().size() - 1);
                    currentLine.requestFocus();
                    currentLine.end();
                } else {
                    if (currentLine.getText().length() > 0)
                    currentLine.deleteText(currentLine.getCaretPosition() - 1, currentLine.getCaretPosition());
                }
            } else {
                if (currentLine.getText().length() * 7 > currentLine.getWidth() - 20) {
                    bottomBox.setPrefHeight(bottomBox.getPrefHeight() + 15);
                    AnchorPane.setBottomAnchor(scrollChat, bottomBox.getPrefHeight() + 10);
                    textBox.getChildren().add(new TextField());
                    textBox.setSpacing(-20);
                    currentLine = (TextField) textBox.getChildren().get(textBox.getChildren().size() - 1);
                    currentLine.requestFocus();
                }
            }
            e.consume();
        });

        sendButton.setOnAction(e -> {
            if (currentLine.getText().length() > 0) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < textBox.getChildren().size(); i++) {
                    builder.append(((TextField) textBox.getChildren().get(i)).getText());
                }
                while (textBox.getChildren().size() > 1) {
                    bottomBox.setPrefHeight(bottomBox.getPrefHeight() - 30);
                    textBox.getChildren().remove(1);
                }
                currentLine = (TextField) textBox.getChildren().get(0);

                Text message = new Text(builder.toString());
                message.setId("message");

                currentMessage = new TextFlow(message);
                HBox box = new HBox();
                if (count % 2 == 0) {
                    currentMessage.setId("rightMessageContainer");
                    box.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
                } else {
                    currentMessage.setId("leftMessageContainer");
                    box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                }
                box.setPadding(new javafx.geometry.Insets(20));
                box.getChildren().add(currentMessage);

                chatBox.getChildren().add(box);
                chatBox.setSpacing(-35);
                currentLine.setText("");
                bottomBox.setPrefHeight(bottomBox.getMinHeight());
                AnchorPane.setBottomAnchor(scrollChat, bottomBox.getPrefHeight() + 10);

                count++;
            }
        });

        scrollChat.needsLayoutProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                scrollChat.layout();
                scrollChat.setVvalue(1.0d);
            }
        });
    }


}