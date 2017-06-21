package com.dongwt.zookeeper.original;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dongwt on 2017/6/21.
 */
public class ZkConstructor implements Watcher{

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws  Exception{
        ZooKeeper zooKeeper = new ZooKeeper("192.168.1.13:2181",5000,new ZkConstructor());
        System.out.println(zooKeeper.getState());
        connectedSemaphore.await();
        System.out.println("zookeeper session established.");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive watched event: " + event);
        if(Event.KeeperState.SyncConnected == event.getState()){
            connectedSemaphore.countDown();
        }
    }
}
