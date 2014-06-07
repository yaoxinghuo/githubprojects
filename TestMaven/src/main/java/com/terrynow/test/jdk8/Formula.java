package com.terrynow.test.jdk8;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 12, 2014 8:38:04 PM
 * @description
 */
@FunctionalInterface
public interface Formula {
	abstract double calculate(int a);
	
	@Override
	public boolean equals(Object obj);
	
	public default double a(int b){
		return 0;
	};
	
	public default double sqrt(int a){
		return Math.sqrt(a);
	}
}
