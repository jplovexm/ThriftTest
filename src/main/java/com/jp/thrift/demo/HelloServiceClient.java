package com.jp.thrift.demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.jp.thrift.demo.Hello.AsyncClient.helloVoid_call;

public class HelloServiceClient {

	public static void main(String[] args) {
		new HelloServiceClient().startSyncClient();
	}
	public void startSyncClient(){
		TTransport transport = null;
		try {
			//transport = new TSocket("localhost",7911,3000);
			transport = new TFramedTransport(new TSocket("localhost",7911,3000));
			//TProtocol protocol = new TBinaryProtocol(transport);
			TProtocol protocol = new TCompactProtocol(transport);
			Hello.Client client = new Hello.Client(protocol);
			transport.open();
			String re = client.helloString("nihao");
			System.out.println(re);
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != transport){
				transport.close();
			}
		}
		
	}
	
	public void startAsyncClient(){
		try {
			TAsyncClientManager clientaManager = new TAsyncClientManager();
			TNonblockingTransport transport = new TNonblockingSocket("localhost",7911,3000);
			TProtocolFactory tprotocol = new TCompactProtocol.Factory();
			Hello.AsyncClient client = new Hello.AsyncClient(tprotocol, clientaManager, transport);
			System.out.println("Client start  ...");
			CountDownLatch latch = new CountDownLatch(1);
			AsynCallback callBack = new AsynCallback(latch);
			System.out.println("call method ");
			client.helloVoid(callBack);
			boolean wait = latch.await(30, TimeUnit.SECONDS); 
			System.out.println("latch.await =:" + wait);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("startClient end."); 
	}
	
	public class AsynCallback implements AsyncMethodCallback<Void>{
		private CountDownLatch latch;
		
		public AsynCallback(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void onError(Exception exception) {
			// TODO Auto-generated method stub
			System.out.println("onError :" + exception.getMessage());  
            latch.countDown();  
		}

		@Override
		public void onComplete(Void response) {
			// TODO Auto-generated method stub
			System.out.println("on complete");
			try {
				System.out.println("AsyncCall result=:"+response);
			}finally{
				latch.countDown();
			}
		}
		
	}
}
