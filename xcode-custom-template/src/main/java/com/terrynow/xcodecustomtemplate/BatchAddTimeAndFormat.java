package com.terrynow.xcodecustomtemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Jan 26, 2014 5:31:16 PM
 * @description 将Xcode的模板文件批量修改，一个是找到Created by ___FULLUSERNAME___ on
 *              ___DATE___. 添加一个时间___TIME___ 另一件事情是格式化代码，使用之前确保IOS_TEMPLATE_PATH
 *              和MACOS_TWMPLATE_PATH的文件已经备份 搞成类似下面这样，和eclipse差不多
 */
//
// TRVSeciewController.m
// HelloWorld
//
// @author Terry, Copyright (c) 2014 Terry. All rights reserved.
// @date 2014-1-26 21:28
// @description
//
public class BatchAddTimeAndFormat {
	public static final String TEST_TEMPLATE_PATH = "/Users/Terry/Downloads/File Templates";

	// IOS的模板文件
	public static final String IOS_TEMPLATE_PATH = "/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/Library/Xcode/Templates/File Templates";
	// MACOS的模板文件
	public static final String MACOS_TWMPLATE_PATH = "/Applications/Xcode.app/Contents/Developer/Library/Xcode/Templates/File Templates";
	// 扩展名
	public static final String[] TEMPLATE_EXT = new String[] { ".m", ".h",
			".cpp", ".pch", ".c" };
	private static boolean uncrustify = true;// 是否需要格式化代码
	private static String uncrustifyConfig = null;

	public static void main(String[] args) {
		uncrustifyConfig = BatchAddTimeAndFormat.class.getClassLoader()
				.getResource("uncrustify.cfg").getPath();
		new BatchAddTimeAndFormat().start();
	}

	public void start() {
		// for test
		// findFileAndChangeTemplate(new File(TEST_TEMPLATE_PATH));

		// 正式的，请先备份哦
		findFileAndChangeTemplate(new File(IOS_TEMPLATE_PATH));
		findFileAndChangeTemplate(new File(MACOS_TWMPLATE_PATH));
	}

	private String findExt(String fileName) {
		int lastDot = fileName.lastIndexOf(".");
		if (lastDot > -1)
			return fileName.substring(lastDot);
		else
			return fileName;
	}

	private boolean isTemplateExt(String ext) {
		for (String str : TEMPLATE_EXT) {
			if (str.equals(ext))
				return true;
		}
		return false;
	}

	private void findFileAndChangeTemplate(File parentDir) {
		File[] files = parentDir.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				String ext = findExt(pathname.getName());
				return isTemplateExt(ext);
			}
		});
		if (files == null)
			return;
		for (File file : files) {
			if (file.isDirectory()) {
				findFileAndChangeTemplate(file);
			} else {
				changeTemplateByAddTime(file);
			}
		}
	}

	private void changeTemplateByAddTime(File file) {
		BufferedReader reader = null;
		StringBuffer sb = null;
		boolean detected = false;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line;
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;
				if (line.contains("Created by ___FULLUSERNAME___ on ___DATE___")) {
					detected = true;
					line = "//  @author ___FULLUSERNAME___,___COPYRIGHT___";
				} else if (line.contains("___COPYRIGHT___")) {
					line = "//  @date ___DATE___ ___TIME___\n//  @description ";
				}
				if (sb == null) {
					sb = new StringBuffer();
					sb.append(line);
				} else {
					sb.append("\n").append(line);
				}
			}

			reader.close();
			reader = null;
			if (detected && sb != null) {
				BufferedWriter bw = null;
				try {
					bw = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(file), "UTF-8"));
					bw.write(sb.toString());
					bw.flush();
					System.out.println("write for file: "
							+ file.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bw != null)
						try {
							bw.close();
						} catch (Exception e) {
						}
				}
			}

			if (uncrustify)
				uncrustify(file.getAbsolutePath(), uncrustifyConfig);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
	}

	private void uncrustify(String file, String config) {
		System.out.println("uncrustify -c " + config + " --no-backup " + file);
		try {
			Process pid = null;
			String[] cmd = { "uncrustify",/* "-l", "OC", */"-c", config,
					"--no-backup", file };
			pid = Runtime.getRuntime().exec(cmd);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(pid.getInputStream()), 1024);

			String line = null;
			while (bufferedReader != null
					&& (line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

			pid.waitFor();
			pid.exitValue();
			pid.destroy();// to kill the subprocess
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
