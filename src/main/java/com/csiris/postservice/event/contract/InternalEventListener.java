package com.csiris.postservice.event.contract;

/*
 * Interface to listen jvm-local i.e internal events
 */
public interface InternalEventListener<T> {
	public void onEvent(T event);
}
