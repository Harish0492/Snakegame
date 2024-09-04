import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JFrame {
	private int LEVEL_1_DIM = 40;
	private int LEVEL_2_DIM = 30;
	private int LEVEL_3_DIM = 20;
	private JLayeredPane gamePanel;
    private Mode mode;
    private Timer timer;
	private int score;
	private int gameState;
	private int level = 1;
	private String MODE = "Easy";
	private JLabel scoreLable;
	private JButton playPauseButton;
	private JButton resetButton;
	private JMenu menu;
	private JMenuBar modeMenu;
	private JLabel levelLabel;
	private JButton plusButton;
	private JButton minusButton;
    
    public Game(){
		// setting inital game state and mode
		gameState = 0;
        mode = new IntermediateMode(getDIM());
		mode.setBounds(0, 0, (mode.width+mode.borderWidth+mode.settingWidth)*mode.BOXSIZE, (mode.height+mode.borderHeight)*mode.BOXSIZE);
		mode.addKeyListener (new GameController (mode.snake));

		// creating score lable
		score = 0;
		scoreLable = new JLabel("Score : " + score);
		scoreLable.setFont(new Font("Arial", Font.BOLD, 20));
		scoreLable.setBounds((mode.width+mode.borderWidth+mode.settingWidth/3) * 20, mode.height / 6 * 20, 200, 50);
		
		// creating playpause button
		playPauseButton = new JButton("▶");
		playPauseButton.setFont(new Font("Arial", Font.BOLD, 20));
		playPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				
				if(gameState == 0){
					timer.start();
					mode.requestFocus(true);
					gameState = 1;
					playPauseButton.setText("I I");
					resetButton.setVisible(false);
					playPauseButton.setBounds((mode.width+mode.borderWidth+5) * 20, (mode.height / 6 + 4) * 20, 50, 50);
					menu.setEnabled(false);
					plusButton.setEnabled(false);
					minusButton.setEnabled(false);
				} else if(gameState == 1){
					timer.stop();
					gameState = 0;
					playPauseButton.setText("▶");
					enableResetButton();
				} else if(gameState == -2){
					timer.restart();
					playPauseButton.setText("I I");
					mode.requestFocus(true);
					gameState = 1;
					menu.setEnabled(false);
					plusButton.setEnabled(false);
					minusButton.setEnabled(false);
				}
			}	
		});
		playPauseButton.setBounds((mode.width+mode.borderWidth+5) * 20, (mode.height / 6 + 4) * 20, 50, 50);

		resetButton = new JButton("↺");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				timer.stop();
				resetMode();
				gameState = -2;
				playPauseButton.setText("▶");
				resetButton.setVisible(false);
				playPauseButton.setEnabled(true);
				playPauseButton.setBounds((mode.width+mode.borderWidth+5) * 20, (mode.height / 6 + 4) * 20, 50, 50);
				menu.setEnabled(false);
				menu.setEnabled(true);
				plusButton.setEnabled(true);
				minusButton.setEnabled(true);
				
			}
		});
		resetButton.setFont(new Font("Arial", Font.BOLD, 20));
		resetButton.setVisible(false);
		// creating mode menu
		modeMenu = new JMenuBar();

		// Create a menu
		menu = new JMenu("Mode : Easy");

		// Create menu items
		JMenuItem classicItem = new JMenuItem("Classic");
		JMenuItem easyItem = new JMenuItem("Easy");
		JMenuItem mediumItem = new JMenuItem("Medium");
		JMenuItem hardItem = new JMenuItem("Hard");
		// JMenuItem exitItem = new JMenuItem("Extra Hard");

		// Add menu items to the menu
		menu.add(classicItem);
		menu.addSeparator(); 
		menu.add(easyItem);// Add a separator line
		menu.add(mediumItem);
		menu.add(hardItem);

		// Add the menu to the menu bar
		modeMenu.setBounds((mode.width+mode.borderWidth+3) * 20+5, (mode.height / 6 + 8) * 20, 120, 50);
		modeMenu.add(menu);

		ActionListener menuItemListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem source = (JMenuItem) e.getSource();
				menu.setText("Mode : " + source.getText());
				MODE = source.getText();

				updateUI();
				
			}
		};

		// Add action listeners to menu items
		classicItem.addActionListener(menuItemListener);
		easyItem.addActionListener(menuItemListener);
		mediumItem.addActionListener(menuItemListener);
		hardItem.addActionListener(menuItemListener);

		// adding level buttons and label
		levelLabel = new JLabel("Level " + level);
		plusButton = new JButton("+");
		plusButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e){
				level = Math.min(level + 1, 3);
				levelLabel.setText("Level " + level);
				updateUI();
			}
			
		});

		minusButton = new JButton("-");
		minusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				level = Math.max(1, level-1);
				levelLabel.setText("Level " + level);
				updateUI();
			}
			
		});
		
		plusButton.setBounds((mode.width+mode.borderWidth+8) * 20+2, (mode.height / 6 + 12) * 20, 50, 50);
		levelLabel.setBounds((mode.width+mode.borderWidth+5) * 20+2, (mode.height / 6 + 12) * 20, 120, 50);
		minusButton.setBounds((mode.width+mode.borderWidth+2) * 20+2, (mode.height / 6 + 12) * 20, 50, 50);

		// setting up game timer
		timer = new Timer (500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				mode.snake.move(mode.hasBorder);
				if(!mode.validateMove(mode.snake.getPos())){
					timer.stop();
					enableResetButton();
					playPauseButton.setEnabled(false);
					menu.setEnabled(false);
					plusButton.setEnabled(false);
					minusButton.setEnabled(false);
				}

				if(mode.ate()){
					mode.snake.addSegment();
					int oldDelay = timer.getDelay();
					int newDealy = (int)(oldDelay - ((int)Math.ceil((float)oldDelay / 100) * 100) * 0.01);
					newDealy = Math.max(100, newDealy);
					timer.setDelay(newDealy);
					if(mode.food.getCount() % 5 == 0)
						score += 2;
					score++;
				

					mode.food.updateFood(mode.getRandomPos());
					
					
					scoreLable.setText("Score : " + score);
				}

				mode.addSquares();	
				mode.updateSquares();
				
				mode.repaint();
				
			}
		});

		// adding the UI elements to gamepanel
		mode.setVisible(true);
		gamePanel = new JLayeredPane();
		gamePanel.setPreferredSize(new Dimension((mode.getWid()+mode.settingWidth+1)*mode.BOXSIZE, (mode.getHig() + 2)*mode.BOXSIZE));
		gamePanel.add(mode, JLayeredPane.DEFAULT_LAYER);
		gamePanel.add(scoreLable, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(playPauseButton, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(resetButton, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(modeMenu, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(plusButton, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(levelLabel, JLayeredPane.PALETTE_LAYER);
		gamePanel.add(minusButton, JLayeredPane.PALETTE_LAYER);
		
		// setting game settings
		add(gamePanel);
        setTitle("Snake");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension((mode.getWid()+mode.settingWidth+2)*mode.BOXSIZE, (mode.getHig() + 2)*mode.BOXSIZE));
		pack();
		setVisible (true);
    }

	// 
	private void enableResetButton(){
		resetButton.setVisible(true);
		playPauseButton.setBounds((mode.width+mode.borderWidth+3) * 20, (mode.height / 6 + 4) * 20, 50, 50);
		resetButton.setBounds((mode.width+mode.borderWidth+7) * 20, (mode.height / 6 + 4) * 20, 50, 50);
				
	}

	// returns game dimentions based on the level
	private int getDIM(){
		if(level == 1) return LEVEL_1_DIM;
		if(level == 2) return LEVEL_2_DIM;
		return LEVEL_3_DIM;
	}

	// resets mode and repaints UI elements
	private void resetMode(){
        mode.reset();
		mode.addKeyListener (new GameController (mode.snake));
		mode.repaint();
		score = 0;
		mode.addSquares();	
		mode.updateSquares();

		scoreLable.setText("Score : 0");
	}

	// updates UI , when level changed or mode changed	
	private void updateUI(){
		if(MODE.equals("Classic")){
			mode.setVisible(false);
			mode = new EasyMode(getDIM());
		} else if(MODE.equals("Easy")){
			mode.setVisible(false);
			mode = new IntermediateMode(getDIM());
		} else if(MODE.equals("Medium")){
			mode.setVisible(false);
			mode = new HardMode(getDIM());
		} else if(MODE.equals("Hard")){
			mode.setVisible(false);
			mode = new SuperHardMode(getDIM());
		}

		mode.setBounds(0, 0, (mode.width+mode.borderWidth+mode.settingWidth)*mode.BOXSIZE, (mode.height+mode.borderHeight)*mode.BOXSIZE);
		scoreLable.setBounds((mode.width+mode.borderWidth+mode.settingWidth/3) * 20, mode.height / 6 * 20, 200, 50);
		playPauseButton.setBounds((mode.width+mode.borderWidth+5) * 20, (mode.height / 6 + 4) * 20, 50, 50);
		modeMenu.setBounds((mode.width+mode.borderWidth+3) * 20+5, (mode.height / 6 + 8) * 20, 120, 50);
	
		mode.addKeyListener (new GameController (mode.snake));
		gamePanel.add(mode, JLayeredPane.DEFAULT_LAYER);
		mode.setVisible(true);

		plusButton.setBounds((mode.width+mode.borderWidth+8) * 20+2, (mode.height / 6 + 12) * 20, 50, 50);
		levelLabel.setBounds((mode.width+mode.borderWidth+5) * 20+2, (mode.height / 6 + 12) * 20, 120, 50);
		minusButton.setBounds((mode.width+mode.borderWidth+2) * 20+2, (mode.height / 6 + 12) * 20, 50, 50);

	}
	
    private class GameController extends KeyAdapter
	{
		private Snake snake;

		public GameController (Snake snake) {
			this.snake = snake;
		}

		public void keyPressed (KeyEvent e)
		{

			switch (e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				snake.turn(4);
				break;
			case KeyEvent.VK_RIGHT:
				snake.turn(2);
				break;
			case KeyEvent.VK_UP:
				snake.turn(1);
				break;
			case KeyEvent.VK_DOWN:
				snake.turn(3);
				break;
			}
		}
	}


}
