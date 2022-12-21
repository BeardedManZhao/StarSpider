package starShards.container;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * LABEL文件文档对象，是由LABEL解析组件解析之后的出来的结果数据包裹对象。
 * <p>
 * The LABEL file document object is the result data package object parsed by the LABEL parsing component.
 *
 * @author zhao
 */
public class LABELDocument implements Container {

    public final String[] Text;
    public final String nowNodeText;
    public final HashMap<String, String> attribs;
    public final String Name;

    /**
     * 构造一个文档对象
     *
     * @param text    当前LABEL节点的数据
     * @param attribs 当前LABEL节点的属性
     * @param name    当前LABEL的标签名称
     */
    public LABELDocument(String[] text, HashMap<String, String> attribs, String name) {
        ArrayList<String> arrayList = new ArrayList<>(text.length + 16);
        nowNodeText = text[0];
        for (String s : text) {
            String trim = s.trim();
            if (trim.length() != 0) {
                arrayList.add(trim);
            }
        }
        this.Text = arrayList.toArray(new String[0]);
        this.attribs = attribs;
        Name = name;
    }

    /**
     * @return 获取到节点的字符串数据，在LABEL中的实现为获取到当前节点的文本数据
     * <p>
     * Get the string data to the node
     */
    @Override
    public String getText() {
        return this.nowNodeText;
    }

    /**
     * @return 获取到当前节点以及当前节点所有子节点的数据，其中节点之间的分隔符默认是制表符。
     * <p>
     * Get the data of the current node and all the child nodes of the current node. The separator between nodes is tab by default.
     */
    public String getChildrenText() {
        return getChildrenText("\t");
    }

    /**
     * @param split 自定义数据获取的分隔符
     * @return 获取到当前节点以及当前节点所有子节点的数据，其中节点之间的分隔符默认是制表符。
     * <p>
     * Get the data of the current node and all the child nodes of the current node. The separator between nodes is tab by default.
     */
    @Override
    public String getChildrenText(String split) {
        final StringBuilder stringBuilder = new StringBuilder(this.Text.length * this.nowNodeText.length() + 16);
        for (String s : this.Text) {
            stringBuilder.append(s.trim()).append(split);
        }
        return stringBuilder.toString();
    }

    /**
     * 以数组形式获取到该节点以及其子类的所有数据
     *
     * @param isNew 如果设置为true代表使用一个新数组存储数据，可以修改新数组而不影响该数据对象，如果设置为false 则直接返回当前节点的数据
     * @return 当前节点以及当前节点的所有子节点的数据，以一个数组的形式返回这些数据。
     * <p>
     * The data of the current node and all its children are returned in an array.
     */
    @Override
    public String[] getChildrenTextArray(boolean isNew) {
        if (isNew) {
            String[] strings = new String[this.Text.length];
            System.arraycopy(this.Text, 0, strings, 0, strings.length);
            return strings;
        } else {
            return this.Text;
        }
    }

    /**
     * @return 获取到节点的属性，在LABEL中的实现为获取到当前节点的属性数据
     * <p>
     * Get the attributes of the node
     */
    @Override
    public HashMap<String, String> getAttribs() {
        return this.attribs;
    }

    /**
     * @param attrName 属性名称
     * @return 获取到节点的属性，在LABEL中的实现为获取到当前节点的属性数据
     * <p>
     * Get the attributes of the node
     */
    @Override
    public String getAttrib(String attrName) {
        return this.attribs.get(attrName);
    }

    /**
     * @return 获取到节点的名称
     * <p>
     * Get the name of the node
     */
    @Override
    public String getName() {
        return this.Name;
    }
}
