package cc.tank;

import cc.tank.scene.GameOver;
import cc.tank.scene.GameScene;
import cc.tank.scene.Index;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.omg.CORBA.portable.ValueOutputStream;

public class Director {
    //界面的宽度与高度
    public static final double WIDTH=960 , HEIGHT=640;

    private static Director instance = new Director();
    private Stage stage;

    //GameScene类产生的对象作为游戏场景
    private GameScene gameScene=new GameScene();

    private Director() {}

    public static Director getInstance(){
        return instance;
    }

    //初始化窗口
    public void init(Stage stage){
        AnchorPane root=new AnchorPane();
        Scene scene=new Scene(root,WIDTH,HEIGHT);

        stage.setTitle("坦克大战");
        stage.getIcons().add(new Image("image/logo.png"));
        //不可改变窗口大小
        stage.setResizable(false);

        stage.setScene(scene);
        this.stage=stage;
        toIndex();
        stage.show();
    }

    //回到主页
    public void toIndex(){
        Index.load(stage);
    }

    //游戏开始函数
    public void gameStart()
    {
        //初始化游戏场景
        gameScene.init(stage);
    }

    //游戏结束函数
    public void gameOver(boolean success){
        gameScene.clear(stage);
        GameOver.load(stage,success);

    }
}
