package starSpider.parser;

import starSpider.ConstantRegion;
import starSpider.container.Container;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * 星蛛爬虫解析库门户，在这里指定各类爬虫组件进行数据解析任务，对各类组件可以进行统一管理。
 * <p>
 * StarSpider reptile analysis library portal, where all kinds of reptile components are designated for data analysis tasks, and all kinds of components can be managed uniformly.
 *
 * @author zhao
 */
public final class StarSpider {

    // 内置实现解析组件池
    public final static LABELParser LABEL_PARSER = new LABELParser();
    public final static MEParser ME_PARSER = new MEParser();
    public final static HTMLParser HTML_PARSER = new HTMLParser();
    public final static PatternParser PATTERN_PARSER = new PatternParser();
    public final static SQLStringParser SQL_STRING_PARSER = new SQLStringParser();
    public final static FastJsonParser JSON_PARSER = new FastJsonParser();
    public final static float VERSION = 1.0f;
    private final static HashMap<String, Parser> STRING_PARSER_HASH_MAP = new HashMap<>();

    // 内置实现解析组件注册
    static {
        StarSpider.register(LABEL_PARSER);
        StarSpider.register(ME_PARSER);
        StarSpider.register(HTML_PARSER);
        StarSpider.register(PATTERN_PARSER);
        StarSpider.register(SQL_STRING_PARSER);
        StarSpider.register(JSON_PARSER);
    }

    private StarSpider() {
    }

    /**
     * 将一个解析组件注册到星蛛门户中。
     * <p>
     * Register a resolution component to the Star Spider Portal.
     *
     * @param parser 需要被注册的组件
     *               <p>
     *               Components to be registered
     */
    public static void register(Parser parser) {
        STRING_PARSER_HASH_MAP.put(parser.getName(), parser);
    }

    /**
     * 将一个解析组件从星蛛门户中注销注册
     * <p>
     * Unregister a parsing component from Starscream Portal
     *
     * @param Name 需要被注销的组件的名称
     *             <p>
     *             The name of the component that needs to be unregistered
     * @return 需要被注销的组件名称
     * <p>
     * Name of the component to be unregistered
     */
    public static Parser unRegister(String Name) {
        return STRING_PARSER_HASH_MAP.remove(Name);
    }

    /**
     * 使用指定的组件解析出来数据
     *
     * @param data   需要被解析的数据
     *               <p>
     *               Data to be parsed
     * @param parser 解析数据是使用的数据解析器对象，这里是一个解析器对象
     *               <p>
     *               Parsing data is the data parser object used. Here is a parser object
     * @param args   解析数据所需要的参数，不同解析器有不同的实现
     *               <p>
     *               Parameters required for parsing data. Different parsers have different implementations
     * @return 解析出来的结果数据
     */
    public static Container[] parse(String data, Parser parser, String... args) {
        return parser.parse(data, args);
    }

    /**
     * 使用指定的组件解析出来数据
     *
     * @param data      需要被解析的数据
     *                  <p>
     *                  Data to be parse
     * @param parseName 解析数据时使用的数据解析器的名称
     *                  <p>
     *                  The name of the data parser used when parsing data
     * @param args      解析数据所需要的参数，不同解析器有不同的实现
     *                  <p>
     *                  Parameters required for parsing data. Different parsers have different implementations
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
     * 使用指定的组件解析一份文件中的数据，使用IO流对一份数据进行加载，并将结果传递给解析器。
     * <p>
     * Use the specified component to parse the data in a file, use the IO stream to load the data, and pass the result to the parser.
     *
     * @param file      需要被解析的数据所在的文件对象
     *                  <p>
     *                  The file object of the data to be parsed
     * @param parseName 解析数据时使用的数据解析器
     *                  <p>
     *                  Data parser used when parsing data
     * @param args      解析数据所需要的参数，不同解析器由不同的实现
     *                  <p>
     *                  Parameters required for parsing data. Different parsers are implemented differently
     * @return 解析器解析字符串数据之后的结果对象
     * <p>
     * The result object after the parser parses the string data
     * @throws IOException 读取文件数据时可能发生的错误
     *                     <p>
     *                     Possible errors when reading file data
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

    /**
     * @return 所有已注册到门户的组件名称。
     * <p>
     * Names of all components registered to the portal.
     */
    public static Set<String> listAllParserName() {
        return STRING_PARSER_HASH_MAP.keySet();
    }

    /**
     * 使用指定的组件解析一份网络中的数据，使用URL对一份网络数据包中的数据进行加载，并将结果传递给解析器。
     * <p>
     * Use the specified component to parse the data in a network, use the URL to load the data in a network packet, and pass the result to the parser.
     *
     * @param url       需要被解析的URL，门户会对该URL发起一个请求，并获取到回复的HTML页面的数据
     *                  <p>
     *                  For the URL to be parsed, the portal will send a request to the URL and obtain the data of the returned HTML page
     * @param parseName 解析数据时使用的数据解析器
     *                  <p>
     *                  Data parser used when parsing data
     * @param args      解析数据所需要的参数，不同解析器有不同的实现
     *                  <p>
     *                  Parameters required for parsing data. Different parsers are implemented differently
     * @return 解析器解析字符串数据之后的结果对象
     * <p>
     * The result object after the parser parses the string data
     * @throws IOException 网络IO中可能会发生的错误
     *                     <p>
     *                     Possible errors in network IO
     */
    public static Container[] parse(URL url, String parseName, String... args) throws IOException {
        Parser parser = STRING_PARSER_HASH_MAP.get(parseName);
        if (parser != null) {
            final InputStream inputStream = url.openStream();
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[ConstantRegion.Packet_size];
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
}
