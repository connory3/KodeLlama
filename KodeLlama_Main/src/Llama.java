import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class Llama extends Canvas {
	Image image; // Image save currently only supports default Image
	int x; // X location value
	int y; // Y location value
	int height; // height of llama
	int width; // width of llama
	double direction; //Counterclockwise, in radians.
	boolean penDown; // Whether or not the llama is drawing
	transient RGB penColor = new RGB(0, 0, 0); // Color of line to draw (not changeable)//TODO
	int penSize = 2; // Size of line to draw
	long priority; // display priority (not implemented)
	int zoom; // size of llama on screen (not implemented, redundant?)
	boolean drawBorder; // Does it have borders on screen?
	String name; // Name of Llama for code and List

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
	 * towards(Llama n) done
	 * penSize() done
	 * resize(int n) done
	 * 
	 */

	/**
	 * @param ld Serializable form of data stored in llama
	 * @param parent composite to add llama to
	 */
	public Llama(LlamaData ld, Composite parent, Image newImage)
	{
		super(parent, SWT.TRANSPARENT);
		x = ld.getX();
		y = ld.getY();
		height = ld.getHeight();
		width = ld.getWidth();
		direction = ld.getDirection();
		penDown = ld.isPenDown();
		penSize = ld.getPenSize();
		priority = ld.getPriority();
		zoom = ld.getZoom();
		name = ld.getName();
		this.image = newImage;
		refreshBounds();
		addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e) {
            	GC gc = e.gc;
            	Transform transform = new Transform(e.display);
            	transform.translate(width/2, height/2);
                transform.rotate((float) Math.toDegrees(-direction));
                transform.translate(-width/2, -height/2);
                gc.setTransform(transform);
                gc.drawImage(image, width/2-image.getBounds().width/2, width/2-image.getBounds().width/2);
                if (drawBorder)
            		gc.drawRectangle(0, 0, width-1, height-1);
            }
        });
	}

	/**
	 * @param parent composite to place llama onto
	 * @param firstX initial x position of llama
	 * @param firstY initial y position of llama
	 * @param firstWidth initial width of llama
	 * @param firstHeight initial height of llama
	 */
	public Llama(Composite parent, int firstX, int firstY, int firstWidth, int firstHeight) {
		super(parent, SWT.TRANSPARENT);
		this.
		image = getImage();
		x = firstX;
		y = firstY;
		name = "";
		height = firstHeight;
		width = firstWidth;
		direction = 0;
		drawBorder = false;
		refreshBounds();
		addPaintListener(new PaintListener(){
            public void paintControl(PaintEvent e) {
            	GC gc = e.gc;
            	Transform transform = new Transform(e.display);
            	transform.translate(width/2, height/2);
                transform.rotate((float) Math.toDegrees(-direction));
                transform.translate(-width/2, -height/2);
                //System.out.println(transform + " " + name);
                gc.setTransform(transform);
                gc.drawImage(image, width/2-image.getBounds().width/2, height/2-image.getBounds().height/2);
                //gc.getTransform(transform);
                //System.out.println(transform);
                //drawBorder = true;
                if (drawBorder)
            		gc.drawRectangle(width/2-image.getBounds().width/2, height/2-image.getBounds().height/2, image.getBounds().width - 1, image.getBounds().height - 1);
            }
        });
		
	}
	
	/**
	 * Sets the llama up to be bordered
	 */
	public void drawBorder()
	{
		drawBorder = true;
		redraw();
	}
	
	/**
	 * Removes any existing borders from the llama
	 */
	public void clearBorder()
	{
		drawBorder = false;
		redraw();
	}
	
	/**
	 * @param n new image for llama
	 */
	public void setImage(Image n)
	{
		image = new Image(getDisplay(), n.getImageData());
	}
	
	/**
	 * @return current image of llama
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * passes bounds to parent function to be drawn
	 */
	public void refreshBounds()
	{
		setBounds(x-(int)(width/2), y-(int)(height/2), width, height);
	}
	
	/**
	 * @return position of llama on parent
	 */
	public Point position()
	{
		Point n = new Point(x, y);
		return n;
	}
	
	/**
	 * @param n number of pixels to move llama back by
	 */
	public void back(int n)
	{
		int xFactor = (int)(n*Math.cos(direction));
		int yFactor = (int)(n*Math.sin(direction));
		x -= xFactor;
		y += yFactor;
		refreshBounds();
	}
	
	/**
	 * @param n number of pixeld to move llama forward by
	 */
	public void forward(int n)
	{
		int xFactor = (int)(n*Math.cos(direction));
		int yFactor = (int)(n*Math.sin(direction));
		x += xFactor;
		y -= yFactor;	
		refreshBounds();
	}
	
	public void invisible()
	{
		this.setVisible(false);
	}
	
	public void visible()
	{
		this.setVisible(true);
	}
	
	/**
	 * @param n The llama to find the distance to
	 * @return the distance between this llama and l
	 */
	public int distance(Llama n)
	{
		int myX = x;
		int myY = y;
		int theirX = n.x;
		int theirY = n.y;
		double distance = Math.abs(Math.sqrt(Math.pow(myX-theirX, 2) + Math.pow(myY-theirY, 2)));
		return (int)distance;
	}
	
	/**
	 * @param n number of pixels to rotate the llama to the left by
	 */
	public void lt(int n)
	{
		direction += Math.toRadians(n);
		setOrientation(SWT.HORIZONTAL);
		redraw();
	}
	
	/**
	 * @param n number of pixels to rotate the llama to the left by
	 */
	public void leftTurn(int n)
	{
		lt(n);
	}
	
	/**
	 * @param n number of pixels to rotate the llama to the right by
	 */
	public void rt(int n)
	{
		direction -= Math.toRadians(n);
		setOrientation(SWT.VERTICAL);
		redraw();
	}
	
	/**
	 * @param n number of pixels to rotate the llama to the right by
	 */
	public void rightTurn(int n)
	{
		rt(n);
	}
	
	/**
	 * Puts this llama in front of all other llamas on the field
	 */
	public void inFront()
	{
		priority = System.currentTimeMillis();
		//This will be bigger than any other priority already set, forcing it to be on top.
	}
	
	/**
	 * Puts this llama behind all other llamas on the field
	 */
	public void behind()
	{
		priority = -1*System.currentTimeMillis();
		//This will be smaller than any other priority already set, forcing it to be on the bottom.
	}
	
	/**
	 * Puts the pen of this llama down
	 */
	public void pd()
	{
		penDown = true;
	}
	
	/**
	 * Puts the pen of this llama down
	 */
	public void penDown()
	{
		penDown = true;
	}
	
	/**
	 * Brings the pen of this llama up
	 */
	public void pu()
	{
		penDown = false;
	}
	
	/**
	 * Brings the pen of this llama up
	 */
	public void penUp()
	{
		penDown = false;
	}
	
	/**
	 * @param n the llama to turn this llama towards
	 */
	public void towards(Llama n)
	{
		double dx = n.x - this.x;
		double dy = n.y - this.y;
		System.out.println(dx);
		if (dx != 0)
		{
			direction = -Math.atan(dy/dx);
			if (dx < 0)
			{
				direction += Math.PI;
			}
		}
		else if (dy > 0)
		{
			direction = -Math.PI/2;
		}
		else
		{
			direction = Math.PI/2;
		}
		redraw();
	}
	
	/**
	 * @return a serializable object representing this llama
	 */
	public LlamaData Data()
	{
		LlamaData data = new LlamaData();
		data.setDirection(direction);
		data.setHeight(height);
		data.setName(name);
		data.setPenDown(penDown);
		data.setPenSize(penSize);
		data.setPriority(priority);
		data.setWidth(width);
		data.setX(x);
		data.setY(y);
		data.setZoom(zoom);
		return data;
	}
	
	/**
	 * @param n new size of this llama's pen
	 */
	public void resize(int n)
	{
		penSize = n;
	}
	
	/**
	 * @return size of this llama's pen
	 */
	public int penSize()
	{
		return penSize;
	}
}
