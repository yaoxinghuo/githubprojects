/*
 * Copyright (c) 2021.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.terrynow.test.seu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date 2021-5-28 18:54
 * @description
 */
public class TestProcessImages {

    public static void main(String[] args) throws Exception {
        File folder = new File("/Users/Terry/Downloads/EDA共享实验室仪器导入20210527/img");
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jpg");
            }
        });
        if(files == null) {
            return;
        }
        for (File file : files) {
            processUploadedImage(file);
        }
    }

    private static void processUploadedImage(File tmpFile) throws Exception {
        BufferedImage src = ImageIO.read(tmpFile); // 读入文件
        int width = src.getWidth(); // 得到源图宽
        int height = src.getHeight(); // 得到源图长

        //比原来加4倍
        final int MIN_WIDTH = 96 * 4;
        final int MIN_HEIGHT = 64 * 4;
        final int MAX_WIDTH = 600 * 4;
        final int MAX_HEIGHT = 450 * 4;

        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            return;
        } else {
            double scale = 1;
            if (width > MAX_WIDTH || height > MAX_HEIGHT) {
                scale = Math.max((double) width / (double) MAX_WIDTH, (double) height
                        / (double) MAX_HEIGHT);
                width = (int) (width / scale);
                height = (int) (height / scale);
            }

            File imageFolder = tmpFile.getParentFile();
            String fileName = "s_"+tmpFile.getName();

//            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
//            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics g = tag.getGraphics();
//            g.drawImage(image, 0, 0, null);
//            g.dispose();
//            ImageIO.write(tag, "JPEG", new File(imageFolder, equipId + ".jpg"));

            Image image = src.getScaledInstance(MIN_WIDTH, MIN_HEIGHT, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(MIN_WIDTH, MIN_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(imageFolder, fileName));
            System.out.println(fileName+" processed");
        }
    }
}
