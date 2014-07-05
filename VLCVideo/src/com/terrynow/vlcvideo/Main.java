package com.terrynow.vlcvideo;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
	private static boolean threadStopFlag = false;

	public static void main(String[] args) throws Exception {
		while (true) {
			String clipContent = retriveContentFromClipBoard();
			String clipStream = YoukuVideoParser
					.parserStreamFromUrl(clipContent);
			String prompt = "Input Youku video URL(<URL> [0|1|2] for video quality, default 2 high, 'exit' for quit):\n";
			if (clipStream != null) {
				prompt = prompt
						+ "Detect stream from clipboard, press enter or [0|1|2] to launch.\n";
				new DefaultThread(clipStream).start();
			}
			String url = readUserInput(prompt);
			if (url != null && "exit".equals(url.toLowerCase())) {
				System.out.println("bye!");
				break;
			}
			threadStopFlag = true;
			if (url != null && url.trim().length() > 0) {
				// 直接输入了0/1/2这种
				if (url.trim().length() == 1 && clipStream != null) {
					String stream = YoukuVideoParser
							.parserStreamFromUrl(clipContent + " " + url.trim());
					if (stream == null) {
						System.out
								.println("can not parser stream from: " + url);
					} else {
						System.out.println("stream from clipboard: " + stream);
						launchVLC(stream);
						break;
					}
				} else {
					String stream = YoukuVideoParser.parserStreamFromUrl(url);
					if (stream == null) {
						System.out
								.println("can not parser stream from: " + url);
					} else {
						System.out.println("stream from input: " + stream);
						launchVLC(stream);
						break;
					}
				}
			} else if (clipStream != null) {// 直接用剪贴板里的播放
				System.out.println("stream from clipboard: " + clipStream);
				launchVLC(clipStream);
				break;
			}
		}
		System.exit(0);
	}

	private static String retriveContentFromClipBoard() {
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 获取剪切板中的内容
		Transferable clipTf = sysClip.getContents(null);

		String ret = null;
		if (clipTf != null) {
			// 检查内容是否是文本类型
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					ret = (String) clipTf
							.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
				}
			}
		}
		return ret;
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

	static class DefaultThread extends Thread {
		private String stream;

		public DefaultThread(String stream) {
			this.stream = stream;
		}

		@Override
		public void run() {
			System.out.print("Default stream from clipboard, wait seconds: ");
			for (int i = 0; i < 3; i++) {
				System.out.print(3 - i);
				try {
					Thread.sleep(300L);
					System.out.print(".");
					Thread.sleep(300L);
					System.out.print(".");
					Thread.sleep(300L);
					System.out.print(".");
				} catch (InterruptedException e) {
				}
				if (threadStopFlag)
					break;
			}
			if (!threadStopFlag) {
				try {
					launchVLC(stream);
				} catch (Exception e) {
				}
				System.out.println("\tDone");
				System.out.println("default stream from clipboard: " + stream);
				System.exit(0);
			}
		}
	}

}
