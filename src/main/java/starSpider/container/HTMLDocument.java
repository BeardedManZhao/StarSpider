package starSpider.container;

import starSpider.ConstantRegion;
import starSpider.parser.StarSpider;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML解析器解析出来的HTML文档对象
 *
 * @author zhao
 */
public class HTMLDocument extends LABELDocument {
    // (?<=<table[\s\S]*)<[\s\S]*(?=</table>)
    public final static Pattern CHILDREN_REX = Pattern.compile("(?<=<)[^/].*?.*?(?=>+?)");
    public final static Pattern REGEXP_INVISIBLE_CHARACTERS_ALL_PATTERN = Pattern.compile(ConstantRegion.REGEXP_INVISIBLE_CHARACTERS_ALL);
    public final String childrenNodeName;
    public final String HTMLData;

    /**
     * 构造一个文档对象
     *
     * @param text     当前LABEL节点的数据
     * @param attribs  当前LABEL节点的属性
     * @param name     当前LABEL的标签名称
     * @param HTMLData 该对象中，原本HTML或XML文档的对象。
     */
    public HTMLDocument(String[] text, HashMap<String, String> attribs, String name, String HTMLData) {
        super(text, text.length == 0 ? ConstantRegion.STRING_NULL : text[0], attribs, name);
        this.HTMLData = HTMLData;
        Matcher matcher = HTMLDocument.CHILDREN_REX.matcher(this.HTMLData);
        if (matcher.find(2)) {
            this.childrenNodeName = REGEXP_INVISIBLE_CHARACTERS_ALL_PATTERN.split(matcher.group())[0];
        } else this.childrenNodeName = null;
    }

    /**
     * @return 当前节点下的第一个子节点
     * <p>
     * The first child node under the current node
     */
    public HTMLDocument getChildren() {
        // 获取到子标签
        if (this.childrenNodeName == null) return null;
        return (HTMLDocument) StarSpider.HTML_PARSER.ANodeParse(this.HTMLData, this.childrenNodeName, 2);
    }

    /**
     * 获取当下节点所有子节点中的起名为某个节点名称的所有节点（含当前节点）
     *
     * @param childrenNodeName 需要获取到的子节点的节点名字
     * @return 当前节点与当前所有子节点中的某一个指定节点数据
     */
    public HTMLDocument[] getAllChildrenByNodeName(String childrenNodeName) {
        return (HTMLDocument[]) StarSpider.HTML_PARSER.getDocumentByNodeName(this.HTMLData, false, ConstantRegion.PARSER_NAME_HTML, childrenNodeName);
    }

    /**
     * 获取当下节点所有子节点中 属性为 childrenNodeAttribKey = childrenNodeAttribValue 的所有子节点（含当前节点）
     *
     * @param childrenNodeAttribKey   需要获取的子节点的属性名称
     * @param childrenNodeAttribValue 需要获取的子节点的属性值
     * @return 当前节点与当前所有子节点中的所有符合属性条件的节点文档对象
     */
    public HTMLDocument[] getAllChildrenByNodeAttrib(String childrenNodeAttribKey, String childrenNodeAttribValue) {
        return (HTMLDocument[]) StarSpider.HTML_PARSER.getDocumentByNodeAttrib(this.HTMLData, ConstantRegion.PARSER_NAME_HTML, childrenNodeAttribKey, childrenNodeAttribValue);
    }

    @Override
    public String toString() {
        return this.HTMLData;
    }
}
