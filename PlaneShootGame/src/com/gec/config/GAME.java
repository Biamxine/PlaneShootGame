package com.gec.config;

public interface GAME {
	int WIDTH = RES.background.getWidth();  //���ڵĿ�
	int HEIGHT = RES.background.getHeight();  //���ڵĸ�
	
	int START = 0;  //������
	int RUNNING = 1;  //������
	int PAUSE = 2;  //����ͣ
	int GAME_OVER = 3;  //��Ϸ����
	
	int SCORE = 5;  //�з���ֵ
}
