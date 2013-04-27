import java.io.Serializable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;


public class Line implements Serializable
{
	private static final long serialVersionUID = 1L; // Constant for serialization. DO NOT TOUCH
	private int x1; // x-coordinate of first point
	private int y1; // y-coordinate of first point
	private int x2; // x-coordinate of second point
	private int y2; // y-coordinate of second point
	private float width; // width of line
	transient private Color color; // color of line
	private int r; // Red value of color
	private int g; // Green value of color
	private int b; // Blue value of color
	
	/**
	 * Default constructor. Does nothing
	 */
	public Line() {}
	
	/**
	 * @param x1 x-coord. of first point
	 * @param y1 y-coord. of first point
	 * @param x2 x-coord. of second point
	 * @param y2 y-coord. of second point
	 * @param width width of line
	 * @param color color of line
	 */
	public Line(int x1, int y1, int x2, int y2, float width, Color color)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.width = width;
		this.color = color;
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	/**
	 * @param p, a Point (position of a llama)
	 * @return whether p is contained in this line
	 */
	public boolean contains(Point p)
	{
		int dx = x2 - x1; // Calculate difference in x
		if (dx == 0) // Vertical line
		{
			return (p.x == x1); // p has the same x, so p is on this line
		}
		int dy = y2 - y1; // Calculate difference in y
		// Make formula y=mx+b
		int m = dy/dx; // slope
		int b = y1 - m * x1; // y-intercept
		return ((p.y == m * p.x + b + width) || (p.y == m * p.x + b - width)); // Check if p falls on line (within width)
	}

	/**
	 * @return the x1
	 */
	public int getX1()
	{
		return x1;
	}

	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(int x1)
	{
		this.x1 = x1;
	}

	/**
	 * @return the y1
	 */
	public int getY1()
	{
		return y1;
	}

	/**
	 * @param y1 the y1 to set
	 */
	public void setY1(int y1)
	{
		this.y1 = y1;
	}

	/**
	 * @return the x2
	 */
	public int getX2()
	{
		return x2;
	}

	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(int x2)
	{
		this.x2 = x2;
	}

	/**
	 * @return the y2
	 */
	public int getY2()
	{
		return y2;
	}

	/**
	 * @param y2 the y2 to set
	 */
	public void setY2(int y2)
	{
		this.y2 = y2;
	}

	/**
	 * @return the width
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width)
	{
		this.width = width;
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	/**
	 * @return the r
	 */
	public int getR()
	{
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(int r)
	{
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public int getG()
	{
		return g;
	}

	/**
	 * @param g the g to set
	 */
	public void setG(int g)
	{
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public int getB()
	{
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(int b)
	{
		this.b = b;
	}
}
