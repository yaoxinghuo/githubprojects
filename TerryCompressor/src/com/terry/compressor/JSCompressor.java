package com.terry.compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/*
 * @author xinghuo.yao yaoxinghuo at 126 dot com
 * @create Apr 2, 2009 2:53:31 PM
 * @description 
 *
 * JS压缩（处理空白，混淆变量等），需要yuicompressor.jar包
 */
public class JSCompressor {
	private static final String charset = "UTF-8";
	private static final boolean munge = true;
	private static final boolean preserveAllSemiColons = false;
	private static final boolean disableOptimizations = false;
	private static final boolean verbose = false;
	private static final int linebreakpos = -1;

	public static void main(String[] args) throws Exception {
		// compressFile("E:\\Java\\lab\\src\\js-original\\app\\login.js",
		// "E:\\Java\\lab\\WebContent\\js\\app\\login.js");
		compressFolder("E:\\Java\\lab\\src\\js-original\\", "E:\\js\\");
	}

	/*
	 * 压缩单个js文件
	 */
	public static void compressFile(String inputFileName, String outputFileName) {
		if (compress(new File(inputFileName), new File(outputFileName)))
			System.out.println("Successed.\tSource:" + inputFileName
					+ "\tDestination:" + outputFileName);
		else
			System.err.println("Failed.\tSource" + inputFileName);
	}

	/*
	 * 压缩文件夹下的所有js和子文件下的js，目标路径下如果没有目录会自己建，文件会覆盖掉哦
	 */
	public static void compressFolder(String inputFolderName,
			String outputFolderName) throws Exception {
		File inputFolder = new File(inputFolderName);
		File outputFolder = new File(outputFolderName);
		System.out.println("------Compress folder:\t"
				+ inputFolder.getCanonicalPath() + "\tDestination:"
				+ outputFolder.getCanonicalPath());
		for (File f : inputFolder.listFiles()) {// 两个for不写在一起，让他先处理文件,然后文件夹
			if (f.getName().toLowerCase().endsWith("js"))
				compressFile(f.getCanonicalPath(), outputFolder
						.getCanonicalPath()
						+ File.separator + f.getName());
		}
		for (File f : inputFolder.listFiles()) {
			if (f.isDirectory())
				compressFolder(f.getCanonicalPath(), outputFolder
						.getCanonicalPath()
						+ File.separator + f.getName());
		}
	}

	private static boolean compress(File inputFilename, File outputFilename) {
		Reader in = null;
		Writer out = null;
		try {
			in = new InputStreamReader(new FileInputStream(inputFilename),
					charset);
			JavaScriptCompressor compressor = new JavaScriptCompressor(in,
					new ErrorReporter() {

						public void warning(String message, String sourceName,
								int line, String lineSource, int lineOffset) {
							if (line < 0)
								System.err.println("\n[WARNING] " + message);
							else
								System.err.println("\n[WARNING] " + line + ':'
										+ lineOffset + ':' + message);
						}

						public void error(String message, String sourceName,
								int line, String lineSource, int lineOffset) {
							if (line < 0)
								System.err.println("\n[ERROR] " + message);
							else
								System.err.println("\n[ERROR] " + line + ':'
										+ lineOffset + ':' + message);
						}

						public EvaluatorException runtimeError(String message,
								String sourceName, int line, String lineSource,
								int lineOffset) {
							error(message, sourceName, line, lineSource,
									lineOffset);
							return new EvaluatorException(message);
						}

					});
			in.close();
			in = null;
			if (outputFilename == null)
				out = new OutputStreamWriter(System.out, charset);
			else {
				if (!outputFilename.getParentFile().exists()) {
					outputFilename.getParentFile().mkdir();
					System.out.println("******Make directory:\t"
							+ outputFilename.getParent());
				}
				out = new OutputStreamWriter(new FileOutputStream(
						outputFilename), charset);
			}
			compressor.compress(out, linebreakpos, munge, verbose,
					preserveAllSemiColons, disableOptimizations);
			out.flush();
			out.close();
			out = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}