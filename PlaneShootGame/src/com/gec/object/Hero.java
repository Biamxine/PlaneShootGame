package com.gec.object;

import java.awt.image.BufferedImage;

import com.gec.config.RES;

public class Hero extends FlyingObject {
	//相关属性
	private int life;
	protected int doubleFire;
	private int ikunFire;
	public BufferedImage[] images;
	private int index = 0;
	private int count = 0;
	public int heroBType = 1;
	
	public Hero() {
		//指定图片
		super.image = RES.hero0;
		//设置宽高
		super.width = image.getWidth();
		super.height = image.getHeight();
		//设置x，y
		super.x = (RES.background.getWidth()/2) - (width/2);
		super.y = RES.background.getHeight()/2;
		//初始化2条命
		life = 5;
		//默认单倍火力
		doubleFire = 0;
		//存入两张图片到数组
		images = new BufferedImage[] {
				 RES.hero0,RES.hero1
		         };
		//index=0
	}
	
	
	@Override
	public void step() {
		//全局计算器是10ms 刷新界面数据
		//  100ms 换一张图
		count ++;  //索引号 ++ [0 --> 9]
		if( count ==9 ) {
			count = 0;  //归零时更换一张图片
			//等于0时 --> 1，否则 --> 0
			index = (index==0)? 1 : 0;
			image = images[ index ];
		}
	}
	
	@Override
	public boolean outOfBound() {
		return false;
	}
	
	//getLife();
	public int getLife() {
		return life;
	}
	
	//moveTo();
	public void moveTO(int x,int y) {
		//System.out.printf("移到：%s，%s\n",x ,y );
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	public Bullet[] shoot() {
		int hW = this.width;  //获取英雄战机的宽度
		int hX = this.x;
		int hY = this.y;
		
		if( ikunFire==0&&doubleFire==1 ) {  //单倍火力
			int bW = RES.bullet.getWidth();
			int bH = RES.bullet.getHeight();
			int baX = hX + (hW/2) - bW - 3;
			int bbX = hX + (hW/2) + 3;
			int bY = hY - bH;
			Bullet Ba = new Bullet(heroBType,baX,bY);
			Bullet Bb = new Bullet(heroBType,bbX,bY);
			return new Bullet[] { Ba,Bb };
		}
		
		else if( ikunFire==1 ) {
			int bW = RES.bullet2.getWidth();
			int bH = RES.bullet2.getHeight();
			int baX = hX + (hW/2) - bW - 3;
			int bbX = hX + (hW/2) + 3;
			int bcX = hX + (hW/2) - (2*bW) - 6;
			int bdX = hX + (hW/2) + bW + 6;
			int bY = hY - bH;
			Bullet Ba = new Bullet(heroBType,baX,bY);
			Bullet Bb = new Bullet(heroBType,bbX,bY);
			Bullet Bc = new Bullet(heroBType,bcX,bY);
			Bullet Bd = new Bullet(heroBType,bdX,bY);
			return new Bullet[] { Ba,Bb,Bc,Bd };
		}
		else{  //单倍火力
			int bW = RES.bullet.getWidth();
			int bH = RES.bullet.getHeight();
			int bX = hX + (hW/2) - (bW/2) + 2;
			int bY = hY - bH;
			Bullet B = new Bullet(heroBType,bX,bY);
			return new Bullet[] { B };
		}
	}
	
	//吃到奖励物【生命】时，奖励一条生命
	public void addLife() {
		life ++;
	}
	
	//碰撞到敌机或被敌方击中，减少一条生命
	public void substractLife() {
		life --;
	}
	
	//吃到奖励物【火力】时设置双重火力
	public void setDouble() {
		doubleFire = 1;
	}
	
	//挂了之后取消双重火力
	public void cancelDoubleFire() {
		doubleFire = 0;
	}
	
	public void setIkunFire() {
		ikunFire = 1;
	}
	
	public void cancelIkunFire() {
		ikunFire = 0;
	}
	
	//战机碰撞敌人时
	public boolean hit(FlyingObject E) {
		//计算我方四个点
		int myXS = this.x;
		int myXE = this.x + this.width;
		int myYS = this.y;
		int myYE = this.y + this.height;
		//计算敌方四个点
		int emyXS = E.x;
		int emyXE = E.x + E.width;
		int emyYS = E.y;
		int emyYE = E.y + E.height;
		
		return (((myXS>=emyXS && myXS<=emyXE)
			   ||
			   (myXE>=emyXS && myXE<=emyXE))
			   &&
			   ((myYS>=emyYS && myYS<=emyYE)
			   ||
			   (myYE>=emyYS && myYE<=emyYE)))
			   ||
			   (((myXS <= emyXS && emyXS <= myXE)
			   ||
			   (myXS <= emyXE && emyXE <= myXE))
			   &&
			   ((myYS <= emyYS && emyYS <= myYE)
			   ||
			   (myYS <= emyYE && emyYE <= myYE)));
	}
}
