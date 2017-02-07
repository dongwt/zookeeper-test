package com.dongwt.zookeeper;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class LockTest {

    private static String connectString = "192.168.188.129";
    private static int sessionTimeoutMs = 5000;
    private static int connectionTimeoutMs = 5000;

    private static CuratorFramework client;

    public static void before() {
        client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, new RetryPolicy() {
            @Override
            public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
                return true;
            }
        });
        client.start();
        System.out.println("zk client start successfully!");
    }
    
    
    
    private static void doWithLock(CuratorFramework client) {
        InterProcessMutex lock = new InterProcessMutex(client, "/0001");
        try {
            if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    
    public static void main(String[] args){
        before();
        
        for(int i = 0; i< 10; i++){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    doWithLock(client);
                }
            }); 
            t1.start();
        }
    }
    
    
    

  
    

}
