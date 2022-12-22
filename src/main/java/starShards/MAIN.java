package starShards;

import starShards.container.HTMLDocument;
import starShards.parser.StarShards;

import java.io.IOException;
import java.net.URL;

/**
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 构建需要爬取页面的URL
        URL url = new URL("http://www.cnsoftbei.com/plus/view.php?aid=755");
//        // 使用星陨解析组件，对文件对象进行HTML页面的爬取
//        LABELDocument[] parses1 = (LABELDocument[]) StarShards.parse(url, "LABEL", "nodePath", "table", "tbody");
//        for (String s : parses1[0].getChildrenTextArray(false)) {
//            System.out.println(s);
//        }
//        // 使用星陨解析组件，对文件对象进行HTML页面的爬取
//        LABELDocument[] parses2 = (LABELDocument[]) StarShards.parse(url, "LABEL", "node", "tr");
//        for (LABELDocument container : parses2) {
//            String[] childrenTextArray = container.getChildrenTextArray(false);
//            System.out.println(childrenTextArray[0] + '\t' + childrenTextArray[1] + '\t' + childrenTextArray[2] + '\t' + childrenTextArray[3]);
//        }

        // 使用星陨解析组件，对文件对象进行HTML页面的爬取
        HTMLDocument[] parses1 = (HTMLDocument[]) StarShards.parse(url, "HTML", "nodePath", "table", "tr");
        for (HTMLDocument htmlDocument : parses1) {
            System.out.println(htmlDocument);
        }
//        // 构建需要解析页面的文件对象
//        File file = new File("C:\\Users\\zhao\\Desktop\\out\\out.txt");
//        // 使用星陨解析组件，对数学表达式字符串进行解析，智能提取出来数学表达式
//        ExpressionData[] parse = (ExpressionData[]) StarShards.parse(file, "ME", "brackets");
//        for (ExpressionData container : parse) {
//            System.out.println("提取到表达式【" + container.getName() + "】 = " + container.getText());
//        }
    }
}
