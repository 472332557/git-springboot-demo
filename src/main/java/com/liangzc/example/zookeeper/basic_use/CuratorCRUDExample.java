package com.liangzc.example.zookeeper.basic_use;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorCRUDExample {
    private  CuratorFramework curatorFramework;

    public CuratorCRUDExample() {
        this.curatorFramework = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(10000)
                .connectString("121.37.249.94:2181")
                //指定重试
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(10000)
                .build();
        curatorFramework.start();//启动
    }

    public void crudTest() throws Exception {
        String node = curatorFramework.create().
                creatingParentContainersIfNeeded().
                withMode(CreateMode.PERSISTENT).
                forPath("/liangzc", "will success".getBytes());

        System.out.println("创建节点成功：" + node);
        Stat stat = new Stat();//存储状态信息的对象
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/liangzc");
        System.out.println("节点value值:"+new String(bytes));
        //修改
        stat = curatorFramework.setData().
                withVersion(stat.getVersion()).
                forPath(node, "will success quick".getBytes());
        byte[] result = curatorFramework.getData().forPath(node);
        System.out.println("修改节点之后额数据：" + new String(result));

        System.out.println("============开始删除节点");

        curatorFramework.delete().forPath(node);
        Stat stat1 = curatorFramework.checkExists().forPath(node);
        if (stat1 == null){
            System.out.println("节点删除成功");

        }
    }

    public static void main(String[] args) throws Exception {
        CuratorCRUDExample example = new CuratorCRUDExample();
        example.crudTest();
    }
}
