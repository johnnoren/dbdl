import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

public class DBDL {
	public static void main(String[] args) throws IOException {
		new DBDL().run();
	}
	private void run() throws IOException {

		/*// Get the url
		System.out.println("Enter channel url: ");
		var scanner = new Scanner(System.in);
		var channelUrl = scanner.next();*/


		String strURL = "https://dreambroker.com/channel/rjvel23m" ;
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX)) {
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			// Get the html
			HtmlPage page = webClient.getPage(strURL);
			webClient.waitForBackgroundJavaScript(10 * 1000);

			// Retrieve all <div> elements containing video information
			List<HtmlElement> elements = page.getByXPath("//div[@class=\"ember-view video-thumb-wrapper\"]");

			if (!elements.isEmpty()) {
				for (HtmlElement element : elements) {
					String videoName = element.getAttribute("aria-label");

					HtmlElement videoURLtag = element.getFirstByXPath(".//a");
					String videoURL = videoURLtag.getAttribute("href");

					System.out.println("Name: " + videoName);
					System.out.println("URL: " + videoURL);
					System.out.println("");
				}
			}
		}
	}
}
