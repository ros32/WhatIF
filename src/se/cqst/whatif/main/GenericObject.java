/*
 * 
 */
package se.cqst.whatif.main;

/**
 * <p>GenericObject is the abstract superclass for all object that are imported from file.</p>
 * 
 * <p>The class contains the basic properites needed for handling these objects, such as an
 * {@code identifier} value, a {@code name} and a {@code description}.</p>
 */
public abstract class GenericObject {

	/*
	 * 			Static variables
	 */
	
	public static final String	TXT_LINE_HOR		=	"================================================================================";
	
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
	
	/**
	 * Instantiates a new GenericObject with a {@code name} and an {@code identifier}.
	 *
	 * @param name Name of the GenericObject.
	 * @param identifier Identifier used to identify the object.
	 */
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
	
	/**
	 * Using the look()-method displays the textual representation of the object from {@code GenericObject.printDescription()}.
	 */
	public void		look()
	{
		this.printDescription();
	}
	
	/**
	 * Prints a textual representation of the object ({@code name} plus {@code description}).
	 */
	public void		printDescription()
	{
		System.out.println();
		System.out.println("You examine the " + this.name + ":");
		System.out.println(TXT_LINE_HOR);
		System.out.println(this.description);
		System.out.println(TXT_LINE_HOR);
	}
	
	public abstract void	use();
	
	public abstract void	use(GenericObject object);
	
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
