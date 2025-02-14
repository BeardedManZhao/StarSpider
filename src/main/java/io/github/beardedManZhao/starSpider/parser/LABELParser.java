package io.github.beardedManZhao.starSpider.parser;

import io.github.beardedManZhao.starSpider.ConstantRegion;
import io.github.beardedManZhao.starSpider.container.Container;
import io.github.beardedManZhao.starSpider.container.LABELDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * LABEL文件解析组件，支持对标签文档进行数据解析。
 * <p>
 * LABEL file parsing component, which supports data parsing of tag documents.
 *
 * @author zhao
 */
public class LABELParser implements Parser {

    protected final static Pattern REGEXP_LABEL_REGULARITY_PATTERN = Pattern.compile(ConstantRegion.REGEXP_LABEL_REGULARITY);
    protected final static Pattern GREATER_THAN_SIGN_STRING_PATTERN = Pattern.compile(ConstantRegion.GREATER_THAN_SIGN_STRING);
    protected final static Pattern REGEXP_SPACE_ALL_PATTERN = Pattern.compile(ConstantRegion.REGEXP_SPACE_ALL);
    protected final static Pattern REGEXP_COMMA_ALL_PATTERN = Pattern.compile(ConstantRegion.REGEXP_COMMA_ALL);
    protected final static Pattern REGEXP_LABEL_ATTRIBUTE_SEGMENTATION = Pattern.compile(ConstantRegion.REGEXP_LABEL_ATTRIBUTE_SEGMENTATION);
    protected boolean childrenClass = false;

    protected LABELParser() {
    }

    private static void createHD(ArrayList<LABELDocument> arrayList, String nowNode, String[] text) {
        for (int i = 1; i < text.length - 1; i++) {
            // 开始提取当前节点的属性
            HashMap<String, String> hashMap = new HashMap<>();
            String[] s1 = GREATER_THAN_SIGN_STRING_PATTERN.split(REGEXP_LABEL_REGULARITY_PATTERN.matcher(text[i]).replaceAll(ConstantRegion.COMMA_STRING));
            if (s1.length >= 2) {
                for (String s : REGEXP_SPACE_ALL_PATTERN.split(s1[0])) {
                    if (!s.isEmpty()) {
                        String[] split = REGEXP_LABEL_ATTRIBUTE_SEGMENTATION.split(s);
                        if (split.length >= 2) {
                            hashMap.put(split[0].trim(), split[1].trim());
                        }
                    }
                }
                String s = s1[1];
                if (!s.isEmpty()) {
                    String[] split = REGEXP_COMMA_ALL_PATTERN.split(s);
                    if (split.length != 0) {
                        arrayList.add(new LABELDocument(split, hashMap, nowNode));
                    }
                }
            } else {
                String s = s1[0];
                if (!s.isEmpty()) {
                    String[] split = REGEXP_COMMA_ALL_PATTERN.split(s);
                    if (split.length != 0) {
                        arrayList.add(new LABELDocument(split, hashMap, nowNode));
                    }
                }
            }
        }
    }

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_LABEL;
    }

    /**
     * 按照节点解析功能，针对每一个节点进行解析，并获取到数据
     *
     * @param data 需要被解析的标签文档数据
     * @param args 节点集合，存在于集合中的节点都会被解析出来，其中从索引1开始就是待解析节点了，0 索引位的数据不做限制
     * @return 存在于集合中的所有节点的数据。
     */
    public Container[] getDocumentByNodeName(String data, String... args) {
        String dataAll = ConstantRegion.HORIZONTAL_BAR_CHARACTER + data + ConstantRegion.HORIZONTAL_BAR_CHARACTER;
        final ArrayList<LABELDocument> arrayList = new ArrayList<>();
        for (String arg : args) {
            // 根据节点路径生成正则表达式 然后进行数据过滤，将当前节点下的数据进行初次过滤与格式化
            createHD(arrayList, arg, dataAll.split(getSplitRegExp(arg)));
        }
        return arrayList.toArray(new LABELDocument[0]);
    }

    /**
     * 按照节点路径解析功能，针对每一个路径进行节点的数据查找并获取到节点文档对象
     *
     * @param data 需要被接地的标签文档数据
     * @param args 节点路径，每一个元素是一个节点路径，最后一个节点也就是目标节点。
     * @return 符合路径的所有节点的数据。
     */
    public Container[] NodePathParse(String data, String... args) {
        final ArrayList<LABELDocument> arrayList = new ArrayList<>();
        // 进行数据过滤，将当前节点下的数据进行初次过滤与格式化
        StringBuilder stringBuilder = new StringBuilder();
        int length_1 = args.length - 1;
        for (int i = 1; i < length_1; i++) {
            stringBuilder.append(args[i])
                    .append(ConstantRegion.SPACE)
                    .append(ConstantRegion.GREATER_THAN_SIGN_CHAR);
        }
        stringBuilder.append(ConstantRegion.SPACE).append(args[length_1]);
        createHD(
                arrayList, stringBuilder.toString(),
                (ConstantRegion.HORIZONTAL_BAR_CHARACTER + data + ConstantRegion.HORIZONTAL_BAR_CHARACTER).split(getSplitRegExp(args))
        );
        return arrayList.toArray(new LABELDocument[0]);
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
     * @return 解析器解析之后的结果数值
     */
    @Override
    public Container[] parse(String data, String... args) {
        String arg = args[0];
        if ("node".equalsIgnoreCase(arg)) {
            return getDocumentByNodeName(data, args);
        } else if ("nodePath".equalsIgnoreCase(arg)) {
            return NodePathParse(data, args);
        }
        if (!this.childrenClass) {
            // 如果子类有拓展，就暂时不抛出异常，将解析任务交给子类进行
            throw new RuntimeException("您的解析模式【" + arg + "】在LABELParser中并没有实现，因此无法进行指定数据的解析。\n" +
                    "Your parsing mode [" + arg + "] is not implemented in LABELParser, so you cannot parse the specified data.");
        }
        return null;
    }

    /**
     * 根据参数计算出第一次分割所需正则
     *
     * @param node 需要提取的节点
     * @return 节点在爬虫的时候，作为切分正则表达式的字符串对象
     */
    private String getSplitRegExp(String node) {
        return ConstantRegion.LESS_THAN_SIGN_CHAR +
                node +
                ConstantRegion.REGEXP_MATCH_ALL1 +
                ConstantRegion.GREATER_THAN_SIGN_CHAR +
                ConstantRegion.REGEXP_GREEDY_SYMBOL +
                ConstantRegion.REGEXP_OR +
                ConstantRegion.End_Label1 +
                node +
                ConstantRegion.GREATER_THAN_SIGN_CHAR;
    }

    /**
     * 根据参数计算出第一次分割所需正则
     *
     * @param nodePath 需要提取的节点在LABEL文档中的路径
     * @return 节点在爬虫的时候，作为切分正则表达式的字符串对象
     */
    private String getSplitRegExp(String[] nodePath) {
        StringBuilder stringBuilder = new StringBuilder(nodePath.length << 1);
        for (int i = 1; i < nodePath.length; i++) {
            stringBuilder.append(ConstantRegion.LESS_THAN_SIGN_CHAR)
                    .append(nodePath[i])
                    .append(ConstantRegion.REGEXP_MATCH_ALL1)
                    .append(ConstantRegion.GREATER_THAN_SIGN_CHAR)
                    .append(ConstantRegion.REGEXP_GREEDY_SYMBOL)
                    .append(ConstantRegion.REGEXP_MATCH_ALL2)
                    .append(ConstantRegion.REGEXP_GREEDY_SYMBOL);
        }
        stringBuilder.append(ConstantRegion.REGEXP_OR);
        for (int length = nodePath.length - 1; length > 0; length--) {
            stringBuilder.append(ConstantRegion.End_Label1)
                    .append(nodePath[length])
                    .append(ConstantRegion.GREATER_THAN_SIGN_CHAR)
                    .append(ConstantRegion.REGEXP_MATCH_ALL2)
                    .append(ConstantRegion.REGEXP_GREEDY_SYMBOL);
        }
        return stringBuilder.toString();
    }

}
