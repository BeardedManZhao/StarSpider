package starShards.parser;

import starShards.ConstantRegion;
import starShards.container.Container;
import starShards.container.HTMLDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhao
 */
public class HTMLParser extends LABELParser {

    public final static Pattern ATTRIBUTE_PATTEN = Pattern.compile("<.*?(?=>)");

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return "HTML";
    }

    /**
     * 按照节点解析功能，针对每一个节点进行解析，并获取到数据
     *
     * @param data 需要被解析的标签文档数据
     * @param args 节点集合，存在于集合中的节点都会被解析出来，其中从索引1开始就是待解析节点了，0 索引位的数据不做限制
     * @return 存在于集合中的所有节点的数据。
     */
    @Override
    public Container[] NodeParse(String data, String... args) {
        ArrayList<HTMLDocument> arrayList = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            String node = args[i];
            Pattern compile = Pattern.compile(getNodeRex(node));
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
        Pattern compile = Pattern.compile(getNodeRex(nodeName));
        Matcher matcher = compile.matcher(data);
        if (matcher.find(index)) {
            return getHtmlDocument(nodeName, matcher);
        }
        return null;
    }

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
        return NodeParse(data.replaceFirst(stringBuilder.toString(), ""), ConstantRegion.PARSER_NAME_HTML, args[args.length - 1]);
    }

    /**
     * 获取到适合的节点正则表达式
     *
     * @param node 需要获取的节点名称
     * @return 节点正则表达式
     */
    public String getNodeRex(String node) {
        return ConstantRegion.LESS_THAN_SIGN_CHAR + node + ".*?>?.*?</" + node + ConstantRegion.GREATER_THAN_SIGN_CHAR;
    }
}
