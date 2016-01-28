package parser;

import com.gargoylesoftware.htmlunit.WebClient;

public class MyWebClient {
	private static WebClient webClient=new WebClient();
	static{
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setPrintContentOnFailingStatusCode(false);
	}
	private MyWebClient(){
	}
	public static WebClient getWebClient() {
		return webClient;
	}
}
