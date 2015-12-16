package zookpeer.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class CrudExampleMain2 {

	public static void main(String[] args) throws Exception {
		String path = "/test_path";
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000).build();
		// 启动 上面的namespace会作为一个最根的节点在使用时自动创建
		client.start();

		// // 创建一个节点
		// client.create().forPath("/head", new byte[0]);
		//
		// // 异步地删除一个节点
		// client.delete().inBackground().forPath("/head");
		//
		// // 创建一个临时节点
		// client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child",
		// new byte[0]);
		//
		// // 取数据
		// client.getData().watched().inBackground().forPath("/test");
		//
		// // 检查路径是否存在
		// client.checkExists().forPath(path);
		//
		// // 异步删除
		// client.delete().inBackground().forPath("/head");

//		CuratorListener listener = new CuratorListener() {
//			@Override
//			public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
//				System.out.println("node is changed");
//			}
//		};
//		client.getCuratorListenable().addListener(listener);
		// 注册观察者，当节点变动时触发
		client.getChildren().usingWatcher(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//event.get
				System.out.println("node is changed");
			}
		}).forPath(path);
		System.in.read();
		// 结束使用
		// client.close();
	}
}
