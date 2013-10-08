package com.ahmed.network.scanner;

import java.util.concurrent.ConcurrentLinkedQueue;

public class IPPinger implements Runnable
{
	ConcurrentLinkedQueue<IPv4AddressStructure> addressList; 
	IPv4AddressStructure ip; 
	
	
	public IPPinger(IPv4AddressStructure ip, ConcurrentLinkedQueue<IPv4AddressStructure> addressList)
	
	{
		this.ip = ip; 
		this.addressList = addressList; 
		
		
	}
	
	@Override
	public void run(){
		
		try{
			
			if(ip.isReachable())
			{
				synchronized (addressList){
					addressList.add(ip); 
				}
			}
		}catch(Exception ex){}
		
	}
}
