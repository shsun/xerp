package base.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * Time: 13-2-5 下午1:33
 */
public class TaskExcutor {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*10);

    public static void addTask(Runnable task){
        executor.submit(task);
        //System.out.println("添加一个任务");
    }
}
