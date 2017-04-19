package zlbyzc.gui.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches
{
    public static void main( String args[] ){

      // 按指定模式在字符串查找
      //String line = "http://dsa:sda/terminals/1544";
      //String pattern = "http(s?)://(.*)terminals/(\\d+)(.*)";
    	
    	//http://127.0.0.1:8888/nbconvert/script/Untitled2.ipynb?download=true
    	//http://127.0.0.1:8888/files/Untitled2.ipynb?download=1
    	//http://127.0.0.1:8888/nbconvert/html/Untitled2.ipynb?download=true
    	//http://127.0.0.1:8888/nbconvert/pdf/Untitled2.ipynb?download=true
    	//http://127.0.0.1:8888/nbconvert/markdown/Untitled2.ipynb?download=true
    	//http://127.0.0.1:8888/nbconvert/rst/Untitled2.ipynb?download=true
    	//
    	//http://127.0.0.1:8888/files/Untitled3.ipynb?download=1
    	//http://127.0.0.1:8888/edit/Untitled5.py
    	//http://127.0.0.1:8888/notebooks/Untitled2.ipynb
    	String line ="http://127.0.0.1:8888/notebooks/Untitled1.ipynb";
    	//line=line.replaceAll("files|string2", "nbconvert");
    	String pattern = "http(s?)://(.*)/notebooks/(.*).ipynb(.*)";
      // 创建 Pattern 对象
      Pattern r = Pattern.compile(pattern);
      // 现在创建 matcher 对象
      Matcher m = r.matcher(line);
      if(m.find()){
         System.out.println("Found value: " + m.group(0) );
         System.out.println("Found value: " + m.group(1) );
         System.out.println("Found value: " + m.group(2) );
         System.out.println("Found value: " + m.group(3) );
         System.out.println("Found value: " + m.group(4) );
         
      } 
      
      String input = "<a>aaa</a><a>aaaa</a>";  
      //String regex = "<a>(.*?)</a>";              // 非贪婪模式  
    String regex = "<a>(.*)</a>";               // 贪婪模式  
        
      Pattern p = Pattern.compile(regex);  
      Matcher mm = p.matcher(input);  
        
      while(mm.find()){  
          System.out.println(mm.group());  
      }  
   }
}