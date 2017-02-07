package com.dongwt.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

public class ConnectTest {
    
    public static void main(String[] args) throws Exception{
        
        String connectString = "192.168.188.129";
        int sessionTimeoutMs = 5000;
        int connectionTimeoutMs = 5000;
        
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, new RetryPolicy() {
            @Override
            public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
                return true;
            }
        });
        client.start();
        System.out.println("zk client start successfully!");
        
        
        System.out.println(client.getChildren().forPath("/"));
        
    }

}
