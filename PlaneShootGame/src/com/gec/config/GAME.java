package com.gec.config;

public interface GAME {
	int WIDTH = RES.background.getWidth();  //窗口的宽
	int HEIGHT = RES.background.getHeight();  //窗口的高
	
	int START = 0;  //待运行
	int RUNNING = 1;  //运行中
	int PAUSE = 2;  //已暂停
	int GAME_OVER = 3;  //游戏结束
	
	int SCORE = 5;  //敌方分值
}
