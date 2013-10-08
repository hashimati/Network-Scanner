package com.ahmed.network.scanner;

import java.util.concurrent.ConcurrentLinkedQueue;

public class IPGenerator implements Runnable {
	IPv4AddressStructure from, to, current;
	ConcurrentLinkedQueue<IPv4AddressStructure> addressList;

	public IPGenerator(String from, String to,
			ConcurrentLinkedQueue<IPv4AddressStructure> addressList)
			throws Exception {
		this.from = new IPv4AddressStructure(from);
		this.to = new IPv4AddressStructure(to);
		this.current = this.from;
		this.addressList = addressList;
		if (current.isReachable()) {
			synchronized (addressList) {
				this.addressList.add(this.current);
			}
		}
	}

	public void generate() throws Exception {

		while (current.equals(to) == false) {
			current = current.getNext();
			//System.out.println(current);
			if (current.isReachable()) {
				System.out.println(current);
				synchronized (addressList) {
					this.addressList.add(this.current);
				}
				 Thread.sleep(50);
			}
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
