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
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class app {

	private JFrame frame;
	private JLabel dinoLabel;
	private JButton btnEnd;
	private JLabel landLabel1;
	private JLabel landLabel2;
	private boolean runState = true;
	private boolean bowState = false;
	private boolean endState = false;
	private boolean jumpState = false;

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
		frame.setTitle("dinodino");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		dinoLabel.setBounds(56, 147, 80, 81);
		dinoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dinoLabel.setIcon(new ImageIcon(
				new ImageIcon("img//dino1.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		frame.getContentPane().add(dinoLabel);
		
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
		 * Start按鈕
		 */
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startRun();
			}
		});
		btnStart.setBounds(81, 40, 85, 23);
		btnStart.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		frame.getContentPane().add(btnStart);
		
		
		/**
		 * End按鈕
		 */
		btnEnd = new JButton("End");
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				endRun();
			}
		});
		btnEnd.setBounds(284, 40, 85, 23);
		btnEnd.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		frame.getContentPane().add(btnEnd);

	}

	/**
	 *  恐龍跑 Thread
	*/
	private void dinoRunThread(){
		Thread runThread = new Thread(new Runnable() {
			@Override
			public void run() {
				/* 確保位置正確 */
				dinoLabel.setBounds(56, 147, 80, 81);
				/* Run */
				while (runState) {
					dinoRun();
				}
				/* 恐龍撞牆的姿勢 */
				if (endState) {
					dinoLabel.setIcon(new ImageIcon(new ImageIcon("img//dinocrash.png").getImage().getScaledInstance(50,
							50, Image.SCALE_DEFAULT)));
				}
				/* 脫離跑步狀態就 Return */
				if(!runState) {
					return;
				}

			}

		});
		
		runThread.start();
	}


	/**
	 *  地板跑  Thread
	*/	
	private void landRunThread() {
		Thread landThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!endState) {
					landRun();
					/* 補充地板 */
					if (toolbox.needToAddLand(landLabel1.getLocation().x)) {
						setLandPosition(landLabel1);
					} else if (toolbox.needToAddLand(landLabel2.getLocation().x)) {
						setLandPosition(landLabel2);
					}
				}
				/* Return */
				if(endState) {
					return;
				}
			}

		});
		landThread.start();
	}
	
	
	/**
	 *  恐龍蹲下  Thread
	*/
	private void dinoBowThread() {
		Thread bowThread = new Thread(new Runnable() {
			@Override
			public void run() {
				if(!bowState) {
					return;
				}
				dinoLabel.setBounds(50, 155, 80, 81);
				while(bowState) {
					dinoBow();
				}
			}

		});
		bowThread.start();
	}

	
	/**
	 *  恐龍跳躍  Thread
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
	 * 開跑
	 */
	private void startRun() {
		
		runState = true;
		endState = false;
		
		dinoRunThread();
		landRunThread();

	}

	
	/**
	 * 恐龍跳Event
	 */
	Action dinoJumpEvent = new AbstractAction() {
		/* 表明不同版本間的兼容性 */
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			/* 上一次還沒完成 => Return */
			if(jumpState) {
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
			/* 正在跳就不讓你蹲 */
			if(jumpState) {
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
			if(jumpState) {
				return;
			}
			runState = true;
			bowState = false;
			dinoRunThread();
	    }
	};
	
	
	
	/**
	 * 恐龍跑步(含計時)
	 */
	private void dinoRun() {
		try {
			if(!runState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinorun1.png").getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
			if(!runState) {
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
			if(!bowState) {
				return;
			}
			dinoLabel.setIcon(new ImageIcon(
					new ImageIcon("img//dinodown1.png").getImage().getScaledInstance(50, 35, Image.SCALE_DEFAULT)));
			Thread.sleep(100);
			if(!bowState) {
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
			for (int t = 0; t <= 65; t++) {
				dinoLabel.setLocation(dinoLabel.getLocation().x, dinoLabel.getLocation().y - 1);
				Thread.sleep(5);
			}
			for (int t = 0; t <= 65; t++) {
				dinoLabel.setLocation(dinoLabel.getLocation().x, dinoLabel.getLocation().y + 1);
				Thread.sleep(5);
			}

			jumpState = false;
			
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 地板移動(含計時)
	 */
	private void landRun() {
		try {
			landLabel1.setLocation(landLabel1.getLocation().x - 2, landLabel1.getLocation().y);
			landLabel2.setLocation(landLabel2.getLocation().x - 2, landLabel1.getLocation().y);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 搬地板到最前面(補充地板)
	 */
	private void setLandPosition(JLabel label) {
		label.setLocation(436, label.getLocation().y);
	}
	
	/**
	 * 遊戲結束
	 */
	private void endRun() {
		endState = true;
		runState = false;
	}
}
