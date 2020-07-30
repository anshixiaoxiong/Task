import java.util.Arrays;

public class Task07 {
    /*
循环控制，return,break，continue用法
return: 从当前的方法中退出,返回到该调用的方法的语句处,继续执行。
break:  跳出本层循环
continue：跳过本次循环，继续下次循环
     */
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        int i = 10;
        for (int x : numbers) {
            // x 等于 3 时跳出本层循环
            if (x == 3) {
                break;
            }
            System.out.println(x);
        }

        for (int x : numbers) {
            // x 等于 3 时跳出本次循环，进行下次循环
            if (x == 3) {
                continue;
            }
            System.out.println(x);
        }

        do {
            // 无论是否满足while条件，都会执行一次do语句块。
            i++;
            System.out.println(i);
        } while (i >= 15);
/*
正常的swich语句
 */
        int p = 1;
        switch (p) {
            case 0:
                System.out.println("0");
                break;
            case 1:
                System.out.println("1");
                break;
            case 2:
                // break;
            case 3:
                System.out.println("2或者3");
                break;
            default:
                System.out.println("default");
        }
/*
如果 case 语句块中没有 break 语句时，不会顺序输出每一个 case 对应的返回值，
而是继续匹配，匹配不成功则返回default case
 */
        int m = 3;
        switch (m) {
            case 0:
                System.out.println("0");
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
            default:
                System.out.println("default");
        }
/*
如果当前匹配成功的 case 语句块没有 break 语句，则从当前 case 开始，后续所有 case 的值都会输出，
如果后续的 case 语句块有 break 语句则会跳出判断。
 */
        int n = 2;
        switch (n) {
            case 0:
                System.out.println("0");
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
            case 3:
                System.out.println("3");
//                break;
            default:
                System.out.println("default");
        }
/*
总结规律：全部不匹配，输出default分支；一旦匹配上一直输出到遇到break为止，没有break就一直输出到底。——有始必有终（不知道是否准确）。
 */
    }

    /*
    练习算法1：在左——>右递增，上——>下递增的特殊矩阵中查找是否存在目标值
     */
    public boolean findValueInArray(int target, int[][] arr) {
        boolean isFind = false;
        int rows = arr.length;
        int cols = arr[0].length;
        int row = 0;
        int col = cols - 1;
        while (row < rows && col >= 0) {
            if (target == arr[row][col]) {
                isFind = true;
                break;
            } else if (target < arr[row][col]) {
                col--;
            } else {
                row++;
            }
        }
        return isFind;
    }

    /*
    练习算法2：打怪兽的金币，能力值不小于怪兽得一金币，一金币可换一点能力值，求最多能获得的金币数量，可以按任意顺序打怪。
              说明：初始能力值为a,初始金币为0，怪物数量n,怪物能力值在数组arr中。
     */
    public static int getMaxCoins(int a, int n, int[] arr) {
        int currValues = a;
        int currCoins = 0;
        int maxCoins = 0;
        Arrays.sort(arr);
        for (int i = 0; i < n; i++) {
            if (a >= arr[i]) {
                currCoins += 1;
                maxCoins = Math.max(maxCoins, currCoins);
            } else if (currValues + currCoins < arr[i]) {
                break;
            } else {
                //更新能量继续打怪
                currCoins -= (arr[i] - currValues);
                currValues = arr[i];
                currCoins += 1;
                maxCoins = Math.max(maxCoins, currCoins);
            }
        }
        return maxCoins;
    }
}
