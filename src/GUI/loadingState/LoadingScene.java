package GUI.loadingState;

import GUI.addState.AddStateController;
import core.MainScene;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.SAVManager;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class LoadingScene extends SwingWorker {

    private AnchorPane pane;
    private LoadingController loadingController;
    private AnchorPane origin;
    private MainScene turing;
    private SAVManager savManager;
    private Stage stage;

    public LoadingScene(MainScene turing, AnchorPane origin) {
        this.origin = origin;
        this.turing = turing;
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/loadingState/LoadingWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();

        loadingController = loader.getController();
        loadingController.setLoadingScene(this);

        stage.setScene(new Scene(pane, 600, 350));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        loadingController = loader.getController();
        loadingController.setLoadingScene(this);
    }

    public AnchorPane getPane() {
        return pane;
    }

    @Override
    protected Object doInBackground() throws Exception {
        publish(0.01);
        Thread.sleep(500);
        publish(0.1);
        savManager = new SAVManager();
        publish(0.7);
        turing.setSavManager(savManager);
        publish(0.8);
        turing.setProjects(FXCollections.observableArrayList(savManager.getProjects()));
        publish(0.9);
        turing.getMainWindowController().getTableProjects().setItems(FXCollections.observableArrayList(savManager.getProjects()));
        publish(1.0);
        Thread.sleep(500);
        return null;
    }

    @Override
    protected void process(List chunks) {
        double i = (double) chunks.get(chunks.size() - 1);
        loadingController.setValueOfProgressBar(i);
    }

    @Override
    protected void done(){
        turing.getPrimaryStage().getScene().setRoot(origin);
        closeWindow();
    }

    private void closeWindow(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.close();
                turing.getPrimaryStage().show();
            }
        });
    }
}
