package GUI.lodingState;

import GUI.run.RunController;
import core.Machine;
import core.MainScene;
import core.State;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import utils.SAVManager;

import java.io.IOException;

public class LoadingScene {

    private AnchorPane pane;
    private LoadingController loadingController;
    private AnchorPane origin;
    private MainScene turing;
    private SAVManager savManager;

    public LoadingScene(MainScene turing, AnchorPane origin) {
        this.origin = origin;
        this.turing = turing;
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/loadingState/LoadingWindow.fxml"));
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadingController = loader.getController();
        loadingController.setLoadingScene(this);

        savManager = new SAVManager();
        turing.setSavManager(savManager);
        turing.setProjects(FXCollections.observableArrayList(savManager.getProjects()));
        turing.getMainWindowController().getTableProjects().setItems(FXCollections.observableArrayList(savManager.getProjects()));
    }
}
