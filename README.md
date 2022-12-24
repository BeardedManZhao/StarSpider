# StarShards

## introduce

StarShards Parsing Library is a library provided for data parsing, which has many built-in data parsing components. It
supports the parsing of network data packets, file objects, and string objects. It is a data parsing method implemented
by Java, and a powerful tool that can meet the needs of crawlers, intelligent extraction, and so on.

[//]: # (### 如何获取？)

[//]: # (目前该组件已支持maven与gradle两种方式获取。)

[//]: # (#### maven配置)

[//]: # (```xml)

[//]: # ()

[//]: # (```)

[//]: # (#### gradle配置)

[//]: # (```xml)

[//]: # ()

[//]: # (```)

### What is the architecture like?

In the framework, users can directly interact with starshards portal class (starShards. parser. StarShards). Through
this class, you can extract and parse the contents of URL, FILE, String, and three objects. Users can transfer formal
parameters to starshards analytic functions according to their needs to achieve different effects.

StarShards portal will obtain the corresponding components from the hash table according to the formal parameters passed
by the user for data analysis, and directly pass the component analysis result object through the StarShards portal
class (starShards. parser. StarShards)

It is returned to the user. The user can perform more operations according to various functions in the result. The
current result objects are as follows.

#### Type and function of result object

| Result Object Data Type             | Corresponding resolution component | Special functions                   | Supported versions |
|-------------------------------------|------------------------------------|-------------------------------------|--------------------|
| starShards.container.LABELDocument  | starShards.parser.LABELParser      | 面向标签节点进行数据提取操作                      | v1.0               |
| starShards.container.HTMLDocument   | starShards.parser.HTMLParser       | 面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现 | v1.0               |
| starShards.container.ExpressionData | starShards.parser.MEParser         | 面向每一个数学表达式进行数据提取操作                  | v1.0               |
| starShards.container.StringData     | starShards.parser.PatternParser    | 面向每一个符合提取条件的字符串                     | v1.0               |

#### Resolve the type and function of components

| Component name     | Component Type                  | Author URI                        | Format oriented   | Registered to portal | effect                                              | Supported versions |
|--------------------|---------------------------------|-----------------------------------|-------------------|----------------------|-----------------------------------------------------|--------------------|
| NULL               | starShards.parser.StarShards    | https://github.com/BeardedManZhao | URL, FILE, String | NO                   | 与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务                | v1.0               |
| PARSER_NAME_LABEL  | starShards.parser.LABELParser   | https://github.com/BeardedManZhao | 任何使用标签进行数据存储的文本内容 | YES                  | 提取与解析标签数据中的每一个节点                                    | v1.0               |
| PARSER_NAME_HTML   | starShards.parser.HTMLParser    | https://github.com/BeardedManZhao | HTML XML          | YES                  | 通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象 | v1.0               |
| PARSER_NAME_REGEXP | starShards.parser.PatternParser | https://github.com/BeardedManZhao | String            | YES                  | 通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据              | v1.0               |
| PARSER_NAME_ME     | starShards.parser.MEParser      | https://github.com/BeardedManZhao | String            | YES                  | 智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果                    | v1.0               |
| ...                | ...                             | Dear friends                      | ...               | NO                   | 事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星陨门户        | ...                |

## 有哪些功能？

In the StarShards portal, users can directly set parameters and parse objects through the parse function to obtain the
parsing results. The parsing work in the library is performed by the parser. There are many implementations of the
parser, and each component registered to Starlite can be accessed through the portal. Next, we will show more
information.

### 标签文本的解析

A typical example of label text is HTML. For the task of parsing such text, you can use the label component (
LABELParser), as shown below

```java
package starShards;

import starShards.container.LABELDocument;
import starShards.parser.StarShards;

import java.io.File;
import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 构建需要爬取页面的URL
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); 可以使用url进行数据的解析
        // 也可使用文件对象，在这里我们对一个文本数据进行爬取
        File file = new File("D:\\MyGitHub\\StarShards\\src\\main\\resources\\Test.html");
        // 使用星陨解析组件，对file对象进行LABEL页面的解析（也可以对url进行解析） 按照节点路径 table > tbody
        // LABELDocument[] parses1 = (LABELDocument[]) StarShards.parse(url, "LABEL", "nodePath", "table", "tbody");
        LABELDocument[] parses1 = (LABELDocument[]) StarShards.parse(file, "LABEL", "nodePath", "table", "tbody");
        // 迭代每一个被解析到的节点文档对象
        for (LABELDocument labelDocument : parses1) {
            // 获取节点名称
            System.out.println(labelDocument.getName());
            // 获取节点数据
            System.out.println(labelDocument.getText());
            // 获取节点属性（id的值）
            System.out.println(labelDocument.getAttrib("id"));
            // 获取该节点下所有子节点的字符串数据（也可以获取到数组形式的数据）
            System.out.println(labelDocument.getChildrenText());
        }

        // 使用星陨解析组件，对file对象进行LABEL页面的爬取 按照节点[tr]
        LABELDocument[] parses2 = (LABELDocument[]) StarShards.parse(file, "LABEL", "node", "tr");
        // 迭代获取到的所有tr标签
        System.out.println("+---------------------------------------------------------------------------------------+");
        for (LABELDocument container : parses2) {
            // 将每一个tr标签以及标签子节点的数据以数组的形式获取到（也可以像上面一样使用getChildrenText直接获取字符串）
            String[] childrenTextArray = container.getChildrenTextArray(false);
            if (childrenTextArray.length >= 3) {
                System.out.println(
                        childrenTextArray[0] + "\t|\t" +
                                childrenTextArray[1] + "\t|\t" +
                                childrenTextArray[2] + "\t|\t"
                );
            }
        }
    }
}
```

### HTML或XML属性的查找

In the library, there is a component, HTMLParser, which is dedicated to extracting data such as HTML and XML. It extends
a new function based on the label component (LABELParser), that is, it searches through attributes. It has all the
functions of the label component, and it is also HTML

The crawling of XML texts has been optimized, making the component much better for crawling HTML and XML. For the
demonstration effect, such a file has been prepared. The file content is shown below.

```html

<table id="table1">
    <colgroup>
        <col style="width: 31%"/>
        <col style="width: 27%"/>
        <col style="width: 31%"/>
        <col style="width: 10%"/>
    </colgroup>
    <thead>
    <tr class="header">
        <th>结果对象数据类型</th>
        <th>该结果类型对应的提取组件</th>
        <th>该结果类型的特有功能</th>
        <th>该结果类型的支持版本</th>
    </tr>
    </thead>
    <tbody>
    <tr class="odd">
        <td>starShards.container.LABELDocument</td>
        <td>starShards.parser.LABELParser</td>
        <td>面向标签节点进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>starShards.container.HTMLDocument</td>
        <td>starShards.parser.HTMLParser</td>
        <td>面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>starShards.container.ExpressionData</td>
        <td>starShards.parser.MEParser</td>
        <td>面向每一个数学表达式进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>starShards.container.StringData</td>
        <td>starShards.parser.PatternParser</td>
        <td>面向每一个符合提取条件的字符串</td>
        <td>v1.0</td>
    </tr>
    </tbody>
</table>
<h4 id="解析组件的类型与作用">解析组件的类型与作用</h4>
<table id="table2" style="width:100%;">
    <colgroup>
        <col style="width: 11%"/>
        <col style="width: 20%"/>
        <col style="width: 19%"/>
        <col style="width: 10%"/>
        <col style="width: 4%"/>
        <col style="width: 29%"/>
        <col style="width: 4%"/>
    </colgroup>
    <thead>
    <tr class="header">
        <th>组件名称(常量区中的常量名)</th>
        <th>组件类型</th>
        <th>作者主页</th>
        <th>面向格式</th>
        <th>已注册至门户</th>
        <th>组件作用</th>
        <th>组件支持版本</th>
    </tr>
    </thead>
    <tbody>
    <tr class="odd">
        <td>NULL</td>
        <td>starShards.parser.StarShards</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>URL, FILE, String</td>
        <td>NO</td>
        <td>与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_LABEL</td>
        <td>starShards.container.LABELDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>任何使用标签进行数据存储的文本内容</td>
        <td>YES</td>
        <td>提取与解析标签数据中的每一个节点</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_HTML</td>
        <td>starShards.container.HTMLDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>HTML XML</td>
        <td>YES</td>
        <td>通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_REGEXP</td>
        <td>starShards.parser.PatternParser</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>String</td>
        <td>YES</td>
        <td>通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_ME</td>
        <td>starShards.parser.MEParser</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>String</td>
        <td>YES</td>
        <td>智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>…</td>
        <td>…</td>
        <td>Dear friends</td>
        <td>…</td>
        <td>NO</td>
        <td>事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星陨门户</td>
        <td>…</td>
    </tr>
    </tbody>
</table>
```

There are two tables. There is an ID attribute in the table label. The ID attributes of the two tables are different.
Now we want to obtain the node object of the second table. The specific operation method is shown as follows (it shows
that the method of obtaining through attributes is the same as that of LABELParser).

```java
package starShards;

import starShards.container.HTMLDocument;
import starShards.parser.StarShards;

import java.io.File;
import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 构建需要爬取页面的URL
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); 可以使用url进行数据的解析
        // 也可使用文件对象，在这里我们对一个文本数据进行爬取
        File file = new File("D:\\MyGitHub\\StarShards\\src\\main\\resources\\Test.html");
        // 使用星陨库将文件中 id 属性为 table2 的文件数据提取出来
        HTMLDocument[] parse = (HTMLDocument[]) StarShards.parse(file, "HTML", "attr", "id", "table2");
        // 获取到当前节点下的所有tr标签
        for (HTMLDocument htmlDocument : parse[0].getAllChildrenByNodeName("tr")) {
            // 打印tr 标签的数据
            System.out.println(htmlDocument.getChildrenText());
        }
    }
}
```

- 提取结果

```
组件名称(常量区中的常量名)	组件类型	作者主页	面向格式	已注册至门户	组件作用	组件支持版本	
NULL	starShards.parser.StarShards	https://github.com/BeardedManZhao	URL, FILE, String	NO	与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务	v1.0	
PARSER_NAME_LABEL	starShards.container.LABELDocument	https://github.com/BeardedManZhao	任何使用标签进行数据存储的文本内容	YES	提取与解析标签数据中的每一个节点	v1.0	
PARSER_NAME_HTML	starShards.container.HTMLDocument	https://github.com/BeardedManZhao	HTML XML	YES	通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象	v1.0	
PARSER_NAME_REGEXP	starShards.parser.PatternParser	https://github.com/BeardedManZhao	String	YES	通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据	v1.0	
PARSER_NAME_ME	starShards.parser.MEParser	https://github.com/BeardedManZhao	String	YES	智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果	v1.0	
…	…	Dear friends	…	NO	事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星陨门户	…	
```

### 数学表达式的提取

For a piece of chaotic and unformatted data containing mathematical expressions, there is a component in the library (
starShards. parser. MEParser) that can extract all the mathematical expressions and judge whether to calculate the
results according to the parameters passed by the user. Next is the demonstration of the use of this component (directly
crawl all the mathematical expressions in a page of Baidu Encyclopedia here).

```java
package starShards;

import starShards.container.ExpressionData;
import starShards.parser.StarShards;

import java.io.IOException;
import java.net.URL;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // 构建需要解析的url，这里是百度百科的网站页面
        URL url1 = new URL("https://baike.baidu.com/item/%E8%A1%A8%E8%BE%BE%E5%BC%8F/7655228");
        // 使用星陨组件，对url对象进行数学表达式的智能提取与计算，这里指定使用的解析组件为ME（表达式解析组件）以及计算模式（brackets）
        ExpressionData[] parse = (ExpressionData[]) StarShards.parse(url1, "ME", "brackets");
        for (ExpressionData expressionData : parse) {
            System.out.println("提取到表达式【" + expressionData.getName() + "】 = " + expressionData.getText());
        }
    }
}
```

### 正则表达式的匹配

If you want to use regular expressions to extract data, there is also a component in the library that supports you to do
this. The next step is to demonstrate the use of this component

```java
package starShards;

import starShards.container.Container;
import starShards.parser.StarShards;

import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) {
        // 提取出一个字符串中所有的数值
        Container[] regulars = StarShards.parse(
                "zhaodsandjsaklfdhajkndfjsdhfaudSUD123HDUSIFCNDJNJDKS678DJSKAF2341233HDSD",
                "regular", "\\d+"
        );
        for (Container regular : regulars) {
            System.out.println(regular.getText());
        }
    }
}
```

- Calculation results

```
123
678
2341233
```

## More information

Thank you for your use. The parsing components in the library will flow and continue to be optimized at any time. If you
want to register the components you have implemented with Starlite, please send the source code and some information to
your mailbox Liming7887@qq.com In fact, we are looking forward to adding your implementation to the portal.
<hr>

- date: 2022-12-24