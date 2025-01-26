package io.github.beardedManZhao.starSpider.parser;

import com.alibaba.fastjson2.JSONObject;
import io.github.beardedManZhao.starSpider.ConstantRegion;
import io.github.beardedManZhao.starSpider.container.Container;
import io.github.beardedManZhao.starSpider.container.FastJsonDocument;

/**
 * @author zhao
 */
public class FastJsonParser extends PatternParser {

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_FASTJSON;
    }

    /**
     * 解析一个字符串
     *
     * @param data 需要被解析的数据
     * @param args 解析器需要使用到的参数
     * @return 解析器解析之后的结果数值
     */
    @Override
    public Container[] parse(String data, String... args) {
        FastJsonDocument[] fastJsonDocuments = new FastJsonDocument[args.length == 0 ? 1 : args.length - 1];
        if (args.length > 0) {
            int count = 0;
            String mode = args[0];
            if (mode.equalsIgnoreCase("other")) {
                JSONObject jsonObject = JSONObject.parseObject(data);
                for (int i = 1; i < args.length; i++) {
                    String name = args[i];
                    fastJsonDocuments[count++] = new FastJsonDocument(jsonObject.getString(name), name);
                }
            } else if (mode.equalsIgnoreCase("json")) {
                JSONObject jsonObject = JSONObject.parseObject(data);
                for (int i = 1; i < args.length; i++) {
                    String name = args[i];
                    fastJsonDocuments[count++] = new FastJsonDocument(jsonObject.getJSONObject(name), name);
                }
            } else {
                throw new RuntimeException("解析Json时出现异常，您传入的解析模式不存在哦！\nAn exception occurred while parsing Json. The parsing mode you passed in does not exist!\nERROR => " + mode +
                        "\n 使用示例: FastJsonDocument[] parse = (FastJsonDocument[]) StarSpider.parse(new File(...), \"json\", \"json\", \"node1\", \"node2\")" +
                        "\n 解析模式\n" +
                        "       【json】： 代表将目标节点作为一个json文档对象解析，这样返回的JsonDocument具有更多功能，使用方式与JSONObject的效果是一样的。\n" +
                        "       【other】：代表将目标节点作为一个数据类型获取到，这样在JsonDocument中存储的往往是一个字符串，并不具有太多功能，不建议使用。");
            }
        } else {
            fastJsonDocuments[0] = new FastJsonDocument(JSONObject.parseObject(data), "root");
        }
        return fastJsonDocuments;
    }
}
