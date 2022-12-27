# ![image](https://user-images.githubusercontent.com/113756063/209512042-8b0f1b56-e783-48a6-9ff6-99360aa87198.png) 星蛛

## 介绍

星蛛解析库是一款针对数据解析提供的库，其中内置诸多数据解析组件，支持对网络数据包，文件对象，以及字符串对象进行解析，是Java实现的一种数据解析手段，是可以实现爬虫，智能提取等需求的强悍工具。

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

### 架构是什么样的？

在框架中，用户直接与星蛛门户类(starSpider.parser.StarSpider)进行交互，通过该类，可对URL,FILE,String，三种对象进行内容提取与解析，用户可以根据自己的需求向星蛛解析函数中传递形参，实现不同效果。

星蛛门户会根据用户传递的形参，从哈希表中获取到对应的组件进行数据解析，并将组件解析结果对象直接通过星蛛门户类(starSpider.parser.StarSpider)
返回给用户，用户可根据结果中的各类函数进行更多的操作，目前的结果对象有如下几种。

#### 结果对象的类型与作用

| 结果对象数据类型                              | 该结果类型对应的提取组件                     | 该结果类型的特有功能                          | 该结果类型的支持版本 |
|---------------------------------------|----------------------------------|-------------------------------------|------------|
| starSpider.container.LABELDocument    | starSpider.parser.LABELParser    | 面向标签节点进行数据提取操作                      | v1.0       |
| starSpider.container.HTMLDocument     | starSpider.parser.HTMLParser     | 面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现 | v1.0       |
| starSpider.container.ExpressionData   | starSpider.parser.MEParser       | 面向每一个数学表达式进行数据提取操作                  | v1.0       |
| starSpider.container.StringData       | starSpider.parser.PatternParser  | 面向每一个符合提取条件的字符串                     | v1.0       |
| starSpider.container.FastJsonDocument | starSpider.parser.FastJsonParser | 面向json字符串中每一个json节点进行提取操作。          | v1.0       |

#### 解析组件的类型与作用

| 组件名称(常量区中的常量名)       | 组件类型                             | 作者主页                              | 面向格式              |     | 已注册至门户 | 组件作用                                                | 组件支持版本 |
|----------------------|----------------------------------|-----------------------------------|-------------------|:----|--------|-----------------------------------------------------|--------|
| NULL                 | starSpider.parser.StarSpider     | https://github.com/BeardedManZhao | URL, FILE, String |     | NO     | 与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务                | v1.0   |
| PARSER_NAME_LABEL    | starSpider.parser.LABELParser    | https://github.com/BeardedManZhao | 任何使用标签进行数据存储的文本内容 |     | YES    | 提取与解析标签数据中的每一个节点                                    | v1.0   |
| PARSER_NAME_HTML     | starSpider.parser.HTMLParser     | https://github.com/BeardedManZhao | HTML XML          |     | YES    | 通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象 | v1.0   |
| PARSER_NAME_REGEXP   | starSpider.parser.PatternParser  | https://github.com/BeardedManZhao | String            |     | YES    | 通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据              | v1.0   |
| PARSER_NAME_ME       | starSpider.parser.MEParser       | https://github.com/BeardedManZhao | String            |     | YES    | 智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果                    | v1.0   |
| PARSER_NAME_FASTJSON | starSpider.parser.FastJsonParser | https://github.com/alibaba        | Json              |     | YES    | 针对JSON进行解析的库，将FastJson接入到门户，它返回的结果就是一个JSONObject对象  | v1.0   |
| ...                  | ...                              | Dear friends                      | ...               |     | NO     | 事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星蛛门户        | ...    |

## 有哪些功能？

在星蛛门户中，用户可以直接通过parse函数设置参数与解析对象，进而获取到解析结果，在库中的解析工作由解析器进行，解析器的实现有很多，每一个注册到星蛛的组件都可以使用门户访问到，接下来就展示下更多的信息。

### 标签文本的解析

标签文本的典型例子就是HTML，针对这类文本的解析任务，可以使用label组件（LABELParser），具体使用方式如下所示

```java
package starSpider;

import starSpider.container.LABELDocument;
import starSpider.parser.StarSpider;

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
        File file = new File("D:\\MyGitHub\\StarSpider\\src\\main\\resources\\Test.html");
        // 使用星蛛解析组件，对file对象进行LABEL页面的解析（也可以对url进行解析） 按照节点路径 table > tbody
        // LABELDocument[] parses1 = (LABELDocument[]) StarSpider.parse(url, "LABEL", "nodePath", "table", "tbody");
        LABELDocument[] parses1 = (LABELDocument[]) StarSpider.parse(file, "LABEL", "nodePath", "table", "tbody");
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

        // 使用星蛛解析组件，对file对象进行LABEL页面的爬取 按照节点[tr]
        LABELDocument[] parses2 = (LABELDocument[]) StarSpider.parse(file, "LABEL", "node", "tr");
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

在库中有一个组件HTMLParser是专用于提取HTML与XML这类数据的，它在label组件（LABELParser）的基础上拓展了一个新的功能，就是通过属性查找，它具有label组件的所有功能，同时为HTML
XML这两类文本的爬取进行了优化，使得该组件针对HTML与XML的爬取性能要好得多，为演示效果，准备好了这样的一份文件，文件内容如下所示。

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
        <td>starSpider.container.LABELDocument</td>
        <td>starSpider.parser.LABELParser</td>
        <td>面向标签节点进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>starSpider.container.HTMLDocument</td>
        <td>starSpider.parser.HTMLParser</td>
        <td>面向标签节点与节点属性进行数据提取操作,是LABEL结果对象的子类实现</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>starSpider.container.ExpressionData</td>
        <td>starSpider.parser.MEParser</td>
        <td>面向每一个数学表达式进行数据提取操作</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>starSpider.container.StringData</td>
        <td>starSpider.parser.PatternParser</td>
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
        <td>starSpider.parser.StarSpider</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>URL, FILE, String</td>
        <td>NO</td>
        <td>与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_LABEL</td>
        <td>starSpider.container.LABELDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>任何使用标签进行数据存储的文本内容</td>
        <td>YES</td>
        <td>提取与解析标签数据中的每一个节点</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_HTML</td>
        <td>starSpider.container.HTMLDocument</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>HTML XML</td>
        <td>YES</td>
        <td>通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象</td>
        <td>v1.0</td>
    </tr>
    <tr class="even">
        <td>PARSER_NAME_REGEXP</td>
        <td>starSpider.parser.PatternParser</td>
        <td>https://github.com/BeardedManZhao</td>
        <td>String</td>
        <td>YES</td>
        <td>通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据</td>
        <td>v1.0</td>
    </tr>
    <tr class="odd">
        <td>PARSER_NAME_ME</td>
        <td>starSpider.parser.MEParser</td>
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
```

其中有两个表，表标签中有一个id属性，两张表的id属性不同，现在我们想要获取到第二张表的节点对象，具体操作方式如下所示（展示的是通过属性获取，通过节点获取的方式与LABELParser是一样的）。

```java
package starSpider;

import starSpider.container.HTMLDocument;
import starSpider.parser.StarSpider;

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
        File file = new File("D:\\MyGitHub\\StarSpider\\src\\main\\resources\\Test.html");
        // 使用星蛛库将文件中 id 属性为 table2 的文件数据提取出来
        HTMLDocument[] parse = (HTMLDocument[]) StarSpider.parse(file, "HTML", "attr", "id", "table2");
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
NULL	starSpider.parser.StarSpider	https://github.com/BeardedManZhao	URL, FILE, String	NO	与用户进行直接交互，根据用户的需求调整框架内部的结构并提交解析数据的任务	v1.0	
PARSER_NAME_LABEL	starSpider.container.LABELDocument	https://github.com/BeardedManZhao	任何使用标签进行数据存储的文本内容	YES	提取与解析标签数据中的每一个节点	v1.0	
PARSER_NAME_HTML	starSpider.container.HTMLDocument	https://github.com/BeardedManZhao	HTML XML	YES	通过节点名称或节点属性，提取与解析HTML与XML中的每一个节点，并返回具有相对节点解析功能的结果对象	v1.0	
PARSER_NAME_REGEXP	starSpider.parser.PatternParser	https://github.com/BeardedManZhao	String	YES	通过用户提供的正则表达式解析任意文本中的内容，提取出符合正则表达式的所有数据	v1.0	
PARSER_NAME_ME	starSpider.parser.MEParser	https://github.com/BeardedManZhao	String	YES	智能提取出所有数学表达式，并通过ME框架进行表达式计算，返回结果	v1.0	
…	…	Dear friends	…	NO	事实上，我们希望有更多人可以将自己的实现提供给我们，由各位亲自将自己的组件接入至星蛛门户	…	
```

### 数学表达式的提取

针对一段包含数学表达式的混乱无格式的数据，库中有一个组件（starSpider.parser.MEParser）可以提取出所有的数学表达式，并根据用户传递的参数来判断是否需要计算出结果，接下来就是该组件的使用演示（在这里直接爬取百度百科一个页面中的所有数学表达式）。

```java
package starSpider;

import starSpider.container.ExpressionData;
import starSpider.parser.StarSpider;

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
        // 使用星蛛组件，对url对象进行数学表达式的智能提取与计算，这里指定使用的解析组件为ME（表达式解析组件）以及计算模式（brackets）
        ExpressionData[] parse = (ExpressionData[]) StarSpider.parse(url1, "ME", "brackets");
        for (ExpressionData expressionData : parse) {
            System.out.println("提取到表达式【" + expressionData.getName() + "】 = " + expressionData.getText());
        }
    }
}
```

### 正则表达式的匹配

用户如果想要使用正则表达式提取数据，库中也有一个组件支持用户进行这样的操作，接下来就是该组件的使用演示。

```java
package starSpider;

import starSpider.container.Container;
import starSpider.parser.StarSpider;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) {
        // 提取出一个字符串中所有的数值
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

- 打印结果

```
123
678
2341233
```

### Json字符串的解析

在门户中，同样存在着Json解析器被注册，它非常适用于Json文件的解析，在本框架中，将[fastJson](https://github.com/alibaba/fastjson)
作为FastJsonDocument的底层实现，FastJsonDocument遵循fastJson的JSONObject类的操作方式，使得具有FastJson经验的用户在使用FastJsonDocument的时候能够快速上手，接下来就是有关FastJsonDocument的一些操作示例。

```java
package starSpider;

import starSpider.container.FastJsonDocument;
import starSpider.parser.StarSpider;

import java.io.File;
import java.io.IOException;

/**
 * 测试用例类
 *
 * @author zhao
 */
public final class MAIN {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\MyGitHub\\StarSpider\\src\\main\\resources\\testJson.json");
        // 获取到json 文件中的data1与two，并以json格式解析它
        // 返回解析到的结果，这里是data1的结果对象与two组成的数组
        FastJsonDocument[] parse = (FastJsonDocument[]) StarSpider.parse(file, "fastJson", "json", "data1", "two");
        for (FastJsonDocument container : parse) {
            // 在这里就是针对每一个FastJsonDocument的获取数据操作，其中的函数风格符合JSONObject，减少学习成本
            if (container.isJObject) {
                // 如果当前节点返回的是一个jsonObject，就可以使用JSONObject风格的函数
                System.out.println(container.getName() + '\t' + container.getText() + '\t' + container.getString("key1"));
            } else {
                // 如果当前节点返回的不是一个jsonObject，那么就不可以进行JSONObject的函数调用
                // 但基础函数还是可以调用的，例如获取节点名称和节点数据等操作
                System.out.println(container.getName() + '\t' + container.getText());
            }
        }
    }
}
```

- 打印结果

```
data1	{"key1":"value1年后suiadhs468237&*……&*","key2":"value2","key3":"value3","key4":"value4","key5":"value5","key6":"value6"}	value1年后suiadhs468237&*……&*
two     {"data1":{"key1":[1,2,3,4,5],"key2":"value2","key3":"value3","key4":"value4","key5":"value5","key6":"value6"}}	                null
```

## 更多信息

感谢各位的使用，库中的解析组件将会随时时间的流动而不断优化，如果各位想要将自己实现的组件注册到星蛛中，请将与您项目有关的一些信息发送到邮箱Liming7887@qq.com中，事实上我们很期待将您的实现加入到门户中。
<hr>

- date: 2022-12-24
- 切换至 [English document](https://github.com/BeardedManZhao/StarSpider/blob/main/README.md)
