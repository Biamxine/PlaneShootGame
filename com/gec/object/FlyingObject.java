package com.gec.object;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	//����ͼƬ����
	protected BufferedImage image;
	//�������
	protected int width;
	//�������
	protected int height;
	//������x����
	protected int x;
	//������y����
	protected int y;
	
	//һ���ߵĲ���
	public abstract void step();
	//�Ƿ��г����߽�
	public abstract boolean outOfBound();
	
	
	
	//�Ƿ񱻶Է����ӵ�������
	public boolean shootBy(Bullet bullet){
		int bX = bullet.x;
		int bY = bullet.y;
		int xStart = this.x;
		int xEnd = this.x + width;
		int yStart = this.y;
		int yEnd = this.y + height;
		return ( xStart <= bX && bX <= xEnd )
			   &&
			   ( yStart <= bY && bY <= yEnd );
	}
}
