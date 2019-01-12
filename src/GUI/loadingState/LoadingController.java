package GUI.loadingState;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class LoadingController {

    private LoadingScene loadingScene;
    @FXML
    private ProgressBar progressBar;

    @FXML private ImageView imageView;

    public void setLoadingScene(LoadingScene loadingScene) {
        this.loadingScene = loadingScene;
    }

    public void setValueOfProgressBar(double d){
        progressBar.setProgress(d);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
