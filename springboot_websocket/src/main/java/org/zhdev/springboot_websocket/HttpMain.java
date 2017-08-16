package org.zhdev.springboot_websocket;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpMain {

    public static void main(String[] args) throws Exception {
        //我现在在dev分支上面
        //URL url = new URL("http://lemanoosh.com/app/themes/lemanoosh2017/resources/publications.php?action=list_publications&page=2");
        //URL url = new URL("http://www.baidu.com");

        URL url = new URL("http://lemanoosh.com/app/uploads");
        URLConnection conn = url.openConnection();//获得UrlConnection 连接对象

        conn.setReadTimeout(20000);

        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
        conn.setRequestProperty("Accept","application/json, text/plain, */*");
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        //conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
        //conn.setRequestProperty("X-Requested-With","XMLHttpRequest");
        //conn.setRequestProperty("Accept-Charset","UTF-8");
        //conn.setRequestProperty("Content-Type","text/html;charset=utf-8");
        conn.setRequestProperty("Cookie","__cfduid=df8a3b9d3010770cee3f42a632eb2ea621502803854; PHPSESSID=2a6jea5f3ebhkcjsaf55v85qh2; currency=usd; _ga=GA1.2.874899378.1502803683; _gid=GA1.2.1620255088.1502803683; __stripe_mid=4ba6501c-3c3f-4579-b87a-4f3cfb6bc5ec; __stripe_sid=de3fe314-f27e-4e11-a050-ed49e695265c");
        conn.setRequestProperty("Content-Type","multipart/form-data; boundary=---------------------------314183144421313");

        InputStream is = conn.getInputStream();//获得输入流
        //BufferedReader br = new BufferedReader(new InputStreamReader(is));//buffered表示缓冲类

        InputStreamReader isr = new InputStreamReader(is,"utf-8");
        BufferedReader br = new BufferedReader(isr);

        String str;

        int index = 0;
        while (null != (str = br.readLine())) {
            //String s = new String(str.getBytes("GB18030"),"UTF-8");
            //System.out.println(str);

            String startCode = "<tr><td valign=\"top\"><img src=\"/icons/image2.gif\" alt=\"[IMG]\"></td><td><a href=\"";
            String endCode = "\">";
            int urlIndexStart = str.indexOf(startCode)+startCode.length();
            int urlIndexEnd = str.indexOf(endCode,urlIndexStart);

            String subUrlString = "";
            //System.out.println("开始 : "+urlIndexStart+" 结束 : "+urlIndexEnd);
            if(urlIndexStart>0 && urlIndexEnd>0){
                subUrlString = str.substring(urlIndexStart,urlIndexEnd);
                String tempUrl = "http://lemanoosh.com/app/uploads/"+subUrlString;
                int suffixIndex = tempUrl.indexOf("-");
                if(suffixIndex==-1){
                    System.out.println(tempUrl);
                }
            }

            index++;
            if(index==20000){
                break;
            }
        }
        br.close();
        is.close();
    //<a class="wall-item__view" data-publication-id="63117" href="https://lemanoosh.com/app/uploads/Artem_Smirnov_Truck_For_Audi_01.jpg">
    }
}
