package com.jp.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;

public class HelloServiceServer {
	public static void main(String[] args) {
		HelloServiceServer server = new HelloServiceServer();
		//server.startTSimpleServer();
		//server.startPoolServer();
		server.startNonBServer();
	}
	
	public void startTSimpleServer(){
		try {  
            System.out.println("HelloWorld TSimpleServer start ....");  
            TProcessor tprocessor = new Hello.Processor<Hello.Iface>(  
                    new HelloServiceImpl());  
            // 简单的单线程服务模型，一般用于测试    
            TServerSocket serverTransport = new TServerSocket(7911);  
            TServer.Args tArgs = new TServer.Args(serverTransport);  
            tArgs.processor(tprocessor);  
            tArgs.protocolFactory(new TBinaryProtocol.Factory());  
            TServer server = new TSimpleServer(tArgs);  
            server.serve();  
        } catch (Exception e) {  
            System.out.println("Server start error!!!");  
            e.printStackTrace();  
        }  
	}
	
	public void startPoolServer(){
		try {  
            System.out.println("HelloWorld TThreadPoolServer start ....");  
   
            TProcessor tprocessor = new Hello.Processor<Hello.Iface>(  
                    new HelloServiceImpl());  
            TServerSocket serverTransport = new TServerSocket(7911);  
            //TThreadPoolServer 线程池服务模型  
            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(  
                     serverTransport);  
            ttpsArgs.processor(tprocessor);  
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());  
            //线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。  
            TServer server = new TThreadPoolServer(ttpsArgs);  
            server.serve();  
        } catch (Exception e) {  
            System.out.println("Server start error!!!");  
            e.printStackTrace();  
        }  
	}
	
	public void startNonBServer(){
		try {  
            System.out.println("HelloWorld TNonblockingServer start ....");  
   
            TProcessor tprocessor = new Hello.Processor<Hello.Iface>(  
                    new HelloServiceImpl());  
            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(7911);  
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);  
            tnbArgs.processor(tprocessor);  
            tnbArgs.transportFactory(new TFramedTransport.Factory());  
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());  
            TServer server = new TNonblockingServer(tnbArgs);  
            server.serve();  
        } catch (Exception e) {  
            System.out.println("Server start error!!!");  
            e.printStackTrace();  
        }  
	}
	
	public void startTHsServer(){
		try {  
            System.out.println("HelloWorld THsHaServer start ....");  
   
            TProcessor tprocessor = new Hello.Processor<Hello.Iface>(  
                    new HelloServiceImpl());  
            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(7911);  
            THsHaServer.Args args = new THsHaServer.Args(tnbSocketTransport);  
            args.processor(tprocessor);  
            args.transportFactory(new TFramedTransport.Factory());  
            args.protocolFactory(new TBinaryProtocol.Factory());  
            TServer server = new THsHaServer(args);  
            server.serve();  
              
        } catch (Exception e) {  
            System.out.println("Server start error!!!");  
            e.printStackTrace();  
        } 
	}
}
