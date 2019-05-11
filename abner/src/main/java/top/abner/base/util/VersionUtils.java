package top.abner.base.util;
import java.util.Arrays;

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2018/10/30 10:46
 */
public class VersionUtils {

    private static final String TAG = "VersionUtils";
    /**
     * 比较两个版本号.
     * 1.  主版本号
     * 2.  次版本号
     * 3.  修正版本号
     *
     * @param versionServer
     * @param versionLocal
     * @return if versionServer > versionLocal, return 1, if equal, return 0, else return -1
     */
    public static int versionComparison(String versionServer, String versionLocal) {
        String version1 = versionServer;
        String version2 = versionLocal;
        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0) {
            throw new IllegalArgumentException("Invalid parameter!");
        }

        int index1 = 0;
        int index2 = 0;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            System.out.println(" ===== number1 ====" + Arrays.toString(number1));
            int[] number2 = getValue(version2, index2);
            System.out.println(" ===== number2 ====" + Arrays.toString(number2));

            if (number1[0] < number2[0]){
                System.out.println(" ===== number1[0] ====" + number1[0]);
                System.out.println(" ===== number2[0] ====" + number2[0]);
                return -1;
            } else if (number1[0] > number2[0]){
                System.out.println(" ===== number1[0] ====" + number1[0]);
                System.out.println(" ===== number2[0] ====" + number2[0]);
                return 1;
            } else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if (index1 == version1.length() && index2 == version2.length())
            return 0;
        if (index1 < version1.length())
            return 1;
        else
            return -1;
    }

    /**
     *
     * @param version
     * @param index
     *            the starting point
     * @return the number between two dots, and the index of the dot
     */
    private static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;
        return value_index;
    }

    public static void main(String[] args) {
        System.out.println("" + versionComparison("3.01.100027","3.1.1"));
    }
}
