package cc.tank.util;

import javafx.scene.media.AudioClip;

public class SoundEffect {
    //调用音效
    public static void play(String src){
        //使用SoundEffect类的类路径来获取资源，通过getResource()方法返回一个 URL对象，然后将其转换为字符串来传参,不然会报错
        AudioClip audioClip= new AudioClip(SoundEffect.class.getResource(src).toString());
        audioClip.setVolume(0.05);
        audioClip.play();
    }

}
