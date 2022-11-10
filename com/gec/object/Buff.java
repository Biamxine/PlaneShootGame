package com.gec.object;

import com.gec.config.GAME;
import com.gec.config.RES;

public class Buff extends FlyingObject {
	private int speed = 2;
	public int type;  //0£º¼ÓÉúÃü  1£º±äÀ¤¸ç
	
	public Buff(int type) {
		if( type == 0 ) {
			this.type = 0;
			this.image = RES.bee;
			this.width = RES.bee.getWidth();
			this.height = RES.bee.getHeight();
			
			int W = GAME.WIDTH - RES.bee.getWidth();
			int bX = (int)(Math.random()*W);
			this.x = bX;
			int bH = RES.bee.getHeight();
			this.y = -bH;
		}
		else if( type == 1 ) {
			this.type = 1;
			this.image = RES.ikun;
			this.width = RES.ikun.getWidth();
			this.height = RES.ikun.getHeight();
			
			int W = GAME.WIDTH - RES.ikun.getWidth();
			int bX = (int)(Math.random()*W);
			this.x = bX;
			int bH = RES.ikun.getHeight();
			this.y = -bH;
		}else {
			this.type = 2;
			this.image = RES.life;
			this.width = RES.life.getWidth();
			this.height = RES.life.getHeight();
			
			int W = GAME.WIDTH - RES.life.getWidth();
			int bX = (int)(Math.random()*W);
			this.x = bX;
			int bH = RES.life.getHeight();
			this.y = -bH;
		}
	}
	
	
	@Override
	public void step() {
		y = y + speed;
		
	}

	@Override
	public boolean outOfBound() {
		return this.y >= GAME.HEIGHT;
	}

}
