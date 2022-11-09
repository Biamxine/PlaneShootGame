package com.gec.object;

import java.awt.image.BufferedImage;

import com.gec.config.RES;

public class Hero extends FlyingObject {
	//�������
	private int life;
	protected int doubleFire;
	private int ikunFire;
	public BufferedImage[] images;
	private int index = 0;
	private int count = 0;
	public int heroBType = 1;
	
	public Hero() {
		//ָ��ͼƬ
		super.image = RES.hero0;
		//���ÿ��
		super.width = image.getWidth();
		super.height = image.getHeight();
		//����x��y
		super.x = (RES.background.getWidth()/2) - (width/2);
		super.y = RES.background.getHeight()/2;
		//��ʼ��2����
		life = 5;
		//Ĭ�ϵ�������
		doubleFire = 0;
		//��������ͼƬ������
		images = new BufferedImage[] {
				 RES.hero0,RES.hero1
		         };
		//index=0
	}
	
	
	@Override
	public void step() {
		//ȫ�ּ�������10ms ˢ�½�������
		//  100ms ��һ��ͼ
		count ++;  //������ ++ [0 --> 9]
		if( count ==9 ) {
			count = 0;  //����ʱ����һ��ͼƬ
			//����0ʱ --> 1������ --> 0
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
		//System.out.printf("�Ƶ���%s��%s\n",x ,y );
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	public Bullet[] shoot() {
		int hW = this.width;  //��ȡӢ��ս���Ŀ��
		int hX = this.x;
		int hY = this.y;
		
		if( ikunFire==0&&doubleFire==1 ) {  //��������
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
		else{  //��������
			int bW = RES.bullet.getWidth();
			int bH = RES.bullet.getHeight();
			int bX = hX + (hW/2) - (bW/2) + 2;
			int bY = hY - bH;
			Bullet B = new Bullet(heroBType,bX,bY);
			return new Bullet[] { B };
		}
	}
	
	//�Ե������������ʱ������һ������
	public void addLife() {
		life ++;
	}
	
	//��ײ���л��򱻵з����У�����һ������
	public void substractLife() {
		life --;
	}
	
	//�Ե������������ʱ����˫�ػ���
	public void setDouble() {
		doubleFire = 1;
	}
	
	//����֮��ȡ��˫�ػ���
	public void cancelDoubleFire() {
		doubleFire = 0;
	}
	
	public void setIkunFire() {
		ikunFire = 1;
	}
	
	public void cancelIkunFire() {
		ikunFire = 0;
	}
	
	//ս����ײ����ʱ
	public boolean hit(FlyingObject E) {
		//�����ҷ��ĸ���
		int myXS = this.x;
		int myXE = this.x + this.width;
		int myYS = this.y;
		int myYE = this.y + this.height;
		//����з��ĸ���
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
