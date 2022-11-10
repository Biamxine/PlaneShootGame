package com.gec.main;

import javax.swing.JFrame;

import com.gec.config.GAME;
import com.gec.config.RES;
import com.gec.object.ShootGame;

/*
 * дһ����Ϸ����ڳ���
 * 1�����������
 * 2����ʼ��Ϸ
 */
public class PlaneAPP {
	public static void main(String[] args) {
		JFrame F =new JFrame("�Ǽ��Ժ�");
		//���������
		ShootGame game = new ShootGame();
		//���뵽����
		F.add( game );
		//��Ϸͼ��
		F.setIconImage( RES.icon );
		//����Ŀ��
		F.setSize(GAME.WIDTH, GAME.HEIGHT);
		F.setAlwaysOnTop( true );
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		F.setLocationRelativeTo( null );
		F.setVisible( true );
		
		game.start();
	}
}
