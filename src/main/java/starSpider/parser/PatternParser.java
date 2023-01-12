package starSpider.parser;

import starSpider.ConstantRegion;
import starSpider.container.Container;
import starSpider.container.StringData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 按照正则表达式匹配数据，并返回其中所有满足匹配的结果对象。
 * <p>
 * Match the data according to the regular expression, and return all the matching result objects.
 * <p>
 * 在该匹配中，有一个缓存区，用于存储所有的之前使用过的正则表达式，当遇到重复的正则解析需求的时候，会直接从缓冲区中提取出已经缓存好的正则表达式。
 * <p>
 * In this matching, there is a buffer area for storing all previously used regular expressions. When encountering repeated regular parsing requirements, the cached regular expressions will be directly extracted from the buffer area.
 *
 * @author zhao
 */
public class PatternParser extends MEParser {

    protected final static HashMap<String, Pattern> PATTERN_HASH_MAP = new HashMap<>();

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_REGEXP;
    }

    /**
     * 从缓冲区中获取到正则表达式的编译对象，如果缓冲区中不存在，会新建一个正则对象
     *
     * @param rex   正则表达式的字符串形式
     * @param flags 正则表达式形参，该函数支持设置正则的附加参数
     * @return 正则表达式的编译对象
     */
    public Pattern getPattern(String rex, int flags) {
        Pattern compile = PATTERN_HASH_MAP.get(rex);
        if (compile == null) {
            compile = Pattern.compile(rex);
            PATTERN_HASH_MAP.put(rex, compile);
        }
        return compile;
    }

    /**
     * 从缓冲区中获取到正则表达式的编译对象，如果缓冲区中不存在，会新建一个正则对象
     *
     * @param rex 正则表达式默认形参的字符串形式
     * @return 正则表达式的编译对象
     */
    public Pattern getPattern(String rex) {
        return getPattern(rex, 0);
    }

    /**
     * 解析一个字符串中包含的所有数学表达式
     *
     * @param data 需要被解析的数据，是数学表达式的几个集合
     * @param args 解析器需要使用到的参数 其中第一个参数是数据解析正则表达式
     * @return 解析器解析之后的所有表达式风咋黄起来的结果数值
     */
    @Override
    public Container[] parse(String data, String... args) {
        if (args.length >= 1) {
            final String regExp = args[0];
            Pattern pattern = PATTERN_HASH_MAP.get(regExp);
            if (pattern == null) {
                pattern = Pattern.compile(regExp);
                PATTERN_HASH_MAP.put(regExp, pattern);
            }
            final ArrayList<StringData> arrayList = new ArrayList<>();
            final Matcher matcher = pattern.matcher(data);
            while (matcher.find()) {
                arrayList.add(new StringData(matcher.group(), regExp));
            }
            return arrayList.toArray(new StringData[0]);
        } else {
            throw new RuntimeException("针对自定义解析的参数，您必须要将解析正则表达式在第一个索引位置书写好。");
        }
    }

    /**
     * @return 当前缓冲区中，缓存的正则对象的数量
     */
    public int cacheSize() {
        return PATTERN_HASH_MAP.size();
    }

    /**
     * 判断一个正则表达式是否已经被缓存过。
     *
     * @param RegExp 需要被判断缓存状态的正则表达式。
     * @return 正则表达式的缓存情况，如果返回true，代表正则表达式已经被缓存过。
     */
    public boolean containsRegExp(String RegExp) {
        return PATTERN_HASH_MAP.containsKey(RegExp);
    }

    /**
     * 判断一个正则表达式对象是否已经被缓存过。
     *
     * @param pattern 需要被判断缓存状态的正则表达式对象。
     * @return 正则表达式的缓存情况，如果返回true，代表正则表达式对象已经被缓存过。
     */
    public boolean containsPattern(Pattern pattern) {
        return PATTERN_HASH_MAP.containsValue(pattern);
    }

    /**
     * 清理所有缓存过的正则对象。
     */
    public void cacheClear() {
        PATTERN_HASH_MAP.clear();
    }
}
