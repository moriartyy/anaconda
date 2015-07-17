package org.anaconda.common.logging;

import org.anaconda.common.env.Environment;
import org.anaconda.common.settings.Settings;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Loggers {

    private static Map<String, Logger> loggers = new HashMap<String, Logger>();
    
    public static void initialize(Settings settings, Environment environment) throws IOException{
    	
    	boolean enableConsoleLogger = settings.getAsBoolean("logging.consolelogger.enabled", 
    			"true".equalsIgnoreCase(System.getProperty("logging.consolelogger.enabled")) ? true : false);
        
    	if (enableConsoleLogger) {
            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS}[%-5p][%-25c{1}] - %m%n"));
            consoleAppender.setTarget("System.out");
            consoleAppender.setName("console");
            consoleAppender.activateOptions();
            LogManager.getRootLogger().addAppender(consoleAppender);
        }
    	
    	String projectName = settings.get("project_name", System.getProperty("project_name", "application"));
    	File logFile = new File(environment.logsDir(), projectName + ".log");
    	DailyRollingFileAppender fileAppender = new DailyRollingFileAppender(
    			new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS}[%-5p][%-25c{1}] - %m%n"),
    			logFile.getAbsolutePath(),
    			"'.'yyyy-MM-dd");
        fileAppender.setThreshold(Level.INFO);
        fileAppender.setName("info");
        fileAppender.setAppend(true);
        fileAppender.activateOptions();
        LogManager.getRootLogger().addAppender(fileAppender);
        
        Level level = Level.INFO;
        String levelString = settings.get("logging.level", System.getProperty("logging.level", "INFO"));
        if (!"INFO".equalsIgnoreCase(levelString)) {
        	level = Level.toLevel(levelString);
        }

        LogManager.getRootLogger().setLevel(level); 
        LogManager.getRootLogger().info("Use config: " + settings.get("config_file", "Unknown"));
        LogManager.getRootLogger().info("Use env config: " + settings.get("config_file_env", "Unknown"));
    }
    
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String loggerName) {
        if (loggers.containsKey(loggerName)){
            return loggers.get(loggerName);
        }else{
            loggers.put(loggerName, new Logger(loggerName));
            return loggers.get(loggerName);
        }
    }

}

