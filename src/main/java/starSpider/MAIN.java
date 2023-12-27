package starSpider;

import starSpider.container.SQLStringData;
import starSpider.parser.StarSpider;

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
