package se.cqst.whatif.main;

public interface Gate {

	public abstract boolean open();
	public abstract boolean close();
	public abstract boolean lock();
	public abstract boolean unlock();
	
}
