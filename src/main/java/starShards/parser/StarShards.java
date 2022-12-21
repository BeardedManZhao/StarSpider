package starShards.parser;

import starShards.container.Container;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

/**
 * 星陨爬虫解析库门户，在这里指定各类爬虫组件进行数据解析任务，对各类组件可以进行统一管理。
 * <p>
 * Starlite reptile analysis library portal, where all kinds of reptile components are designated for data analysis tasks, and all kinds of components can be managed uniformly.
 *
 * @author zhao
 */
public final class StarShards implements Parser {

    // 内置实现解析组件池
    public final static LABELParser HTML_PARSER = new LABELParser();
    private final static HashMap<String, Parser> STRING_PARSER_HASH_MAP = new HashMap<>();

    // 内置实现解析组件注册
    static {
        StarShards.register(HTML_PARSER);
    }

    private StarShards() {
    }

    /**
     * 将一个解析组件注册到星陨门户中。
     *
     * @param parser 需要被注册的组件
     */
    public static void register(Parser parser) {
        STRING_PARSER_HASH_MAP.put(parser.getName(), parser);
    }

    /**
     * 将一个解析组件从星陨门户中注销注册
     *
     * @param Name 需要被注销的组件的名称
     * @return 被注销的组件
     */
    public static Parser unRegister(String Name) {
        return STRING_PARSER_HASH_MAP.remove(Name);
    }

    /**
     * 使用指定的组件解析出来数据
     *
     * @param data   需要被解析的数据
     * @param parser 解析数据是使用的数据解析器对象，这里是一个解析器对象
     * @param args   解析数据所需要的参数，不同解析器有不同的实现
     * @return 解析出来的结果数据
     */
    public static Container[] parse(String data, Parser parser, String... args) {
        return parser.parse(data, args);
    }

    /**
     * 使用指定的组件解析出来数据
     *
     * @param data      需要被解析的数据
     * @param parseName 解析数据时使用的数据解析器
     * @param args      解析数据所需要的参数，不同解析器由不同的实现
     * @return 解析出来的结果数据
     */
    public static Container[] parse(String data, String parseName, String... args) {
        Parser parser = STRING_PARSER_HASH_MAP.get(parseName);
        if (parser != null) {
            return parse(data, parser, args);
        } else {
            throw new RuntimeException("您想要使用的组件【" + parseName + "】似乎没有被注册，在这里没有找到它。\n" +
                    "It seems that the component [" + parseName + "] you want to use has not been registered, and it is not found here.");
        }
    }

    /**
     * 使用指定的组件解析一份文件中的数据
     *
     * @param file      需要被解析的数据所在的文件对象
     * @param parseName 解析数据时使用的数据解析器
     * @param args      解析数据所需要的参数，不同解析器由不同的实现
     * @return 解析器解析之后的结果对象
     * @throws IOException 读取文件数据时可能发生的错误
     */
    public static Container[] parse(File file, String parseName, String... args) throws IOException {
        Parser parser = STRING_PARSER_HASH_MAP.get(parseName);
        if (parser != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);
            }
            Container[] parse = parse(stringBuilder.toString(), parser, args);
            bufferedReader.close();
            return parse;
        } else {
            throw new RuntimeException("您想要使用的组件【" + parseName + "】似乎没有被注册，在这里没有找到它。\n" +
                    "It seems that the component [" + parseName + "] you want to use has not been registered, and it is not found here.");
        }
    }

    public static Container[] parse(URL url, String parseName, String... args) throws IOException {
        Parser parser = STRING_PARSER_HASH_MAP.get(parseName);
        if (parser != null) {
            InputStream inputStream = url.openStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[65535];
            int offset;
            while ((offset = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, offset);
            }
            bufferedInputStream.close();
            inputStream.close();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            return parse(byteArrayOutputStream.toString(), parser, args);
        } else {
            throw new RuntimeException("您想要使用的组件【" + parseName + "】似乎没有被注册，在这里没有找到它。\n" +
                    "It seems that the component [" + parseName + "] you want to use has not been registered, and it is not found here.");
        }
    }

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return "Star shards";
    }

    /**
     * 解析一个字符串，该方法用于测试，如果正常，这里会返回一个null，
     *
     * @param data 任意数据
     * @param args 任意数据
     * @return 返回一个null
     */
    @Override
    public Container[] parse(String data, String... args) {
        System.out.println("感谢使用。");
        return null;
    }
}
