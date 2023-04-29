package Game;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

class Display extends JPanel implements ActionListener {

	private boolean newGame = false;
	private StripGenerator stripGen = new StripGenerator();
															
	private int numOfStrips = 9;
	private Sprite[][] allStrips = new Sprite[numOfStrips][8];
																
	private ArrayList<Integer> special = new ArrayList<>();
															
	private int land = 1, water = 0;
									
	private ArrayList<Sprite> cars = new ArrayList<>();
														
	private ArrayList<Sprite> trains = new ArrayList<>();
															
	private JButton startButton, controlsButton, loseButton, restartButton, cotinuceButton, quitButton, menuButton,
			pauseButton, xButton;
	private JLabel backgroundLabel, backgroundLabel1, controltextLabel, nameLabel;
	private ManageVehicles vManager = new ManageVehicles();

	private Sprite hero = new Sprite("/Player/Up.png");
	private int score = 0, movement = 0;
	private HighScore scoreManager = new HighScore();
	private Score sc = new Score();
	private int up = 0, down = 0, left = 0, right = 0;
	private boolean press = false;
	private boolean isPlaying = false;
	private boolean invincibility = false;
	private int invDuration = 0, invTimeLeft = 0;
	private Timer gameLoop;
	private Random rand = new Random();
	private double scrollSpeed = 2.0;
	private JTextField nameField;
	private static String name;
	private SqlManager sqlManager = new SqlManager();
	Sound sound = new Sound();


	Display(boolean pause) {
		setLayout(null);
		startButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Start.png")));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		controlsButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Controls.png")));
		controlsButton.setBorder(BorderFactory.createEmptyBorder());
		controltextLabel = new JLabel(new ImageIcon(getClass().getResource("/Misc/Control_text.png")));
		xButton = new JButton(new ImageIcon(getClass().getResource("/Misc/X.png")));
		xButton.setBorder(BorderFactory.createEmptyBorder());
		loseButton = new JButton(new ImageIcon(getClass().getResource("/Misc/you_lose.png")));
		loseButton.setBorder(BorderFactory.createEmptyBorder());
		restartButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Restart.png")));
		restartButton.setBorder(BorderFactory.createEmptyBorder());
		cotinuceButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Cotinuce.png")));
		cotinuceButton.setBorder(BorderFactory.createEmptyBorder());
		quitButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Quit.png")));
		quitButton.setBorder(BorderFactory.createEmptyBorder());
		menuButton = new JButton(new ImageIcon(getClass().getResource("/Misc/Menu.png")));
		menuButton.setBorder(BorderFactory.createEmptyBorder());
		pauseButton = new JButton(new ImageIcon(getClass().getResource("/Misc/pause.png")));
		pauseButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.addActionListener(this);
		controlsButton.addActionListener(this);
		xButton.addActionListener(this);
		restartButton.addActionListener(this);
		cotinuceButton.addActionListener(this);
		quitButton.addActionListener(this);
		menuButton.addActionListener(this);

		backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/Misc/Loading.png")));
		backgroundLabel.setBounds(0, 0, 800, 800);
		backgroundLabel1 = new JLabel(new ImageIcon(getClass().getResource("/Misc/Loading1.jpg")));
		backgroundLabel1.setBounds(0, 0, 800, 800);
		nameLabel = new JLabel(new ImageIcon(getClass().getResource("/Misc/playerLabel.png")));
		nameLabel.setBounds(230, 250, 150, 30);
		nameField = new JTextField();
		nameField.setBounds(380, 250, 180, 30);

		add(nameLabel);
		add(nameField);
		add(startButton);
		add(controlsButton);
		add(xButton);
		add(controltextLabel);
		add(restartButton);
		add(loseButton);
		add(cotinuceButton);
		add(quitButton);
		add(menuButton);
		add(pauseButton);
		add(backgroundLabel);
		add(backgroundLabel1);

		startButton.setBounds(250, 350, 300, 120);
		controlsButton.setBounds(250, 550, 300, 120);
		controltextLabel.setBounds(150, 250, 500, 400);
		xButton.setBounds(600, 250, 50, 50);
		loseButton.setBounds(120, 70, 600, 200);
		restartButton.setBounds(250, 300, 300, 116);
		menuButton.setBounds(100, 150, 300, 200);
		quitButton.setBounds(250, 600, 300, 116);
		cotinuceButton.setBounds(250, 420, 300, 116);
		pauseButton.setBounds(150, 150, 500, 70);
		menuButton.setBounds(250, 250, 300, 115);
		
		controltextLabel.setVisible(false);
		xButton.setVisible(false);
		loseButton.setVisible(false);
		restartButton.setVisible(false);
		menuButton.setVisible(false);
		cotinuceButton.setVisible(false);
		quitButton.setVisible(false);
		pauseButton.setVisible(false);
		backgroundLabel1.setVisible(false);
		addKeyListener(new KeyPressing());

		setFocusable(true);

		setDoubleBuffered(true);

		setInitialLocations();
		gameLoop = new Timer(25, this);

		if (!pause ) {
			nameLabel.setVisible(false);
			nameField.setVisible(false);
			startButton.setVisible(false);
			controlsButton.setVisible(false);
			backgroundLabel.setVisible(false);
			backgroundLabel.setVisible(false);
			loseButton.setVisible(false);
			restartButton.setVisible(false);
			gameLoop.start();
			isPlaying = true;
		} else {
			playMusic(3);
		}
	}

	private void setInitialLocations() {
		hero.setXLoc(298);
		hero.setYLoc(400);
		for (int i = 0; i < numOfStrips; i++) {
			Sprite[] strip = stripGen.getLandStrip();
			allStrips[i] = strip;
									
		}

		allStrips[5][3].setImage("/Misc/Sand.png");// vi set nhan vat khoi dau o vi tri 500 400
		allStrips[4][3].setImage("/Misc/Sand.png");

		int x = 0;
		int y = -100;

		for (int i = 0; i < numOfStrips; i++) {

			for (int z = 0; z < 8; z++) {

				allStrips[i][z].setXLoc(x);//dat vi tri cua cac vat the

				allStrips[i][z].setYLoc(y);// vi cua so la 800 800 va vat the la 100 100
											

				x += 100;
			}
			x = 0;
			y += 100;
		}

		for (int i = 0; i < 8; i++) {
			if (allStrips[0][i].getFileName().equals("/Misc/Sand.png")) {// kiem tra xem vat the dau tien co phai la sand.png k
				special.add(i);//them vi tri vao special
				land++;
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == startButton || 
				e.getSource() == restartButton)) {
			name = nameField.getText();
			sound.stop();
			playSE(4);

			try {
				Thread.sleep(4300);//nghi 4,3 s
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			newGame = true;
			newGame();

		} else if (e.getSource() == controlsButton) {
			nameField.setVisible(false);
			nameLabel.setVisible(false);
			startButton.setVisible(false);
			controlsButton.setVisible(false);
			controltextLabel.setVisible(true);
			xButton.setVisible(true);
		} else if (e.getSource() == xButton) {
			controltextLabel.setVisible(false);
			xButton.setVisible(false);
			nameField.setVisible(true);
			nameLabel.setVisible(true);
			startButton.setVisible(true);
			controlsButton.setVisible(true);
		} else if (e.getSource() == menuButton) {
			playMusic(3);
			restartButton.setVisible(false);
			menuButton.setVisible(false);
			cotinuceButton.setVisible(false);
			quitButton.setVisible(false);
			pauseButton.setVisible(false);
			nameField.setVisible(true);
			nameLabel.setVisible(true);
			backgroundLabel.setVisible(true);
			loseButton.setVisible(false);
			startButton.setVisible(true);
			controlsButton.setVisible(true);

		} else if (e.getSource() == cotinuceButton) {

			gameLoop.start();
			pauseButton.setVisible(false);
			menuButton.setVisible(false);
			cotinuceButton.setVisible(false);
			quitButton.setVisible(false);
			backgroundLabel1.setVisible(false);
		} else if (e.getSource() == quitButton) {
			System.exit(0);
		} else {
			heroBounds();//kiem tra va cham nguoi choi
			jumpHero();//kiem tra di chuyen cua nguoi choi
			hero.move();//di chuyen nguoi choi

			manageCars();//quan ly xe
			manageTrains();//quan ly tau

			for (int i = 0; i < numOfStrips; i++) {
				for (int x = 0; x < 8; x++) {
					allStrips[i][x].move();//di chuyen cac vat the
				}
			}
			manageStrips();//quan ly dat

			scrollScreen();//di chuyen man hinh
			if (movement > score)
				score = movement;//tang diem moi khi di chuyen

			repaint();
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void newGame() {

		if (newGame) {
			playMusic(7);
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

			frame.dispose();
			new Window(false);
			stopMusic();
		}
	}

	private void killMsg() {

		isPlaying = false;
		repaint();

		playSE(1);
		gameLoop.stop();
		scoreManager.updateScores(score);//cap nhat diem cao nhat
		sc.Scores(name, score);//in diem ra file
		try {
			try {
				sqlManager.addData(name, score);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Name : " + name);
			System.out.println("Score : " + score);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		loseButton.setVisible(true);
		restartButton.setBounds(230, 300, 300, 116);
		menuButton.setBounds(230, 430, 300, 115);
		quitButton.setBounds(230, 570, 300, 116);
		restartButton.setVisible(true);
		menuButton.setVisible(true);
		quitButton.setVisible(true);
		backgroundLabel1.setVisible(true);

	}


	private void heroBounds() {

		if(invincibility){
			invDuration++;

			if(invDuration == 1)
				invTimeLeft = 3;
			if(invDuration == 50)
				invTimeLeft = 2;
			if(invDuration == 100)
				invTimeLeft = 1;
			if(invDuration == 150) {
				invTimeLeft = 0;
				invincibility = false;
				playSE(6);
			}
		}

		for (int i = 0; i < numOfStrips; i++) {
			for (Sprite s : allStrips[i]) {
				if (!invincibility) {
					if (s.getFileName().equals("/Misc/Tree_One.png") 
							|| s.getFileName().equals("/Misc/Tree_Two.png")) {
						if (collision(hero, s)) {

							if ((s.getYLoc() + 100) - (hero.getYLoc()) < 5 
									&& (s.getXLoc() + 100) - hero.getXLoc() < 125 
									&& (s.getXLoc() + 100) - hero.getXLoc() > 20) {
								up = 0;
							} else if ((hero.getYLoc() + 105) > (s.getYLoc()) 
									&& (hero.getXLoc() + 100) - s.getXLoc() < 125 
									&& (hero.getXLoc() + 100) - s.getXLoc() > 20) {
								down = 0;
							} else if (hero.getXLoc() - (s.getXLoc() + 100) > -5 
									&& (s.getYLoc() + 100) - hero.getYLoc() < 125 
									&& (s.getYLoc() + 100) - hero.getYLoc() > 20) {
								left = 0;
							} else if (s.getXLoc() - (hero.getXLoc() + 100) > -5 
									&& (hero.getYLoc() + 100) - s.getYLoc() < 125 
									&& (hero.getYLoc() + 100) - s.getYLoc() > 20) {
								right = 0;
							}
						}
					}
					if (s.getFileName().equals("/Misc/Water.png")) {
						if (s.getXLoc() - hero.getXLoc() > 0 
								&& s.getXLoc() - hero.getXLoc() < 10) {
							if (s.getYLoc() - hero.getYLoc() > 0 
									&& s.getYLoc() - hero.getYLoc() < 10) {
								killMsg();
							}
						}
					}
				}

				if (hero.getYLoc() > 800) {

					hero.setYLoc(500);
					hero.setXLoc(900);
					killMsg();
					
					
				}

				if (hero.getYLoc() < -110) {

					hero.setYLoc(500);
					hero.setXLoc(900);
					killMsg();
					
				}
			}
		}

	}

	private void jumpHero() {

		int location;
		if (left > 0 && press) {
			hero.setXDir(-12.5);
			left--;
			hero.setImage("/Player/Left.png");
		} else if (right > 0 && press) {
			hero.setXDir(12.5);
			right--;
			hero.setImage("/Player/Right.png");
		} else if (left == 0 && right == 0 && up == 0 && down == 0) {
			hero.setXDir(0);
			press = false;
		}

		if (up > 0 && press) {

			hero.setYDir(-10);
			hero.move();
			hero.setImage("/Player/Up.png");
			location = hero.getYLoc();

			for (int i = 0; i < numOfStrips; i++) {

				Sprite x = allStrips[i][0];

				if (location - x.getYLoc() > 95 && location - x.getYLoc() < 105) {

					hero.setYDir(0);
					up = 0;
					press = false;

					hero.setYLoc(x.getYLoc() + 101);

					movement++;

					i = numOfStrips;
				}
			}
		}

		else if (down > 0 && press) {

			hero.setYDir(10);
			hero.move();
			hero.setImage("/Player/Down.png");

			location = hero.getYLoc();

			for (int i = 0; i < numOfStrips; i++) {

				Sprite x = allStrips[i][0];

				if (location - x.getYLoc() < -95 && location - x.getYLoc() > -105) {

					hero.setYDir(0);
					down = 0;
					press = false;

					hero.setYLoc(x.getYLoc() - 99);

					movement--;

					i = numOfStrips;
				}
			}
		}
	}

	private void manageCars() {

		for (int i = 0; i < cars.size(); i++) {

			Sprite car = cars.get(i);

			if (car.getYLoc() > 800) { // neu car ra khoi man hinh
				cars.remove(i);//xoa car
			} else {

				car.move();
				if (car.getXLoc() < -(rand.nextInt(700) + 400)) {//Neu car vuot qua gioi han ben trai man hinh, 

					car.setXDir(-(rand.nextInt(10) + 10));//dat huong di chuyen sang trai

					car.setXLoc(900);// dat lai vi tri x cua car 

					car.setImage(vManager.GetCar("left"));//dat hinh anh cua car thanh hinh anh xe quay sang trai
				} else if (car.getXLoc() > (rand.nextInt(700) + 1100)) {

					car.setXDir((rand.nextInt(10) + 10));

					car.setXLoc(-200);

					car.setImage(vManager.GetCar("right"));
				}
			}

			if (collision(car, hero) && !invincibility) {//kiem tra va cham va trang thai bat tu
				killMsg();
			}
		}
	}

	private void manageTrains() {

		for (int i = 0; i < trains.size(); i++) {

			Sprite train = trains.get(i);

			if (train.getYLoc() > 800) {
				trains.remove(i);
			} else {

				train.move();

				if (train.getXLoc() < -(rand.nextInt(2500) + 2600)) {
					train.setXDir(-(rand.nextInt(10) + 30));

					train.setXLoc(900);

					train.setImage(vManager.GetTrain("left"));
				} else if (train.getXLoc() > rand.nextInt(2500) + 1800) {
					train.setXDir((rand.nextInt(10) + 30));

					train.setXLoc(-1500);

					train.setImage(vManager.GetTrain("right"));
				}

			}

			if (collision(train, hero) && !invincibility) {

				killMsg();
			}
		}

	}

	private void manageStrips() {

		int allWater;
		int allSand;

		for (int v = 0; v < numOfStrips; v++) {

			if (allStrips[v][0].getYLoc() > 800) {// neu ra khoi man hinh

				allStrips[v] = stripGen.getStrip();//tao 1 vat the moi

				do {
					allWater = 0;
					allSand = 0;

					for (Sprite s : allStrips[v]) {
						
						if (s.getFileName().equals("/Misc/Water.png"))
							allWater++;
						if (s.getFileName().equals("/Misc/Sand.png"))
							allSand++;
					}
					
					if (allWater == 8)//neu tat ca la nuoc
						allStrips[v] = stripGen.getWaterStrip();//tao vat the nuoc
					if (allSand == 8)
						allStrips[v] = stripGen.getLandStrip();

				} while (allWater == 8 || allSand == 8);
				
				if (water > 0) {
					
					
					if (allStrips[v][0].getFileName().equals("/Misc/Water.png")
							|| allStrips[v][0].getFileName().equals("/Misc/Wood.png")) {//kiem tra phan tu dau tien co phai la nuoc hoac cau ko

						water = 0;

						for (int i : special) {//chay qua cac phan tu trong special
							allStrips[v][i].setImage("/Misc/Wood.png");//doi tuong tai vi tri i duoc dat anh wood
						}
					}
				}

				if (water > 0) {
					
					
					
					if (allStrips[v][0].getFileName().equals("/Misc/Sand.png")
							|| allStrips[v][0].getFileName().equals("/Misc/Shrub.png") ||
							allStrips[v][0].getFileName().equals("/Misc/Tree_One.png")
							|| allStrips[v][0].getFileName().equals("/Misc/Tree_Two.png")) {
						
						allStrips[v] = stripGen.getSpecialLandStrip();

						water = 0;

						for (int i : special) {
							allStrips[v][i].setImage("Misc/Sand.png");
						}
					}
				}

				if (land > 0) {
					
					
					if (allStrips[v][0].getFileName().equals("/Misc/Water.png")
							|| allStrips[v][0].getFileName().equals("/Misc/Wood.png")) {

						land = 0;

						int val = 0;

						while (val == 0) {
							
							allStrips[v] = stripGen.getWaterStrip();//tao 1 dai nuoc

							for (int i = 0; i < 8; i++) {
								if (allStrips[v][i].getFileName().equals("/Misc/Wood.png")) {
									for (int s : special) {
										if (i == s) {//neu co 1 phan tu dac biet
											val++;//dung vong lap
										}

									}
								}
							}
						}

					}
				}
				
				if (allStrips[v][0].getFileName().equals("Misc/Water.png")
						|| allStrips[v][0].getFileName().equals("Misc/Wood.png")) {
					
					special.clear();

					water = 0;

					for (int i = 0; i < 8; i++) {
						if (allStrips[v][i].getFileName().equals("/Misc/Wood.png")) {
							special.add(i);
							water++;
						}
					}
				} else
					water = 0;

				if (allStrips[v][0].getFileName().equals("/Misc/Sand.png")
						|| allStrips[v][0].getFileName().equals("/Misc/Shrub.png") ||
						allStrips[v][0].getFileName().equals("/Misc/Tree_One.png")
						|| allStrips[v][0].getFileName().equals("/Misc/Tree_Two.png")) {

					special.clear();

					land = 0;

					for (int i = 0; i < 8; i++) {
						if (allStrips[v][i].getFileName().equals("/Misc/Sand.png")) {
							special.add(i);
							land++;
						}
					}
				}

				int X = 0;

				for (int i = 0; i < 8; i++) {

					allStrips[v][i].setYLoc(-99);
					allStrips[v][i].setXLoc(X);

					X += 100;
				}
				
				
				
				if (allStrips[v][0].getFileName().equals("/Misc/Road.png")) {
					cars.add(vManager.setCar(allStrips[v][0].getYLoc() + 10));
				}

				if (allStrips[v][0].getFileName().equals("/Misc/Tracks.png")) {
					trains.add(vManager.setTrain(allStrips[v][0].getYLoc() + 10));
				}
			}
		}	
	}
	
	
	private void scrollScreen() {
		for (int v = 0; v < numOfStrips; v++) {
			for (int x = 0; x < 8; x++) {
				allStrips[v][x].setYDir(scrollSpeed);
														
			}
		}
		if (!press) {
			hero.setYDir(scrollSpeed);

		}
	}
	
	private boolean collision(Sprite one, Sprite two) {
		
		Rectangle first = new Rectangle(one.getXLoc(), one.getYLoc(),
				one.getWidth(), one.getHeight());
		Rectangle second = new Rectangle(two.getXLoc(), two.getYLoc(),
				two.getWidth(), two.getHeight());

		return first.intersects(second);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		for (int i = 0; i < numOfStrips; i++) {
			for (int x = 0; x < 8; x++) {
				allStrips[i][x].paint(g, this);
			}
		}

		hero.paint(g, this);

		for (Sprite s : cars)
			s.paint(g, this);

		for (Sprite s : trains)
			s.paint(g, this);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 14));

		if (name != null) {
			g.drawString(name, hero.getXLoc() + hero.getWidth() / 2 - 10, hero.getYLoc() - 5);
		}

		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
		g.setFont(newFont);
		g.setColor(Color.green);

		g.drawString("Top: " + scoreManager.readScore(), 50, 50);

		Font cF = g.getFont();
		Font nF = cF.deriveFont(cF.getSize() * 3f);
		g.setFont(nF);
		g.setColor(Color.yellow);
		g.drawString("" + score, 50, 150);
		

		
		Font CF = g.getFont();
		Font NF = CF.deriveFont(CF.getSize() * 1f);
		g.setFont(NF);
		g.setColor(Color.red);
		if (invincibility)
			g.drawString("" + invTimeLeft, 350, 350);

		Toolkit.getDefaultToolkit().sync();
	}

	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();

	}

	public void playSE(int i) {

		sound.setFile(i);
		sound.play();

	}

	public void stopMusic() {
		sound.stop();

	}

	private class KeyPressing extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					if (!press && hero.getXLoc() < 695) {
						right = 8;
						press = true;
						playSE(0);
					}
					break;
				case KeyEvent.VK_LEFT:
					if (!press && hero.getXLoc() > 0) {
						left = 8;
						press = true;
						playSE(0);
					}
					break;
				case KeyEvent.VK_UP:
					if (!press) {
						up = 10;
						press = true;
						playSE(0);
					}
					break;
				case KeyEvent.VK_DOWN:
					if (!press) {
						down = 10;
						press = true;
						playSE(0);
					}
					break;
				case KeyEvent.VK_CONTROL:
					if (!invincibility && invDuration < 150) {
						invincibility = true;
						playSE(5);
					}
					break;
				case KeyEvent.VK_SHIFT:
					if (gameLoop.isRunning()) {
						gameLoop.stop();
						pauseButton.setVisible(true);
						menuButton.setVisible(true);
						cotinuceButton.setVisible(true);
						quitButton.setVisible(true);
						backgroundLabel1.setVisible(true);
						playSE(2);
					} else if (!gameLoop.isRunning() && isPlaying == true) {
						gameLoop.start();
						pauseButton.setVisible(false);
						menuButton.setVisible(false);
						cotinuceButton.setVisible(false);
						quitButton.setVisible(false);
						backgroundLabel1.setVisible(false);
						playSE(2);
					}
					repaint();
					break;
				case KeyEvent.VK_ENTER:
					if (!gameLoop.isRunning() && isPlaying == false) {
						sound.stop();
						playSE(4);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						newGame = true;
						newGame();
					}
					break;
			}
		}

		public void keyReleased(KeyEvent e) {

			switch (e.getKeyCode()) {

				case KeyEvent.VK_RIGHT:
					hero.setXDir(0);
					break;
				case KeyEvent.VK_LEFT:
					hero.setXDir(0);
					break;
				case KeyEvent.VK_UP:
					hero.setYDir(2);
					break;
				case KeyEvent.VK_DOWN:
					hero.setYDir(2);
					break;
			}
		}
	}
}
