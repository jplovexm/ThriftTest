package com.jp.thrift.demo;

import org.apache.thrift.TException;

public class HelloServiceImpl implements Hello.Iface{

	public String helloString(String para) throws TException {
		return para;
	}

	public int helloInt(int para) throws TException {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return para;
	}

	public boolean helloBoolean(boolean para) throws TException {
		return para;
	}

	public void helloVoid() throws TException {
		System.out.println("Hello World");
	}

	public String helloNull() throws TException {
		return null;
	}

}