package org.anaconda.common.logging;

public class Logger {
    private org.apache.log4j.Logger realLogger;

    public Logger(String name){
        this.realLogger = org.apache.log4j.Logger.getLogger(name);
    }
 
    public Logger(Class<?> clazz){
        this.realLogger = org.apache.log4j.Logger.getLogger(clazz);
    }

    public boolean isDebugEnabled() {
        return this.realLogger.isDebugEnabled();
    }
    
    public void debug(Object message) {
        this.realLogger.debug(message);        
    }

    public void debug(Object message, Throwable e) {
        this.realLogger.debug(message, e);        
    }

    public void error(Object message, Throwable e) {
        this.realLogger.error(message, e); 
    }
    
    public void error(Object message) {
        this.realLogger.error(message);
    }
    
    public boolean isInfoEnabled() {
        return this.realLogger.isInfoEnabled();
    }

    public void info(Object message) {
        this.realLogger.info(message);
    }
    
    public void info(Object message, Throwable cause) {
        this.realLogger.info(message, cause);
    }

    public boolean isTraceEnabled() {
        return this.realLogger.isTraceEnabled();
    }

    public void trace(Object message) {
        this.realLogger.trace(message);
    }
    
    public void trace(Object message, Throwable cause) {
        this.realLogger.trace(message, cause);
    }

    public void warn(Object message) {
        this.realLogger.warn(message);
    }
    
    public void warn(Object message, Throwable cause) {
        this.realLogger.warn(message, cause); 
    }

}
