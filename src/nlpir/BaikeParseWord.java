package nlpir;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import nlpir.NlpirTest.CLibrary;
import utils.FileTool;

public class BaikeParseWord {
	protected static final String Regex_url = "(http|https):/+[\\w\\-_]?(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	//protected static final String Regex_punt = "[[[\\p{P}+]|[$\\^=<>~+`～｀＄＾＋＝｜＜＞￥×★☆❤❥✰♥❃❂❁❀✿☀☁☂☃♂♀]]&&[^@\\[\\]]]";
	protected static final String Regex_email = "\\w+\\.*\\w+@\\w+\\.\\w+";
	public CLibrary instance;
	public BaikeParseWord(CLibrary instance){
		this.instance=instance;
	}
	public void parse(String filePath,String destFilePath) throws IOException{
		String result="";
		BufferedReader reader=FileTool.getBufferedReaderFromFile(filePath);
		PrintWriter writer=FileTool.getPrintWriterForFile(destFilePath);
		String baikePage="";
		int lineNum=1;
		int mod=500;
		while((baikePage=reader.readLine())!=null){
			if(baikePage.equals("")) continue;
			result=instance.NLPIR_ParagraphProcess(baikePage, 1);
			result=this.filterText(result);
			writer.write(result);writer.write("\r\n");
			result="";
			if(lineNum%mod==0)System.out.println("complete fc "+lineNum+" line");
			++lineNum;
		}
		writer.flush();
		writer.close();
		System.out.println("Total complete fc "+lineNum+" line");
	}
	public String filterText(String text){
		//可不可以考虑用StringBuilder来做？
		text=text.replaceAll("/[0-9a-z]{1,5}", "");//去掉词性 pos tag
		text=text.replaceAll(Regex_url, "");
		//text=text.replaceAll(Regex_email, "");
		return text;
	}

}
