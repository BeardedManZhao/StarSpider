package starSpider.parser;

import core.calculation.number.BracketsCalculation2;
import core.calculation.number.NumberCalculation;
import core.calculation.number.PrefixExpressionOperation;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;
import starSpider.ConstantRegion;
import starSpider.container.Container;
import starSpider.container.ExpressionData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析一组数学表达式，通过正则与ME计算引擎，智能提取出来段落中的所有数学表达式，并通过参数来对提取出来的表达式进行计算或其它操作。
 * <p>
 * Parse a group of mathematical expressions, intelligently extract all the mathematical expressions in the paragraph through the regular and ME calculation engine, and perform calculations or other operations on the extracted expressions through parameters.
 *
 * @author zhao
 */
public class MEParser implements Parser {
    public final static PrefixExpressionOperation PREFIX_EXPRESSION_OPERATION = PrefixExpressionOperation.getInstance("prefix");
    public final static BracketsCalculation2 BRACKETS_CALCULATION_2 = BracketsCalculation2.getInstance("brackets");
    public final static Pattern COMPILE = Pattern.compile(ConstantRegion.REGEXP_ME_ALL);

    /**
     * @return 解析器的名称，用于区别解析器之间的联系
     */
    @Override
    public String getName() {
        return ConstantRegion.PARSER_NAME_ME;
    }

    /**
     * 解析一个字符串中包含的所有数学表达式
     *
     * @param data 需要被解析的数据，是数学表达式的几个集合
     * @param args 解析器需要使用到的参数 其中第一个参数是表达式类型，不同类型的数学表达式可以使用不同的解析模式进行解析
     *             解析模式
     *             def：仅仅将表达式提取出来不进行计算
     *             prefix：无括号数学表达式计算组件
     *             brackets：有括号数学表达式计算组件
     * @return 解析器解析之后的所有表达式风咋黄起来的结果数值
     */
    @Override
    public Container[] parse(String data, String... args) {
        if (args.length >= 1) {
            final Matcher matcher = COMPILE.matcher(data);
            ArrayList<ExpressionData> arrayList = new ArrayList<>();
            if ("def".equalsIgnoreCase(args[0])) {
                while (matcher.find()) {
                    arrayList.add(new ExpressionData("No mathematical calculation", matcher.group()));
                }
                return arrayList.toArray(new ExpressionData[0]);
            }
            NumberCalculation calculationByName = (NumberCalculation) CalculationManagement.getCalculationByName(args[0]);
            while (matcher.find()) {
                String group = matcher.group();
                try {
                    calculationByName.check(group);
                    arrayList.add(new ExpressionData(calculationByName.calculation(group), group));
                } catch (WrongFormat e) {
                    arrayList.add(new ExpressionData(e.toString(), group));
                } catch (RuntimeException ignored) {
                }
            }
            return arrayList.toArray(new ExpressionData[0]);
        } else {
            throw new RuntimeException("针对数学表达式的解析，您必须要将解析模式，在 args 的位置书写好。");
        }
    }
}
