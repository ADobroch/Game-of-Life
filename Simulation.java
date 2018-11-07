import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Simulation implements KeyListener ,MouseListener, MouseMotionListener{


	public class Energy{
		int energyValue;
		float r,g,b;
		Energy(){
			energyValue=0;
			r=0;
			g=0;
			b=0;
		}
	}
	private Cell[] [] cells;
	private int width;
	private int height;
	private int genaration=0;
	private int button;
	private Random random = new Random();
	public static boolean start = false;


	private Color cellColor;
	public static String algorithmCell="moore";
	public static String algorithmSeed="Monte";
	public static String countS = Frame.count;
	public static int n=10; //= Integer.parseInt(Frame.countField.getText());


	public Simulation() {
		width = Main.simwidth / Cell.size;
		height = Main.simheight / Cell.size;
		cells = new Cell[width][height];


		n =Integer.parseInt(countS);
		//algorithmSeed="equal";
		Energy[] energia = new Energy[n];

		for(int i =0;i<n;i++)
			energia[i]= new Energy();



		switch (algorithmSeed) {

			case "rand":
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					cells[x][y] = new Cell(x, y);
					//cells[x][y].setAlive(random.nextBoolean());


				}
			}

				int x;
				int y;
				for (int i =0;i<n;i++) {
					 x = random.nextInt(width);
					 y = random.nextInt(height);

					if(!cells[x][y].isAlive()){
						cells[x][y].setAlive(true);

					}
					else
						i--;

				}
			break;
			case "equal":

				for (int w = 0; w < width; w++) {
					for (int z = 0; z < height; z++) {
						cells[w][z] = new Cell(w, z);
						//cells[x][y].setAlive(random.nextBoolean());


					}
				}

				int square = (int) Math.sqrt(n);

				double xChunk = width/square;
				double yChunk = height/square;
				int nCounter = 0;

				for (double i =xChunk/2;i<width;i+=xChunk)
					for (double j=yChunk/2;j<height;j+=yChunk)
					{

						cells[(int)i][(int)j].setAlive(true);


					}

				break;

			case "Monte":

				for (int g=0;g<n;g++) {
					energia[g].energyValue=g;
					energia[g].r= random.nextFloat();
					energia[g].g= random.nextFloat();
					energia[g].b= random.nextFloat();



				}


				Random rand = new Random();
				float rc=0,gc=0,bc=0;
				int energia1=0;
				for (int w = 0; w < width; w++) {
					for (int z = 0; z < height; z++) {

						for (int q=0;q<n;q++)
						if (rand.nextInt(n)==energia[q].energyValue){
							rc=energia[q].r;
							bc=energia[q].b;
							gc=energia[q].g;
							energia1=energia[q].energyValue;
						}

						cells[w][z] = new Cell(w, z,rc,bc,gc,energia1);
						cells[w][z].setAlive(true);


					}
				}




				break;



		}
	}

	public void update() {
		if (start) {
			genaration++;


			switch (algorithmCell) {

				case "MonteCarlo":
					{
					if (genaration > 1) {
						for (int x = 0; x < width; x++)
							for (int y = 0; y < height; y++) {
								cells[x][y].nextEnergy();

							}
					}


					for (int i = 0; i < width; i++) {
						for (int j = 0; j < height; j++) {


							int mx = i - 1;
							if (mx < 0) mx = width - 1;
							int my = j - 1;
							if (my < 0) my = height - 1;
							int gx = (i + 1) % width;
							int gy = (j + 1) % height;


							int tab[] = new int[8];
							for (int k = 0; k < 8; k++)
								tab[k] = 0;

							int cellEnergy = 0;
							int energia = cells[i][j].energy;
							int energiaTemp = energia;

							tab[0] = cells[mx][my].energy;
							tab[1] = cells[i][my].energy;
							tab[2] = cells[gx][my].energy;
							tab[3] = cells[mx][j].energy;
							tab[4] = cells[gx][j].energy;
							tab[5] = cells[mx][gy].energy;
							tab[6] = cells[i][gy].energy;
							tab[7] = cells[gx][gy].energy;


							Color tempColor = new Color(0, 0, 0);
							int tempNumber = 0;
							for (int y = 0; y < 8; y++) {
								if (tab[y] != energiaTemp)
									cellEnergy++;
							}
							int tempcellEnergy = cellEnergy;

							for (int w = 0; w < 8; w++) {
								cellEnergy = 0;
								energiaTemp = tab[w];

								for (int y = 0; y < 8; y++) {
									if (tab[y] != energiaTemp)
										cellEnergy++;
								}
								if (cellEnergy < tempcellEnergy) {
									tempcellEnergy = cellEnergy;
									tempNumber = w + 10;

								}

							}

							if (tempNumber == 10) {
								tempColor = cells[mx][my].randomColor;
								energia = cells[mx][my].energy;
							} else if (tempNumber == 11) {
								tempColor = cells[i][my].randomColor;
								energia = cells[i][my].energy;
							} else if (tempNumber == 12) {
								tempColor = cells[gx][my].randomColor;
								energia = cells[gx][my].energy;
							} else if (tempNumber == 13) {
								tempColor = cells[mx][j].randomColor;
								energia = cells[mx][j].energy;
							} else if (tempNumber == 14) {
								tempColor = cells[gx][j].randomColor;
								energia = cells[gx][j].energy;
							} else if (tempNumber == 15) {
								tempColor = cells[mx][gy].randomColor;
								energia = cells[mx][gy].energy;
							} else if (tempNumber == 16) {
								tempColor = cells[i][gy].randomColor;
								energia = cells[i][gy].energy;
							} else if (tempNumber == 17) {
								tempColor = cells[gx][gy].randomColor;
								energia = cells[gx][gy].energy;

							}else if(tempNumber==0) {
								tempColor = cells[i][j].randomColor;
								energia = cells[i][j].energy;
							}else
							{
								energia = cells[i][j].energy;
								tempColor = cells[i][j].randomColor;
							}

							cells[i][j].setNextEnergy(energia);
							cells[i][j].setNextRound(true, tempColor);
							//cells[i][j].nextRound();


						}
					}
				}
					break;
				case "moore":

					if (genaration > 1) {
						for (int x = 0; x < width; x++)
							for (int y = 0; y < height; y++) {
								cells[x][y].nextRound();

							}
					}
					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							cellColor = cells[x][y].randomColor;
							int mx = x - 1;
							if (mx < 0) mx = width - 1;
							int my = y - 1;
							if (my < 0) my = height - 1;
							int gx = (x + 1) % width;
							int gy = (y + 1) % height;


							if (cells[x][y].isAlive()) {


								cells[x][y].setNextRound(true, cellColor);


								if (!cells[mx][my].isNextRound() && !cells[mx][my].isAlive())
									cells[mx][my].setNextRound(true, cellColor);

								if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
									cells[mx][y].setNextRound(true, cellColor);

								if (!cells[mx][gy].isNextRound() && !cells[mx][gy].isAlive())
									cells[mx][gy].setNextRound(true, cellColor);

								if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
									cells[x][my].setNextRound(true, cellColor);

								if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
									cells[x][gy].setNextRound(true, cellColor);

								if (!cells[gx][my].isNextRound() && !cells[gx][my].isAlive())
									cells[gx][my].setNextRound(true, cellColor);

								if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
									cells[gx][y].setNextRound(true, cellColor);

								if (!cells[gx][gy].isNextRound() && !cells[gx][gy].isAlive())
									cells[gx][gy].setNextRound(true, cellColor);


							}


						}
					}
					break;

				case "neumann":

					if (genaration > 1) {
						for (int x = 0; x < width; x++)
							for (int y = 0; y < height; y++) {
								cells[x][y].nextRound();
							}

						}

						for (int x = 0; x < width; x++) {
							for (int y = 0; y < height; y++) {
								cellColor = cells[x][y].randomColor;
								int mx = x - 1;
								if (mx < 0) mx = width - 1;
								int my = y - 1;
								if (my < 0) my = height - 1;
								int gx = (x + 1) % width;
								int gy = (y + 1) % height;

								if (cells[x][y].isAlive()) {


									cells[x][y].setNextRound(true, cellColor);




									if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
										cells[mx][y].setNextRound(true, cellColor);


									if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
										cells[x][my].setNextRound(true, cellColor);

									if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
										cells[x][gy].setNextRound(true, cellColor);


									if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
										cells[gx][y].setNextRound(true, cellColor);


								}

							}

						}
					break;

				case "HexLeft":

					if (genaration > 1) {
						for (int x = 0; x < width; x++)
							for (int y = 0; y < height; y++) {
								cells[x][y].nextRound();
							}

					}

					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							cellColor = cells[x][y].randomColor;
							int mx = x - 1;
							if (mx < 0) mx = width - 1;
							int my = y - 1;
							if (my < 0) my = height - 1;
							int gx = (x + 1) % width;
							int gy = (y + 1) % height;

							if (cells[x][y].isAlive()) {


								cells[x][y].setNextRound(true, cellColor);


								if (!cells[mx][my].isNextRound() && !cells[mx][my].isAlive())
									cells[mx][my].setNextRound(true, cellColor);

								if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
									cells[mx][y].setNextRound(true, cellColor);


								if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
									cells[x][my].setNextRound(true, cellColor);

								if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
									cells[x][gy].setNextRound(true, cellColor);


								if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
									cells[gx][y].setNextRound(true, cellColor);

								if (!cells[gx][gy].isNextRound() && !cells[gx][gy].isAlive())
									cells[gx][gy].setNextRound(true, cellColor);
							}

						}

					}
					break;

				case "HexRight":
					if (genaration > 1) {
						for (int x = 0; x < width; x++)
							for (int y = 0; y < height; y++) {
								cells[x][y].nextRound();
							}

					}

					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							cellColor = cells[x][y].randomColor;
							int mx = x - 1;
							if (mx < 0) mx = width - 1;
							int my = y - 1;
							if (my < 0) my = height - 1;
							int gx = (x + 1) % width;
							int gy = (y + 1) % height;

							if (cells[x][y].isAlive()) {


								cells[x][y].setNextRound(true, cellColor);


								int w;

								if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
									cells[mx][y].setNextRound(true, cellColor);


								if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
									cells[x][my].setNextRound(true, cellColor);

								if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
									cells[x][gy].setNextRound(true, cellColor);


								if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
									cells[gx][y].setNextRound(true, cellColor);

								if (!cells[mx][gy].isNextRound() && !cells[mx][gy].isAlive())
									cells[mx][gy].setNextRound(true, cellColor);

								if (!cells[gx][my].isNextRound() && !cells[gx][my].isAlive())
									cells[gx][my].setNextRound(true, cellColor);
							}

						}

					}
					break;

				case "HexRandom":

					Random rand = new Random();

					if(rand.nextBoolean()){
						if (genaration > 1) {
							for (int x = 0; x < width; x++)
								for (int y = 0; y < height; y++) {
									cells[x][y].nextRound();
								}

						}

						for (int x = 0; x < width; x++) {
							for (int y = 0; y < height; y++) {
								cellColor = cells[x][y].randomColor;
								int mx = x - 1;
								if (mx < 0) mx = width - 1;
								int my = y - 1;
								if (my < 0) my = height - 1;
								int gx = (x + 1) % width;
								int gy = (y + 1) % height;

								if (cells[x][y].isAlive()) {


									cells[x][y].setNextRound(true, cellColor);


									int w;

									if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
										cells[mx][y].setNextRound(true, cellColor);


									if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
										cells[x][my].setNextRound(true, cellColor);

									if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
										cells[x][gy].setNextRound(true, cellColor);


									if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
										cells[gx][y].setNextRound(true, cellColor);

									if (!cells[mx][gy].isNextRound() && !cells[mx][gy].isAlive())
										cells[mx][gy].setNextRound(true, cellColor);

									if (!cells[gx][my].isNextRound() && !cells[gx][my].isAlive())
										cells[gx][my].setNextRound(true, cellColor);
								}

							}

						}
					}else
					{
						if (genaration > 1) {
							for (int x = 0; x < width; x++)
								for (int y = 0; y < height; y++) {
									cells[x][y].nextRound();
								}

						}
							int z;
						for (int x = 0; x < width; x++) {
							for (int y = 0; y < height; y++) {
								cellColor = cells[x][y].randomColor;
								int mx = x - 1;
								if (mx < 0) mx = width - 1;
								int my = y - 1;
								if (my < 0) my = height - 1;
								int gx = (x + 1) % width;
								int gy = (y + 1) % height;

								if (cells[x][y].isAlive()) {


									cells[x][y].setNextRound(true, cellColor);


									if (!cells[mx][my].isNextRound() && !cells[mx][my].isAlive())
										cells[mx][my].setNextRound(true, cellColor);

									if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
										cells[mx][y].setNextRound(true, cellColor);


									if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
										cells[x][my].setNextRound(true, cellColor);

									if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
										cells[x][gy].setNextRound(true, cellColor);


									if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
										cells[gx][y].setNextRound(true, cellColor);

									if (!cells[gx][gy].isNextRound() && !cells[gx][gy].isAlive())
										cells[gx][gy].setNextRound(true, cellColor);
								}

							}

						}
					}
						break;
				case "Pentagonal":
						pentagonal();
					break;
					}
			}
		}




	public void draw(Graphics g) {
		//g.setColor(Color.BLACK);
		for(int x = 0;x < width;x++) {
			for (int y = 0; y < height; y++) {
				cells[x] [y].draw(g);
			}
		}
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.BOLD,25));
		g.drawString(""+genaration,50,50+g.getFont().getSize());
	}

	public void pentagonal() {


		Random randd = new Random();

		int x1=0;
		int randdd=0;
		if (genaration > 1) {
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++) {
					cells[x][y].nextRound();

				}
		}
		for (int x = 0; x < width; x++) {
			if(x1<(x-10)) {
				x1 = x;
				 randdd = randd.nextInt(4);
			}
				if (randdd == 1) {
					for (int y = 0; y < height; y++) {
						cellColor = cells[x][y].randomColor;
						int mx = x - 1;
						if (mx < 0) mx = width - 1;
						int my = y - 1;
						if (my < 0) my = height - 1;
						int gx = (x + 1) % width;
						int gy = (y + 1) % height;


						if (cells[x][y].isAlive()) {


							cells[x][y].setNextRound(true, cellColor);


							if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
								cells[mx][y].setNextRound(true, cellColor);

							if (!cells[mx][gy].isNextRound() && !cells[mx][gy].isAlive())
								cells[mx][gy].setNextRound(true, cellColor);


							if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
								cells[x][gy].setNextRound(true, cellColor);


							if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
								cells[gx][y].setNextRound(true, cellColor);

							if (!cells[gx][gy].isNextRound() && !cells[gx][gy].isAlive())
								cells[gx][gy].setNextRound(true, cellColor);

						}
					}


				} else if (randdd == 0) {
					for (int y = 0; y < height; y++) {
						cellColor = cells[x][y].randomColor;
						int mx = x - 1;
						if (mx < 0) mx = width - 1;
						int my = y - 1;
						if (my < 0) my = height - 1;
						int gx = (x + 1) % width;
						int gy = (y + 1) % height;


						if (cells[x][y].isAlive()) {


							cells[x][y].setNextRound(true, cellColor);


							if (!cells[mx][my].isNextRound() && !cells[mx][my].isAlive())
								cells[mx][my].setNextRound(true, cellColor);

							if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
								cells[mx][y].setNextRound(true, cellColor);


							if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
								cells[x][my].setNextRound(true, cellColor);


							if (!cells[gx][my].isNextRound() && !cells[gx][my].isAlive())
								cells[gx][my].setNextRound(true, cellColor);

							if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
								cells[gx][y].setNextRound(true, cellColor);


						}
					}
				} else if (randdd == 2) {
					for (int y = 0; y < height; y++) {
						cellColor = cells[x][y].randomColor;
						int mx = x - 1;
						if (mx < 0) mx = width - 1;
						int my = y - 1;
						if (my < 0) my = height - 1;
						int gx = (x + 1) % width;
						int gy = (y + 1) % height;


						if (cells[x][y].isAlive()) {


							cells[x][y].setNextRound(true, cellColor);


							if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
								cells[x][my].setNextRound(true, cellColor);

							if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
								cells[x][gy].setNextRound(true, cellColor);

							if (!cells[gx][my].isNextRound() && !cells[gx][my].isAlive())
								cells[gx][my].setNextRound(true, cellColor);

							if (!cells[gx][y].isNextRound() && !cells[gx][y].isAlive())
								cells[gx][y].setNextRound(true, cellColor);

							if (!cells[gx][gy].isNextRound() && !cells[gx][gy].isAlive())
								cells[gx][gy].setNextRound(true, cellColor);

						}
					}
				} else if (randdd == 3) {
					for (int y = 0; y < height; y++) {
						cellColor = cells[x][y].randomColor;
						int mx = x - 1;
						if (mx < 0) mx = width - 1;
						int my = y - 1;
						if (my < 0) my = height - 1;
						int gx = (x + 1) % width;
						int gy = (y + 1) % height;


						if (cells[x][y].isAlive()) {


							cells[x][y].setNextRound(true, cellColor);


							if (!cells[mx][my].isNextRound() && !cells[mx][my].isAlive())
								cells[mx][my].setNextRound(true, cellColor);

							if (!cells[mx][y].isNextRound() && !cells[mx][y].isAlive())
								cells[mx][y].setNextRound(true, cellColor);

							if (!cells[mx][gy].isNextRound() && !cells[mx][gy].isAlive())
								cells[mx][gy].setNextRound(true, cellColor);

							if (!cells[x][my].isNextRound() && !cells[x][my].isAlive())
								cells[x][my].setNextRound(true, cellColor);

							if (!cells[x][gy].isNextRound() && !cells[x][gy].isAlive())
								cells[x][gy].setNextRound(true, cellColor);


						}
					}
				}
			}
		}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {


		if (e.getKeyCode() == KeyEvent.VK_G) {
			if (Cell.grid) Cell.grid = false;
			else Cell.grid = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					cells[x][y].setAlive(random.nextBoolean());

		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (start) start = false;
			else start =true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					cells[x][y].setAlive(false);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
			button = e.getButton();
		if(!start){
			int mx = e.getX()/Cell.size;
			int my = e.getY()/Cell.size;
			if(button ==1){
				if(mx<=width && my<=height)
					if (cells[mx][my].isAlive())
						cells[mx][my].setAlive(false);
					else{
						if(mx<=width && my<=height);
						cells[mx][my].setAlive(true);
					}
			}



		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
			//button = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
