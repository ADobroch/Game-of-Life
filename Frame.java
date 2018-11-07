import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
	private Screen s;
	private Simulation sim;
	private float tslu;
	private float PAUSETIMER = 0.01f;


	private JButton startButton = new JButton();
	private JButton pauseButton = new JButton();
	private JButton mooreButton = new JButton();
	private JButton neumannButton = new JButton();
	private JButton HexLeftButton = new JButton();
	private JButton HexRighteButton = new JButton();
	private JButton PentagonalButton = new JButton();
	private JButton HexRandomButton = new JButton();
	private JButton resetButton = new JButton();
	private JButton equalyButton = new JButton();
	private JButton randomButton = new JButton();
	private JButton gridButton = new JButton();
	private JButton sizeButton = new JButton();
	private JButton speedButton = new JButton();
	private JButton monteButton = new JButton();





	static JTextField countField = new JTextField();
	JLabel enterCount = new JLabel();
	 static String   count;

	static JTextField sizeField = new JTextField();

	static int  cellSize;



	public Frame()
	{
		setExtendedState(MAXIMIZED_BOTH);
	}
	public void createScreen() {
		count="10";
		sim = new Simulation();

		addKeyListener(sim);
		addMouseListener(sim);
		addMouseMotionListener(sim);

		s = new Screen();
		s.setBounds(0, 0, Main.width, Main.height);

		monteButton.setText("MonteCarlo");
		monteButton.setBounds(1810,750,80,25);
		monteButton.addActionListener(this);



		resetButton.setText("Reset");
		resetButton.setBounds(1810,500,80,25);
		resetButton.addActionListener(this);

		gridButton.setText("Grid");
		gridButton.setBounds(1650,900,80,25);
		gridButton.addActionListener(this);

		speedButton.setText("Speed");
		speedButton.setBounds(1720,900,80,25);
		speedButton.addActionListener(this);

		sizeButton.setText("Size");
		sizeButton.setBounds(1660,600,80,25);
		sizeButton.addActionListener(this);


		equalyButton.setText("Równomiernie");
		equalyButton.setBounds(1650,400,80,25);
		equalyButton.addActionListener(this);

		randomButton.setText("Losowe");
		randomButton.setBounds(1730,400,80,25);
		randomButton.addActionListener(this);


		startButton.setText("Start");
		startButton.setBounds(1650,500,80,25);
		startButton.addActionListener(this);

		pauseButton.setText("Pause");
		pauseButton.setBounds(1730,500,80,25);
		pauseButton.addActionListener(this);


		mooreButton.setText("Moore");
		mooreButton.setBounds(1700,20,130,45);
		mooreButton.addActionListener(this);

		neumannButton.setText("Neumann");
		neumannButton.setBounds(1700,70,130,45);
		neumannButton.addActionListener(this);

		HexLeftButton.setText("Hex Left");
		HexLeftButton.setBounds(1700,120,130,45);
		HexLeftButton.addActionListener(this);

		HexRighteButton.setText("Hex Right");
		HexRighteButton.setBounds(1700,170,130,45);
		HexRighteButton.addActionListener(this);

		HexRandomButton.setText("Hex Random");
		HexRandomButton.setBounds(1700,270,130,45);
		HexRandomButton.addActionListener(this);


		PentagonalButton.setText("Pentagonal");
		PentagonalButton.setBounds(1700,220,130,45);
		PentagonalButton.addActionListener(this);

		enterCount.setText("Ilosc zarodkow:");
		enterCount.setBounds(1650,340,130,45);

		//enterCount.setSize(20,20);

		countField.setBounds(1750,348,130,30);
		countField.setText("10");

		sizeField.setBounds(1750,600,110,30);
		sizeField.setText("10");



		add(monteButton);
		add(sizeField);
		add(sizeButton);
		//add(speedButton);
		add(gridButton);
		add(randomButton);
		add(equalyButton);
		add(HexRandomButton);
		add(resetButton);
		add(startButton);
		add(pauseButton);
		add(enterCount);
		add(countField);
		add(PentagonalButton);
		add(HexLeftButton);
		add(HexRighteButton);
		add(neumannButton);
		add(mooreButton);
		add(s);
	}
	public void repaint()
	{
	s.repaint();
	}
	public void update(float tslf)
	{
		tslu += tslf;
		if(tslu>PAUSETIMER) {
			sim.update();
			tslu = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent h) {
		//	if(e.getSource()==countField){
		//		Simulation.countS = countField.getText();
		//	}
		if(h.getSource()==mooreButton)
		{
			Simulation.algorithmCell = "moore";

		}
		if(h.getSource()==neumannButton)
		{
			Simulation.algorithmCell = "neumann";

		}
		if(h.getSource()==HexRighteButton)
		{
			Simulation.algorithmCell = "HexRight";
		}
		if(h.getSource()==HexLeftButton)
		{
			Simulation.algorithmCell = "HexLeft";
		}
		if(h.getSource()==HexRandomButton)
		{
			Simulation.algorithmCell = "HexRandom";
		}
		if(h.getSource()==PentagonalButton)
		{
			Simulation.algorithmCell = "Pentagonal";
		}
		if(h.getSource()==startButton)
		{
			Simulation.start = true;
		}
		if(h.getSource()==pauseButton)
		{
			Simulation.start = false;
		}
		if(h.getSource()==monteButton)
		{
			Simulation.algorithmCell = "MonteCarlo";
			Simulation.algorithmSeed = "Monte";
			//sim = new Simulation();/////////////
		}
		if(h.getSource()==resetButton){
			Simulation.countS = countField.getText();
			sim = new Simulation();

		}

		if(h.getSource()==randomButton)
		{
			Simulation.algorithmSeed = "rand";
			sim = new Simulation();
		}
		if(h.getSource()==equalyButton)
		{
			Simulation.algorithmSeed = "equal";
			sim = new Simulation();
		}

		if(h.getSource()==sizeButton)
		{
			cellSize = Integer.parseInt(sizeField.getText());
			Cell.size = cellSize;
			sim = new Simulation();
		}

		if(h.getSource()==gridButton)
		{
			if (Cell.grid)
			Cell.grid= false;
			else
				Cell.grid = true;
		}
	}


	public static String getCount()
	{
		return countField.getText();
	}
	public class Screen extends JLabel {
		@Override
		protected void paintComponent(Graphics g) {
		 super.paintComponent(g);

		 sim.draw(g);
	}
	}

}
