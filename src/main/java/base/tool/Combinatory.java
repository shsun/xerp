package base.tool;

/**
 * 
 * Time: 15-4-2 上午9:39
 */
/**
 * 求 N 个元素的全排列算法:
 * 1. 创建一个大小为 N 个元素的数组.
 * 2. 利用 N 进制，满 N 加 1的原则，对数组的第0个元素加 1，满 N 了，则下一个元素值加 1.
 * 3. 检查数组中的元素是否有重复的，如果没有，则是一个排列.
 * 4. 直到数组中的元素为0, 1, 2, ..., N - 1，则结束，否则继续第2步直到结束.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 求 N 个元素中 M 个元素的组合算法:
 * 1. 创建一个大小为 N 个元素的数组，前 M 个元素为1，后面的 N-M 个元素为0
 * 2. 从左向右找到 10 的元素(前一个元素是1，下一个元素是0), 交换这两个元素；
 * 把此元素前面的所有1都移动到数组的最前面，此为一个组合，输出.
 * 3. 直到前 N-M 个元素都为0，则结束，否则继续第2步直到结束.
 */
public class Combinatory {
    public static List<String> produceCombination(String str, int size) {
        List<String> nameList = new ArrayList<String>();
        if (size > str.length()) {
            //System.out.println("Size is to large.");
            return nameList;
        }

        // 创建一个数组，前size个元素全是1
        int[] digit = new int[str.length()];
        for (int i = 0; i < size; ++i) {
            digit[i] = 1;
        }

        // 输出第一组
        String name = printCombination(str, digit);
        nameList.add(name);

        while (!end(digit, digit.length - size)) {
            for (int i = 0; i < digit.length - 1; ++i) {
                if (digit[i] == 1 && digit[i + 1] == 0) {
                    // i上是1，i + 1是0，交换
                    int temp = digit[i];
                    digit[i] = digit[i + 1];
                    digit[i + 1] = temp;

                    // 移动i前面的所有1到最左端
                    int count = countOf1(digit, i);
                    for (int j = 0; j < count; ++j) {
                        digit[j] = 1;
                    }

                    for (int j = count; j < i; ++j) {
                        digit[j] = 0;
                    }

                    name = printCombination(str, digit);
                    nameList.add(name);
                    break;
                }
            }
        }

        return nameList;
    }

    // 在下标end前1的个数
    private static int countOf1(int[] digit, int end) {
        int count = 0;
        for (int i = 0; i < end; ++i) {
            if (digit[i] == 1) {
                ++count;
            }
        }

        return count;
    }

    /**
     * 数组中为1的下标对应的字符需要输出
     * @param str
     * @param digit
     * @return
     */
    private static String printCombination(String str, int[] digit) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digit.length; ++i) {
            if (digit[i] == 1) {
                sb.append(str.charAt(i));
            }
        }

       // //System.out.println(sb);
        return sb.toString();
    }

    /**
     * 结束条件：前 size 个元素都是0
     * @param digit
     * @param size
     * @return
     */
    private static boolean end(int[] digit, int size) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            sum += digit[i];
        }

        return sum == 0 ? true : false;
    }

    public static List<String> fenci(String name){
        List<String> nameList = new ArrayList<String>();

        List<String> nameList4 = Combinatory.produceCombination(name, 4);
        for(String name4:nameList4){
            nameList.add(name4);
        }

        List<String> nameList3 = Combinatory.produceCombination(name, 3);
        for(String name3:nameList3){
            nameList.add(name3);
        }

        List<String> nameList2 = Combinatory.produceCombination(name, 2);
        for(String name2:nameList2){
            nameList.add(name2);
        }

        List<String> nameList1 = Combinatory.produceCombination(name, 1);
        for(String name1:nameList1){
            nameList.add(name1);
        }

        return nameList;
    }

    public static void main(String[] args) {
        List<String> nameList = Combinatory.fenci("豪门权少霸宠妻");

        for(String name:nameList){
            //System.out.println("----" + name);
        }

        //System.out.println("size=" + nameList.size());
    }
}