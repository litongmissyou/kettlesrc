package org.pentaho.di.laf;

import org.pentaho.di.i18n.MessageHandler;
import org.pentaho.di.laf.Handler;

import java.util.HashMap;

/** 
 * the LAFFactory provides a mechanism whereby @see Handler s can be dynamically replaced to 
 * enable user driven replacement of dynamic resolvers whether ImageHandlers, MessageHandlers, or other
 * elements of Look and Feel.
 * 
 * @author dhushon
 *
 */
public class LAFFactory {
	
	static Class<? extends Handler> _defMessageHandler = org.pentaho.di.i18n.LAFMessageHandler.class;
	static Class<? extends Handler> _defPropertyHandler = org.pentaho.di.laf.OverlayPropertyHandler.class;
	
	//Registry of Delegates that know how to load the appropriate handlers
	private static HashMap<Class<? extends Handler>,LAFDelegate<? extends Handler>> delegateRegistry = new HashMap<Class<? extends Handler>,LAFDelegate<? extends Handler>>();
	
	//Map an abstract ClassName (by String) to an implementing Class
	private static HashMap<String,Class<? extends Handler>> handlerRef = new HashMap<String,Class<? extends Handler>>();
	static {
		//handlers.put(MessageHandler.class.), (Handler)_defMessageHandler.newInstance());
		handlerRef.put(MessageHandler.class.getName(), _defMessageHandler);
		handlerRef.put(PropertyHandler.class.getName(), _defPropertyHandler);
	}
	
	protected static synchronized <V extends Handler>LAFDelegate<V> getDelegate(Class<V> handler) {
		LAFDelegate<V> l = (LAFDelegate<V>) delegateRegistry.get(handler);
		if (l==null) {
			//TODO: check subclasses
			Class<V> defaultHandler = (Class<V>)handlerRef.get(handler.getName());
			l = new LAFDelegate<V>(handler, defaultHandler);
			delegateRegistry.put(handler, l);
		}
		return l;
	}
	/**
	 * Return an instance of the class that has been designated as the implementor of the requested
	 * Interface, will return null if there is no implementor.
	 * 
	 * @param <V>
	 * @param handler
	 * @return
	 */
	public static <V extends Handler>V getHandler(Class<V> handler) {
		LAFDelegate<V>l = getDelegate(handler);
		return (V)l.getHandler();
	}
	
}
