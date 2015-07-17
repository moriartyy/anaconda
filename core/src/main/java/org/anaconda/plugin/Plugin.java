package org.anaconda.plugin;

import com.google.inject.Module;

public interface Plugin {
	
	void processModule(Module module);
}
