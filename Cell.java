import java.awt.*;
import java.util.Random;

public class Cell {
	//public static final int SIZE = 1;

	private int x;
	private int y;
	static int  size =10;
    static boolean grid = false;
    public int energy;
    public int nextEnergy;

    public boolean isNextRound() {
        return nextRound;
    }

    private boolean nextRound;
	private boolean alive;
    boolean isNeighbour =false;
    Random rand= new Random();

    private float r= rand.nextFloat();
    private float g= rand.nextFloat();
    private float b= rand.nextFloat();


	Color randomColor = new Color(r,g,b);

	public Cell(int x, int y,float r, float g, float b,int energia)
    {
        this.x=x;
        this.y=y;
        this.r =r;
        this.b=b;
        this.g=g;
        randomColor = new Color(r,g,b);
        this.energy = energia;
    }

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;

	}



    public void setNextRound(boolean nextRound,Color nextColor) {
        this.nextRound = nextRound;
        this.randomColor = nextColor;
    }

    public void setNextEnergy(int energia){
	    this.nextEnergy = energia;
    }

    public void nextEnergy()
    {
        energy=nextEnergy;
    }
    public void nextRound()
    {
        alive = nextRound;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void draw(Graphics g) {

        if (grid) {


            g.setColor(Color.BLACK);
            g.drawRect(x * size, y * size, size, size);
            if (alive) g.setColor(randomColor);
            else g.setColor(Color.WHITE);
            g.fillRect(x * size + 1, y * size + 1, size - 1, size - 1);

        }
        else{
            if (alive) g.setColor(randomColor);
            else g.setColor(Color.WHITE);
            g.fillRect(x * size , y * size , size , size );
        }
    }
}
