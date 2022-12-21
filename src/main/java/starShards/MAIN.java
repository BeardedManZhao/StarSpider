package starShards;

import starShards.container.Container;
import starShards.parser.StarShards;

import java.io.IOException;
import java.net.URL;

/**
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 使用星陨爬虫组件，对文件对象进行HTML页面的爬取
        Container[] parses1 = StarShards.parse(new URL("http://www.cnsoftbei.com/plus/view.php?aid=755"), "LABEL", "nodePath", "table", "tbody");
        for (String s : parses1[0].getChildrenTextArray(false)) {
            System.out.println(s);
        }
        // 使用星陨爬虫组件，对文件对象进行HTML页面的爬取
        Container[] parses2 = StarShards.parse(new URL("http://www.cnsoftbei.com/plus/view.php?aid=755"), "LABEL", "node", "tr");
        for (Container container : parses2) {
            String[] childrenTextArray = container.getChildrenTextArray(false);
            System.out.println(childrenTextArray[0] + '\t' + childrenTextArray[1] + '\t' + childrenTextArray[2] + '\t' + childrenTextArray[3]);
        }
    }
}
