package com.gec.config;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class RES {
	public static BufferedImage background;
	public static BufferedImage backgroundA;
	public static BufferedImage ikun_background;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage life;
	public static BufferedImage ikun;
	public static BufferedImage bullet;
	public static BufferedImage bullet1;
	public static BufferedImage bullet2;
	public static BufferedImage gameover;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage heroA;
	public static BufferedImage heroB;
	public static BufferedImage heroC;
	public static BufferedImage heroD;
	public static BufferedImage heroE;
	public static BufferedImage icon;
	public static BufferedImage pause;
	public static BufferedImage start;
	public static BufferedImage blast;
	public static BufferedImage hero_blast;
	
	public static Clip music;
	public static Clip red_music;
	public static Clip gameovermusic;
	public static Clip ikun_music;
	public static Clip music_blast;
	public static Clip music_blast1;
	public static Clip music_blast2;
	
	static {
		ClassLoader loader = RES.class.getClassLoader();
		try {
			//用一个图片读取器去加载图片数据(请输入正确的文件路径)
			backgroundA = ImageIO.read(loader.getResource("background.png"));
			ikun_background = ImageIO.read(loader.getResource("ikun_background.png"));
			airplane = ImageIO.read(loader.getResource("airplane.png"));
			bee = ImageIO.read(loader.getResource("bee.png"));
			life = ImageIO.read(loader.getResource("life.png"));
			ikun = ImageIO.read(loader.getResource("ikun.png"));
			bullet = ImageIO.read(loader.getResource("bullet.png"));
			bullet1 = ImageIO.read(loader.getResource("bullet1.png"));
			bullet2 = ImageIO.read(loader.getResource("bullet2.png"));
			gameover = ImageIO.read(loader.getResource("gameover.png"));
			heroA = ImageIO.read(loader.getResource("heroA.png"));
			heroB = ImageIO.read(loader.getResource("heroB.png"));
			heroC = ImageIO.read(loader.getResource("heroC.png"));
			heroD = ImageIO.read(loader.getResource("heroD.png"));
			heroE = ImageIO.read(loader.getResource("heroE.png"));
			icon = ImageIO.read(loader.getResource("icon.jpg"));
			pause = ImageIO.read(loader.getResource("pause.png"));
			start = ImageIO.read(loader.getResource("start.png"));
			blast = ImageIO.read(loader.getResource("blast.png"));
			hero_blast = ImageIO.read(loader.getResource("hero_blast.png"));
			
			hero0 = heroA;
			hero1 = heroB;
			background = backgroundA;
			
			//加载背景音乐，载入音频剪辑对象中
			File file = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\game_music.wav");
			AudioInputStream is = AudioSystem.getAudioInputStream(file);
			music = AudioSystem.getClip();
			music.open( is );
			
			File red_file = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\red_music.wav");
			AudioInputStream red_is = AudioSystem.getAudioInputStream(red_file);
			red_music = AudioSystem.getClip();
			red_music.open( red_is );
			
			File over_file = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\gameovermusic.wav");
			AudioInputStream over_is = AudioSystem.getAudioInputStream(over_file);
			gameovermusic = AudioSystem.getClip();
			gameovermusic.open( over_is );
			
			File imusic_file = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\ikun_music.wav");
			AudioInputStream iis = AudioSystem.getAudioInputStream(imusic_file);
			ikun_music = AudioSystem.getClip();
			ikun_music.open( iis );
			
			File file_blast = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\music_blast.wav");
			AudioInputStream is_blast = AudioSystem.getAudioInputStream(file_blast);
			music_blast = AudioSystem.getClip();
			music_blast.open( is_blast );
			
			File file_blast1 = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\music_blast1.wav");
			AudioInputStream is_blast1 = AudioSystem.getAudioInputStream(file_blast1);
			music_blast1 = AudioSystem.getClip();
			music_blast1.open( is_blast1 );
			
			File file_blast2 = new File("C:\\Users\\把这行中文改成你登录电脑的用户名\\eclipse-workspace\\PlaneShootGame\\resources\\music_blast2.wav");
			AudioInputStream is_blast2 = AudioSystem.getAudioInputStream(file_blast2);
			music_blast2 = AudioSystem.getClip();
			music_blast2.open( is_blast2 );
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
