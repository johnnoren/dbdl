import java.io.*;

public class DownloadEntryWriter implements Closeable {
	private final BufferedWriter writer;

	public DownloadEntryWriter(Writer writer) {
		this.writer = new BufferedWriter(writer);
	}

	public void write(String url) {
		var stringToWrite = url + "\n";
		try {
			writer.append(stringToWrite);
			writer.flush();
		} catch (IOException e) {
			System.out.println("An error occurred when writing to the file.");
			System.exit(1);
		}
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
}

