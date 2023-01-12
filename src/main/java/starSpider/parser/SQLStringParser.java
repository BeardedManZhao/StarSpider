package starSpider.parser;

import starSpider.ConstantRegion;
import starSpider.container.Container;
import starSpider.container.SQLStringData;
import strInfo.parser.AlterParser;
import strInfo.parser.CreateParser;
import strInfo.parser.InsertParser;
import strInfo.parser.SelectParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类是专注于SQL语句的解析组件，在该类中有着专用于SQL解析的函数，原理采用的是SQLStringINFO的SQL解析树。
 * <p>
 * This class is a parsing component that focuses on SQL statements. In this class, there are functions dedicated to SQL parsing. The principle is the SQL parsing tree of SQLStringINFO.
 *
 * @author zhao
 */
public class SQLStringParser extends PatternParser implements Parser {

    protected final static Pattern SQL_PATTERN = Pattern.compile("(select|create|insert|alter)[\\s\\S]+?;", Pattern.CASE_INSENSITIVE);
    protected final static SelectParser selectParser = SelectParser.getInstance();
    protected final static CreateParser createParser = CreateParser.getInstance();
    protected final static InsertParser insertParser = InsertParser.getInstance();
    protected final static AlterParser alterParser = AlterParser.getInstance();

    /**
     * @return 该解析器的名称，用于区别解析器之间的联系，同时也是获取解析器的标识。
     * <p>
     * The name of the parser is used to distinguish the relationship between parsers, and also to obtain the identifier of the parser.
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_SQL_STRING_INFO;
    }

    /**
     * 解析一个字符串，并返回解析到的所有结果数据，具体的操作有不同的实现
     * <p>
     * Parse a string and return all the result data parsed. The specific operation has different implementations
     *
     * @param data 需要被解析的数据的字符串对象
     *             <p>
     *             The string object of the data to be parsed
     * @param args 解析器需要使用到的参数，有不同的解析器实现不同的形参格式
     *             <p>
     *             The parameters that the parser needs to use. Different parsers implement different parameter formats
     *             <p>
     *             在这里是将sql语句解析模式传递进来，目前支持select create insert alter这几种解析模式
     * @return 解析器解析之后的结果对象
     * <p>
     * The result object parsed by the parser
     */
    @Override
    public Container[] parse(String data, String... args) {
        ArrayList<SQLStringData> arrayList = new ArrayList<>();
        if (args.length == 0) {
            // 泛滥模式，如果不设置形参会进入该模式，在该模式下会匹配所有满足条件且可以被处理的sql语句
            Matcher matcher = SQL_PATTERN.matcher(data);
            while (matcher.find()) {
                try {
                    String mod = matcher.group(1);
                    if ("select".equalsIgnoreCase(mod)) {
                        arrayList.add(new SQLStringData(mod, selectParser.parseSql(matcher.group())));
                    } else if ("create".equalsIgnoreCase(mod)) {
                        arrayList.add(new SQLStringData(mod, createParser.parseSql(matcher.group())));
                    } else if ("insert".equalsIgnoreCase(mod)) {
                        arrayList.add(new SQLStringData(mod, insertParser.parseSql(matcher.group())));
                    } else if ("alter".equalsIgnoreCase(mod)) {
                        arrayList.add(new SQLStringData(mod, alterParser.parseSql(matcher.group())));
                    }
                } catch (RuntimeException ignored) {

                }
            }
        } else {
            // 如果设置了形参，就是精准模式，在该模式下，只会解析第一个参数中的sql语句。同时正则性能会进行优化
            String mod = args[0];
            if ("select".equalsIgnoreCase(mod)) {
                Pattern compile = super.getPattern(mod + "[\\s\\S]+?;", Pattern.CASE_INSENSITIVE);
                Matcher matcher = compile.matcher(data);
                while (matcher.find()) {
                    try {
                        arrayList.add(new SQLStringData(mod, selectParser.parseSql(matcher.group())));
                    } catch (RuntimeException ignored) {

                    }
                }
            } else if ("create".equalsIgnoreCase(mod)) {
                Pattern compile = super.getPattern(mod + "[\\s\\S]+?;", Pattern.CASE_INSENSITIVE);
                Matcher matcher = compile.matcher(data);
                while (matcher.find()) {
                    try {
                        arrayList.add(new SQLStringData(mod, createParser.parseSql(matcher.group())));
                    } catch (RuntimeException ignored) {

                    }
                }
            } else if ("insert".equalsIgnoreCase(mod)) {
                Pattern compile = super.getPattern(mod + "[\\s\\S]+?;", Pattern.CASE_INSENSITIVE);
                Matcher matcher = compile.matcher(data);
                while (matcher.find()) {
                    try {
                        arrayList.add(new SQLStringData(mod, insertParser.parseSql(matcher.group())));
                    } catch (RuntimeException ignored) {

                    }
                }
            } else if ("alter".equalsIgnoreCase(mod)) {
                Pattern compile = super.getPattern(mod + "[\\s\\S]+?;", Pattern.CASE_INSENSITIVE);
                Matcher matcher = compile.matcher(data);
                while (matcher.find()) {
                    try {
                        arrayList.add(new SQLStringData(mod, alterParser.parseSql(matcher.group())));
                    } catch (RuntimeException ignored) {

                    }
                }
            } else {
                throw new RuntimeException("SQLStringINFO的解析模式错误，您传入的解析模式[" + mod +
                        "]暂时不支持！\nThe parsing mode of SQLStringINFO is incorrect. The parsing mode [\"+mod+\"] you passed in does not support it temporarily!\nERROR => " + mod +
                        "\n目前支持的解析模式\n" +
                        "【select】\t代表解析一个select语句。\n【create】\t代表解析一个create的建表语句\n【insert】\t代表解析一个数据插入语句。\n【alter】\t代表解析一个修改表的sql语句。");
            }
        }
        return arrayList.toArray(new SQLStringData[0]);
    }
}
