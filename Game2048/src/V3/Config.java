package V3;

import java.net.URL;

import javax.swing.ImageIcon;
/**
 * 便于打包图片文件
 * @author 斌
 *
 */
public class Config {
	public static ImageIcon getImage(String s)
	{
		URL url=Game.class.getResource(s);
		return new ImageIcon(url);
	}
}