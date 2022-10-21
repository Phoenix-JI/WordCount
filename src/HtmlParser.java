//Published by TANG Haoqing 2022-03-26
//version 1.0

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;


import net.htmlparser.jericho.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;



public class HtmlParser {
    //read html file
    public static String readTextFile(String sFileName, String sEncode){       
        StringBuffer sbStr = new StringBuffer();
        try
        {
            File ff = new File(sFileName);
            InputStreamReader read = new InputStreamReader(new FileInputStream(ff),sEncode);            
            BufferedReader ins = new BufferedReader(read);            
            String dataLine = "";
            while (null != (dataLine = ins.readLine())){
                sbStr.append(dataLine);
                sbStr.append("/r/n");
            }
            ins.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sbStr.toString(); 
    }
    
    public static void extractUrl(String inputPath, String outputPath) throws InterruptedException{
    	
    	///TimeUnit.SECONDS.sleep(10);
    	
        File root = new File(inputPath);
		File[] subfiles = root.listFiles();
		
		System.out.println(subfiles.length);
		
		for(File file:subfiles){

			String txtpath = file.getName().split(".html")[0]+".txt";
		   	//File input = new File("/Users/phoenixji/Desktop/DIstributed 3/topic1/input/1.html");
			//Document document  = Jsoup.parse(file, "UTF-8");
			
//            	Element link = doc.select("td.topic.starter > a").first();
//            	String url = link.attr("href");
			
			Config.LoggerProvider = LoggerProvider.DISABLED;

		    //Elements allLinks = document.getElementsByTag("a");
			try {
		    	Source source = new Source(file);
		        // get all 'a' tags
		        List<StartTag> aTags = source.getAllStartTags("a");
		        // get the URL ("href" attribute) in each 'a' tag
		        for (StartTag aTag : aTags) {
		        	try {
		        	String url = aTag.getAttributeValue("href").toString();
		        	writeUrl(txtpath, url ,outputPath);
		        	}catch (NullPointerException n) {
		        		continue;
		        	}
		        	
		    }
		    
			}catch (IOException e) {   // in case of malformed url
		        
				System.out.println(txtpath);
				System.err.println(e.getMessage());

		}  
         }
    }


    //save the urls
    public static void writeUrl(String txtpath, String url, String outputPath){
        try{
        	
            File file = new File(outputPath+txtpath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter write = new OutputStreamWriter(fos);
            BufferedWriter outs = new BufferedWriter(write);
            outs.write(url);
            outs.newLine();
            outs.flush();
            outs.close();
            write.close();
            fos.close();
            
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws IOException {
//    	
//
//
//    
//    	String cur_dir = System.getProperty("user.dir");
//    
//    	String Input_path = cur_dir +"/input/";
//    	
//    	String Output_path = cur_dir +"/input_txt/";
//    	new File(Output_path).mkdirs();
//    	
//        extractUrl(Input_path,Output_path);
//
//    }
}
