package com.dongwt.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.junit.Before;
import org.junit.Test;

public class ConnectTest {

    private String connectString = "192.168.188.129";
    private int sessionTimeoutMs = 5000;
    private int connectionTimeoutMs = 5000;

    private CuratorFramework client;

    @Before
    public void before() {
        client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, new RetryPolicy() {
            @Override
            public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
                return true;
            }
        });
        client.start();
        System.out.println("zk client start successfully!");
    }

    @Test
    public void createNode() throws Exception {
        client.create().creatingParentsIfNeeded().forPath("/0001/0002", "curator".getBytes());

        System.out.println(client.getChildren().forPath("/0001"));

        System.out.println(client.getData().forPath("/0001/0002"));
    }
    
    @Test
    public void removeNode() throws Exception{
        client.delete().forPath("/0001/0002");
        
        System.out.println(client.getChildren().forPath("/0001"));
    }
    
    @Test
    public void modifyData() throws Exception{
        
        client.setData().forPath("/0001/0002", "888".getBytes());
        
        System.out.println(client.getData().forPath("/0001/0002"));
    }
    
    
    
    

}
