package mytabungan.scenes;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import mytabungan.dao.UserDAO;
import mytabungan.models.User;
import mytabungan.utils.ValidationUtil;

public class RegisterScene {
    public static Scene getRegist(Stage stage) {

        // Left Side
        VBox leftSide = buildFormPanel(stage);

        // Right Side
        VBox rightSide = AuthLayout.buildPanel();

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
        VBox leftSide = new VBox(0);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setPrefWidth(500);
        leftSide.setMinWidth(500);
        leftSide.setMaxWidth(Double.MAX_VALUE);
        leftSide.setStyle("-fx-background-color: #F6F7ED; -fx-padding: 60 70 60 70;");

        Label title = new Label("Sign Up");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #001F3F;");

        Rectangle Underline = new Rectangle(62, 3);
        Underline.setFill(Color.web("#001F3F"));
        Underline.setArcWidth(2);
        Underline.setArcHeight(2);

        VBox titleBlock = new VBox(8, title, Underline);
        titleBlock.setAlignment(Pos.CENTER);
        titleBlock.setMaxWidth(260);

        Region gap1 = new Region();
        gap1.setPrefHeight(58);

        // Email
        Label EmailLabel = new Label("Email");
        EmailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; fx-text-fill: #4A4A4A;");

        TextField EmailField = new TextField();
        EmailField.setPromptText("Input your email");
        EmailField.setMaxWidth(Double.MAX_VALUE);
        EmailField.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #D0D0C8;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10 12 10 12;
            -fx-font-size: 13px;
            -fx-text-fill: #222;
            -fx-prompt-text-fill: #AAAAAA;
        """);

        VBox EmailBox = new VBox(6, EmailLabel, EmailField);
        Region gap2 = new Region();
        gap2.setPrefHeight(24);

        // Username
        Label UsernameLabel = new Label("Username");
        UsernameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; fx-text-fill: #4A4A4A;");

        TextField UsernameField = new TextField();
        UsernameField.setPromptText("Input username");
        UsernameField.setMaxWidth(Double.MAX_VALUE);
        UsernameField.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #D0D0C8;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10 12 10 12;
            -fx-font-size: 13px;
            -fx-text-fill: #222;
            -fx-prompt-text-fill: #AAAAAA;
        """);

        VBox UsernameBox = new VBox(6, UsernameLabel, UsernameField);
        Region gap3 = new Region();
        gap3.setPrefHeight(24);

        // Password
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4A4A4A;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Input your password (min. 8 characters)");
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
        Region gap4 = new Region();
        gap4.setPrefHeight(24);

        // Button
        Button registerBtn = new Button("Register");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.getStyleClass().add("buttonRegist");

        // Message
        Label message = new Label();
        message.setWrapText(true);
        message.setMaxWidth(300);

        Region gap5 = new Region();
        gap5.setPrefHeight(6);

        // Register link
        Label desc = new Label("Already have an account? ");
        desc.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        Label loginHere = new Label("Login here");
        loginHere.setStyle("""
            -fx-font-size: 13px;
            -fx-text-fill: #1E488F;
            -fx-underline: true;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """);

        // HBox loginBox = new HBox(3);
        // loginBox.setAlignment(Pos.CENTER);
        // loginBox.getChildren().addAll(desc, loginHere);
        HBox loginBox = new HBox(3, desc, loginHere);
        loginBox.setAlignment(Pos.CENTER);

        VBox formArea = new VBox();
        formArea.setMaxWidth(320);
        formArea.setAlignment(Pos.CENTER);
        Region gapR = new Region(); gapR.setPrefHeight(42);
        formArea.getChildren().addAll(
            titleBlock, gap1,
            EmailBox, gap2,
            UsernameBox, gap3,
            passBox, gap4,
            message, gap5,
            registerBtn, gapR,
            loginBox
        );

        leftSide.getChildren().add(formArea);

        loginHere.setOnMouseClicked(e -> {
            // navigate to LoginScene
            stage.setScene(LoginScene.getLogin(stage));
        });

        // === Action Sign Up ===
        registerBtn.setOnAction(e -> {
            String username = UsernameField.getText().trim();
            String email = EmailField.getText().trim();
            String password = passwordField.getText();

            UserDAO userDAO = new UserDAO();

            if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(email) || ValidationUtil.isEmpty(password)) {
                message.setText("Inputan tidak boleh kosong!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                message.setText("Format email tidak valid!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (!ValidationUtil.isValidUsername(username)) {
                message.setText("Username harus min. 3 karakter dan max. 20 karakter!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                message.setText("Password minimal 8 karakter!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (userDAO.isUsernameExists(username)) {
                message.setText("Username sudah digunakan.");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }

            if (userDAO.isEmailExists(email)) {
                message.setText("Email sudah terdaftar!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
                return;
            }
            message.setText("Processing registration...");
            message.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");

            User user = new User(email, username, password);
            boolean success = new UserDAO().register(user);
            if (success) {
                message.setText("Registrasi berhasil! Silakan login.");
                message.setStyle("-fx-text-fill: #74C365; -fx-font-weight: bold; -fx-font-size: 12px;");
                
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(event -> {
                    stage.setScene(LoginScene.getLogin(stage));
                });

                delay.play();
            } else {
                message.setText("Terjadi kesalahan. Registrasi Anda gagal!");
                message.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
            }
        });

        return leftSide;
    }
}