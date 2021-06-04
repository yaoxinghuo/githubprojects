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

package com.terrynow.test;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtils {

    public static void main(String[] args) {
        // 中文转成全拼大写测试，并用空白分割打印
        String[] strings = getFullPinyin("拼音测试", true);
        // 使用String.join来输出，如果你用的JDK比较落后，那么就迭代每个string，拼接出来
        System.out.println(String.join(" ", strings)); // PIN YIN CE SHI

        // 中文转成首字母大写测试
        String[] strings2 = getInitialPinyin("拼音测试", false);
        System.out.println(String.join("", strings2)); // pycs
    }

    /**
     * 将字符串转成首字母拼音
     * @param source 字符
     * @param uppercase 是否大写
     */
    public static String[] getInitialPinyin(String source, boolean uppercase) {
        char[] chars = source.toCharArray();
        String[] strings = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            strings[i] = getInitialPinyin(chars[i], uppercase);
        }
        return strings;
    }

    /**
     * 将char转成首字母拼音
     * @param source 字符
     * @param uppercase 是否大写
     */
    public static String getInitialPinyin(char source, boolean uppercase) {
        String result = getFullPinyin(source, uppercase);
        if (result == null || result.length() == 0) {
            return "";
        }
        return result.substring(0, 1);
    }

    /**
     * 将字符串转成全部拼音
     * @param source 字符
     * @param uppercase 是否大写
     */
    public static String[] getFullPinyin(String source, boolean uppercase) {
        char[] chars = source.toCharArray();
        String[] strings = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            strings[i] = getFullPinyin(chars[i], uppercase);
        }
        return strings;
    }

    /**
     * 将char转成全部拼音
     * @param source 字符
     * @param uppercase 是否大写
     */
    public static String getFullPinyin(char source, boolean uppercase) {
        if (source <= 128) {
            return String.valueOf(source);
        }

        if (String.valueOf(source).matches("[\\u4E00-\\u9FA5]+")) {//中文
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

            String[] strings = null;
            try {
                strings = PinyinHelper.toHanyuPinyinStringArray(source, hanYuPinOutputFormat);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                return "";
            }
            if (strings == null || strings.length == 0) {
                return "";
            }
            return uppercase ? strings[0].toUpperCase() : strings[0];
        } else if (((int) source >= 65 && (int) source <= 90)
                || ((int) source >= 97 && (int) source <= 122)) {//英文
            return String.valueOf(source);
        } else {
            return "";
        }

    }
}
