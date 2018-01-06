package com.sc.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sc.bean.MyIp;

public class GetIP {

	public static List<MyIp> getIps() throws IOException {
		Document doc = Jsoup.connect("http://www.xicidaili.com/").userAgent("Mozilla").cookie("auth", "token")
				.timeout(3000).get();
		Element ele=doc.getElementById("ip_list");
		List<MyIp> ipList = new ArrayList<>();
		
		Elements eles=ele.getElementsByTag("tr");
		for(int i=0;i<eles.size();i++){
			Element trEl =eles.get(i);
			
			if(i!=1&&trEl.childNodeSize()==17){
				MyIp Myip = new MyIp();
				Element ip=trEl.child(1);
				Element port=trEl.child(2);
				String ip_addr = ip.html();
				String ip_port = port.html();
				Myip.setAddress(ip_addr);
				Myip.setPort(ip_port);
				ipList.add(Myip);
			}
			
		}
		
		return ipList;
	}
}
