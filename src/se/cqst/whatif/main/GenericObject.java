/*
 * 
 */
package se.cqst.whatif.main;

public abstract class GenericObject {

	/*
	 * 			Static variables
	 */
	
	/*
	 *			Variables
	 */
	
	private String 		name;
	private String 		identifier;
	
	private String 		description;
	
	/*
	 * 			Constructors
	 */
	
	public			GenericObject(String name, String identifier)
	{
		this.name = name;
		this.identifier = identifier;
		this.description = "";
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
		System.out.println("================================================================================");
		System.out.println(this.description);
		System.out.println();
	}
	
	public abstract void	use();
	
	public abstract void	use(GenericObject object);
	
	
}
