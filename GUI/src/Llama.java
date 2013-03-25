

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class Llama extends Label {
	Image image;
	int x;
	int y;
	int height;
	int width;
	double direction; //Counterclockwise, in radians. 
	boolean penDown;
	Color color;
	int penSize;
	Color penColor;
	long priority;
	int zoom;
	boolean drawBorder;
	String name;

	/*
	 * List of variables:
	 * color
	 * colorUnder - Will not be implemented; this should not be a method of the llama itself but rather should take the llama as an argument. 
	 * direction
	 * priority - Allows program to determine which llama is on top
	 * penSize
	 * image
	 * x
	 * y
	 * height
	 * width
	 * penDown
	 * penColor
	 * zoom
	 * 
	 * List of methods to be implemented:
	 * back(int n) / bk(int n) done
	 * forward(int n) / fd(int n) done 
	 * distance(Llama n) done
	 * lt(int n) / leftTurn(int n) done
	 * rt(int n) / rightTurn(int n) done
	 * infront() done
	 * behind() done
	 * pd() / penDown() done
	 * pu() / penUp() done
	 * position() / pos() done
	 * remove() - Will not be implemented; this should not be a method of the llama itself.
	 * towards(Llama n)
	 * 
	 */
	protected void checkSubclass() { 
	} 

	public Llama(Composite parent, int firstX, int firstY, int firstWidth, int firstHeight) {
		super(parent, SWT.NONE);
		this.
		image = getImage();
		x = firstX;
		y = firstY;
		height = firstHeight;
		width = firstWidth;
		direction = 0;
		drawBorder = false;
		refreshBounds();
		addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e) {
            	if (drawBorder) e.gc.drawRectangle(0, 0, width-1, height-1);
            }
        });
	}
	
	public void drawBorder()
	{
		drawBorder = true;
		redraw();
	}
	
	public void clearBorder()
	{
		drawBorder = false;
		redraw();
	}
	
	public void setImage(Image n)
	{
		super.setImage(n);
		image = n;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void refreshBounds()
	{
		setBounds(x-(int)(width/2), y-(int)(height/2), width, height);
	}
	
	public Point position()
	{
		Point n = new Point(x, y);
		return n;
	}
	
	public void back(int n)
	{
		int xFactor = (int)(n*Math.cos(direction));
		int yFactor = (int)(n*Math.sin(direction));
		x -= xFactor;
		y += yFactor;
		refreshBounds();
	}
	
	public void forward(int n)
	{
		int xFactor = (int)(n*Math.cos(direction));
		int yFactor = (int)(n*Math.sin(direction));
		x += xFactor;
		y -= yFactor;	
		refreshBounds();
	}
	
	public int distance(Llama n)
	{
		int myX = x;
		int myY = y;
		int theirX = n.x;
		int theirY = n.y;
		double distance = Math.abs(Math.sqrt(Math.pow(myX-theirX, 2) + Math.pow(myY-theirY, 2)));
		return (int)distance;
	}
	
	public void lt(int n)
	{
		direction += Math.toRadians(n);
		setOrientation(SWT.HORIZONTAL);
	}
	
	public void leftTurn(int n)
	{
		lt(n);
	}
	
	public void rt(int n)
	{
		direction -= Math.toRadians(n);
		setOrientation(SWT.VERTICAL);
	}
	
	public void rightTurn(int n)
	{
		rt(n);
	}
	
	public void inFront()
	{
		priority = System.currentTimeMillis();
		//This will be bigger than any other priority already set, forcing it to be on top.
	}
	
	public void behind()
	{
		priority = -1*System.currentTimeMillis();
		//This will be smaller than any other priority already set, forcing it to be on the bottom.
	}
	
	public void pd()
	{
		penDown = true;
	}
	
	public void penDown()
	{
		penDown = true;
	}
	
	public void pu()
	{
		penDown = false;
	}
	
	public void penUp()
	{
		penDown = false;
	}
	
	public void towards(Llama n)
	{
		//TODO
	}
}
