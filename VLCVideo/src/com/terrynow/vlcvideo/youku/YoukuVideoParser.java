package com.terrynow.vlcvideo.youku;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Jun 18, 2014 10:31:07 AM
 * @description
 */
public class YoukuVideoParser {
	// public static Pattern regPattern = Pattern
	// .compile("http:\\/\\/v\\.youku\\.com\\/v_show\\/id_([a-zA-Z0-9]+)\\.html");
	public static Pattern regPattern = Pattern
			.compile(".youku\\.com\\/v_show\\/id_([a-zA-Z0-9_]+)\\.html");

	public static String parserStreamFromUrl(String urlRaw) {
		String[] parts = urlRaw.split("\\s");
		String url = parts[0];
		Matcher matcher = regPattern.matcher(url);
		if (matcher.find()) {
			String vid = matcher.group(1);
			int index = vid.indexOf("_");
			if (index > 0)
				vid = vid.substring(0, index);
			return getStream(vid, parts.length < 2 ? null : parts[1]);
		}
		return null;
	}

	private static String getStream(String vid, String videoQuality) {
		if ("0".equals(videoQuality))
			return "http://v.youku.com/player/getM3U8/vid/" + vid
					+ "/type/flv/video.m3u8";
		else if ("1".equals(videoQuality))
			return "http://v.youku.com/player/getM3U8/vid/" + vid
					+ "/type/mp4/video.m3u8";
		// 用最高清晰版本
		return "http://v.youku.com/player/getM3U8/vid/" + vid
				+ "/type/hd2/video.m3u8";
	}

	public static void main(String[] args) {
		// 测试解析
		String url = "http://v.youku.com/v_show/id_XNzI3NjQwMDY4_ev_4.html?from=y1.3-idx-grid-1519-9909.86808-86807.7-1";
		// String url = "http://v.youku.com/v_show/id_XNzI3OTcyMTI0.html";
		System.out.println(parserStreamFromUrl(url));
	}
}
