package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

public class ToolKit {
	public static void sortFileLineByKey(String filePath,String seperater) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
		Map<String,String> sortMap=new TreeMap<String,String>();
		String curLine="";
		String[] elms=null;
		while((curLine=br.readLine())!=null){
			elms=curLine.split(seperater);
			String key=elms[0];
			String value=curLine.substring(key.length()+1, curLine.length());
			sortMap.put(key, value);
		}
		br.close();

		OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
		for(Map.Entry<String, String> entry:sortMap.entrySet()){
			osw.write(entry.getKey()+seperater+entry.getValue());
			osw.write("\r\n");
		}
		osw.flush();
		osw.close();
	}
	public static void copy(String srcFile,String destFile) throws IOException{
		BufferedReader reader=FileTool.getBufferedReaderFromFile(srcFile);
		PrintWriter writer=FileTool.getPrintWriterForFile(destFile);
		String line="";
		while((line=reader.readLine())!=null){
			writer.write(line);
			writer.write("\r\n");
		}
		writer.close();
	}

}
