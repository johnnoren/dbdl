package io;

import java.io.*;
import java.net.URL;

public class HtmlReader extends Reader implements Closeable {
	private final BufferedReader reader;

	protected HtmlReader(URL url) throws IOException {
		this.reader = new BufferedReader(new InputStreamReader(url.openStream()));
	}

	@Override
	public int read() throws IOException {
		return reader.read();
	}
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return 0;
	}
	@Override
	public void close() throws IOException {
		reader.close();
	}
}
