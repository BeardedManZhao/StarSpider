package io.github.beardedManZhao.starSpider.container;

/**
 * 字符串匹配对象，其中存储的是由解析器按照正则解析出来的字符串对象，在字符串对象中存储的是内容与匹配正则两个数据。
 * <p>
 * The string matching object stores the string object parsed by the parser according to the regularity, and the string object stores the content and matching regularity data.
 *
 * @author zhao
 */
public final class StringData implements Container {

    public final String Text;
    public final String RegExp;

    /**
     * 构造一个字符串匹配对象
     *
     * @param text   字符串本身的数据
     * @param regExp 字符串匹配的正则
     */
    public StringData(String text, String regExp) {
        Text = text;
        RegExp = regExp;
    }

    /**
     * @return 获取到节点的字符串数据，在HTML中的实现为获取到当前节点的文本数据
     * <p>
     * Get the string data to the node
     */
    @Override
    public String getText() {
        return this.Text;
    }

    /**
     * @return 获取到数据的匹配正则表达式
     * <p>
     * Get the name of the node
     */
    @Override
    public String getName() {
        return this.RegExp;
    }

    @Override
    public String toString() {
        return "StringData{" +
                "Text='" + Text + '\'' +
                ", RegExp='" + RegExp + '\'' +
                '}';
    }
}
