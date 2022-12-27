package starSpider;

import starSpider.container.FastJsonDocument;
import starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\MyGitHub\\StarSpider\\src\\main\\resources\\testJson.json");
        // 获取到json 文件中的data1与two，并以json格式解析它
        FastJsonDocument[] parse = (FastJsonDocument[]) StarSpider.parse(file, "fastJson", "json", "data1", "two");
        // 返回解析到的结果，这里是data1的结果对象与two组成的数组
        for (FastJsonDocument container : parse) {
            if (container.isJObject) {
                System.out.println(container.getName() + '\t' + container.getText() + '\t' + container.getString("key1"));
            } else {
                System.out.println(container.getName() + '\t' + container.getText());
            }
        }
    }
}
