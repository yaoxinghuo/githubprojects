package com.terrynow.vlcvideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.terrynow.vlcvideo.youku.YoukuVideoParser;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Jun 18, 2014 10:30:35 AM
 * @description
 */
public class Main {
	public static void main(String[] args) throws Exception {
		while (true) {
			String url = readUserInput("Input Youku video URL(<URL> [0|1|2] for video quality, default 2 high, 'exit' for quit):\n");
			if (url != null && "exit".equals(url.toLowerCase())) {
				System.out.println("bye!");
				break;
			}
			if (url != null && url.trim().length() > 0) {
				String stream = YoukuVideoParser.parserStreamFromUrl(url);
				if (stream == null) {
					System.out.println("can not parser stream from: " + url);
				} else {
					System.out.println("stream: " + stream);
					launchVLC(stream);
					break;
				}
			}
		}
		System.exit(0);
	}

	private static void launchVLC(String stream) throws Exception {
		ProcessBuilder pb = new ProcessBuilder(
				"/Applications/VLC.app/Contents/MacOS/VLC", "--video-on-top",
				"--disable-screensaver", "--no-video-title-show", stream);
		pb.redirectErrorStream(true);
		pb.start();
	}

	private static String readUserInput(String prompt) throws IOException {
		// 输出提示文字
		System.out.print(prompt);
		InputStreamReader is_reader = new InputStreamReader(System.in);
		return new BufferedReader(is_reader).readLine();
	}
}
