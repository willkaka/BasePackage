package com.base.readfile;

import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class OperateTxtFile {

	/**
	 * 使用FileInputStream，读TXT
	 * @param filePath
	 * @return
	 */
    public static String readFile(String filePath){
        //设置默认编码
        String charset = "UTF-8";
        if (filePath.equals(null)) {
            filePath = "./File/test1.txt";
        }
        
        File file = new File(filePath);
         
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 
                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    sb.append(text);
                }
                return sb.toString();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }
    
    public static StringBuffer readFile2(String filePath){
        StringBuffer sb = new StringBuffer();
        //设置默认编码
        String charset = "UTF-8";
        if (filePath.equals(null)) {
            filePath = "./File/test1.txt";
        }
        
        File file = new File(filePath);
         
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                 
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    sb.append(text);
                }
                return sb;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return sb;
    }
    
    /**
     * 按行读取TXT文件记录，一行作为ArraryList的一个元素
     * @param filePath TXT文件路径
     * @return
     */
    public static ArrayList readFile3(String filePath){
        //StringBuffer sb = new StringBuffer();
        ArrayList<String> sb = new ArrayList<String>();
        //设置默认编码
        String charset = "UTF-8";
        if (filePath.equals(null)) {
            filePath = "./File/test1.txt";
        }
        
        File file = new File(filePath);
         
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                 
                String text = null;
                int i=0;
                while((text = bufferedReader.readLine()) != null){
                    //sb.append(text);
                    //System.out.println("sb["+i+"]="+text);
                    sb.add(text);                    
                }
                return sb;
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        }
        return sb;
    }
    
  //文件不存在时候，主动创建文件。
    public static void writeToTxtFile1(String filePath,String content){    
        try {
            //String content = "测试使用字符串";
            File file = new File(filePath);
            //文件不存在时候，主动穿件文件。
            if(!file.exists()){
                System.out.println("Begin ----  Create TxtFile");
                file.createNewFile();
                System.out.println("CreatedTxtFile:"+file.getAbsolutePath());
            }
            String txtFileString = readFile(filePath);
            PrintWriter pw = new PrintWriter(file);
            pw.println(content);
            pw.flush();
            /*FileWriter fw = new FileWriter(file,false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(txtFileString +"\r\n" + content);*/
//            bw.close(); fw.close();
            //System.out.println("writeToTxtFile done!");
             
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
    
    public static void writeToTxtFile2(String filePath, String content) {
        try {
            File file = new File(filePath);
            // 文件不存在时候，主动创建文件。
            if (!file.exists()) {
                System.out.println("Begin ----  Create TxtFile");
                file.createNewFile();
                System.out.println("CreatedTxtFile:" + file.getAbsolutePath());
            }
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 使用FileWriter方式,写TXT
     */
    public static void writeToFile1(){
        
        try {
            String content = "测试使用字符串";
            File file = new File("./File/test1.txt");
            if(file.exists()){
                FileWriter fw = new FileWriter(file,false);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                //bw.write("我会写入文件啦\r\n"); // \r\n即为换行
                //bw.flush(); // 把缓存区内容压入文件
                bw.close(); fw.close();
                System.out.println("test1 done!");
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
    
    /**
     * 使用FileOutputStream来写入txt文件。
     */
    public static void writeToFile3(){    
       String content = "测试使用字符串";
       FileOutputStream fileOutputStream = null;
       File file = new File("./File/test3.txt");
        
       try {
           if(file.exists()){
               file.createNewFile();
           }
            
           fileOutputStream = new FileOutputStream(file);
           fileOutputStream.write(content.getBytes());
           fileOutputStream.flush();
           fileOutputStream.close();
       } catch (Exception e) {
           e.printStackTrace();
           // TODO: handle exception
       }
       System.out.println("test3 done");
     
    }
}
