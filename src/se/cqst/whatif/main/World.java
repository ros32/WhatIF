//	TODO: Cohersion
package se.cqst.whatif.main;

import java.io.Serializable;
import java.util.List;

/**
 * <p>The World class is the representation of the world in-game. This class contains all information about
 * what state the world is in, where every {@link GenericObject} is located and what state they are in.</p>
 * 
 * <p>World is a Singleton object and should be accessed through {@code World.getInstance()}, upon calling that
 * creates a new instance of World.</p>
 * @see {@link ItemStore} (interface), {@link Serializable} (interface)
 */
public class World implements Serializable {
	
	/*
	 * 							Static Variables
	 */
	
	private static final long 	serialVersionUID = -3766562480367208771L;
	
	/*
	 * 							Variables
	 */
	
	private List<Room> 			roomList;
	private List<Actor>			actorList;

	/*
	 * 							Constructors
	 */

	public					World(List<Room> roomList, List<Actor> actorList)
	{
		this.roomList=roomList;
		this.actorList=actorList;
	}
	
	/*
	 * 							Basic get Methods
	 */
	
	public List<Room> 			getRoomList() 					{	return this.roomList;		}
	
	/*
	 * 							Basic set Methods
	 */
	
	/*
	 * 							Other Methods
	 */
	
	/*
	 * 							Interface Methods - ItemStore
	 */

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public List<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}

}
