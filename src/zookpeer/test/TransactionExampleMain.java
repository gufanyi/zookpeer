package zookpeer.test;

import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;

/** 
 * 事务操作 
 *  
 * @author shencl 
 */  
public class TransactionExampleMain {  
    private static CuratorFramework client =  CuratorFrameworkFactory
			.builder()
			.connectString("localhost:2181")
			.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
			.build(); 
  
    public static void main(String[] args) {  
        try {  
            client.start();  
            // 开启事务  
            CuratorTransaction transaction = client.inTransaction();  
  
            Collection<CuratorTransactionResult> results = transaction.create()  
                    .forPath("/a/path", "some data".getBytes()).and().setData()  
                    .forPath("/another/path", "other data".getBytes()).and().delete().forPath("/yet/another/path")  
                    .and().commit();  
  
            for (CuratorTransactionResult result : results) {  
                System.out.println(result.getForPath() + " - " + result.getType());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 释放客户端连接  
            CloseableUtils.closeQuietly(client);  
        }  
  
    }  
} 