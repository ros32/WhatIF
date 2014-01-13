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
	
	public String 		getIdentifier() 				{	return identifier;		}
	public Room 		getTarget() 					{	return target;			}
	public String 		getName() 					{	return name;			}
	
	/*
	 * 			Basic set Methods
	 */
	
	public void 		setTarget(Room target) 				{	this.target = target;		}

	/*
	 * 			Other Methods
	 */
	
	
	/*
	 * 			Static Methods
	 */

	
	
}
