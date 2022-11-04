import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
	public void download(String url, File outputFile) throws IOException {
		System.out.print("Downloading " + outputFile.toString() + ".. ");
		ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
		outputFile.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		System.out.println("Done.");
	}
}
