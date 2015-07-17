package org.anaconda.common.env;

import java.io.File;

import org.anaconda.common.io.Files;
import org.anaconda.common.settings.Settings;


public class Environment {
    
    private File homeDir;
    private File configDir;
    private File logsDir;
	private File baseDir;
	private File pluginsDir;
    
    public Environment(Settings settings) {
    	
		this.homeDir = Files.file(settings.get("path.home"));
		this.baseDir = Files.file(settings.get("path.base"));
		this.configDir = Files.file(baseDir, "config");
		this.logsDir = Files.file(settings.get("path.logs", new File(this.baseDir, "logs").getAbsolutePath()));
		this.pluginsDir = Files.file(settings.get("path.plugins", new File(this.baseDir, "plugins").getAbsolutePath()));
    }
    
    public File pluginsDir() {
        return pluginsDir;
    }

    public File configDir() {
        return configDir;
    }

    public File logsDir() {
        return logsDir;
    }
    
    public File homeDir() {
        return homeDir;
    }
    
    public File baseDir() {
        return baseDir;
    }
}
