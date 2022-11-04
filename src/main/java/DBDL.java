import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class DBDL {
	public static void main(String[] args) throws IOException {
		new DBDL().run(args[0]);
	}

	private void run(String url) throws IOException {
		File downloadedVideos = new File("downloaded.txt");
		downloadedVideos.createNewFile();
		var downloadEntryWriter = new DownloadEntryWriter(new FileWriter(downloadedVideos,true));
		var downloadEntryReader = new DownloadEntryReader(new FileReader(downloadedVideos));
		var downloader = new Downloader();

		List<String> downloadedVideoUrls = downloadEntryReader.read()
				.collect(Collectors.toList());

		var parser = new VideoHyperlinkParser(new URL(url));
		var videoHyperlinks = parser.parse();

		videoHyperlinks.forEach(videoHyperlink -> {

			try {
				var videoURL = videoHyperlink.getUrl();
				var videoName = videoHyperlink.getName();
				if (!downloadedVideoUrls.contains(videoURL)) {
					File outputFile = new File(videoName + ".mp4");
					downloader.download(videoURL, outputFile);
					downloadEntryWriter.write(videoURL);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		System.out.println("All videos have been downloaded.");
	}
}
