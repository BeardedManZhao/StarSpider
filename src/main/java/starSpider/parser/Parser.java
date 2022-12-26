package starSpider.parser;

import starSpider.container.Container;

/**
 * 解析器接口对象，所有的解析器由此类拓展
 *
 * @author zhao
 */
public interface Parser {

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    String getName();

    /**
     * 解析一个字符串
     *
     * @param data 需要被解析的数据
     * @param args 解析器需要使用到的参数
     * @return 解析器解析之后的结果数值
     */
    Container[] parse(String data, String... args);
}
