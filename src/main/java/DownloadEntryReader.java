import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class DownloadEntryReader implements Closeable {
	private final Scanner scanner;

	public DownloadEntryReader(Reader reader) {
		this.scanner = new Scanner(new BufferedReader(reader));
	}

	public Stream<String> read() {
		Stream.Builder<String> stream = Stream.builder();
		while (scanner.hasNext()) {
			var url = scanner.nextLine();
			stream.add(url);
		}
		return stream.build();
	}

	@Override
	public void close() {
		scanner.close();
	}
}
