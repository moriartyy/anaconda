package org.anaconda.crawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.anaconda.common.env.Environment;
import org.anaconda.common.settings.Settings;
import org.anaconda.common.stats.StatsSupplier;
import org.anaconda.components.AbstractLifecycleComponent;

import com.google.inject.Inject;

public class CrawlerService extends AbstractLifecycleComponent implements StatsSupplier<CrawlersStats> {
	
	private Set<Crawler> crawlers = new HashSet<Crawler>();
	private static final int THREAD_NUMBER = 20;
	private static final int QUEUED_TASK_THRESHOLD = 10;
	private static final int BATCH_SIZE = 10;
	private int threadNumber;
	private ExecutorService taskExecutor;
	private AtomicBoolean isRunning = new AtomicBoolean();
	private Queue<Task> taskQueue = new LinkedList<Task>();
	private ScheduledExecutorService queueMaintenancer;
	private int batchSize = BATCH_SIZE;
	
	@Inject
	public CrawlerService(Settings settings, Environment environment) {
		this.threadNumber = settings.getAsInt("processor.thread_number", THREAD_NUMBER);
	}
	
	public <C extends Crawler> void registerCrawler(C crawler) {
		crawlers.add(crawler);
	}

	private synchronized Task acquireTask() {
		return taskQueue.poll();
	}
	
	private boolean needFillQueue() {
		return this.taskQueue.size() < this.threadNumber * QUEUED_TASK_THRESHOLD;
	}
 	
	private void fillQueue() {
		int size = 0;
		crawlers.stream().mapToInt(Crawler::priority).sum();
		Random random = new Random(System.currentTimeMillis());
		Map<Integer, Queue<Task>> tasks = new TreeMap<Integer, Queue<Task>>();
		this.crawlers.forEach(crawler -> {
			for (int i=0; i<crawler.priority(); i++) {
				int index = random.nextInt(size);
				while (tasks.containsKey(index)) {
					index = random.nextInt();
				}
				tasks.put(random.nextInt(size), new LinkedList<Task>(crawler.acquire(batchSize)));
			}
		});
		List<Entry<Integer, Queue<Task>>> tasksList = new ArrayList<>(tasks.entrySet());
		Collections.sort(tasksList, new Comparator<Entry<Integer, Queue<Task>>>() {

			@Override
			public int compare(Entry<Integer, Queue<Task>> o1, Entry<Integer, Queue<Task>> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		for (int i=0; i<batchSize; i++) {
			tasksList.forEach(entry -> {
				Task task = entry.getValue().poll();
				if (task != null) {
					this.taskQueue.add(task);
				}
			});
		}
		
	}
	
	@Override
	protected void doStart() {
		isRunning.set(true);
		
		queueMaintenancer = Executors.newSingleThreadScheduledExecutor();
		queueMaintenancer.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				if (CrawlerService.this.needFillQueue()) {
					CrawlerService.this.fillQueue();
				}
			}
		}, 5, 1, TimeUnit.SECONDS);
		
		taskExecutor = Executors.newFixedThreadPool(threadNumber);
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning.get()) {
					try {
						Task task = acquireTask();
						if (task != null) {
							task.execute();
						} else {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}
					} catch (Exception e) {
						// We do nothing for now.
						// TODO add something.
					}
				}
			}

		});
	}

	@Override
	protected void doStop() {
		isRunning.set(false);
		taskExecutor.shutdown();
		queueMaintenancer.shutdown();
	}

	@Override
	protected void doClose() {
		
	}

	@Override
	public CrawlersStats getStats() {
		// TODO Auto-generated method stub
		return null;
	}
}
