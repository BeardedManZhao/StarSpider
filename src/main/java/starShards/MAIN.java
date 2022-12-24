package starShards;

import starShards.container.Container;
import starShards.parser.StarShards;

import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 提取出一个字符串中所有的数值
        Container[] regulars = StarShards.parse(
                "zhaodsandjsaklfdhajkndfjsdhfaudSUD123HDUSIFCNDJNJDKS678DJSKAF2341233HDSD",
                "regular", "\\d+"
        );
        for (Container regular : regulars) {
            System.out.println(regular.getText());
        }
    }
}
