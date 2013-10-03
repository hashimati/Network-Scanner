package com.ahmed.network.scanner;

import java.util.concurrent.ConcurrentLinkedQueue;
//d


public class IPGenerator implements Runnable
{
	IPv4AddressStructure from, to, current;

	ConcurrentLinkedQueue<IPv4AddressStructure> addressList; 

	
	public IPGenerator(String from, String to, ConcurrentLinkedQueue<IPv4AddressStructure> addressList) throws Exception
	{
		this.from = new IPv4AddressStructure(from); 
		this.to = new IPv4AddressStructure(to); 
		this.current = this.from; 
		this.addressList = addressList; 
		
	}
	
	public void next()
	{
		
	}
	@Override
	public void run()
	{
		
		
	}
}
