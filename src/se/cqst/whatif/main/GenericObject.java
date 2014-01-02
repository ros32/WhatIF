package se.cqst.whatif.main;

public abstract class GenericObject {

	/*
	 * 			Static variables
	 */
	
//	private static int 	counter;
	
	/*
	 *			Variables
	 */
	
	private String 		name;
	private String 		identifier;
	
	private String 		description;
	
	/*
	 * 			Constructors
	 */
	
//	public			GenericObject(String name)		{	this(name, name + counter++); counter--;	}
	
	public			GenericObject(String name, String identifier)
	{
		this.name = name;
		this.identifier = identifier;
//		counter++;
		this.description = "";
//		CmdLib.writeLog("DEBUG", "Item Object " + identifier + " created.");
	}
	
	/*
	 * 			Overwritten Object methods
	 */
	
	public String 		toString()			{	return this.identifier;		}
	
	/*
	 * 			Basic get Methods
	 */
	
	public String 		getName()			{	return this.name;		}
	public String 		getDescription()		{	return this.description;	}

	
	/*
	 * 			Basic set Methods
	 */
	
	public void 		setDescription(String desc)	{	this.description=desc;		}	
	
	/*
	 * 			Other Methods
	 */
	
	public void		look()
	{
		this.printDescription();
	}
	
	public void		printDescription()
	{
		System.out.println();
		System.out.println("You examine the " + this.name + ":");
		System.out.println(TextLib.TXT_LINE_HOR);
		System.out.println(this.description);
		System.out.println(TextLib.TXT_LINE_HOR);
	}
	
/*	public boolean 		equals(Object obj)
	{
		if(obj.toString().equals(this.toString()))
			return true;
		else
			return false;
	}*/
	
	/*
	 * 			Static Methods
	 */
	
	
}
