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
	 * ʹ��FileInputStream����TXT
	 * @param filePath
	 * @return
	 */
    public static String readFile(String filePath){
        //����Ĭ�ϱ���
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
        //����Ĭ�ϱ���
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
     * ���ж�ȡTXT�ļ���¼��һ����ΪArraryList��һ��Ԫ��
     * @param filePath TXT�ļ�·��
     * @return
     */
    public static ArrayList readFile3(String filePath){
        //StringBuffer sb = new StringBuffer();
        ArrayList<String> sb = new ArrayList<String>();
        //����Ĭ�ϱ���
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
    
  //�ļ�������ʱ�����������ļ���
    public static void writeToTxtFile1(String filePath,String content){    
        try {
            //String content = "����ʹ���ַ���";
            File file = new File(filePath);
            //�ļ�������ʱ�����������ļ���
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
            // �ļ�������ʱ�����������ļ���
            if (!file.exists()) {
                System.out.println("Begin ----  Create TxtFile");
                file.createNewFile();
                System.out.println("CreatedTxtFile:" + file.getAbsolutePath());
            }
            // ��һ����������ļ���������д��ʽ
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            // �ļ����ȣ��ֽ���
            long fileLength = randomFile.length();
            // ��д�ļ�ָ���Ƶ��ļ�β��
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ʹ��FileWriter��ʽ,дTXT
     */
    public static void writeToFile1(){
        
        try {
            String content = "����ʹ���ַ���";
            File file = new File("./File/test1.txt");
            if(file.exists()){
                FileWriter fw = new FileWriter(file,false);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                //bw.write("�һ�д���ļ���\r\n"); // \r\n��Ϊ����
                //bw.flush(); // �ѻ���������ѹ���ļ�
                bw.close(); fw.close();
                System.out.println("test1 done!");
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
    
    /**
     * ʹ��FileOutputStream��д��txt�ļ���
     */
    public static void writeToFile3(){    
       String content = "����ʹ���ַ���";
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
