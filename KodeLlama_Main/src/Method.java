public class Method
{
	private String body;
	private String name;
	private Llama belongsTo;
	private String[] args;
	
	public Method(String n, String[] args, String b, Llama o)
	{
		setBelongsTo(o);
		setName(n);
		body = b;
		this.args = args;
		// System.out.println(body);
	}
	
	public String[] getArgs()
	{
		return args;
	}
	
	public String getBody()
	{
		// System.out.println(body);
		return body;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Llama getBelongsTo()
	{
		return belongsTo;
	}
	
	public void setBelongsTo(Llama belongsTo)
	{
		this.belongsTo = belongsTo;
	}
	
}
