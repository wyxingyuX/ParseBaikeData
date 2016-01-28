package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;

import parser.BaikeParser;
import parser.MyWebClient;
import utils.FileTool;

public class Drive {

	public static void main(String[] args) throws Exception{
		String sourceDir="F:/ExpData/DataFromOther/zzq/baikeData/part-1/";
		String destDir=FileTool.backReplaceDirNode(sourceDir, "F:", "D:");
		destDir=FileTool.forwardInsertDirNode(destDir, "ExpData", "ZMyDisk");

		int fileNum=1;
		int mod=500;
		BaikeParser parser=new BaikeParser(sourceDir,destDir);
		WebClient webClient=MyWebClient.getWebClient();
		PrintWriter writer=FileTool.getPrintWriterForFile(destDir+"data.txt");
		
		File dir=new File(sourceDir);
		File[] dirs=dir.listFiles();
		for(File d:dirs){
			if(d.isDirectory()){
				List<String> files=FileTool.getFileNames(d.getCanonicalPath());
				for(String file:files){
					String url="file:/"+file;
					parser.parse2File(webClient.getPage(url),writer);
					if(fileNum%mod==0) System.out.println("complete parse "+ fileNum+" file.");
					++fileNum;
				}
			}
		}
		System.out.println("Total complete parse "+ fileNum+" file.");
		writer.flush();
		writer.close();
	}

}
