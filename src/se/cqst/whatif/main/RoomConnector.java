package se.cqst.whatif.main;

public class RoomConnector {
	
	/*
	 * 			Static Variables
	 */
	
	private static int 	counter;
	
	/*
	 * 			Variables
	 */
	
	private String		name;
	private String 		identifier;
	private Room 		target;
	private String 		description;
	private boolean 	isLocked;
	private String 		prefix;
	
	/*
	 * 			Constructors
	 */
	
	public			RoomConnector(String name, Room target)		{	this(name, name + counter++,target); counter--;	}
	
	public 			RoomConnector(String name, String identifier, Room target)
	{
		this.name = name;
		this.identifier = identifier;
		counter++;
		this.setTarget(target);
		CmdLib.writeLog("DEBUG", "RoomConnector Object " + identifier + " created.");
	}

	/*
	 * 			Overwritten Object Methods
	 */
	
	public String 		toString() 					{	return this.identifier;		}
	
	/*
	 * 			Basic get Methods
	 */
	
	public String 		getDescription() 				{	return description;		}
	public String 		getIdentifier() 				{	return identifier;		}
	public Room 		getTarget() 					{	return target;			}
	public String 		getPrefix() 					{	return prefix;			}
	public String 		getName() 					{	return name;			}
	public boolean 		isLocked() 					{	return isLocked;		}
	
	/*
	 * 			Basic set Methods
	 */
	
	public void 		setDescription(String description) 		{	this.description = description;	}
	public void 		setTarget(Room target) 				{	this.target = target;		}
	public void 		setLocked(boolean isLocked) 			{	this.isLocked = isLocked;	}
	public void 		setPrefix(String prefix) 			{	this.prefix = prefix;		}

	/*
	 * 			Other Methods
	 */
	
	
	/*
	 * 			Static Methods
	 */

	
	
}
