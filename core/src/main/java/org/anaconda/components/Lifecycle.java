package org.anaconda.components;

public class Lifecycle {
	
	public static enum State {
		Unstarted,
		Starting,
		Started,
		Stopping,
		Stopped,
		Closing,
		Closed
	}
	
	private volatile State state = State.Unstarted;
	
	public State state() {
        return this.state;
    }
	
	public boolean canMoveToStarted() {
		return state == State.Unstarted || state == State.Closed || state == State.Stopped;
	}
	
	public boolean moveStarted() {
		if (canMoveToStarted()) {
			state = State.Started;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canMoveToStopped() {
		return state == State.Started || state == State.Unstarted;
	}
	
	public boolean moveStopped() {
		if (canMoveToStopped()) {
			state = State.Stopped;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canMoveToClosed() {
		return state == State.Stopped || state == State.Unstarted;
	}
	
	public boolean moveClosed() {
		if (canMoveToClosed()) {
			state = State.Closed;
			return true;
		} else {
			return false;
		}
	}
}