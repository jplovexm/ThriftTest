package com.yy.jp.TestBTrace;

import java.util.Random;

public class HelloWorldTest {
	public static void main(String[] args) {
		while(true){
			Random random = new Random();
			execute(random.nextInt(4000));
		}
	}
	
	public static int  execute(int sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sleep time is=>"+sleepTime);
		return 0;
	}
}
