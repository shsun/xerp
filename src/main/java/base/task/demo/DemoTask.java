package base.task.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * Time: 13-2-5 下午1:37
 */
public class DemoTask implements Runnable {
	private static AtomicLong sumCount = new AtomicLong();
	private static AtomicInteger curCount = new AtomicInteger();

    private String content;

	public DemoTask(String content){
		this.content = content;
		sumCount.incrementAndGet();
		curCount.incrementAndGet();
	}

	@Override
	public void run() {
		//System.out.println( "当前任务状况  "+ sumCount.get() + " " + curCount.get());
		//System.out.println("this.content : " + this.content);
		curCount.decrementAndGet();
	}
}
