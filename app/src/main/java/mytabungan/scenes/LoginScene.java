package mytabungan.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mytabungan.dao.UserDAO;
import mytabungan.utils.ValidationUtil;

public class LoginScene {
    private Stage stage;
    private UserDAO userDAO = new UserDAO();

    public LoginScene(Stage stage) {
        this.stage = stage;
    }

    public static Scene getLogin(Stage stage) {

        // Left Side
        VBox leftSide = AuthLayout.buildPanel();

        // Right Side
        VBox rightSide = buildFormPanel(stage);

        // === Root Layout ===
        HBox root = new HBox(leftSide, rightSide);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        HBox.setHgrow(rightSide, Priority.ALWAYS);

        Scene scene = new Scene(root, 960, 600);
        scene.getStylesheets().add(
            LoginScene.class.getResource("/style.css").toExternalForm()
        );
        return scene;
    }
    
    private static VBox buildFormPanel(Stage stage) {
        VBox rightSide = new VBox(0);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPrefWidth(500);
        rightSide.setMinWidth(500);
        rightSide.setMaxWidth(Double.MAX_VALUE);
        rightSide.setStyle("-fx-background-color: #F6F7ED; -fx-padding: 60 70 60 70;");

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #001F3F;");

        Rectangle Underline = new Rectangle(46, 3);
        Underline.setFill(Color.web("#001F3F"));
        Underline.setArcWidth(2);
        Underline.setArcHeight(2);

        VBox titleBlock = new VBox(8, title, Underline);
        titleBlock.setAlignment(Pos.CENTER);
        titleBlock.setMaxWidth(260);

        Region gap1 = new Region();
        gap1.setPrefHeight(58);

        // Username or Email
        Label userLabel = new Label("Username or Email");
        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; fx-text-fill: #4A4A4A;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Input Username or Email");
        usernameField.setMaxWidth(Double.MAX_VALUE);
        usernameField.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #D0D0C8;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10 12 10 12;
            -fx-font-size: 13px;
            -fx-text-fill: #222;
            -fx-prompt-text-fill: #AAAAAA;
        """);

        VBox userBox = new VBox(6, userLabel, usernameField);
        Region gap2 = new Region();
        gap2.setPrefHeight(24);

        // Password
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4A4A4A;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Input Password");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #D0D0C8;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10 12 10 12;
            -fx-font-size: 13px;
            -fx-text-fill: #222;
            -fx-prompt-text-fill: #AAAAAA;
        """);

        VBox passBox = new VBox(6, passLabel, passwordField);
        Region gap3 = new Region();
        gap3.setPrefHeight(24);

        // Button
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add("buttonLogin");

        // Message
        Label message = new Label();
        message.setWrapText(true);
        message.setMaxWidth(300);

        Region gap4 = new Region();
        gap4.setPrefHeight(6);

        // Register link
        Label desc = new Label("Don't have an account? ");
        desc.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        Label registerNow = new Label("Register now");
        registerNow.setStyle("""
            -fx-font-size: 13px;
            -fx-text-fill: #1E488F;
            -fx-underline: true;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """);

        // HBox registerBox = new HBox(3);
        // registerBox.setAlignment(Pos.CENTER);
        // registerBox.getChildren().addAll(desc, registerNow);
        HBox registerBox = new HBox(3, desc, registerNow);
        registerBox.setAlignment(Pos.CENTER);

        VBox formArea = new VBox();
        formArea.setMaxWidth(320);
        formArea.setAlignment(Pos.CENTER);
        Region gapR = new Region(); gapR.setPrefHeight(42);
        formArea.getChildren().addAll(
            titleBlock, gap1,
            userBox, gap2,
            passBox, gap3,
            message, gap4,
            loginBtn, gapR,
            registerBox
        );

        rightSide.getChildren().add(formArea);

        registerNow.setOnMouseClicked(e -> {
            // navigate to RegisterScene
            stage.setScene(RegisterScene.getRegist(stage));
        });

        // === Action Login ===
        loginBtn.setOnAction(e -> {
            String user = usernameField.getText();
            String password = passwordField.getText();

            if (ValidationUtil.isEmpty(user) || ValidationUtil.isEmpty(password)) {
                message.setText("Inputan tidak boleh kosong!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                message.setText("Password minimal 8 karakter!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }
            message.setText("Processing login...");
            message.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
            boolean success = new UserDAO().login(user, password);
            
            if (success) {
                message.setText("Login berhasil!");
                message.setStyle("-fx-text-fill: #74C365; -fx-font-weight: bold; -fx-font-size: 12px;");
                // DashboardScene dashboard = new DashboardScene(stage);
                // stage.setScene(dashboard.getScene());
            } else {
                message.setText("Username / email atau password salah!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
            }
        });

        return rightSide;
    }
}
