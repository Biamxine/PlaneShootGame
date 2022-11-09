package com.gec.main;

import javax.swing.JFrame;

import com.gec.config.GAME;
import com.gec.config.RES;
import com.gec.object.ShootGame;

/*
 * 写一个游戏的入口程序
 * 1、创建主面板
 * 2、开始游戏
 */
public class PlaneAPP {
	public static void main(String[] args) {
		JFrame F =new JFrame("星际迷航");
		//创建主面板
		ShootGame game = new ShootGame();
		//载入到窗体
		F.add( game );
		//游戏图标
		F.setIconImage( RES.icon );
		//窗体的宽高
		F.setSize(GAME.WIDTH, GAME.HEIGHT);
		F.setAlwaysOnTop( true );
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		F.setLocationRelativeTo( null );
		F.setVisible( true );
		
		game.start();
	}
}
