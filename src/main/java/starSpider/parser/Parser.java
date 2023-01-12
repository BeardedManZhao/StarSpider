package starSpider.parser;

import starSpider.container.Container;

/**
 * 解析器接口对象，所有的解析器由此类拓展，该类就是针对字符串对象做出解析操作的逻辑类。
 * <p>
 * Parser interface object. All parsers are extended by this class, which is a logical class for parsing string objects.
 *
 * @author zhao
 */
public interface Parser {

    /**
     * @return 该解析器的名称，用于区别解析器之间的联系，同时也是获取解析器的标识。
     * <p>
     * The name of the parser is used to distinguish the relationship between parsers, and also to obtain the identifier of the parser.
     */
    String getName();

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
     * @return 解析器解析之后的结果对象
     * <p>
     * The result object parsed by the parser
     */
    Container[] parse(String data, String... args);
}
