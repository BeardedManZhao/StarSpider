package starShards;

/**
 * 星陨解析库常量区静态类，其中存储的都是全局经常使用的数据对象。
 * <p>
 * The static class in the constant area of the meteorite parsing library stores data objects that are often used globally.
 *
 * @author zhao
 */
public final class ConstantRegion {

    public final static char HORIZONTAL_BAR_CHARACTER = '-';
    public final static char LESS_THAN_SIGN_CHAR = '<';
    public final static char GREATER_THAN_SIGN_CHAR = '>';
    public final static String LESS_THAN_SIGN_STRING = String.valueOf(LESS_THAN_SIGN_CHAR);
    public final static String End_Label1 = LESS_THAN_SIGN_STRING + '/';
    public final static String GREATER_THAN_SIGN_STRING = String.valueOf(GREATER_THAN_SIGN_CHAR);
    public final static char COMMA_CHAR = ',';
    public final static String COMMA_STRING = String.valueOf(COMMA_CHAR);
    public final static char REGEXP_OR = '|';
    public final static char REGEXP_GREEDY_SYMBOL = '?';
    public final static char SPACE = ' ';
    public final static String STRING_NULL = "null";
    public final static String PARSER_NAME_LABEL = "LABEL";
    public final static String PARSER_NAME_ME = "ME";
    public final static String PARSER_NAME_HTML = "HTML";
    public final static String PARSER_NAME_REGEXP = "regular";
    public final static int Packet_size = 65535;
    public final static String REGEXP_ME_ALL = "[0-9().]+ *[+\\-*/%][+\\-*/%0-9(). ]+[0-9]";
    public final static String REGEXP_LABEL_ATTRIBUTE_SEGMENTATION = "=";
    public final static String REGEXP_COMMA_ALL = new String(new char[]{COMMA_CHAR, '+'});
    public final static String REGEXP_SPACE_ALL = new String(new char[]{SPACE, '+'});
    public final static String REGEXP_MATCH_ALL1 = ".*" + REGEXP_GREEDY_SYMBOL;
    public final static String REGEXP_START_LABEL_2 = REGEXP_MATCH_ALL1 + GREATER_THAN_SIGN_CHAR + REGEXP_GREEDY_SYMBOL;
    public final static String REGEXP_LABEL_REGULARITY = LESS_THAN_SIGN_CHAR + REGEXP_MATCH_ALL1 + GREATER_THAN_SIGN_CHAR;
    public final static String REGEXP_MATCH_ALL2 = "[\\s\\S]*";
}
