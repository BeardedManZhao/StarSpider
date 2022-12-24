package starShards.parser;

import com.sun.istack.internal.NotNull;
import starShards.ConstantRegion;
import starShards.container.Container;
import starShards.container.HTMLDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML解析器，同时也可以作为XML的解析器，在该解析器中，您可以使用节点，节点路径，节点属性的信息获取到某一个节点的数据对象。
 * <p>
 * An HTML parser can also be used as an XML parser. In this parser, you can use the node, node path, and node attribute information to obtain the data object of a node.
 *
 * @author zhao
 */
public class HTMLParser extends LABELParser {

    public final static Pattern ATTRIBUTE_PATTEN = Pattern.compile("<.*?(?=>)");

    public HTMLParser() {
        super.childrenClass = true;
    }

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_HTML;
    }

    /**
     * 按照节点解析功能，针对每一个节点进行解析，并获取到数据
     *
     * @param data 需要被解析的标签文档数据
     * @param args 节点集合，存在于集合中的节点都会被解析出来，其中从索引1开始就是待解析节点了，0 索引位的数据不做限制
     * @return 存在于集合中的所有节点的数据。
     */
    @Override
    public Container[] getDocumentByNodeName(String data, String... args) {
        return getDocumentByNodeName(data, false, args);
    }

    /**
     * 按照节点解析功能，针对每一个节点进行解析，并获取到数据
     *
     * @param data     需要被解析的标签文档数据
     * @param isGreedy 如果为true，代表使用贪婪模式匹配标签，这样匹配的标签会很大
     * @param args     节点集合，存在于集合中的节点都会被解析出来，其中从索引1开始就是待解析节点了，0 索引位的数据不做限制
     * @return 存在于集合中的所有节点的数据。
     */
    public Container[] getDocumentByNodeName(String data, boolean isGreedy, String... args) {
        ArrayList<HTMLDocument> arrayList = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            String node = args[i];
            Pattern compile = Pattern.compile(getNodeRex(node, isGreedy));
            Matcher matcher = compile.matcher(data);
            while (matcher.find()) {
                arrayList.add(getHtmlDocument(node, matcher));
            }
        }
        return arrayList.toArray(new HTMLDocument[0]);
    }

    /**
     * 获取到指定的一个节点
     *
     * @param data     需要被解析的HTML文本数据
     * @param nodeName 需要获取的节点名称
     * @param index    获取第几个索引处的节点数据
     * @return 如果指定索引处有数据，会返回一个HTML文档对象，反之会返回null
     */
    public Container ANodeParse(String data, String nodeName, int index) {
        Pattern compile = Pattern.compile(getNodeRex(nodeName, true));
        Matcher matcher = compile.matcher(data);
        if (matcher.find(index)) {
            return getHtmlDocument(nodeName, matcher);
        }
        return null;
    }

    /**
     * 按照节点的属性，获取到指定节点数据
     *
     * @param data 需要被解析的HTML文本数据
     * @param args 0-预置位
     *             1-attribsName 需要被获取目标节点的属性key
     *             2-attribsValue 需要被获取目标节点的属性value
     * @return 具有属性 attribsName=attribsValue 的节点的所有数据
     */
    public Container[] getDocumentByNodeAttrib(String data, String... args) {
        if (args.length >= 3) {
            Matcher matcher = Pattern.compile(getAttrRex(args[1], args[2])).matcher(data);
            ArrayList<HTMLDocument> arrayList = new ArrayList<>();
            if (matcher.find()) {
                arrayList.add(getHtmlDocument(matcher.group(1), matcher));
            }
            return arrayList.toArray(new HTMLDocument[0]);
        } else {
            return new HTMLDocument[0];
        }
    }

    /**
     * 生成一个文档对象
     *
     * @param nodeName 节点名称
     * @param matcher  节点匹配的正则对象
     * @return 一个符合条件的文档对象
     */
    @NotNull
    private HTMLDocument getHtmlDocument(String nodeName, Matcher matcher) {
        // 获取到当前节点的标签文本
        String group = matcher.group();
        // 提取出当前节点的标签属性
        HashMap<String, String> hashMap = new HashMap<>();
        Matcher matcher1 = ATTRIBUTE_PATTEN.matcher(group);
        if (matcher1.find()) {
            for (String s : matcher1.group().split(" +")) {
                String[] split = s.split("=");
                if (split.length >= 2) {
                    hashMap.put(split[0], split[1]);
                }
            }
        }
        String[] split = group.split("<.*?>");
        ArrayList<String> arrayList = new ArrayList<>(split.length + 16);
        for (String s : split) {
            String trim = s.trim();
            if (trim.length() != 0) arrayList.add(trim);
        }
        return new HTMLDocument(arrayList.toArray(new String[0]), hashMap, nodeName, group);
    }

    /**
     * 按照节点路径解析功能，针对每一个路径进行节点的数据查找并获取到节点文档对象
     *
     * @param data 需要被解析的标签文档数据
     * @param args 节点路径，每一个元素是一个节点路径，最后一个节点也就是目标节点。
     * @return 符合路径的所有节点的数据。
     */
    @Override
    public Container[] NodePathParse(String data, String... args) {
        StringBuilder stringBuilder = new StringBuilder(args.length << 12);
        stringBuilder.append("[\\s\\S]*");
        // <colgroup.*>?[\s\S]*?<col.*>?|</col>[\s\S]*</colgroup>
        // <table.*>?|</table>
        for (int i = 1, len = args.length - 1, len1 = len - 1; i < len; i++) {
            stringBuilder.append(ConstantRegion.LESS_THAN_SIGN_CHAR)
                    .append(args[i])
                    .append(ConstantRegion.REGEXP_MATCH_ALL1)
                    .append(ConstantRegion.GREATER_THAN_SIGN_CHAR)
                    .append(ConstantRegion.REGEXP_GREEDY_SYMBOL);
            if (i != len1) {
                stringBuilder.append(ConstantRegion.REGEXP_MATCH_ALL2)
                        .append(ConstantRegion.REGEXP_GREEDY_SYMBOL);
            }
        }
        stringBuilder.append(ConstantRegion.REGEXP_OR);
        for (int length = args.length - 2; length > 0; length--) {
            stringBuilder.append(ConstantRegion.End_Label1)
                    .append(args[length]).append(ConstantRegion.GREATER_THAN_SIGN_CHAR);
            if (length != 1) stringBuilder.append(ConstantRegion.REGEXP_MATCH_ALL2);
        }
        stringBuilder.append("[\\s\\S]*");
        return getDocumentByNodeName(data.replaceFirst(stringBuilder.toString(), ""), false, ConstantRegion.PARSER_NAME_HTML, args[args.length - 1]);
    }

    /**
     * 根据节点名称获取到适合的节点正则表达式
     *
     * @param node     需要获取的节点名称
     * @param isGreedy 是否使用贪婪匹配，贪婪模式下会获取到尽可能大范围的节点
     * @return 节点正则表达式
     */
    public String getNodeRex(String node, boolean isGreedy) {
        if (isGreedy) {
            return ConstantRegion.LESS_THAN_SIGN_CHAR + node + ".*?>?[\\s\\S]*</" + node + ConstantRegion.GREATER_THAN_SIGN_CHAR;
        } else {
            return ConstantRegion.LESS_THAN_SIGN_CHAR + node + ".*?>?[\\s\\S]*?</" + node + ConstantRegion.GREATER_THAN_SIGN_CHAR;
        }

    }

    /**
     * 根据节点属性获取到适合节点的正则表达式
     *
     * @param key   节点属性的key
     * @param value 节点属性的value
     * @return 节点的正则表达式
     */
    public String getAttrRex(String key, String value) {
        return "<([a-zA-Z]+)? \\S*?" + key + "\\S*?=\\S*?([\"'])?" + value + "\\2.*?>[\\s\\S]*?</\\1>";
    }

    /**
     * 解析一个LABEL页面
     *
     * @param data 需要被解析的数据
     * @param args 解析器需要使用到的参数
     *             索引0应为 您解析数据使用的手段
     *             node：按照节点解析所有节点文档数据
     *             如果选择此方式进行文档解析，那么您需要在从1开始的位置书写所有需要被解析的节点名称
     *             nodePath：按照节点路径解析所有节点文档数据
     *             如果选择此方式进行文档解析，那么您需要在从1开始的位置书写路径，符合路径描述的所有节点都会被解析
     *             attr：按照节点属性解析所有节点的文档数据
     *             如果选择此当时进行文档解析，那么您需要在1号索引书写属性名称，2号索引书写属性的数值
     * @return 解析器解析之后的结果数值
     */
    @Override
    public Container[] parse(String data, String... args) {
        Container[] parse = super.parse(data, args);
        if (parse != null) return parse;
        else {
            String arg = args[0];
            if ("attr".equalsIgnoreCase(arg)) {
                return getDocumentByNodeAttrib(data, args);
            } else {
                throw new RuntimeException("您的解析模式【" + arg + "】在HTMLParser中并没有实现，因此无法进行指定数据的解析。\n" +
                        "Your parsing mode [" + arg + "] is not implemented in HTMLParser, so you cannot parse the specified data.");
            }
        }
    }
}
