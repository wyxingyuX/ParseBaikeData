package test;
import java.awt.Toolkit;
import java.io.IOException;

import utils.FileTool;
import utils.MyToolKit;
import utils.ToolKit;

public class testToolKit {
	public static void excMerge() throws IOException{
		String sourceDir="D:/ZMyDisk/ExpData/DataFromOther/zzq/baikeData/test/";
		MyToolKit.mergeFiles(sourceDir);
	}
	public static void excAppend(){
		String base="D:/ZMyDisk/ExpData/DataFromOther/zzq/baikeData/";
		//MyToolKit.appendFile(base+"part-3/fc.txt", base+"allFc.txt");
		//MyToolKit.appendFile(base+"part-1/fc.txt", base+"allFc.txt");
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}

}
