package cc.tank.sprite;

import cc.tank.scene.GameScene;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//抽象类，作为所有可视化对象的父类
public abstract class Sprite {

    //数据成员
    Image image;
    double x,y,width,height;
    GameScene gameScene;

    //构造方法
    public Sprite(Image image, double x, double y, double width, double height, GameScene gameScene) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameScene = gameScene;
    }
    public Sprite(Image image, double x, double y, double width, double height) { //用于无场景需求的对象
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    //绘制方法
    public void paint(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image,x,y,width,height);
    }
    //判断是否产生碰撞
    public Rectangle2D getContour(){
        return new Rectangle2D(x,y,width,height);
    }
    //销毁方法
    public void destroy(){

    }
}
