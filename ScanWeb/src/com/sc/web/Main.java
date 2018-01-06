package com.sc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sc.bean.MyIp;
import com.sc.tools.GetIP;


public class Main {
	
	public static void main(String[] args) {
        //1.想http代理地址api发起请求，获得想要的代理ip地址
//        String url = "http://api.xicidaili.com/free2016.txt";
//        List<MyIp> ipList = getIp(url);
		List<MyIp> ipList = new ArrayList<>();
		try {
			ipList = GetIP.getIps();
			System.out.println("代理IP数量:"+ipList.size());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String blogUrl = "";
        Scanner sc = new Scanner(System.in);
        
        System.out.print("请输入文章地址:");
        blogUrl = sc.nextLine();
        System.out.print("请输入轮番刷新的倍数:");
        int time = sc.nextInt();
        int count = 0;
        
        for(int i = 0; i< time; i++) {
            //2.设置ip代理
            for(final MyIp myIp : ipList) {
                System.setProperty("http.maxRedirects", "50");
                System.getProperties().setProperty("proxySet", "true");
                System.getProperties().setProperty("http.proxyHost", myIp.getAddress());
                System.getProperties().setProperty("http.proxyPort", myIp.getPort());
                
                try {
                    Document doc = Jsoup.connect(blogUrl)
                                      .userAgent("Mozilla")
                                      .cookie("auth", "token")
                                      .timeout(3000)
                                      .get();
                    if(doc != null) {
                        count++;
                        System.out.println("ip:"+myIp.getAddress()+" port:"+myIp.getPort()+"成功刷新次数: " + count);
                    }
                } catch (IOException e) {
                	System.out.println(myIp.getAddress() + ":" + myIp.getPort() + "报错");
                }
            }
        }
    }
	
	public static List<MyIp> getIp(String url) {
        List<MyIp> ipList = null;
        try {
            //1.向ip代理地址发起get请求，拿到代理的ip
            Document doc = Jsoup.connect(url)
              .userAgent("Mozilla")
              .cookie("auth", "token")
              .timeout(3000)
              .get();
             
            //2,将得到的ip地址解析除字符串
            String ipStr = doc.body().text().trim().toString();
             
            //3.用正则表达式去切割所有的ip
//            String[] ips = ipStr.split("\\s+");
              List<String> ips = new ArrayList();
              ips.add("14.29.84.50:8080");
              ips.add("123.112.19.37:53281");
              ips.add("101.81.105.233:9000");
              ips.add("124.89.33.75:9999");
              ips.add("221.9.12.4:9000");
              ips.add("61.155.164.112:3128");
              ips.add("220.249.185.178:9999");
              ips.add("218.56.132.154:8080");
             
            //4.循环遍历得到的ip字符串，封装成MyIp的bean
            ipList = new ArrayList<MyIp>();
            for(final String ip : ips) {
                MyIp myIp = new MyIp();
                String[] temp = ip.split(":");
                myIp.setAddress(temp[0].trim());
                myIp.setPort(temp[1].trim());
                ipList.add(myIp);
            }
        } catch (IOException e) {
            System.out.println("加载文档出粗");
        }
        System.out.println("获取IP数量:"+ipList.size());
        return ipList;
    }
	
}