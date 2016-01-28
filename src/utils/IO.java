package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IO {
   public static void writeStr(String str,String destFile,boolean isAppend) throws UnsupportedEncodingException, FileNotFoundException{
	   PrintWriter writer=FileTool.getPrintWriterForFile(destFile,isAppend);
	   writer.write(str);
	   writer.flush();
	   writer.close();
   }
   public static void writeStr(String str,PrintWriter writer){
	   writer.write(str);
   }
}
