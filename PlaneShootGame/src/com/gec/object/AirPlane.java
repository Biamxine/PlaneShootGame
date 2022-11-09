package com.gec.object;

import com.gec.config.GAME;
import com.gec.config.RES;

public class AirPlane extends FlyingObject implements Enemy {
	private int speed = 2;
	private int ySpeed = 2;
	private int xSpeed = 1;
	public int type;  //0:��ǰ�壬1������գ�2�����ҹ�
	
	public AirPlane(int type) {
		this.type = type;
		this.image = RES.airplane;
		this.width = RES.airplane.getWidth();
		this.height = RES.airplane.getHeight();
		if( type==1 ) {
			this.x = RES.background.getWidth() - width;
		}
		else if( type==2 ) {
			this.x = 0;
		}
		else {
			int W = GAME.WIDTH - RES.airplane.getWidth();
			int eX = (int)(Math.random()*W);
			this.x = eX;
		}
		int eH = RES.airplane.getHeight();
		this.y = -eH;
		
	}
	
	@Override
	public void step() {
		y = y + speed;
	}
	
	public void lStep() {
		y = y + ySpeed;
		x = x - xSpeed;
	}
	
	public void rStep() {
		y = y + ySpeed;
		x = x + xSpeed;
	}
	
	@Override
	public boolean outOfBound() {
		return this.y >= GAME.HEIGHT;
	}

	@Override
	public int getScore() {
		return GAME.SCORE;
	}
	
	public Bullet shoot() {
		int aW = this.width;  //��ȡ�л��Ŀ��
		int aH = this.height;
		int aX = this.x;
		int aY = this.y;
		int bW = RES.bullet1.getWidth();
		int bH = RES.bullet1.getHeight();
		
		int bX = aX + (aW/2) - (bW/2);
		int bY = aY + aH;
		Bullet B = new Bullet(2,bX,bY);
		return B;
	}
	

}
