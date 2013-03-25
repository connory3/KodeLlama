
public class Method {
	private String body, name;
	private ObjectType belongsTo;
	private String[] args;
	
	public Method(String n, String[] args, String b, ObjectType o){
		belongsTo = o;
		name = n;
		body = b;
		this.args = args;
	}
	
}
