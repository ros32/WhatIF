package main_project;

import java.util.List;

public interface ContainerStore {

	public abstract Container getContainer(String identifier);
	public abstract List<Container> getContainerList();
	
}
