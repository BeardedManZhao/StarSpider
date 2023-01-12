package starSpider;

import starSpider.container.FastJsonDocument;
import starSpider.container.SQLStringData;
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
        SQLStringData[] alters = (SQLStringData[]) StarSpider.parse(data, ConstantRegion.PARSER_NAME_SQL_STRING_INFO, "alter");
        for (SQLStringData alter : alters) {
            System.out.println(alter.getStatement().getSqlStr());
        }

        File file = new File("D:\\MyGitHub\\StarSpider\\src\\main\\resources\\testJson.json");
        // 获取到json 文件中的data1与two，并以json格式解析它
        FastJsonDocument[] parse = (FastJsonDocument[]) StarSpider.parse(file, "fastJson", "json", "data1", "two");
        // 返回解析到的结果，这里是data1的结果对象与two组成的数组
        for (FastJsonDocument container : parse) {
            if (container.isJObject) {
                System.out.println(container.getName() + '\t' + container.getText() + '\t' + container.getString("key1"));
            } else {
                System.out.println(container.getName() + '\t' + container.getText());
            }
        }
    }
}
