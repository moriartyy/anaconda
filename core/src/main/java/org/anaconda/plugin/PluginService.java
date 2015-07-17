package org.anaconda.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.anaconda.common.env.Environment;
import org.anaconda.common.reflect.ClassLoaders;
import org.anaconda.common.reflect.Classes;
import org.anaconda.common.settings.Settings;
import org.anaconda.components.AbstractLifecycleComponent;
import com.google.inject.Module;

public class PluginService extends AbstractLifecycleComponent {
	
	private Settings settings;
	private Environment environment;
	private List<Plugin> plugins = new ArrayList<>();

	public PluginService(Settings settings, Environment environment) {
		this.settings = settings;
		this.environment = environment;
		loadPlugins();
	}
	
	private void registerPlugin(Plugin plugin) {
		this.plugins.add(plugin);
	}

	private void loadPlugins() {
		List<File> classPaths = getClassPaths();
		ClassLoaders.addUrls(classPaths.stream().map(f -> {
			try {
				return f.toURI().toURL();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).toArray(URL[]::new), getClass().getClassLoader());
		
		classPaths.forEach(path -> {
			if (path.getName().endsWith(".jar")) {
				List<Class<?>> classes = Classes.forJar(path, getClass().getClassLoader());
				for (Class<?> clazz : classes) {
					try {
						if (Plugin.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
							Plugin plugin = null;
							try {
								plugin = (Plugin) clazz.getConstructor(Settings.class).newInstance(settings);
							} catch (NoSuchMethodException e) {
								plugin = (Plugin) clazz.getConstructor().newInstance();
							}
							if (plugin != null) {
								registerPlugin(plugin);
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	}
	
	private List<File> getClassPaths() {
		List<File> classPaths = new ArrayList<>(); 
		File[] dirs = environment.pluginsDir().listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		for (File dir : dirs) {
			classPaths.add(dir);
			File[] libFiles = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar") || name.endsWith(".zip");
				}
			});
			classPaths.addAll(Arrays.asList(libFiles));
		}
		return classPaths;
	}
 
	public List<Plugin> plugins() {
		return plugins;
	}

	public void processModules(List<Module> modules) {
		modules.forEach(module -> {
			plugins().forEach(plugin -> plugin.processModule(module));
		});
		
	}
	
	public void processModule(Module module) {
		plugins().forEach(plugin -> plugin.processModule(module));
	}

	@Override
	protected void doStart() {
		
	}

	@Override
	protected void doStop() {
		
	}

	@Override
	protected void doClose() {
		
	}

}
