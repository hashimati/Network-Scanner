package com.ahmed.network.scanner;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IPGenerator implements Runnable {
	IPv4AddressStructure from, to, current;
	ConcurrentLinkedQueue<IPv4AddressStructure> addressList;
	ExecutorService pool; 

	public IPGenerator(String from, String to,
			ConcurrentLinkedQueue<IPv4AddressStructure> addressList)
			throws Exception {
		this.from = new IPv4AddressStructure(from);
		this.to = new IPv4AddressStructure(to);
		this.current = this.from;
		this.addressList = addressList;
		if (current.isReachable()) {
			synchronized (addressList) {
				System.out.println(current); 
				this.addressList.add(this.current);
			}
		}
		
		pool =Executors.newFixedThreadPool(5000); 
	}

	public void generate() throws Exception {

		
		mainLoop:
		while (current.equals(to) == false) {
			ArrayList<IPv4AddressStructure> list = new ArrayList<IPv4AddressStructure>(); 
			insideLoop:
			for(int i =0; i < 40; i++){
				
				current = current.getNext();
				list.add(current);
				if(current.equals(to))
					break insideLoop; 
				
			}
			try{
				pool.execute(new IPPingerService(list, addressList)); 
			}
			catch(Exception ex)
			{
				ex.printStackTrace(); 
				
			}
			pool.shutdown();
			pool.awaitTermination(00, TimeUnit.SECONDS);
			
		//	if(current.equals(to)) break mainLoop; 
			
			//System.out.println(current);
			//if (current.isReachable()) {
			//	System.out.println(current);
			//	synchronized (addressList) {
			//		this.addressList.add(this.current);
			//	}
			//	 Thread.sleep(50);
		//	}
		}
	}

	@Override
	public void run() {
		try {
			generate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String... args) throws Exception {

		ConcurrentLinkedQueue<IPv4AddressStructure> list = new ConcurrentLinkedQueue<IPv4AddressStructure>();
		IPGenerator g = new IPGenerator("192.168.1.0", "192.168.1.255", list);
		g.run();
		System.out.println(g.addressList);
		// System.out.println(Runtime.getRuntime().totalMemory());

	}

}
