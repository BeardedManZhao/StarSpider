package starSpider.container;

import strInfo.result.Statement;

/**
 * SQL语句解析结果对象，其中封装了有关SQL语句的解析信息。
 * <p>
 * The SQL statement parses the result object, which encapsulates the parsing information about the SQL statement.
 *
 * @author zhao
 */
public class SQLStringData implements Container {

    private final String mod;
    private final strInfo.result.Statement statement;

    public SQLStringData(String mod, Statement statement) {
        this.mod = mod;
        this.statement = statement;
    }

    /**
     * @return 获取到sql语句的整体字符串对象
     * <p>
     * Get the whole string object of the sql statement
     */
    public String getText() {
        return statement.getSqlStr();
    }

    /**
     * @return 获取到sql语句作用的表名称。
     * <p>
     * Get the table name used by the SQL statement.
     */
    public String getName() {
        return statement.getTableName();
    }

    /**
     * @return 获取到SQL字符串的原语句
     */
    public String getSqlStr() {
        return statement.getSqlStr();
    }

    /**
     * @return 本次解析SQL语句使用到的解析模式，不同模式下的解析词不同，具体需要查阅介绍文档。
     * <p>
     * The parsing mode used for parsing SQL statements is different in different modes. Please refer to the introduction document for details.
     *
     * 链接：href="https://github.com/BeardedManZhao/SQLStringInFo/blob/core/README.md
     */
    public String getMod() {
        return mod;
    }

    /**
     * @return 返回本次解析到的SQL结果对象，是SQLStringINFO库中的原生结果类型。
     * <p>
     * The returned SQL result object is a native result type in the SQLStringINFO library.
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * 按照SQL解析词获取到目标解析词下的sql语句
     *
     * @param sqlWord 需要获取的sql语句所属的解析词
     * @return sqlWord对应的的sql语句
     */
    public String getStatementStrByWord(String sqlWord) {
        return statement.getStatementStrByWord(sqlWord);
    }

    /**
     * 按照SQL解析词获取到目标解析词下的sql语句
     *
     * @param sqlWord 需要获取的sql语句所属的解析词
     * @return sqlWord对应的的sql语句
     */
    public String[] getStatementArrayByWord(String sqlWord) {
        return statement.getStatementArrayByWord(sqlWord);
    }
}
