package com.terrynow.test.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Feb 17, 2014 11:18:04 AM
 * @description
 */
public class Test {
	public static void main(String[] args) throws Exception {
		BufferedImage imageSrc = ImageIO.read(new File("/Users/Terry/Downloads/flower.jpg"));
		BufferedImage imageAnother = ImageIO.read(new File("/Users/Terry/Downloads/another.jpg"));
		ImageComparer imageComparer = new ImageComparer(imageSrc, imageAnother);
		System.out.println(imageComparer.modelMatch());
	}
}
