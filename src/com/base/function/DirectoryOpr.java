package com.base.function;

import java.io.File;
import java.util.ArrayList;

import com.base.readfile.OperateTxtFile;

public class DirectoryOpr
{
	
    /**
     * 返回指定路径下的所有文件清单
     * @param path
     * @param sType(ALL/DIR/FILE)
     * @return java.io.File
     */
    public static ArrayList<File> getAllFiles(String path,String sType) {
        ArrayList<File> aFileList = new ArrayList<File>();
        
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return null;
            } else {
                for (File fSubFile : files) {
                    if (fSubFile.isDirectory()) {
                    	if(sType.equals("ALL") || sType.equals("DIR")) aFileList.add( fSubFile );
                        ArrayList<File> aSubFileList = getAllFiles(fSubFile.getAbsolutePath(),sType);
                        if( aSubFileList != null){
                            aFileList.addAll( aSubFileList );
                        }
                    } else {
                        if(sType.equals("ALL") || sType.equals("FILE"))
                        	aFileList.add( fSubFile );
                    }
                }
            }
        } else {
            return null;
        }
        return aFileList;
    }
    
    /**
     * 返回指定路径下的所有文件路径清单
     * @param path
     * @param sType(ALL/DIR/FILE)
     * @return String
     */
    public static ArrayList<String> getAllFilesPath(String path,String sType) {
        ArrayList<String> aFileList = new ArrayList<String>();
        
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return null;
            } else {
                for (File fSubFile : files) {
                    if (fSubFile.isDirectory()) {
                    	if(sType.equals("ALL") || sType.equals("DIR")) aFileList.add( fSubFile.getAbsolutePath() );
                        ArrayList<String> aSubFileList = getAllFilesPath(fSubFile.getAbsolutePath(),sType);
                        if( aSubFileList != null){
                            aFileList.addAll( aSubFileList );
                        }
                    } else {
                        if(sType.equals("ALL") || sType.equals("FILE"))
                        	aFileList.add( fSubFile.getAbsolutePath() );
                    }
                }
            }
        } else {
            return null;
        }
        return aFileList;
    }
    
    /**
     * 删除目录中的空文件夹
     * @param path
     */
    public static void delEmptyDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) return;
        
        ArrayList<File> aFiles = getAllFiles(path,"DIR");
        if (null == aFiles || aFiles.size() == 0) return;
        
        for (int i=aFiles.size()-1; i>=0; i--) {
        	File fSubFile = aFiles.get(i);
        	if (fSubFile.isDirectory() && fSubFile.listFiles().length <= 0){
        		fSubFile.delete();
        		System.out.println("删除空文件夹："+fSubFile.getPath());
        	}
        }
    }
    
    public static void main(String args[]){
        String sPath = "D:/Java/DaShuSource/DSPM_20181228";
        
        ArrayList<String> aFile = DirectoryOpr.getAllFilesPath( sPath ,"ALL");
        
        for(String sFilePath:aFile){
            //System.out.println( sFilePath );
            OperateTxtFile.writeToTxtFile2( "D:/DSPM_20181228.txt",sFilePath);
        }
        
    }
}
