package cc.tank.sprite;

import cc.tank.Director;
import cc.tank.scene.GameScene;
import cc.tank.util.Direction;
import cc.tank.util.Group;
import cc.tank.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import cc.tank.sprite.Sprite;
import java.util.List;
import java.util.Random;

public class Tank extends Role{

    Direction pdir;     //炮筒方向
    boolean keyup,keydown,keyleft,keyright;     //键盘是否按下，初始为false
    double oldx,oldy;//记录原坐标用于坦克碰撞检测
    public static Random random=new Random();//生成随机数
    public Tank(double x, double y, Group group, Direction dir,Direction pdir, GameScene gameScene ) {
        super(x, y, 60, 60, group, dir, gameScene);
        //定义速度
        speed=3;

        //设置炮筒方向
        this.pdir = pdir;

        //设置图片
        if (group.equals(Group.green)){
            imageMap.put("up",new Image("image/tank-green-up.png"));
            imageMap.put("down",new Image("image/tank-green-down.png"));
            imageMap.put("right",new Image("image/tank-green-right.png"));
            imageMap.put("left",new Image("image/tank-green-left.png"));
        }else {
            imageMap.put("up",new Image("image/tank-red-up.png"));
            imageMap.put("down",new Image("image/tank-red-down.png"));
            imageMap.put("right",new Image("image/tank-red-right.png"));
            imageMap.put("left",new Image("image/tank-red-left.png"));
        }
    }

    //判断键盘按下还是松开
    public void pressed(KeyCode keyCode){
        //按下对应方向键则修改对应方向的布尔值
        switch (keyCode){
            case W:
                keyup=true;
                break;
            case S:
                keydown=true;
                break;
            case A:
                keyleft=true;
                break;
            case D:
                keyright=true;
                break;
        }
        redirect();
    }

    public void released(KeyCode keyCode){
        //松开对应方向键则改回对应方向的值
        switch (keyCode){
            case F:
                openFire();//松开则开火
                break;
            case W:
                keyup=false;
                break;
            case S:
                keydown=false;
                break;
            case A:
                keyleft=false;
                break;
            case D:
                keyright=false;
                break;
        }
        redirect();
    }

    //重定向
    public void redirect() {
        if (keyup && !keydown && !keyleft && !keyright) dir = Direction.up;
        else if (!keyup && keydown && !keyleft && !keyright) dir = Direction.down;
        else if (!keyup && !keydown && keyleft && !keyright) dir = Direction.left;
        else if (!keyup && !keydown && !keyleft && keyright) dir = Direction.right;
        else if (!keyup && !keydown && !keyleft && !keyright) dir=Direction.stop;
    }

    @Override
    public void move() {
        oldx=x;
        oldy=y;
        //移动
        switch (dir){
            case up:
                y-=speed;
                break;
            case down:
                y+=speed;
                break;
            case left:
                x-=speed;
                break;
            case right:
                x+=speed;
                break;
        }

        //调整炮筒方向
        if (dir!=Direction.stop){
            pdir=dir;
        }

        //限制坦克移动范围
        if(x<0)x=0;
        if(y<0)y=0;//控制坐标最小值

        if(x> Director.WIDTH-width)x=Director.WIDTH-width;
        if(y>Director.HEIGHT-height)y=Director.HEIGHT-height;

        if(group.equals(Group.red)){
            int i= random.nextInt(60);
            switch (i){
                case 15:
                    Direction d[]=Direction.values();
                    dir=d[random.nextInt(d.length)];
                case 30:
                    openFire();
                    break;
            }
        }

    }

    //重写Sprite类的绘制方法
    @Override
    public void paint(GraphicsContext graphicsContext){
        if(group.equals(Group.red)&&!alive){
            gameScene.tanks.remove(this);
            return;
        }
        switch (pdir){
            case up:
                image=imageMap.get("up");
                break;
            case down:
                image=imageMap.get("down");
                break;
            case left:
                image=imageMap.get("left");
                break;
            case right:
                image=imageMap.get("right");
                break;
        }
        super.paint(graphicsContext);
        //绘制完毕后让其移动
        move();
    }

    //坦克开火方法
    public void openFire(){
        double bulletx=x;
        double bullety=y;
        switch (pdir){
            case  up:
                bulletx=x+25;
                bullety=y-22;
                break;
            case down:
                bulletx=x+25;
                bullety=y+height;
                break;
            case left:
                bulletx=x-22;
                bullety=y+25;
                break;
            case right:
                bulletx=x+width;
                bullety=y+25;
                break;
        }
        SoundEffect.play("/sound/attack.mp3");
        gameScene.bullets.add(new Bullet(bulletx,bullety,group,pdir,gameScene));

    }
    //坦克和坦克的碰撞检测
    public boolean impact(Sprite sprite) {
        if(!sprite.equals(this)&&sprite!=null &&  getContour().intersects(sprite.getContour())){
           x=oldx;
           y=oldy;
            return true;
        }
        return false;
    }

    public void impact(List<? extends Sprite> sprites){
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite=sprites.get(i);
            impact(sprite);
        }
    }
}
