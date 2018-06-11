package com.base.function;

import java.util.ArrayList;

import com.base.consts.ConstDefined;
import com.base.readfile.OperateTxtFile;

public class PrcString {

	public static String ConvertToCamelCase(String inputstring) {
		if (inputstring == null)
			return null;
		int indexNum = 0;
		String out = "";
		String subs = "";
		ArrayList<String> stringList = getWords();
		for (int beg = 0; beg < inputstring.length(); beg++) {
			for (int i = inputstring.length(); i >= beg + 1; i--) {
				subs = inputstring.substring(beg, i);
				indexNum = stringList.indexOf(subs);
				if (indexNum >= 0) {
					out = out + firstLetterToUppercase(stringList.get(indexNum));
					beg = i - 1;
					break;
				} else if (indexNum < 0 && subs.length() == 1) {
					out = out + subs;
					beg = i - 1;
					break;
				}

			}
		}
		if (inputstring.trim().length() == out.trim().length())
			return out;
		else
			return inputstring;
	}
	    
	public static String firstLetterToUppercase(String s) {
		String out = "";
		for (int i = 0; i < s.length(); i++) {
			if (i == 0)
				out = s.substring(0, 1);
			else
				out = out + s.substring(i, i + 1).toLowerCase();
		}
		return out;
	}
	
	public static ArrayList<String> getWords(){
        ArrayList<String> wordList = new ArrayList<String>();
        ArrayList<String> wordListTemp = OperateTxtFile.readFile3(ConstDefined.TXT_FILE_PATH_WORDLIST);
        
        for (String line : wordListTemp){
            //String k = line.trim().substring(0, 2);
            if (line.trim().substring(0, 2).equals("//")) continue;
            String[] words = line.split(",");
            for (String word: words){
                if(word != "") wordList.add(word);
            }
        }       
        
        return wordList;
    }
	
	public static ArrayList<String> getWordsFromWordLibrary(){
        ArrayList<String> wordList = new ArrayList<String>();
        ArrayList<String> wordListTemp = OperateTxtFile.readFile3(ConstDefined.TXT_FILE_PATH_WORDLIBRARY);
        
        for (String line : wordListTemp){
            wordList.add(line.substring(0, line.indexOf('[')));
        }
        
        return wordList;
    }
	
/*	public static void main(String[] args){
		ArrayList<String> wList = getWordsFromWordLibrary();
	}*/
}
