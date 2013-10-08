package com.ahmed.network.scanner;

import java.util.concurrent.*;
import java.util.*; 
public class IPPingerService implements Runnable {

	
	ExecutorService pool; 
	ArrayList<IPv4AddressStructure> list;; 
	ConcurrentLinkedQueue<IPv4AddressStructure> addressList; 
	
	public IPPingerService(ArrayList<IPv4AddressStructure> list,  ConcurrentLinkedQueue<IPv4AddressStructure> addressList)
	{
		this.list = list; 
		this.addressList = addressList;
		
		pool = Executors.newFixedThreadPool(list.size()); 
		
	}
	
	
	public void startPing() throws InterruptedException
	{
		
		for(IPv4AddressStructure ip : list)
		{
			
			pool.execute(new IPPinger(ip, addressList)); 
			Thread.sleep(100); 
		}
		
		pool.shutdown();
		pool.awaitTermination(00, TimeUnit.SECONDS);
	}
	
	@Override
	public void run()
	{
		try
		{
			startPing(); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace(); 	
		}
		
	}
}
