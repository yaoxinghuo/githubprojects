package com.terrynow.test;

import com.google.common.base.Joiner;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Feb 9, 2014 3:45:50 PM
 * @description
 */
public class TestGuava {
	public static void main(String[] args) throws Exception {
//		Map<String, String> map = Maps.newHashMap();
//		
//		ImmutableList<String> of = ImmutableList.of("a", "b");
		
//		File file = new File("/Users/Terry/Drive/Unclutter Notes.txt");
//		List<String> lines = Files.readLines(file, Charsets.ISO_8859_1);
//		for(String line:lines)
//			System.out.println(line);
		
		
		String numbersAsStringDirectly = Joiner.on(",").join(new Object[]{"1",2});
		System.out.println(numbersAsStringDirectly);
		
		// String testString = "foo , what,,,more,";
		// Iterable<String> split =
		// Splitter.on(",").omitEmptyStrings().trimResults().split(testString); 
		
	}
}
