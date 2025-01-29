package lk.ijse.gdse.main.cicvetcare;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/Loading.fxml"));
        stage.setScene(new Scene(load));
        stage.show();

        Task<Scene> task = new Task<Scene>() {
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/LogIn.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };
        task.setOnSucceeded(e -> {
            Scene value = task.getValue();
            stage.setTitle("CIC Vet Care");
            stage.setResizable(false);
            stage.setScene(value);
        });
        new Thread(task).start();
    }
}
