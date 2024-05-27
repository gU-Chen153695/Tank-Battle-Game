package cc.tank.sprite;

import cc.tank.Director;
import cc.tank.scene.GameScene;
import cc.tank.util.Direction;
import cc.tank.util.Group;
import cc.tank.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class Bullet extends Role{
    public Bullet(double x, double y,  Group group, Direction dir, GameScene gameScene) {
        super(x, y, 0, 0, group, dir, gameScene);
        speed=4;
        if(dir.equals(Direction.up)||dir.equals(Direction.down)){//判断子弹飞的方向
            width=10;
            height=22;

        }else if(dir.equals(Direction.left)||dir.equals(Direction.right)){
            width=22;
            height=10;
        }

        //根据是哪个组的设置图片
        if(group.equals(Group.green)){
            switch (dir){
                case up:
                    image=new Image("image/bullet-green-up.png");
                    break;
                case down:
                    image=new Image("image/bullet-green-down.png");
                    break;
                case left:
                    image=new Image("image/bullet-green-left.png");
                    break;
                case right:
                    image=new Image("image/bullet-green-right.png");
                    break;
            }
        }else{
            switch (dir){
                case up:
                    image=new Image("image/bullet-red-up.png");
                    break;
                case down:
                    image=new Image("image/bullet-red-down.png");
                    break;
                case left:
                    image=new Image("image/bullet-red-left.png");
                    break;
                case right:
                    image=new Image("image/bullet-red-right.png");
                    break;
            }
        }

    }

    @Override
    public void move() {//重写子弹的移动
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

//        if(x<0)x=0;
//        if(y<0)y=0;//控制坐标最小值
//
//        if(x> Director.WIDTH-width)x=Director.WIDTH-width;
//        if(y>Director.HEIGHT-height)y=Director.HEIGHT-height;
        if(x<0||y<0||x>Director.WIDTH||y>Director.HEIGHT){
            gameScene.bullets.remove(this);
        }
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        if(!alive){
            gameScene.bullets.remove(this);
            gameScene.explodes.add(new Explode(x,y,gameScene));//在子弹消失后添加爆炸
            SoundEffect.play("/sound/explosion.wav");
            return;
        }
        super.paint(graphicsContext);
        move();
    }

    //判断是否与坦克发生碰撞
    public boolean impactTank(Tank tank) {
        if(tank!=null && !tank.group.equals(this.group)&& getContour().intersects(tank.getContour())){
            tank.setAlive(false);
            alive=false;
            return true;
        }
        return false;
    }

    //对坦克数列的碰撞检测
    public void impactTank(List<Tank> tanks){
        for (Tank t:tanks) {
            impactTank(t);
        }
    }
    //对板条箱的碰撞检测
    public boolean impactCrate(Crate crate){
        if(crate!=null&&getContour().intersects(crate.getContour()))
        {
            alive=false;
            gameScene.crates.remove(crate);
            return true;
        }
        return false;
    }

    public void impactCrates(List<Crate> crates){
        for (int i = 0; i < crates.size(); i++) {
            Crate crate=crates.get(i);
            impactCrate(crate);
        }
    }
    //对石头的碰撞检测
    public boolean impactRock(Rock rock){
        if(rock!=null&&getContour().intersects(rock.getContour()))
        {
            alive=false;
            return true;
        }
        return false;
    }
    public void impactRocks(List<Rock> rocks){
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock=rocks.get(i);
            impactRock(rock);
        }
    }
}
