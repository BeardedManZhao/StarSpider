package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.HTMLDocument;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // Construct the URL of the page to be crawled
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); You can use URL for data parsing
        // Alternatively, use a file object. Here we parse a text file.
        File file = new File("F:\\MyGitHub\\StarSpider\\src\\main\\resources\\Test.html");
        // Use the StarSpider library to extract data from the file where the id attribute is "table2"
        HTMLDocument[] parse = (HTMLDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_HTML, "attr", "id", "table2");
        // Get all tr tags under the current node
        for (HTMLDocument htmlDocument : parse[0].getAllChildrenByNodeName("tr")) {
            // Print the data within the tr tag
            System.out.println(htmlDocument.getChildrenText());
            // Continue to retrieve child nodes of tr by attributes or tag names
            HTMLDocument[] tds = htmlDocument.getAllChildrenByNodeName("td");
            System.out.printf("There are %d td tags under this node%n", tds.length);
            // Retrieve child nodes by attributes
            HTMLDocument[] allChildrenByNodeAttrib = htmlDocument.getAllChildrenByNodeAttrib("class", "td1");
            System.out.printf("There are %d tags with class attribute 'td1' under this node%n%n", allChildrenByNodeAttrib.length);
        }
    }
}
