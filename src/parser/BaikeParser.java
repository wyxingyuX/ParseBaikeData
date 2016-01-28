package parser;

import java.io.PrintWriter;
import java.util.List;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import other.Log;
import utils.FileTool;
import utils.IO;

public class BaikeParser {
	private Log log;
	private String sourceDir;
	private String destDir;
	public BaikeParser(String sourceDir,String destDir) throws Exception{
		this.sourceDir=sourceDir;
		this.destDir=destDir;
		this.log=new Log(destDir);
	}
	public String parse(HtmlPage page) throws Exception{
		//System.out.println(page.getUrl().toString());
		StringBuilder stb=new StringBuilder();
		String path1="/html/body/div";
		List<HtmlElement> elementLists=(List<HtmlElement>) page.getByXPath(path1);if(isNull(elementLists)){return "";}
		HtmlElement bodyWrapperElm=null;
		boolean isNormal=true;
		for(HtmlElement e:elementLists){
			if(e.getAttribute("class").contains("body-wrapper")){
				bodyWrapperElm=e;
				if(!e.getAttribute("class").equals("body-wrapper")){
					isNormal=false;
				}
				break;
			}
		}
		if(isNull(bodyWrapperElm)){	return "";}
		HtmlElement contentWrapper=getElementContain(bodyWrapperElm,"div","class","content-wrapper");if(isNull(contentWrapper)){return "";}
		HtmlElement content=getElementContain(contentWrapper,"div","class","content");if(isNull(content)){return "";}
		HtmlElement mainContents=getElementContain(content,"div","class","main-content");if(isNull(mainContents)){return "";}
		HtmlElement topToll=getElementContain(mainContents,"div","class","top-tool");
		if(isNormal&&topToll!=null){
			HtmlElement lemmaWgtLemmaTitle=getElementContain(mainContents,"dl","class","lemmaWgt-lemmaTitle");
			if(null!=lemmaWgtLemmaTitle) {
				HtmlElement lemmaWgLemmaTitlTitle=getElementContain(lemmaWgtLemmaTitle,"dd","class","lemmaWgt-lemmaTitle-title");
				if(null!=lemmaWgLemmaTitlTitle){
					List<HtmlElement> lt=lemmaWgLemmaTitlTitle.getElementsByTagName("h1");
					if(lt!=null){
						stb.append(filter(lt.get(0).asText())+",");
					}
				}
				
			}
			HtmlElement lemmaSummary=getElementContain(mainContents,"div","class","lemma-summary");
			if(null!=lemmaSummary) stb.append(filter(lemmaSummary.asText()));
			List<HtmlElement> paras=(List<HtmlElement>) mainContents.getByXPath("./div[@class=\"para\"]");
			if(null!=paras){
				for(HtmlElement e:paras){
					stb.append(filter(e.asText()));
				}
			}
		}else{
			stb.append(filter(mainContents.asText()));
		}
		return stb.toString();
	}
	protected HtmlElement getElementContain(HtmlElement elment,String label,String attribute,String value){
		HtmlElement result=null;
		List<HtmlElement> elms=(List<HtmlElement>) elment.getByXPath("./"+label);
		for(HtmlElement e:elms){
			if(e.getAttribute(attribute).contains(value)){
				result=e;
				break;
			}
		}
		return result;
	}
	protected boolean isNull(Object e) throws Exception{
		if(e==null){
			String error="	this page error";
			System.out.println(error);
			return true;
		}
		return false;
	}
	public String parseNormalBaike(HtmlPage page){
		StringBuilder stb=new StringBuilder();
		String baikeMainContentpath="/html/body/div[@class=\"body-wrapper\"]/div[@class=\"content-wrapper\"]/div[@class=\"content\"]/div[@class=\"main-content\"]";
		List<HtmlElement> elementLists=(List<HtmlElement>) page.getByXPath(baikeMainContentpath);
		HtmlElement mainContents=elementLists.get(0);
		HtmlElement lemmaSummary=(HtmlElement) mainContents.getByXPath("./div[@class=\"lemma-summary\"]").get(0);
		stb.append(filter(lemmaSummary.asText()));
		List<HtmlElement> paras=(List<HtmlElement>) mainContents.getByXPath("./div[@class=\"para\"]");
		for(HtmlElement e:paras){
			stb.append(filter(e.asText()));
		}
		return stb.toString();
	}
	public void batchParse2File(List<HtmlPage> pages,String destFile) throws Exception{
		PrintWriter writer=FileTool.getPrintWriterForFile(destFile);
		for(HtmlPage page:pages){
			IO.writeStr(parse(page), writer);
		}
		writer.close();
	}

	public void parse2File(HtmlPage page,PrintWriter writer) throws Exception{
		String content=this.parse(page);
		if(content.equals("")) {
			log.write(page.getUrl().toString()+" "+" error.(Ignore this error) \r\n");
			return;
		}
		IO.writeStr(content+"\r\n", writer);
	}

	public String filter(String str){
		return str.replace("\r\n", "");
	}
}
