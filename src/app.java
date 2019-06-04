import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.Image;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class app {

	private JFrame frame;
	private JLabel dinoLabel;
	private JLabel cactusLabel;
	private JLabel crowLabel;
	private JLabel gameOver;
	private JLabel timeCountLabel;
	private JLabel cloud;
	private JLabel sun;
	private JLabel landLabel1;
	private JLabel landLabel2;
	private boolean runState = true;
	private boolean bowState = false;
	private boolean endState = true;
	private boolean jumpState = false;
	private boolean landState = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app window = new app();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public app() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("DinoDino");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);


		/**
		 * 恐龍圖片
		 */
		dinoLabel = new JLabel();

		/* Key Words */
		KeyStroke Up = KeyStroke.getKeyStroke("UP");
		KeyStroke Space = KeyStroke.getKeyStroke("SPACE");
		KeyStroke Down = KeyStroke.getKeyStroke("DOWN");
		KeyStroke DownRelease = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);

		/* InputMap & ActionMap */
		InputMap inputMap = dinoLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = dinoLabel.getActionMap();

		/* Jump Event */
		inputMap.put(Up, "jump");
		inputMap.put(Space, "jump");
		actionMap.put("jump", dinoJumpEvent);

		/* Bow Event */
		inputMap.put(Down, "bow");
		actionMap.put("bow", dinoBowEvent);

		/* BowRelease Event */
		inputMap.put(DownRelease, "bowRelease");
		actionMap.put("bowRelease", dinoBowReleaseEvent);

		dinoLabel.setBounds(56, 163, 50, 51);
		dinoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dinoLabel.setIcon(new ImageIcon(
				new ImageIcon("img//dino1.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(dinoLabel);

		/**
		 * 仙人掌
		 */
		cactusLabel = new JLabel();
		cactusLabel.setBounds(450, 183, 18, 34);
		cactusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cactusLabel.setIcon(new ImageIcon(
				new ImageIcon("img//cactus.png").getImage().getScaledInstance(20, 40, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(cactusLabel);
		
		/**
		 * 烏鴉
		 */
		crowLabel = new JLabel();
		crowLabel.setBounds(550, 155, 36, 17);
		crowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		crowLabel.setIcon(new ImageIcon(
				new ImageIcon("img//crow1.png").getImage().getScaledInstance(33, 24, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(crowLabel);

		/**
		 * 地板圖片1
		 */
		landLabel1 = new JLabel();
		landLabel1.setBackground(Color.WHITE);
		landLabel1.setBounds(0, 205, 436, 23);
		landLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		landLabel1.setIcon(new ImageIcon("img//land.png"));
		frame.getContentPane().add(landLabel1);

		/**
		 * 地板圖片2
		 */
		landLabel2 = new JLabel();
		landLabel2.setBounds(436, 205, 872, 23);
		landLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		landLabel2.setIcon(new ImageIcon("img//land.png"));
		frame.getContentPane().add(landLabel2);
		
		/**
		 * Title
		 */
		JLabel DinoDino = new JLabel("Dino Dino");
		DinoDino.setHorizontalAlignment(SwingConstants.CENTER);
		DinoDino.setFont(new Font("Arial", Font.PLAIN, 20));
		DinoDino.setBounds(159, 55, 125, 34);
		frame.getContentPane().add(DinoDino);
		
		/**
		 * Time Label
		 */
		timeCountLabel = new JLabel();
		timeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeCountLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 23));
		timeCountLabel.setBounds(183, 15, 75, 34);
		frame.getContentPane().add(timeCountLabel);
		
		/**
		 * Start按鈕
		 */
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Arial", Font.PLAIN, 14));
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnStart.setVisible(false); 
				DinoDino.setVisible(false);
				startRun();
			}
		});
		btnStart.setBounds(178, 111, 85, 23);
		btnStart.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		frame.getContentPane().add(btnStart);

		/**
		 * Game Over
		 */
		gameOver = new JLabel();
		gameOver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gameOver.setVisible(false);
				timeCountLabel.setVisible(true);
				crowLabel.setBounds(550, 155, 36, 17);
				startRun();
			}
		});
		gameOver.setBounds(64, 62, 314, 72);
		gameOver.setHorizontalAlignment(SwingConstants.CENTER);
		gameOver.setIcon(new ImageIcon(
				new ImageIcon("img//gameover.png").getImage().getScaledInstance(180, 70, Image.SCALE_DEFAULT)));
		gameOver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gameOver.setVisible(false); 
		frame.getContentPane().add(gameOver);
		
		/**
		 * background
		 */
		cloud = new JLabel();
		cloud.setBounds(40, 43, 80, 34);
		cloud.setIcon(new ImageIcon(
				new ImageIcon("img//cloud.png").getImage().getScaledInstance(80, 20, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(cloud);
		
		sun = new JLabel();
		sun.setBounds(350, 25, 60, 60);
		sun.setIcon(new ImageIcon(
				new ImageIcon("img//sun.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(sun);
		
	}

/* ------------------------------------------------------------------- */
	
	/**
	 * 開跑
	 */
	private void startRun() {
		runState = true;
		endState = false;
		bowState = false;
		jumpState = false;

		addBarrierThread();
		dinoRunThread();
		gameStartThread();
		setTimerThread();
		detectThread();
	}
	
/* ------------------------------------------------------------------- */
	
	/**
	 * 恐龍跳Event
	 */
	Action dinoJumpEvent = new AbstractAction() {
		/* 表明不同版本間的兼容性 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			/* 上一次還沒跳完 & 遊戲還沒開始AARRR */
			if (jumpState || endState) {
				return;
			}
			jumpState = true;
			dinoJumpThread();
		}
	};

	/**
	 * 恐龍蹲Event
	 */
	Action dinoBowEvent = new AbstractAction() {
		/* 表明不同版本間的兼容性 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			/* 正在跳就不讓你蹲 & 蹲過了還想蹲 & 遊戲根本還沒開始AARRR */
			if (jumpState || bowState || endState) {
				return;
			}
			runState = false;
			bowState = true;
			dinoBowThread();
		}
	};

	/**
	 * 恐龍蹲完Event
	 */
	Action dinoBowReleaseEvent = new AbstractAction() {
		/* 表明不同版本間的兼容性 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (jumpState || endState) {
				return;
			}
			runState = true;
			bowState = false;
			dinoRunThread();
		}
	};

/* ------------------------------------------------------------------- */
	/**
	 * 恐龍跑 Thread
	 */
	private void dinoRunThread() {
		Thread runThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 確保位置正確 */
				dinoLabel.setBounds(56, 163, 50, 51);
				/* Run */
				while (runState) {
					dinoRun();
				}
				/* 恐龍撞牆的姿勢 */
				if (endState) {
					dinoLabel.setIcon(new ImageIcon(new ImageIcon("img//dinocrash.png").getImage().getScaledInstance(50,
							50, Image.SCALE_DEFAULT)));
					gameOver.setVisible(true);
				}
				/* 脫離跑步狀態就 Return */
				if (!runState) {
					return;
				}

			}

		});

		runThread.start();
	}

	/**
	 * 整合GameStart 地板跑&鳥飛行 Thread
	 */
	private void gameStartThread() {
		Thread landRunThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* Prevent Land Accelerate */
				if (!landState)
					landState = true;
				else
					return;
				
				cloud.setBounds(40, 43, 80, 34);
				while (!endState) {
					landRun();
					/* 補充地板 */
					if (toolbox.needToAddLand(landLabel1.getLocation().x)) {
						addLand(landLabel1);
					} else if (toolbox.needToAddLand(landLabel2.getLocation().x)) {
						addLand(landLabel2);
					}
					/* 補充雲 */
					if(toolbox.needToAddCloud(cloud.getLocation().x)) {
						addCloud();
					}
				}
				/* Return */
				if (endState) {
					return;
				}
			}

		});
		
		Thread flyThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 確保位置正確 */
				crowLabel.setBounds(550, 155, 36, 17);
				/* Run */
				while (!endState) {
					crowFly();
				}
				/* 脫離跑步狀態就 Return */
				if (endState) {
					return;
				}

			}

		});

		flyThread.start();
		landRunThread.start();
	}

	/**
	 * 恐龍蹲下 Thread
	 */
	private void dinoBowThread() {
		Thread bowThread = new Thread(new Runnable() {
			@Override
			public void run() {
				if (!bowState || jumpState) {
					return;
				}
				dinoLabel.setBounds(50, 185, 50, 30);
				while (bowState && !endState) {
					dinoBow();
				}
				if (endState) {
					dinoLabel.setBounds(56, 163, 50, 51);
					dinoLabel.setIcon(new ImageIcon(new ImageIcon("img//dinocrash.png").getImage().getScaledInstance(50,
							50, Image.SCALE_DEFAULT)));
					gameOver.setVisible(true);
				}
				return;
			}

		});
		bowThread.start();
	}

	/**
	 * 恐龍跳躍 Thread
	 */
	private void dinoJumpThread() {
		Thread jumpThread = new Thread(new Runnable() {
			@Override
			public void run() {
				dinoJump();
			}
		});
		jumpThread.start();
	}

	/**
	 * 加仙人掌&烏鴉 Thread
	 */
	private void addBarrierThread() {
		Thread cactusThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/* 防止一大堆仙人掌 */
					if (landState)
						return;
					cactusLabel.setBounds(450, 183, 18, 34);
					/* 只要還沒結束 */
					while (!endState) {
						Thread.sleep(1527);
						addCactus();
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		});
		
		Thread crowThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/* 防止一大堆烏鴉*/
					if (landState)
						return;
					cactusLabel.setBounds(450, 183, 18, 34);
					/* 只要還沒結束 */
					while (!endState) {
						Thread.sleep(2260);
						addCrow();
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		});
		

		Thread monitorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(0); // I Don't know why
						if (endState) {
							System.out.println("Break(Cactus & crow)");
							cactusThread.interrupt();
							crowThread.interrupt();
							return;
						}
					}
				} catch (InterruptedException e) {
					return;
				}

			}
		});

		cactusThread.start();
		crowThread.start();
		monitorThread.start();
	}

	/**
	 * 偵測撞到Thread
	 */
	private void detectThread() {

		Thread detectCactusThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 只要還沒結束 */
				while (!endState) {
					if (toolbox.cactusCrashDetect(dinoLabel, cactusLabel)) {
						endRun();
					}
				}
				return;
			}
		});
		
		Thread detectCrowThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 只要還沒結束 */
				while (!endState) {
					if (toolbox.crowCrashDetect(dinoLabel, crowLabel)) {
						endRun();
					}
				}
				return;
			}
		});
		
		detectCactusThread.start();
		detectCrowThread.start();
	}

	/**
	 * 分數 Thread
	 */
	private void setTimerThread() {
		
		Thread timerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 只要還沒結束 */
				long start = System.currentTimeMillis();
				long time;
		        while (!endState) {
		            time = System.currentTimeMillis() - start;
		            float seconds = (float) Math.round((float) time / 10) / 100;
		            timeCountLabel.setText(seconds + "");
		            try { Thread.sleep(97); } catch(Exception e) {}
		        }
		        if(endState) {
		        	return;
		        }
			}
		});
		
		timerThread.start();
	}
	
/* ------------------------------------------------------------------- */

	/**
	 * 恐龍跑步(含計時)
	 */
	private void dinoRun() {
		try {
			if (!runState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinorun1.png").getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
			if (!runState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinorun2.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 恐龍蹲下(含計時)
	 */
	private void dinoBow() {
		try {
			if (!bowState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinodown1.png").getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
			if (!bowState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinodown2.png").getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 恐龍跳躍
	 */
	private void dinoJump() {
		try {
			for (int t = 0; t <= 40; t++) {
				dinoLabel.setLocation(dinoLabel.getLocation().x, dinoLabel.getLocation().y - 2);
				if (endState)
					return;
				Thread.sleep(5);
			}
			for (int t = 0; t <= 40; t++) {
				dinoLabel.setLocation(dinoLabel.getLocation().x, dinoLabel.getLocation().y + 2);
				if (endState)
					return;
				Thread.sleep(5);
			}

			jumpState = false;

		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 地板&仙人掌移動&烏鴉移動&雲移動(含計時)
	 */
	private void landRun() {
		try {
			landLabel1.setLocation(landLabel1.getLocation().x - 4, landLabel1.getLocation().y);
			landLabel2.setLocation(landLabel2.getLocation().x - 4, landLabel1.getLocation().y);
			cactusLabel.setLocation(cactusLabel.getLocation().x - 4, cactusLabel.getLocation().y);
			crowLabel.setLocation(crowLabel.getLocation().x - 8, crowLabel.getLocation().y);
			cloud.setLocation(cloud.getLocation().x - 1, cloud.getLocation().y);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	/**
	 * 烏鴉飛
	 */
	private void crowFly() {
		try {
			if (endState) {
				return;
			}
			crowLabel.setIcon(new ImageIcon(
					new ImageIcon("img//crow1.png").getImage().getScaledInstance(33, 24, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
			if (endState) {
				return;
			}
			crowLabel.setIcon(new ImageIcon(
					new ImageIcon("img//crow2.png").getImage().getScaledInstance(33, 24, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return;
		}
	}

/* ------------------------------------------------------------------- */
	
	/**
	 * 加地板(搬地板到最前面補充地板)
	 */
	private void addLand(JLabel label) {
		label.setLocation(436, label.getLocation().y);
	}
	
	/**
	 * 加仙人掌(拿到最前面)
	 */
	private void addCactus() {
		if (endState) {
			return;
		} else if (toolbox.addCactusOrNot()) {
			System.out.println("ADD CACTUS");
			cactusLabel.setBounds(450, 183, 18, 34);
		}
	}

	/**
	 * 加烏鴉(拿到最前面)
	 */
	private void addCrow() {
		if (endState) {
			return;
		} else if (toolbox.addCrowOrNot()) {
			System.out.println("ADD CROW");
			crowLabel.setBounds(550, 155, 36, 17);
		}
	}
	
	/**
	 * 加雲(拿到最前面)
	 */
	private void addCloud() {
		if (endState) {
			return;
		} else {
			cloud.setBounds(450, 43, 80, 34);
		}
	}

/* ------------------------------------------------------------------- */
	
	/**
	 * 遊戲結束
	 */
	private void endRun() {
		endState = true;
		runState = false;
		landState = false;
	}
}
