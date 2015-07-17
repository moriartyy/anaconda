package org.anaconda.engine;

import java.util.ArrayList;
import java.util.List;

import org.anaconda.common.env.Environment;
import org.anaconda.common.env.EnvironmentModule;
import org.anaconda.common.settings.Settings;
import org.anaconda.common.settings.SettingsModule;
import org.anaconda.components.AbstractLifecycleComponent;
import org.anaconda.crawler.CrawlerModule;
import org.anaconda.plugin.PluginModule;
import org.anaconda.plugin.PluginService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Engine extends AbstractLifecycleComponent {
	
	private Injector injector;

	public Engine(Settings settings, Environment environment) {
		
		this.injector = Guice.createInjector(getModules(settings, environment));
	}
	
	private List<Module> getModules(Settings settings, Environment environment) {
		PluginService pluginService = new PluginService(settings, environment);
		ModulesBuilder modules = new ModulesBuilder(pluginService);
		modules.add(new SettingsModule(settings));
		modules.add(new EnvironmentModule(environment));
		modules.add(new CrawlerModule());
		modules.add(new PluginModule(pluginService));
		return modules.build();
	}
	
	class ModulesBuilder {
		
		private PluginService pluginService;
		private List<Module> modules = new ArrayList<Module>();

		public ModulesBuilder(PluginService pluginService) {
			this.pluginService = pluginService;
		}
		
		public ModulesBuilder add(Module module) {
			pluginService.processModule(module);
			modules.add(module);
			return this;
		}
		
		public List<Module> build() {
			return modules;
		}
	}

	@Override
	protected void doStart() {
//		injector.getInstance(Scheduler.class).start();
//		injector.getInstance(ProcessingService.class).start();
	}

	@Override
	protected void doStop() {
//		injector.getInstance(ProcessingService.class).stop();
//		injector.getInstance(Scheduler.class).stop();
	}

	@Override
	protected void doClose() {
//		injector.getInstance(ProcessingService.class).close();
//		injector.getInstance(Scheduler.class).stop();
	}

}
