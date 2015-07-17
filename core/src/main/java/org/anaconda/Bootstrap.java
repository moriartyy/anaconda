package org.anaconda;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.anaconda.common.env.Environment;
import org.anaconda.common.logging.Loggers;
import org.anaconda.common.settings.DefaultSettingsPreparer;
import org.anaconda.common.settings.Settings;
import org.anaconda.engine.Engine;

public class Bootstrap {
	
	static Engine engine;
	static CountDownLatch countDownLatch = new CountDownLatch(1);
	static Thread keepAliveThread;
	
	public static void main(String[] args) throws Exception {

		System.setProperty("logging.consolelogger.enabled", "true");
		
		Settings settings = DefaultSettingsPreparer.Instance.prepareSettings();
		Environment environment = new Environment(settings);
		try {
			Loggers.initialize(settings, environment);
		} catch (IOException e1) {
			return;
		}
		
		Engine engine = new Engine(settings, environment);
		engine.start();
		
		keepAliveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		keepAliveThread.setDaemon(true);
		keepAliveThread.setName("Anaconda_KeepAlive");
		keepAliveThread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				engine.stop();
				engine.close();
				countDownLatch.countDown();
			}
		}));
	}
}
