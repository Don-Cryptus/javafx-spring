package com.javafxspring;

import com.javafxspring.plugin.LogFile;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

import static javax.swing.filechooser.FileSystemView.getFileSystemView;


@SpringBootApplication()
public class BaseApplication extends Application implements CommandLineRunner, Log {
    public static File outputFile;
    private final Plugin[] plugins = new Plugin[]{
            new LogFile()};
    private TextArea textArea;
    private Label statusLabel;

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            outputFile = File.createTempFile("debug", ".log", getFileSystemView().getDefaultDirectory());
            PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
            System.setOut(output);
            System.setErr(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    public void log(String s) {
        textArea.appendText(s);
        textArea.appendText(System.lineSeparator());
        statusLabel.setText(s);
    }

    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();

        VBox topElements = new VBox();

        MenuBar menuBar = new MenuBar();
        topElements.getChildren().add(menuBar);

        ToolBar toolbar = new ToolBar();
        topElements.getChildren().add(toolbar);

        textArea = new TextArea();
        textArea.setWrapText(true);

        statusLabel = new Label();
        statusLabel.setPadding(new Insets(5.0f, 5.0f, 5.0f, 5.0f));
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        borderPane.setTop(topElements);
        borderPane.setBottom(statusLabel);
        borderPane.setCenter(textArea);

        Scene scene = new Scene(borderPane, 800, 600);

        stage.setTitle("Hello World");
        stage.setScene(scene);

        for (Plugin plugin : plugins) {
            try {
                plugin.setup(stage, textArea, toolbar, this, menuBar);
            } catch (Exception e) {
                System.err.println("Unable to start plugin");
                System.err.println(plugin.getClass().getName());
                e.printStackTrace();
                log("Unable to start plugin");
                log(plugin.getClass().getName());
                log(e.getMessage());
            }
        }

        statusLabel.setText("Ready.");

        stage.show();
        //put window to front to avoid it to be hide behind other.
        stage.setAlwaysOnTop(true);
        stage.requestFocus();
        stage.toFront();
        stage.setAlwaysOnTop(false);
    }
}
