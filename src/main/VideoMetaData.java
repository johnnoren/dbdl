package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VideoMetaData {

	public final String title;
	public final URL url;
	private VideoMetaData(String title, URL url) {
		this.title = title;
		this.url = url;
	}

	public static List<VideoMetaData> fromHtml(String html, String channel) {
		var namePattern = Pattern.compile("aria\\-label\\=\".*\" tabindex\\=\"\\-1\" class\\=\"ember\\-view "
				+ "video\\-thumb\\-wrapper");
		var urlPattern = Pattern.compile("https://www\\.dreambroker\\.com/channel/" + channel + "/[a-zA-Z0-9"
				+ "]{8}");
		var nameOrUrlPattern = Pattern.compile(namePattern.pattern() + "|" + urlPattern);

		var tokenList = new Scanner(html).findAll(nameOrUrlPattern)
				.map(MatchResult::group)
				.map(s -> (s.startsWith("aria")) ? s.substring(12, s.length() - 54) : s)
				.collect(Collectors.toList());

		var videoMetaDataList = IntStream.iterate(1, i -> i + 2)
				.limit(tokenList.size() / 2)
				.mapToObj(i -> {
					try {
						return new VideoMetaData(tokenList.get(i - 1), new URL(tokenList.get(i)));
					} catch (MalformedURLException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());

		return videoMetaDataList;
	}

	@Override
	public String toString() {
		return "VideoMetaData{" + "title='" + title + '\'' + ", url=" + url + '}';
	}
}
