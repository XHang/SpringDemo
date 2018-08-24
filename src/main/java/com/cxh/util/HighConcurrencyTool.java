package com.cxh.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HighConcurrencyTool {
    /**
     * 并发执行某个操作
     * @param concurrencyNumber 并发量
     * @param runnable 执行什么操作
     */
    public static void run(int concurrencyNumber,Runnable runnable){
        ExecutorService executorService = Executors.newFixedThreadPool(concurrencyNumber);
        for (int i=0;i<concurrencyNumber;i++){
            executorService.execute(runnable);
        }
    }
}
