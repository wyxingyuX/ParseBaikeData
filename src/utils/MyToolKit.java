package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyToolKit {
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
	public static void mergeFiles(String sourceDir,String dest) throws IOException{
		List<File> dirs=FileTool.getDirs(sourceDir);
		PrintWriter writer=FileTool.getPrintWriterForFile(dest);
		int lineNum=1;
		int mod=500;
		for(File d:dirs){
			String partFc=d.getAbsolutePath()+"/"+"data.txt";
			BufferedReader reader=FileTool.getBufferedReaderFromFile(partFc);
			String line="";
			while((line=reader.readLine())!=null){
				writer.write(line+"\r\n");
				if(lineNum%mod==0)System.out.println("complete copy "+lineNum+" line");
				++lineNum;
			}
			System.out.println("complete copy "+partFc);
		}
		writer.flush();
		writer.close();
		System.out.println("Total complete copy "+lineNum+" line");
	}
	public static void mergeFiles(String sourceDir) throws IOException{
		String defaultDest=sourceDir+"/baikeFc.txt";
		mergeFiles(sourceDir,defaultDest);
	}
	public static void appendFile(String sourceFile,String destFile) throws IOException{
		PrintWriter writer=FileTool.getPrintWriterForFile(destFile, true);
		BufferedReader reader=FileTool.getBufferedReaderFromFile(sourceFile);
		String line="";
		int  lineNum=1;
		int mod=500;
		while((line=reader.readLine())!=null){
			writer.write(line+"\r\n");
			if(lineNum%mod==0)System.out.println("complete append "+lineNum+" line");
			++lineNum;
		}
		System.out.println("Complete append "+sourceFile+" to "+destFile+", totla line is "+lineNum);
		writer.flush();
		writer.close();
	}
}
