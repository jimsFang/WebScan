package com.sc.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sc.bean.MyIp;

public class Test {
	
	public static void main(String[] args) {
		try {
			getIps();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	///
	public static List<MyIp> getIps() throws IOException {
		Document doc = Jsoup.connect("https://kuaibao.qq.com/s/20171214A07J1S00").userAgent("Mozilla").cookie("auth", "token")
				.timeout(3000).get();
		List<MyIp> ipList = new ArrayList<>();
		System.out.println(doc);
		
		
		return ipList;
	}
}
