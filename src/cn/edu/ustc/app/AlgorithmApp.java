package cn.edu.ustc.app;

import cn.edu.ustc.gui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description the entry of the application
 */

public class AlgorithmApp extends Application{
    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();

        Scene scene = new Scene(mainView, 900, 700);
        primaryStage.setTitle("算法可视化工具");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
