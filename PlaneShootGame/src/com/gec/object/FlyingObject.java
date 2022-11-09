package com.gec.object;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	//保存图片对象
	protected BufferedImage image;
	//飞行物宽
	protected int width;
	//飞行物高
	protected int height;
	//飞行物x坐标
	protected int x;
	//飞行物y坐标
	protected int y;
	
	//一次走的步数
	public abstract void step();
	//是否有超出边界
	public abstract boolean outOfBound();
	
	
	
	//是否被对方的子弹所击中
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
