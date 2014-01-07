package com.ahmed.network.scanner;

import java.net.InetAddress;

public class IPv4AddressStructure {
	
	int[] ip = new int[4];  
	public IPv4AddressStructure(int first, int second, int third, int forth) throws Exception
	{
		if(first > 255 || first < 0 || second > 255 || second < 0||third > 255 || third < 0||forth > 255 || forth < 0 )
			throw new Exception("This is not valid IPv4"); 
		else 
		{
			ip = new int[]{first, second, third, forth}; 
		}
	}
	
	private IPv4AddressStructure(int[] address) throws Exception 
	{
		
		this(address[0], address[1], address[2], address[3]);	
		
	}
	public IPv4AddressStructure(String IPAddress) throws Exception
	{
		if(!isValidIP(IPAddress))
			throw new Exception("This is not valid IPv4"); 
		else 
		{
			String list[] = IPAddress.split("\\."); 
			for(int i = 0; i <ip.length; i++)
			{
				
				ip[i] = Integer.parseInt(list[i]); 
				
			}
		}
	}
	
	
	private boolean isValidIP(String ip)throws Exception{
        	String regex = "(([0-9]|[0-9][0-9]|[0-1][0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])\\.)"
        	 + "(([0-9]|[0-9][0-9]|[0-1][0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])\\.)"
                + "(([0-9]|[0-9][0-9]|[0-1][0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])\\.)"
                + "([0-9]|[0-9][0-9]|[0-1][0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])";
        
        
        	return cleanedIP(ip).matches(regex);
    	}
	    private boolean mayBeIP(String ip)
    {
        String regex = "(([0-9]*)\\.)"
                + "(([0-9]*)\\.)"
                + "(([0-9]*)\\.)"
                + "([0-9]*)"; 
        
        
        
        return ip.matches(regex); 
    }
    private String cleanedIP(String IP) throws Exception
    {
        if(!mayBeIP(IP)) throw new Exception("Not an IP address"); 
        else {
        String[] splited = IP.split("\\."); 
        return (Integer.parseInt(splited[0])+"."
                +Integer.parseInt(splited[1])
                +"."+Integer.parseInt(splited[2])
                +"."+Integer.parseInt(splited[3]))
;       }
    }
	public IPv4AddressStructure getNext() throws Exception
	{
		/*loop:
		for(int i = ip.length-1; i >=0; i--)
		{
			if(i == 0){
				
				if(ip[i] == 255)
					break loop; 
			}
			
			ip[i]++; 
			if(ip[i] >255)
			{
				ip[i] = 0; 
			}
		} */
		
		ip[3]++; 
		if(ip[3]> 255){
			
			ip[3] = 0; 
			ip[2]++; 
			if(ip[2] > 255)
			{
				ip[2] = 0; 
				ip[1]++; 
				if(ip[1] > 255)
				{
					ip[1] = 0; 
					ip[0] ++; 
					if(ip[0] == 255)
						return new IPv4AddressStructure(ip);
				}
				
			}
		}
		return new IPv4AddressStructure(ip);
	}
	@Override
	public String toString() 
	{
		String result = ""; 
		for(int s : ip)
		{
			result +=s+".";
		}
		return result.substring(0, result.length()-1);
	}
//	public static void main(String[] args) throws Exception
//	{
//		
//		IPv4AddressStructure s = new IPv4AddressStructure("0.0.0.0"); 
//		while(s.ip[0] != 256 && s.ip[1] != 256 && s.ip[2] !=256 && s.ip[3] != 256)
//		{	
//			s = s.getNext(); 
//			System.out.println(s); 
//		}	
//			
//			
//	}
	public boolean isReachable() throws Exception
	{
		
		return InetAddress.getAllByName(toString())[0].isReachable(1000000); 
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof IPv4AddressStructure){
			IPv4AddressStructure op = (IPv4AddressStructure)obj; 
			
			for(int i = 0; i < ip.length; i++)
			{
				if(ip[i] !=op.ip[i])
					return false; 
			}
			return true; 
		}
		else 
			return false; 
	}
	
}
