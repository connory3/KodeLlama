import java.io.Serializable;


public class LlamaData implements Serializable
{
	private static final long serialVersionUID = -6599638583942970519L;
	int x; // X-coordinate of the llama
	int y; // Y-coordinate of the llama
	int height; // Height of the llama
	int width; // Width of the llama
	double direction; // direction the llama is facing
	boolean penDown; // Whether or not the llama's pen is down
	int penSize; // The size of this llama's pen
	long priority; // The priority of this llama in drawing order
	int zoom; // How zoomed in this llama is (not implemented)
	String name; // The name of the llama
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	/**
	 * @return the direction
	 */
	public double getDirection()
	{
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	/**
	 * @return the penDown
	 */
	public boolean isPenDown()
	{
		return penDown;
	}
	/**
	 * @param penDown the penDown to set
	 */
	public void setPenDown(boolean penDown)
	{
		this.penDown = penDown;
	}
	/**
	 * @return the penSize
	 */
	public int getPenSize()
	{
		return penSize;
	}
	/**
	 * @param penSize the penSize to set
	 */
	public void setPenSize(int penSize)
	{
		this.penSize = penSize;
	}
	/**
	 * @return the priority
	 */
	public long getPriority()
	{
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(long priority)
	{
		this.priority = priority;
	}
	/**
	 * @return the zoom
	 */
	public int getZoom()
	{
		return zoom;
	}
	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(int zoom)
	{
		this.zoom = zoom;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
