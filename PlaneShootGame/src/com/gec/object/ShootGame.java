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
	//设置相应的属性
	int state = GAME.START;  //默认未开始
	//我方英雄得分
	int score = 0;
	//我方英雄（飞机）
	Hero hero = new Hero();
	//时间参数0
	static int time0 = 0;
	//时间参数1
	static double time1 = 0;
	//定时器
	Timer timer = null;
	//间隔时间：10ms
	int interval = 10;  //每10ms 重绘画面
	
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
	
	
	//Graphics画笔对象
	public void paint(Graphics g) {
		//绘制背景图
		g.drawImage( RES.background,0,0,null);
		//绘制英雄
		paintHero( g );
		//绘制子弹
		paintBullets( g );
		//绘制所有飞行物
		paintFlyingObject( g );
		//绘制敌机子弹
		paintPlaneBullets( g );
		//绘制分数，名称
		paintScore( g );
		//绘制状态图
		paintState( g );
		//绘制爆炸效果
		paintBlast( g );
	}
	
	//绘制我方战机
	public void paintHero(Graphics g) {
		//我方飞机的图片，x坐标，y坐标，null
		g.drawImage( hero.image ,hero.x ,hero.y ,null );
	}
	
	
	//绘制分数
	public void paintScore( Graphics g ) {
		//设置画笔颜色
		g.setColor( Color.BLACK );
		//设置字体大小（字体名称，粗细，大小）
		Font font = new Font(
				    Font.SERIF,
				    Font.BOLD,
				    20 );
		g.setFont( font );
		//写分数 [x:20 y:25]
		g.drawString("分数: "+ score ,17 ,25 );
		//写生命条数
		g.drawString("生命: "+ hero.getLife() ,17 ,50 );
		//写时间
		g.drawString("时间: "+ time0 ,17 ,75 );
		//双倍火力剩余时间
		if( hero.doubleFire==1 ) {
			g.drawString("双倍火力："+ (1000-doubFireC)/10,17,100 );
		}
		if( ikunState==1 ) {
			g.drawString("超只因加分模式", 17, 125 );
		}
	}
	
	
	//绘制状态图
	public void paintState(Graphics g) {
		//根据游戏状态，显示对应的图片
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
	
	
	//绘制子弹
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
	
	
	//让定时器每300ms发射一次子弹
	int shootCount = 0;
	int planeShootCount = 0;
	
	public void shootAction() {
		shootCount ++;  //每10ms进来一次
		planeShootCount ++;
		if( ikunState==0 ) { heroShootCount = defHeroShootCount; }
		if( gameOverState==0&&shootCount >= heroShootCount ) {
			shootCount = 0;
			Bullet[] bArr = hero.shoot();
			
			int oldLen = bullets.length;
			int newLen = bullets.length + bArr.length;
			//把旧数组内容复制给更长的数组中（扩容）
			//产生新数组赋值给旧全局子弹
			bullets = Arrays.copyOf(bullets, newLen);
			//产生的子弹加到全局的子弹中
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
	
	
	
	//绘制所有飞行物
	public void paintFlyingObject(Graphics g) {
		for(FlyingObject F : flyings) {
			g.drawImage( F.image, F.x, F.y, null );
		}
	}
	
	//绘制爆炸效果
	public void paintBlast( Graphics g ) {
		for(Blast B : blasts) {
			g.drawImage( B.image, B.x, B.y ,null );
		}
	}
	
	
	//碰撞方法
	public Bullet bang(Bullet b) {
		List<FlyingObject> temp = new ArrayList();
		List<Blast> tempBlt = new ArrayList();
		for( Blast Blt : blasts ) {
			tempBlt.add( Blt );
		}
		Bullet RET = b;
		//遍历飞行物
		for( FlyingObject F : flyings ) {
			if( F.shootBy(b) ) {
				if( !(F instanceof Enemy ) ) {
					temp.add( F );  //加到上面的List
				}
				else {
					tempBlt.add( new Blast( F.x,F.y ) );  //在这里加爆炸效果
					
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
			
			}else {  //没有打中
				temp.add( F );  //加到上面的List
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
	
	//碰撞检测方法
	public void bangAction() {
		//所有子弹都过一遍
		List<Bullet> list = new ArrayList();
		for( Bullet B : bullets ) {
			Bullet RET = bang( B );
			if( RET!=null ) {
				list.add( RET );  //保留子弹
			}
		}
		//达到子弹不穿透敌机的效果
		Bullet[] ARR = new Bullet[ list.size() ];
		int index = 0;
		for(Bullet f : list) {
			ARR[ index++ ] = f;
		}
		bullets = ARR;
	}
	
	//飞行物移动
	public void stepAction() {
		hero.step();
		for(FlyingObject F : flyings ) {
			//可以在这微调移动长度
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
	
	
	//清除子弹与飞行物(越界的)
	public void outOfBoundsAction() {
		int index = 0;
		//清除敌机与奖励品
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
		
		//清除子弹
		index = 0;  //归零
		Bullet[] tempArr = new Bullet[ bullets.length ];
		Bullet[] tempPBArr = new Bullet[ planeBullets.length ];
		for(Bullet B : bullets) {
			//判断子弹是否越界
			if(!B.outOfBound()) {
				tempArr[index] = B;
				index ++;
			}else {
				//System.out.println("{移除}子弹："+ B );
			}
		}
		//全局的子弹重新指向到这个新数组
		bullets = Arrays.copyOf(tempArr, index);
		
		index = 0;
		for(Bullet pB : planeBullets ) {
			if( !pB.outOfBound() ) {
				tempPBArr[index] = pB;
				index ++;
			}else {
				//System.out.println("{移除}敌方子弹："+ pB );
			}
		}
		planeBullets = Arrays.copyOf(tempPBArr, index);
		
	}
	
	
	//生成飞行物
	public FlyingObject nextOne(int type) {
		//生成随机数
		int R = (int)(Math.random()*15);
		if( gameOverState==0&&ikunState==0&&R==1 ) {
			int buffType = (int)(Math.random()*10);
			if( buffType<=4 ) {
				System.out.println("{GAME}生成【奖励物-双倍火力】");
				return new Buff(0);
			}
			else if( buffType<=7 ) {
				System.out.println("{GAME}生成【奖励物-加生命】");
				return new Buff(2);
			}
			else {
				System.out.println("{GAME}生成【奖励物-IKUN家族证书】");
				return new Buff(1);  //特别注意，后面自己改成奖励物
			}
			
		}else {
			//System.out.println("{GAME}生成【敌机】");
			return new AirPlane(type);
		}
	}
	
	//设置一个变量：记录进入这个方法的计数
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
			//在全局飞行物数组后面追加飞行物
			int newLen = flyings.length + 1;
			flyings = Arrays.copyOf(flyings,newLen);
			//追加飞行物到末尾
			flyings[ newLen-1 ] = F;
		}
	}
	
	//汇总所有计时器，危险闪烁定时停止闪烁、Buff时长等
	int index = 0;
	int redCount = 0;
	int ikunCount = 0;
	int gameOverCount = 0;
	int doubFireC = 0;
	int timeCount = 0;  //难度随时间提升
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
		
		//爆炸效果延时
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
	
	
	//判断敌方撞击我方与游戏结束
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
					//奖励消失，你不消失
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
		
		//没有撞上的保留
		FlyingObject[] ARR = new FlyingObject[ temp.size() ];
		index = 0;
		for (FlyingObject f : temp) {
			ARR[ index++ ] = f;
		}
		flyings = ARR;
		
		return hero.getLife() <= 0;
	}
	
	//检测游戏结束
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
		//设置鼠标监听器
		MouseAdapter AD = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(gameOverState==0&&state==GAME.RUNNING) {
					int x = e.getX();  //拿到鼠标的x坐标
					int y = e.getY();  //拿到鼠标的y坐标
					//设置到英雄飞机上
					hero.moveTO( x, y );
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				switch( state ) {
					case GAME.START:
						//把状态设置为运行
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
					//----省略其他代码----
					timersAction();
					if( gameOverState==0 ) { checkGameOverAction(); }
					enterAction();  //处理飞行物进入画面时行为
					stepAction();  //处理飞行物走步行为
					shootAction();  //处理射击的行为
					outOfBoundsAction();  //处理越界的行为
					bangAction(); //处理子弹 撞击敌机的行为
					time1 = time1 + 0.01;
					time0 = (int)time1;
				}
				repaint(); //重绘视口..
			}
		};
		timer.schedule(task, interval,interval);
	}
	
}
