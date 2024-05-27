package cc.tank.scene;

import cc.tank.Director;
import cc.tank.sprite.*;
import cc.tank.util.Direction;
import cc.tank.util.Group;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene {
    //创建一个画布填满整个界面
    private Canvas canvas = new Canvas(Director.WIDTH,Director.HEIGHT);
    private GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
    //创建一个键盘监听对象的成员变量
    private KeyProcess keyProcess = new KeyProcess();
    //创建一个刷新场景类的成员变量
    private Refresh refresh = new Refresh();
    //布尔类型，用于判断是否启动刷新
    private boolean running = false;

    private Background background=new Background();
    //玩家坦克
    private Tank self=null;

    public List<Bullet> bullets=new ArrayList<>();
    //子弹容器
    public List<Tank> tanks=new ArrayList<>();
    //放敌对坦克的容器

    //爆炸图片容器
    public List<Explode> explodes=new ArrayList<>();
    //板条箱容器
    public List<Crate> crates=new ArrayList<>();
    //石头和树的容器
    public List<Rock> rocks=new ArrayList<>();
    public List<Tree> trees=new ArrayList<>();
    //用于刷新场景 (内部类)
    private class Refresh extends AnimationTimer{
        @Override
        public void handle(long now) {
            if(running) {
                paint();
            }
        }
    }

    //键盘监听对象（内部类）
    private class KeyProcess implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {

            KeyCode keyCode=event.getCode();

            if(event.getEventType()==KeyEvent.KEY_RELEASED){
                //实现按下空格键暂停游戏
                if(keyCode.equals(KeyCode.SPACE)){
                    pauseOrContinue();
                }
                if(self!=null)
                //调用玩家坦克对象的released方法实现对应功能
                self.released(keyCode);
            }else if(event.getEventType()==KeyEvent.KEY_PRESSED){
                if(self!=null)
                //调用玩家坦克对象的pressed方法实现对应功能
                self.pressed(keyCode);
            }
        }
    }

    //实现游戏的暂停与继续功能
    public void pauseOrContinue(){
        if(running){
            running=false;
        }else {
            running=true;
        }
    }

    //绘制场景的成员函数
    private void paint() {
        background.paint(graphicsContext);
        self.paint(graphicsContext);
        self.impact(tanks);
        self.impact(crates);
        self.impact(rocks);
        for(int i=0;i<bullets.size();i++){//子弹的绘制
            Bullet b=bullets.get(i);
            b.paint(graphicsContext);

            b.impactTank(tanks);//子弹碰撞敌军的检测
            b.impactCrates(crates);//子弹碰撞板条箱检测
            b.impactRocks(rocks);//子弹碰撞石头检测
            b.impactTank(self);
        }

        for(int i=0;i<tanks.size();i++){//坦克的绘制
            Tank tank=tanks.get(i);
            tank.paint(graphicsContext);
            tank.impact(crates);//碰撞板条箱的检测
            tank.impact(self);//敌军碰撞玩家的检测
            tank.impact(rocks);//碰撞石头的检测
            tank.impact(tanks);
        }

        for(int i=0;i<explodes.size();i++){//爆炸的绘制
            Explode e=explodes.get(i);
            e.paint(graphicsContext);

        }
        for (int i = 0; i < crates.size(); i++) {//板条箱的绘制
            Crate crate=crates.get(i);
            crate.paint(graphicsContext);
        }
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock=rocks.get(i);
            rock.paint(graphicsContext);
        }
        for (int i = 0; i < trees.size(); i++) {
            Tree tree=trees.get(i);
            tree.paint(graphicsContext);
        }
        
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font(20));
        graphicsContext.fillText("敌军的数量："+tanks.size(),800,60);
        graphicsContext.fillText("子弹的数量："+bullets.size(),800,90);
        if(!self.isAlive())
        {
            Director.getInstance().gameOver(false);
        } else if (tanks.isEmpty()) {
            Director.getInstance().gameOver(true);
        }
    }

    //初始化游戏场景
    public void init(Stage stage){
        //将画布绘制到场景上
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);

        //将running置为true,启动循环绘制
        running=true;
        self=new Tank(400,500, Group.green, Direction.stop,Direction.up,this);
        stage.getScene().setOnKeyReleased(keyProcess);
        stage.getScene().setOnKeyPressed(keyProcess);
        initSprite();
        refresh.start();
    }

    //初始化敌方坦克
    //初始化地形
    private void initSprite(){
        for (int i=0;i<6;i++){
            Tank tank=new Tank(200+i*80,100,Group.red,Direction.stop,Direction.down,this);
            tanks.add(tank);
        }
        for (int i = 0; i < 20; i++) {//板条箱
            Crate crate1=new Crate(100+i*31,200);
            Crate crate2=new Crate(100+i*31,232);
            crates.add(crate1);
            crates.add(crate2);
        }
        for (int i = 0; i < 5; i++) {//石头
            Rock rock = new Rock(550+i*71,300);
            rocks.add(rock);
        }
        for (int i = 0; i < 3; i++) {//树
            Tree tree=new Tree(650+i*86,400);
            trees.add(tree);
        }
    }

    //游戏结束时清除场景
    public void clear(Stage stage){

        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED,keyProcess);
        //将键盘事件监听对象移除
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED,keyProcess);
        //停止刷新
        refresh.stop();
        tanks.clear();
        bullets.clear();
        crates.clear();
        explodes.clear();
        rocks.clear();

    }


}
