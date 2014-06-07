package com.terrynow.test.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 12, 2014 8:39:13 PM
 * @description
 */
public class Test {
	public static void main(String[] args) {
		Predicate<String> p = String::isEmpty;
		boolean b = p.test("");
		System.out.println(b);
		System.out.println(p.negate().test(""));

		List<String> strs = Arrays.asList(new String[] { "a", "b" });
		strs.forEach(System.out::println);
	}

}
