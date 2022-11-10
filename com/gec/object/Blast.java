package com.gec.object;

import com.gec.config.RES;

public class Blast extends FlyingObject {
	public int Count = 0;
	public Blast( int x, int y ) {
		image = RES.blast;
		this.x = x;
		this.y = y;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean outOfBound() {
		// TODO Auto-generated method stub
		return false;
	}

}
