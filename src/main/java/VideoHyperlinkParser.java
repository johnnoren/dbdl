import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class VideoHyperlinkParser {
	private final URL url;

	public VideoHyperlinkParser(URL url) {
		this.url = url;
	}

	public List<VideoHyperlink> parse() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX)) {
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			// Get the html
			HtmlPage page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(10 * 1000);

			// Retrieve all <div> elements containing video information
			List<HtmlElement> elements = page.getByXPath("//div[@class=\"ember-view video-thumb-wrapper\"]");

			// Isolate the video name and url
			return elements.stream()
					.map(element -> {
						var name = element.getAttribute("aria-label");
						var url = ((HtmlElement) element.getFirstByXPath(".//a")).getAttribute("href")
								+ "/get/fullhd.mp4";
						return new VideoHyperlink(name, url);
					})
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
