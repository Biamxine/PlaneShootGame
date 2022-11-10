package com.gec.object;

import com.gec.config.RES;

public class Bullet extends FlyingObject {
	public static int defaultSpeed = 8;
	public static int speed = defaultSpeed;
	private int planeSpeed = 5;
	public Bullet(int n,int x,int y) {
		if( n==1 ) {
			image = RES.bullet;
		}
		if( n==2 ) {
			image = RES.bullet1;
		}
		if( n==3 ) {
			image = RES.bullet2;
		}
		this.width = RES.bullet.getWidth();
		this.height = RES.bullet.getHeight();
		
		this.x = x;
		this.y = y;
	}
	
	
	@Override
	public void step() {
		//更新y坐标
		y = y - speed;
	}
	
	public void planeStep() {
		y = y + planeSpeed;
	}
	
	@Override
	//超出边界判断
	public boolean outOfBound() {
		return (this.y < 0) || (this.y > RES.background.getHeight());
	}
}
