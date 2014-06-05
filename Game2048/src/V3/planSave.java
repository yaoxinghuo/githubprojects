package V3;

import java.io.*;

/**
 * 记录保存读取
 * @author 斌
 *
 */
public class planSave {

	
	/**
	 * 记录保存 
	 * 
	 * 步数 字节数
	 * 0 76+128 
	 * 1 332
	 * 2 460
	 * 3 588
	 * 4 716
	 * >=5 844
	 */
	public static void writeF(File f){
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			fos.write(MyListener.S);
			fos.write(MyListener.status);
//			fos.write(MyListener.score);
//			for(int i=0;i<4;i++){
//				for(int j=0;j<4;j++){
//					fos.write(Game.data[i][j]);
//				}
//			}
			
			int length=MyListener.undoAl.size();
			fos.write(length);
			fos.flush();
			if(length>5){
				for(int i=6;i>0;i--){
					oos.writeObject(MyListener.undoAl.get(MyListener.undoAl.size()-i));
				}
			}else{ 
				for(int i=length;i>0;i--){
					oos.writeObject(MyListener.undoAl.get(MyListener.undoAl.size()-i));
				}
				
				
			}
			oos.flush();
			oos.close();
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 记录读取
	 */
	public static void readF(File f){
		if(f.exists()){
			try {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
//				MyListener.S=fis.read();
				MyListener.status=fis.read();
//				MyListener.score=fis.read();
//				for(int i=0;i<4;i++){
//					for(int j=0;j<4;j++){
//						Game.data[i][j]=fis.read();
//					}
//				}
				int length = fis.read();
				MyListener.undoAl.clear();
				if(length>=5){
					for(int i=1;i<=6;i++){
						try {
							MyListener.undoAl.add((Undo) ois.readObject());
							//System.out.println(MyListener.undoAl.get(i-1).S+" "+MyListener.undoAl.get(i-1).score+" "+MyListener.undoAl.size());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					for(int i=1;i<=length;i++){
						try {
							MyListener.undoAl.add((Undo) ois.readObject());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
