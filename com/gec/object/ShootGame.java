package com.gec.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.gec.config.GAME;
import com.gec.config.RES;

public class ShootGame extends JPanel {
	//������Ӧ������
	int state = GAME.START;  //Ĭ��δ��ʼ
	//�ҷ�Ӣ�۵÷�
	int score = 0;
	//�ҷ�Ӣ�ۣ��ɻ���
	Hero hero = new Hero();
	//ʱ�����0
	static int time0 = 0;
	//ʱ�����1
	static double time1 = 0;
	//��ʱ��
	Timer timer = null;
	//���ʱ�䣺10ms
	int interval = 10;  //ÿ10ms �ػ滭��
	
	int redState = 0;
	int redMusicState = 1;
	int gameOverState = 0;
	int ikunState = 0;
	int defHeroShootCount = 40;
	int defFlyingsEntCount = 100;
	int heroShootCount;
	int flyingsEnterCount;
	int gameOverMusicCount = 0;
	
	int blastNum1 = 1;
	int blastNum2 = 1;
	
	private Bullet[] bullets = new Bullet[] {};
	private Bullet[] planeBullets = new Bullet[] {};
	private FlyingObject[] flyings = new FlyingObject[] {};
	private Blast[] blasts = new Blast[] {};
	
	
	//Graphics���ʶ���
	public void paint(Graphics g) {
		//���Ʊ���ͼ
		g.drawImage( RES.background,0,0,null);
		//����Ӣ��
		paintHero( g );
		//�����ӵ�
		paintBullets( g );
		//�������з�����
		paintFlyingObject( g );
		//���Ƶл��ӵ�
		paintPlaneBullets( g );
		//���Ʒ���������
		paintScore( g );
		//����״̬ͼ
		paintState( g );
		//���Ʊ�ըЧ��
		paintBlast( g );
	}
	
	//�����ҷ�ս��
	public void paintHero(Graphics g) {
		//�ҷ��ɻ���ͼƬ��x���꣬y���꣬null
		g.drawImage( hero.image ,hero.x ,hero.y ,null );
	}
	
	
	//���Ʒ���
	public void paintScore( Graphics g ) {
		//���û�����ɫ
		g.setColor( Color.BLACK );
		//���������С���������ƣ���ϸ����С��
		Font font = new Font(
				    Font.SERIF,
				    Font.BOLD,
				    20 );
		g.setFont( font );
		//д���� [x:20 y:25]
		g.drawString("����: "+ score ,17 ,25 );
		//д��������
		g.drawString("����: "+ hero.getLife() ,17 ,50 );
		//дʱ��
		g.drawString("ʱ��: "+ time0 ,17 ,75 );
		//˫������ʣ��ʱ��
		if( hero.doubleFire==1 ) {
			g.drawString("˫��������"+ (1000-doubFireC)/10,17,100 );
		}
		if( ikunState==1 ) {
			g.drawString("��ֻ��ӷ�ģʽ", 17, 125 );
		}
	}
	
	
	//����״̬ͼ
	public void paintState(Graphics g) {
		//������Ϸ״̬����ʾ��Ӧ��ͼƬ
		switch( state ) {
			case GAME.START:
				g.drawImage(RES.start,0,0,null);
			break;
			case GAME.PAUSE:
				g.drawImage(RES.pause,0,0,null);
			break;
			case GAME.GAME_OVER:
				g.drawImage(RES.gameover,0,0,null);
		}
	}
	
	
	//�����ӵ�
	public void paintBullets(Graphics g) {
		for(Bullet B : bullets) {
			g.drawImage( B.image, B.x, B.y ,null );
		}
	}
	
	public void paintPlaneBullets(Graphics g) {
		for( Bullet B : planeBullets ) {
			g.drawImage( B.image, B.x, B.y, null );
		}
	}
	
	
	//�ö�ʱ��ÿ300ms����һ���ӵ�
	int shootCount = 0;
	int planeShootCount = 0;
	
	public void shootAction() {
		shootCount ++;  //ÿ10ms����һ��
		planeShootCount ++;
		if( ikunState==0 ) { heroShootCount = defHeroShootCount; }
		if( gameOverState==0&&shootCount >= heroShootCount ) {
			shootCount = 0;
			Bullet[] bArr = hero.shoot();
			
			int oldLen = bullets.length;
			int newLen = bullets.length + bArr.length;
			//�Ѿ��������ݸ��Ƹ������������У����ݣ�
			//���������鸳ֵ����ȫ���ӵ�
			bullets = Arrays.copyOf(bullets, newLen);
			//�������ӵ��ӵ�ȫ�ֵ��ӵ���
			System.arraycopy(bArr, 0, bullets, oldLen, bArr.length);
		}
		if(planeShootCount >= 100 ) {
			planeShootCount = 0;
			for( FlyingObject f : flyings ) {
				if( f instanceof Enemy ) {
					AirPlane p = (AirPlane)f;
					Bullet nB = p.shoot();
					Bullet[] pBArr = { nB };
					
					int oldLen = planeBullets.length;
					int newLen = planeBullets.length + 1;
					planeBullets = Arrays.copyOf(planeBullets, newLen);
					System.arraycopy(pBArr, 0, planeBullets, oldLen, 1);
				}
			}
		}
	}
	
	
	
	//�������з�����
	public void paintFlyingObject(Graphics g) {
		for(FlyingObject F : flyings) {
			g.drawImage( F.image, F.x, F.y, null );
		}
	}
	
	//���Ʊ�ըЧ��
	public void paintBlast( Graphics g ) {
		for(Blast B : blasts) {
			g.drawImage( B.image, B.x, B.y ,null );
		}
	}
	
	
	//��ײ����
	public Bullet bang(Bullet b) {
		List<FlyingObject> temp = new ArrayList();
		List<Blast> tempBlt = new ArrayList();
		for( Blast Blt : blasts ) {
			tempBlt.add( Blt );
		}
		Bullet RET = b;
		//����������
		for( FlyingObject F : flyings ) {
			if( F.shootBy(b) ) {
				if( !(F instanceof Enemy ) ) {
					temp.add( F );  //�ӵ������List
				}
				else {
					tempBlt.add( new Blast( F.x,F.y ) );  //������ӱ�ըЧ��
					
					if( blastNum1==1 ) {
						if( blastNum2==1 ) {
							RES.music_blast.loop( 0 );
							blastNum2 = 2;
						}
						else {RES.music_blast.loop( 1 );}
						blastNum1 = 2;
					}
					else if( blastNum1==2 ) {
						if( blastNum2==2 ) {
							RES.music_blast1.loop( 0 );
							blastNum2 = 3;
						}
						else {RES.music_blast1.loop( 1 );}
						blastNum1 = 3;
					}
					else{
						if( blastNum2==3 ) {
							RES.music_blast2.loop( 0 );
							blastNum2 = 4;
						}
						else {RES.music_blast2.loop( 1 );}
						blastNum1 = 1;
					}
					
					Enemy e = (Enemy)F;
					score += e.getScore();
					RET = null;
				}
			
			}else {  //û�д���
				temp.add( F );  //�ӵ������List
			}
		}
		FlyingObject[] ARR = new FlyingObject[temp.size()];
		int index = 0;
		for(FlyingObject f : temp) {
			ARR[ index++ ] = f;
		}
		flyings = ARR;
		
		index = 0;
		Blast[] ARRBlt = new Blast[tempBlt.size()];
		for( Blast B : tempBlt ) {
			ARRBlt[ index++ ] = B;
		}
		blasts = ARRBlt;
		
		return RET;
	}
	
	//��ײ��ⷽ��
	public void bangAction() {
		//�����ӵ�����һ��
		List<Bullet> list = new ArrayList();
		for( Bullet B : bullets ) {
			Bullet RET = bang( B );
			if( RET!=null ) {
				list.add( RET );  //�����ӵ�
			}
		}
		//�ﵽ�ӵ�����͸�л���Ч��
		Bullet[] ARR = new Bullet[ list.size() ];
		int index = 0;
		for(Bullet f : list) {
			ARR[ index++ ] = f;
		}
		bullets = ARR;
	}
	
	//�������ƶ�
	public void stepAction() {
		hero.step();
		for(FlyingObject F : flyings ) {
			//��������΢���ƶ�����
			if( F instanceof Enemy ) {
				AirPlane plane = (AirPlane)F;
				if( plane.type==1 ) {
					plane.lStep();
				}else if( plane.type==2 ) {
					plane.rStep();
				}else {
					plane.step();
				}
			}
			else F.step();
		}
		for(Bullet B : bullets ) {
			B.step();
		}
		for( Bullet pB : planeBullets ) {
			pB.planeStep();
		}
	}
	
	
	//����ӵ��������(Խ���)
	public void outOfBoundsAction() {
		int index = 0;
		//����л��뽱��Ʒ
		FlyingObject[] tempF = new FlyingObject[ flyings.length ] ;
		for( FlyingObject f : flyings ) {
			if( !f.outOfBound() ) {
				tempF[ index ] = f;
				index++;
			}else if( (f instanceof Enemy)&&gameOverState==0&&ikunState==0 ) {
				hero.substractLife();
				hero.images[1] = RES.heroC;
				redState = 1;
				if( redMusicState==1 ) {
					RES.red_music.loop(0);
					redMusicState = 2;
				}else if( hero.getLife()>0 ) {
					RES.red_music.loop(1);
				}
			}
		}
		flyings = Arrays.copyOf(tempF, index);
		
		//����ӵ�
		index = 0;  //����
		Bullet[] tempArr = new Bullet[ bullets.length ];
		Bullet[] tempPBArr = new Bullet[ planeBullets.length ];
		for(Bullet B : bullets) {
			//�ж��ӵ��Ƿ�Խ��
			if(!B.outOfBound()) {
				tempArr[index] = B;
				index ++;
			}else {
				//System.out.println("{�Ƴ�}�ӵ���"+ B );
			}
		}
		//ȫ�ֵ��ӵ�����ָ�����������
		bullets = Arrays.copyOf(tempArr, index);
		
		index = 0;
		for(Bullet pB : planeBullets ) {
			if( !pB.outOfBound() ) {
				tempPBArr[index] = pB;
				index ++;
			}else {
				//System.out.println("{�Ƴ�}�з��ӵ���"+ pB );
			}
		}
		planeBullets = Arrays.copyOf(tempPBArr, index);
		
	}
	
	
	//���ɷ�����
	public FlyingObject nextOne(int type) {
		//���������
		int R = (int)(Math.random()*15);
		if( gameOverState==0&&ikunState==0&&R==1 ) {
			int buffType = (int)(Math.random()*10);
			if( buffType<=4 ) {
				System.out.println("{GAME}���ɡ�������-˫��������");
				return new Buff(0);
			}
			else if( buffType<=7 ) {
				System.out.println("{GAME}���ɡ�������-��������");
				return new Buff(2);
			}
			else {
				System.out.println("{GAME}���ɡ�������-IKUN����֤�顿");
				return new Buff(1);  //�ر�ע�⣬�����Լ��ĳɽ�����
			}
			
		}else {
			//System.out.println("{GAME}���ɡ��л���");
			return new AirPlane(type);
		}
	}
	
	//����һ����������¼������������ļ���
	int flyEnterCount = 0;
	//
	public void enterAction() {
		flyEnterCount ++;
		if( ikunState==0 ) { flyingsEnterCount = defFlyingsEntCount; }
		if(flyEnterCount >= flyingsEnterCount) {
			flyEnterCount = 0;
			int typeNum = (int)(Math.random()*10);
			FlyingObject F;
			F = nextOne(typeNum != 1 && typeNum != 2 ? 0 : typeNum);
			//��ȫ�ַ������������׷�ӷ�����
			int newLen = flyings.length + 1;
			flyings = Arrays.copyOf(flyings,newLen);
			//׷�ӷ����ﵽĩβ
			flyings[ newLen-1 ] = F;
		}
	}
	
	//�������м�ʱ����Σ����˸��ʱֹͣ��˸��Buffʱ����
	int index = 0;
	int redCount = 0;
	int ikunCount = 0;
	int gameOverCount = 0;
	int doubFireC = 0;
	int timeCount = 0;  //�Ѷ���ʱ������
	public void timersAction() {
		if( redState==1 ) { redCount++; }
		if( redCount>=180 ) {
			redCount = 0;
			redState = 0;
			if( ikunState==0&&gameOverState==0 ) {
				hero.images[1] = RES.heroB;
			}
		}
		
		if( ikunState==1 ) { ikunCount++; }
		if( ikunCount>=1000 ) {
			ikunCount = 0;
			ikunState = 0;
			RES.background = RES.backgroundA;
			Bullet.speed = Bullet.defaultSpeed;
			heroShootCount = defHeroShootCount;
			flyingsEnterCount = defFlyingsEntCount;
			hero.cancelIkunFire();
			hero.images[0] = RES.heroA;
			hero.images[1] = RES.heroB;
			hero.heroBType = 1;
			RES.ikun_music.stop();
			RES.music.loop(1000);
			
			flyings = new FlyingObject[] {};
			planeBullets = new Bullet[] {};
		}
		
		if( gameOverState==1 ) { gameOverCount++; }
		if( gameOverCount>=50 ) { hero.moveTO(1000, 200); }
		if( gameOverCount>=800 ) {
			gameOverState = 0;
			gameOverCount = 0;
			state = GAME.GAME_OVER;
			flyings = new FlyingObject[] {};
			planeBullets = new Bullet[] {};
			RES.gameovermusic.stop();
			hero = new Hero();
			defHeroShootCount = 40;
			defFlyingsEntCount = 100;
			time0 = 0;
			time1 = 0;
			score = 0;
		}
		
		timeCount ++;
		if( timeCount>=300 ) {
			timeCount = 0;
			if( defHeroShootCount> 10 ) {
				defHeroShootCount -= 3;
			}
			if( defFlyingsEntCount>30 ) {
				defFlyingsEntCount -= 5;
			}
		}
		
		if( hero.doubleFire==1 ) { doubFireC ++; }
		if( doubFireC>=1000 ) {
			doubFireC = 0;
			hero.cancelDoubleFire();
		}
		
		//��ըЧ����ʱ
		index = 0;
		Blast[] tempBlts = new Blast[ blasts.length ];
		for( Blast Blt : blasts ) {
			Blt.Count ++;
			if( Blt.Count<=50 ) {
				tempBlts[ index ] = Blt;
				index ++;
			}
		}
		blasts = Arrays.copyOf(tempBlts, index);
	}
	
	
	//�жϵз�ײ���ҷ�����Ϸ����
	public boolean checkGameOver() {
		List<FlyingObject> temp = new ArrayList();
		
		for( FlyingObject F : flyings ) {
			if( hero.hit(F) ) {
				if( F instanceof Enemy ) {
					if( ikunState==0 ) {
						hero.substractLife();
						hero.images[1] = RES.heroC;
						redState = 1;
						if( redMusicState==1 ) {
							RES.red_music.loop(0);
							redMusicState = 2;
						}else if( hero.getLife()>0 ) {
							RES.red_music.loop(1);
						}
					}
					
				}
				else {
					//������ʧ���㲻��ʧ
					Buff tempBuffType = (Buff)F;
					if( tempBuffType.type==2 ) {
						hero.addLife();
					}
					else if( tempBuffType.type==1 ) {
						ikunState = 1;
						RES.background = RES.ikun_background;
						Bullet.speed = 15;
						heroShootCount = 10;
						flyingsEnterCount = 5;
						hero.setIkunFire();
						hero.images[0] = RES.heroD;
						hero.images[1] = RES.heroE;
						hero.heroBType = 3;
						RES.music.stop();
						RES.ikun_music.loop(1);
					}
					else {
						if( hero.doubleFire==1 ) {
							doubFireC = 0;
						}
						hero.setDouble();
					}
				}
			}
			else {
				temp.add( F );
			}
		}
		
		int index = 0;
		Bullet[] pBArr = new Bullet[ planeBullets.length ];
		for( Bullet pB : planeBullets ) {
			if( hero.hit(pB) ) {
				if( ikunState==0 ) {
					hero.substractLife();
					hero.images[1] = RES.heroC;
					redState = 1;
					if( redMusicState==1 ) {
						RES.red_music.loop(0);
						redMusicState = 2;
					}else if( hero.getLife()>0 ) {
						RES.red_music.loop(1);
					}
				}
			}
			else {
				pBArr[index] = pB;
				index ++;
			}
		}
		planeBullets = Arrays.copyOf(pBArr, index);
		
		//û��ײ�ϵı���
		FlyingObject[] ARR = new FlyingObject[ temp.size() ];
		index = 0;
		for (FlyingObject f : temp) {
			ARR[ index++ ] = f;
		}
		flyings = ARR;
		
		return hero.getLife() <= 0;
	}
	
	//�����Ϸ����
	public void checkGameOverAction() {
		if( checkGameOver() ) {
			hero.images[0] = RES.hero_blast;
			hero.images[1] = RES.hero_blast;
			RES.music.stop();
			
			RES.gameovermusic.loop(0);
			gameOverState = 1;
		}
	}
	
	
	public void start() {
		//������������
		MouseAdapter AD = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(gameOverState==0&&state==GAME.RUNNING) {
					int x = e.getX();  //�õ�����x����
					int y = e.getY();  //�õ�����y����
					//���õ�Ӣ�۷ɻ���
					hero.moveTO( x, y );
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				switch( state ) {
					case GAME.START:
						//��״̬����Ϊ����
						state = GAME.RUNNING;
						RES.music.start();
						RES.music.loop(5000);
					break;
					case GAME.PAUSE:
						state = GAME.RUNNING;
						if( gameOverState==0 ) {
							RES.music.start();
							RES.music.loop(5000);
						}
					break;
					case GAME.RUNNING:
						state = GAME.PAUSE;
						RES.music.stop();
					break;
					case GAME.GAME_OVER:
						state = GAME.RUNNING;
						RES.music.start();
						RES.music.loop(5000);
					break;
				}
			}
		};
		
		this.addMouseListener( AD );
		this.addMouseMotionListener( AD );
		
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if( state==GAME.RUNNING ) {
					//----ʡ����������----
					timersAction();
					if( gameOverState==0 ) { checkGameOverAction(); }
					enterAction();  //�����������뻭��ʱ��Ϊ
					stepAction();  //����������߲���Ϊ
					shootAction();  //�����������Ϊ
					outOfBoundsAction();  //����Խ�����Ϊ
					bangAction(); //�����ӵ� ײ���л�����Ϊ
					time1 = time1 + 0.01;
					time0 = (int)time1;
				}
				repaint(); //�ػ��ӿ�..
			}
		};
		timer.schedule(task, interval,interval);
	}
	
}
