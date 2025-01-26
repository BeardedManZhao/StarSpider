# ![image](https://user-images.githubusercontent.com/113756063/209512042-8b0f1b56-e783-48a6-9ff6-99360aa87198.png) StarSpider

## introduce

StarSpider Parsing Library is a library provided for data parsing, which has many built-in data parsing components. It
supports the parsing of network data packets, file objects, and string objects. It is a data parsing method implemented
by Java, and a powerful tool that can meet the needs of crawlers, intelligent extraction, and so on.

### How to obtain it?

At present, this component supports both Maven and Gradle methods for obtaining.

#### maven配置

```xml
<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>StarSpider</artifactId>
        <version>1.1</version>
    </dependency>
    <!-- 依赖导入 您可以自己选择版本！！！ -->
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>mathematical-expression</artifactId>
        <version>1.4.7</version>
        <!-- 至少要 1.4.7 版本 -->
    </dependency>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>SQLStringInFo</artifactId>
        <version>1.1</version>
        <!-- 支持所有版本 -->
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>2.0.21</version>
        <!-- 至少要 2.0.21 版本 -->
    </dependency>
</dependencies>
```

### What is the architecture like?

In the framework, users can directly interact with StarSpider portal class (starSpider. parser. StarSpider). Through
this class, you can extract and parse the contents of URL, FILE, String, and three objects. Users can transfer formal
parameters to StarSpider analytic functions according to their needs to achieve different effects.

StarSpider portal will obtain the corresponding components from the hash table according to the formal parameters passed
by the user for data analysis, and directly pass the component analysis result object through the StarSpider portal
class (starSpider. parser. StarSpider)

It is returned to the user. The user can perform more operations according to various functions in the result. The
current result objects are as follows.

#### Type and function of result object

| Result Object Data Type                                        | Corresponding resolution component                         | Special functions                   | Supported versions |
|----------------------------------------------------------------|------------------------------------------------------------|-------------------------------------|:------------------:|
| io.github.beardedManZhao.starSpider.container.LABELDocument    | io.github.beardedManZhao.starSpider.parser.LABELParser     | 面向标签节点进行数据提取操作                      |        v1.0        |
| io.github.beardedManZhao.starSpider.container.HTMLDocument     | io.github.beardedManZhao.starSpider.parser.HTMLParser      | 面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现 |        v1.0        |
| io.github.beardedManZhao.starSpider.container.ExpressionData   | io.github.beardedManZhao.starSpider.parser.MEParser        | 面向每一个数学表达式进行数据提取操作                  |        v1.0        |
| io.github.beardedManZhao.starSpider.container.StringData       | io.github.beardedManZhao.starSpider.parser.PatternParser   | 面向每一个符合提取条件的字符串                     |        v1.0        |
| io.github.beardedManZhao.starSpider.container.SQLStringData    | io.github.beardedManZhao.starSpider.parser.SQLStringParser | 面向每一个SQL语句进行语义解析操作                  |        v1.0        |
| io.github.beardedManZhao.starSpider.container.FastJsonDocument | io.github.beardedManZhao.starSpider.parser.FastJsonParser  | 面向json字符串中每一个json节点进行提取操作。          |        v1.0        |

#### Resolve the type and function of components

| Component name              | Component Type                                             | Author URI                                      | Format oriented   | Registered to portal | effect                                              | Supported versions |
|-----------------------------|------------------------------------------------------------|-------------------------------------------------|-------------------|:--------------------:|-----------------------------------------------------|--------------------|
| NULL                        | io.github.beardedManZhao.parser.starSpider.StarSpider      | https://github.com/BeardedManZhao               | URL, FILE, String |          NO          | 与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务                | v1.0               |
| PARSER_NAME_LABEL           | io.github.beardedManZhao.parser.starSpider.LABELParser     | https://github.com/BeardedManZhao               | 使用标签格式的字符串        |         YES          | 提取与解析标签数据中的每一个节点                                    | v1.0               |
| PARSER_NAME_HTML            | io.github.beardedManZhao.parser.starSpider.HTMLParser      | https://github.com/BeardedManZhao               | HTML XML          |         YES          | 通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象 | v1.0               |
| PARSER_NAME_REGEXP          | io.github.beardedManZhao.parser.starSpider.PatternParser   | https://github.com/BeardedManZhao               | String            |         YES          | 通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据              | v1.0               |
| PARSER_NAME_ME              | io.github.beardedManZhao.parser.starSpider.MEParser        | https://github.com/BeardedManZhao               | String            |         YES          | 智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果                    | v1.0               |
| PARSER_NAME_SQL_STRING_INFO | io.github.beardedManZhao.parser.starSpider.SQLStringParser | https://github.com/BeardedManZhao/SQLStringInFo | SQLString         |         YES          | 智能提取出所有的SQL语句，并通过SQLStringINFO解析库对语句进行解析，然后返回结果     | v1.0               |
| PARSER_NAME_FASTJSON        | io.github.beardedManZhao.parser.starSpider.FastJsonParser  | https://github.com/alibaba                      | Json              |         YES          | 针对JSON进行解析的库，将FastJson接入到门户，它返回的结果就是一个JSONObject对象  | v1.0               |
| ...                         | ...                                                        | Dear friends                                    | ...               |          NO          | 事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星蛛门户        | ...                |

## What are the functions?

In the StarSpider portal, users can directly set parameters and parse objects through the parse function to obtain the
parsing results. The parsing work in the library is performed by the parser. There are many implementations of the
parser, and each component registered to StarSpider can be accessed through the portal. Next, we will show more
information.

### Simple Introduction

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.HTMLDocument;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // Construct the URL of the page to be crawled
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); You can use url for data parsing
        // Alternatively, use a file object. Here we parse a text file.
        File file = new File("F:\\MyGithub\\StarSpider\\src\\main\\resources\\Test.html");
        // Get all tags with class "odd". "attr" means parsing by attribute, "nodePath" means parsing by path
        HTMLDocument[] parse = (HTMLDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_HTML, "attr", "class", "odd");
        for (HTMLDocument container : parse) {
            // Get the text within the tag
            System.out.println(container.getChildrenText());
            // Get tag attributes
            System.out.println(container.getAttribs());
            // Print its raw html
            System.out.println(container.HTMLData);
            // Continue parsing its internal nodes
            HTMLDocument[] tds = container.getAllChildrenByNodeName("td");
            // For convenience, print the text of the first td here
            System.out.println(tds[0]);
            // Print a line break for better readability
            System.out.println();
        }
    }
}
```

### Analysis of label text

A typical example of label text is HTML. For the task of parsing such text, you can use the label component (
LABELParser), as shown below

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.LABELDocument;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // Construct the URL of the page to be crawled
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); You can use URL for data parsing
        // Alternatively, use a file object. Here we parse a text file.
        File file = new File("F:\\MyGithub\\StarSpider\\src\\main\\resources\\Test.html");
        // Use the StarSpider parsing component to parse the LABEL page from the file object (or URL) using node path "table > tbody"
        // LABELDocument[] parses1 = (LABELDocument[]) StarSpider.parse(url, ConstantRegion.PARSER_NAME_LABEL, "nodePath", "table", "tbody");
        LABELDocument[] parses1 = (LABELDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_LABEL, "nodePath", "table", "tbody");
        // Iterate over each parsed node document object
        for (LABELDocument labelDocument : parses1) {
            // Get the node name
            System.out.println(labelDocument.getName());
            // Get the node data
            System.out.println(labelDocument.getText());
            // Get the value of the id attribute
            System.out.println(labelDocument.getAttrib("id"));
            // Get all child nodes' string data under this node (can also get array form data)
            System.out.println(labelDocument.getChildrenText());
            System.out.println("+---------------------------------------------------------------------------------------+");
        }

        // Use the StarSpider parsing component to crawl the LABEL page from the file object using node [tr]
        LABELDocument[] parses2 = (LABELDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_LABEL, "node", "tr");
        // Iterate over all tr tags obtained
        for (LABELDocument container : parses2) {
            // Get each tr tag and its child nodes' data in array form (can also use getChildrenText to get string directly as above)
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

### Lookup of HTML or XML attributes

In the library, there is a component, HTMLParser, which is dedicated to extracting data such as HTML and XML. It extends
a new function based on the label component (LABELParser), that is, it searches through attributes. It has all the
functions of the label component, and it is also HTML

The crawling of XML texts has been optimized, making the component much better for crawling HTML and XML. For the
demonstration effect, such a file has been prepared. The file content is shown below.

```html
<html lang="zh_CN">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<head>
    <title>测试标签页面</title>
</head>
<body>
<h1 id="StarSpider">StarSpider</h1>
<h2 id="介绍">介绍</h2>
<p>星蛛解析库是一款针对数据解析提供的库，其中内置诸多数据解析组件，支持对网络数据包，文件对象，以及字符串对象进行解析，是Java实现的一种数据解析手段，是可以实现爬虫，智能提取等需求的强悍工具。</p>
<h3 id="架构是什么样的">架构是什么样的？</h3>
<p>
    在框架中，用户直接与星蛛门户类(starSpider.parser.StarSpider)进行交互，通过该类，可对URL,FILE,String，三种对象进行内容提取与解析，用户可以根据自己的需求向星蛛解析函数中传递形参，实现不同效果。</p>
<p>星蛛门户会根据用户传递的形参，从哈希表中获取到对应的组件进行数据解析，并将组件解析结果对象直接通过星蛛门户类(starSpider.parser.StarSpider)
    返回给用户，用户可根据结果中的各类函数进行更多的操作，目前的结果对象有如下几种。</p>
<h4 id="结果对象的类型与作用">结果对象的类型与作用</h4>
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
    <tbody id="tb-1">
    <tr class="odd">
        <td>io.github.beardedManZhao.starSpider.container.LABELDocument</td>
        <td>io.github.beardedManZhao.starSpider.parser.LABELParser</td>
        <td>面向标签节点进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>io.github.beardedManZhao.starSpider.container.HTMLDocument</td>
        <td>io.github.beardedManZhao.starSpider.parser.HTMLParser</td>
        <td>面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>io.github.beardedManZhao.starSpider.container.ExpressionData</td>
        <td>io.github.beardedManZhao.starSpider.parser.MEParser</td>
        <td>面向每一个数学表达式进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>io.github.beardedManZhao.starSpider.container.StringData</td>
        <td>io.github.beardedManZhao.starSpider.parser.PatternParser</td>
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
        <td>io.github.beardedManZhao.starSpider.parser.StarSpider</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>URL, FILE, String</td>
        <td>NO</td>
        <td>与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_LABEL</td>
        <td>io.github.beardedManZhao.starSpider.container.LABELDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>任何使用标签进行数据存储的文本内容</td>
        <td>YES</td>
        <td>提取与解析标签数据中的每一个节点</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_HTML</td>
        <td>io.github.beardedManZhao.starSpider.container.HTMLDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>HTML XML</td>
        <td>YES</td>
        <td>通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_REGEXP</td>
        <td>io.github.beardedManZhao.starSpider.parser.PatternParser</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>String</td>
        <td>YES</td>
        <td>通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_ME</td>
        <td>io.github.beardedManZhao.starSpider.parser.MEParser</td>
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
        <td>事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星蛛门户</td>
        <td>…</td>
    </tr>
    </tbody>
</table>
<h2 id="有哪些功能">有哪些功能？</h2>
<p>在星蛛门户中，用户可以直接通过parse函数设置参数与解析对象，进而获取到解析结果，在库中的解析工作由解析器进行，解析器的实现有很多，每一个注册到星蛛的组件都可以使用门户访问到，接下来就展示下更多的信息。</p>
<h3 id="标签文本的解析">标签文本的解析</h3>
<h3 id="html或xml属性的查找">HTML或XML属性的查找</h3>
<h3 id="数学表达式的提取">数学表达式的提取</h3>
<h3 id="正则表达式的匹配">正则表达式的匹配</h3>
</body>
</html>

```

There are two tables. There is an ID attribute in the table label. The ID attributes of the two tables are different.
Now we want to obtain the node object of the second table. The specific operation method is shown as follows (it shows
that the method of obtaining through attributes is the same as that of LABELParser).

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.HTMLDocument;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // Construct the URL of the page to be crawled
        // URL url = new URL("https://xxx.xxx.xxx/xxx"); You can use URL for data parsing
        // Alternatively, use a file object. Here we parse a text file.
        File file = new File("F:\\MyGitHub\\StarSpider\\src\\main\\resources\\Test.html");
        // Use the StarSpider library to extract data from the file where the id attribute is "table2"
        HTMLDocument[] parse = (HTMLDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_HTML, "attr", "id", "table2");
        // Get all tr tags under the current node
        for (HTMLDocument htmlDocument : parse[0].getAllChildrenByNodeName("tr")) {
            // Print the data within the tr tag
            System.out.println(htmlDocument.getChildrenText());
            // Continue to retrieve child nodes of tr by attributes or tag names
            HTMLDocument[] tds = htmlDocument.getAllChildrenByNodeName("td");
            System.out.printf("There are %d td tags under this node%n", tds.length);
            // Retrieve child nodes by attributes
            HTMLDocument[] allChildrenByNodeAttrib = htmlDocument.getAllChildrenByNodeAttrib("class", "td1");
            System.out.printf("There are %d tags with class attribute 'td1' under this node%n%n", allChildrenByNodeAttrib.length);
        }
    }
}
```

- Extract Results

```
组件名称(常量区中的常量名)	组件类型	作者主页	面向格式	已注册至门户	组件作用	组件支持版本	
NULL	io.github.beardedManZhao.starSpider.parser.StarSpider	https://github.com/BeardedManZhao	URL, FILE, String	NO	与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务	v1.0	
PARSER_NAME_LABEL	io.github.beardedManZhao.starSpider.container.LABELDocument	https://github.com/BeardedManZhao	任何使用标签进行数据存储的文本内容	YES	提取与解析标签数据中的每一个节点	v1.0	
PARSER_NAME_HTML	io.github.beardedManZhao.starSpider.container.HTMLDocument	https://github.com/BeardedManZhao	HTML XML	YES	通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象	v1.0	
PARSER_NAME_REGEXP	io.github.beardedManZhao.starSpider.parser.PatternParser	https://github.com/BeardedManZhao	String	YES	通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据	v1.0	
PARSER_NAME_ME	io.github.beardedManZhao.starSpider.parser.MEParser	https://github.com/BeardedManZhao	String	YES	智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果	v1.0	
…	…	Dear friends	…	NO	事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星蛛门户	…	
```

### Extraction of Mathematical Expressions

For a piece of chaotic and unformatted data containing mathematical expressions, there is a component in the library (
starSpider. parser. MEParser) that can extract all the mathematical expressions and judge whether to calculate the
results according to the parameters passed by the user. Next is the demonstration of the use of this component (directly
crawl all the mathematical expressions in a page of Baidu Encyclopedia here).

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.ExpressionData;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.IOException;
import java.net.URL;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        // Construct the URL of the page to be parsed, here it is a Baidu Baike webpage
        URL url1 = new URL("https://baike.baidu.com/item/%E8%A1%A8%E8%BE%BE%E5%BC%8F/7655228");
        // Use the StarSpider component to intelligently extract and calculate mathematical expressions from the URL object.
        // Here, specify the parsing component as ME (Expression Parsing Component) and the calculation mode as "brackets"
        ExpressionData[] parse = (ExpressionData[]) StarSpider.parse(url1, ConstantRegion.PARSER_NAME_ME, "brackets");
        for (ExpressionData expressionData : parse) {
            System.out.println("Extracted expression [" + expressionData.getName() + "] = " + expressionData.getText());
        }
    }
}
```

### Regular expression matching

If you want to use regular expressions to extract data, there is also a component in the library that supports you to do
this. The next step is to demonstrate the use of this component

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.Container;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) {
        // Extract all numbers from a string
        Container[] regulars = StarSpider.parse(
                "zhaodsandjsaklfdhajkndfjsdhfaudSUD123HDUSIFCNDJNJDKS678DJSKAF2341233HDSD",
                "regular", "\\d+"
        );
        for (Container regular : regulars) {
            System.out.println(regular.getText());
        }
    }
}
```

- print results

```
123
678
2341233
```

### Parsing Json String

In the portal, there are also Json parsers registered, which are very suitable for parsing Json files. In this
framework, [fastJson](https://github.com/alibaba/fastjson) As the underlying implementation of FastJsonDocument,
FastJsonDocument follows the operation mode of FastJson JSONObject class, enabling users with FastJson experience to
quickly start using FastJsonDocument. Next are some operation examples about FastJsonDocument.

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.FastJsonDocument;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * Test case class
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        File file = new File("F:\\MyGitHub\\StarSpider\\src\\main\\resources\\testJson.json");
        // Parse the JSON file to extract "data1" and "two" and parse it as JSON format
        // Return the parsed results, which is an array of objects for "data1" and "two"
        FastJsonDocument[] parse = (FastJsonDocument[]) StarSpider.parse(file, ConstantRegion.PARSER_NAME_FASTJSON, "json", "data1", "two");
        for (FastJsonDocument container : parse) {
            // Perform data retrieval operations on each FastJsonDocument. The function style is consistent with JSONObject, reducing learning costs.
            if (container.isJObject) {
                // If the current node returns a JSONObject, use JSONObject-style functions
                System.out.println(container.getName() + '\t' + container.getText() + '\t' + container.getString("key1"));
            } else {
                // If the current node does not return a JSONObject, do not call JSONObject functions
                // However, basic functions can still be called, such as getting node name and node data
                System.out.println(container.getName() + '\t' + container.getText());
            }
        }
    }
}
```

- print results

```
data1	{"key1":"value1年后suiadhs468237&*……&*","key2":"value2","key3":"value3","key4":"value4","key5":"value5","key6":"value6"}	value1年后suiadhs468237&*……&*
two     {"data1":{"key1":[1,2,3,4,5],"key2":"value2","key3":"value3","key4":"value4","key5":"value5","key6":"value6"}}	                null
```

### Parsing SQL statements

```java
package io.github.beardedManZhao.starSpider;

import io.github.beardedManZhao.starSpider.container.SQLStringData;
import io.github.beardedManZhao.starSpider.parser.StarSpider;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) {
        System.out.println("版本：" + StarSpider.VERSION);
        // 准备一个需要被解析的 sql 脚本
        String data = "alter table zhao1 add test varchar(20);\n" +
                "alter table zhao1 drop test;\n" +
                "# 重命名列字段\n" +
                "alter table zhao1 change age age1 int;\n" +
                "# 重命名表名称\n" +
                "alter table zhao1 rename zhao;\n" +
                "# 加索引\n" +
                "alter table zhao add index indexName (name, age1);\n" +
                "# 加主键索引\n" +
                "alter table zhao add primary key (name);\n" +
                "# 加唯一索引\n" +
                "alter table zhao add unique (name);\n" +
                "\n" +
                "create table zhao2 \n" +
                "as \n" +
                "select * from student join achievement join (select * from zhao) join zhao1231;\n" +
                "hasiod;\n" +
                "\n" +
                "select * from zhao;";
        // 使用 PARSER_NAME_SQL_STRING_INFO 解析出 alter 的语句
        SQLStringData[] alters = (SQLStringData[]) StarSpider.parse(data, ConstantRegion.PARSER_NAME_SQL_STRING_INFO, "alter");
        for (SQLStringData alter : alters) {
            // alter 就是解析出来的语句对象包装类
            // 在这里我们简单的将所有 alter 的语句打印了出来
            System.out.println(alter.getStatement().getSqlStr());
        }
    }
}
```
## Update log

### Version 1.1 was updated on January 26, 2025

- To avoid package name conflicts, change the package name to `io.github.beardedManZhao.starSpider`.
- Fixed `io.github.beardedManZhao.starSpider.parser.HTMLParser` The problem of using attributes to retrieve only the first data in HTMLParser
- Support obtaining text data that needs to be crawled through FileReader!
- For methods like `htmlDocument.getAllChildrenBy*`, caching has been implemented to reduce unnecessary parsing overhead!
- Optimized the code

## More information

Thank you for your use. The parsing components in the library will flow and continue to be optimized at any time. If you
want to register your own components in Starscream, please send some information about your project to your email
Liming7887@qq.com In fact, we are looking forward to adding your implementation to the portal.
<hr>

- date: 2023-12-27
- Switch to [中文文档](https://github.com/BeardedManZhao/StarSpider/blob/main/README-Chinese.md)