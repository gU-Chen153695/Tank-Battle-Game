package cc.tank.sprite;

import cc.tank.scene.GameScene;
import cc.tank.util.Direction;
import cc.tank.util.Group;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

//角色类(抽象)
public abstract class Role extends Sprite{


    //数据成员
    boolean alive =true;
    Group group;
    Direction dir;
    double speed;       //速度
    Map<String,Image> imageMap=new HashMap<>();     //用于存放坦克上下左右四个状态的图片

    public Role( double x, double y, double width, double height, Group group, Direction dir, GameScene gameScene) {
        super(null, x, y, width, height, gameScene);
        this.group = group;
        this.dir = dir;
    }

    //抽象类方法
    public abstract void move();                                //移动
    //返回对象存活状态
    public boolean isAlive() {
        return alive;
    }

    //设置存活状态
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
