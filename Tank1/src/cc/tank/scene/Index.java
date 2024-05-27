package cc.tank.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Index {

    /*
    1.Parent root = FXMLLoader.load(Index.class.getResource(""));
这行代码使用FXMLLoader类加载FXML文件并将其实例化为一个Parent对象。FXML是一种用于定义JavaFX用户界面的XML格式文件。
Index.class.getResource("") 返回的是与Index类所在的包相对应的资源路径。在这里，它使用空字符串作为参数，
表示从当前包中加载FXML文件。
    2.stage.getScene().setRoot(root);
这行代码将刚刚加载的FXML文件的根节点设置为舞台（Stage）的场景（Scene）的根节点。
通过调用stage.getScene()获取当前舞台的场景，然后使用setRoot(root)将加载的FXML文件的根节点设置为场景的根节点。
    3.catch (IOException e) { e.printStackTrace(); }
这部分代码用于捕获可能出现的IOException异常。
如果加载FXML文件时发生了IO错误，将打印出堆栈跟踪信息以帮助调试。
    */

    public static void load(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Index.class.getResource("/fxml/index.fxml"));
            stage.getScene().setRoot(root);
        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
