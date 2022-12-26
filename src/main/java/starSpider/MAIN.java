package starSpider;

import starSpider.container.Container;
import starSpider.parser.StarSpider;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) {
        // 提取出一个字符串中所有的数值
        Container[] regulars = StarSpider.parse(
                "zhaodsandjsaklfdhajkndfjsdhfaudSUD123HDUSIFCNDJNJDKS678DJSKAF2341233HDSD",
                "regular", "\\d+"
        );
        for (Container regular : regulars) {
            System.out.println(regular.getText());
        }
    }
}
