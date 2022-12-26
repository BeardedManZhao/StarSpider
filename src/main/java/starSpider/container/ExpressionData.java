package starSpider.container;

import core.container.CalculationNumberResults;

/**
 * 数学表达式解析组件解析之后的表达式对象，封装了一个表达式的数据与计算信息。
 * <p>
 * The expression object parsed by the mathematical expression parsing component encapsulates the data and calculation information of an expression.
 *
 * @author zhao
 */
public class ExpressionData implements Container {

    public final CalculationNumberResults CalculationNumberResults;
    public final String Text;
    public final String formula;

    public ExpressionData(core.container.CalculationNumberResults calculationNumberResults, String formula) {
        CalculationNumberResults = calculationNumberResults;
        this.Text = String.valueOf(calculationNumberResults.getResult());
        this.formula = formula;
    }

    public ExpressionData(String Text, String formula) {
        this.Text = Text;
        this.formula = formula;
        this.CalculationNumberResults = null;
    }

    /**
     * @return 获取到表达式的计算信息，其中存储的是与表达式计算结果相关的数据信息，如果计算过程发生了异常，在这里将会存储异常信息
     * <p>
     * Get the calculation information of the expression, in which the data information related to the calculation result of the expression is stored.
     * If an exception occurs in the calculation process, the exception information will be stored here
     */
    @Override
    public String getText() {
        return this.Text;
    }

    /**
     * @return 获取到表达式的计算结果，每一个数学表达式都有属于自己的结果数值，使用该方法可以获取到计算结果的数值形式。
     * <p>
     * Get the calculation result of the expression. Each mathematical expression has its own result value. Use this method to get the numerical form of the calculation result.
     */
    public double getNumber() {
        return this.CalculationNumberResults.getResult();
    }

    /**
     * @return 获取到表达式的计算来源，每一个数学表达式都需要被计算组件进行计算，使用该方法可以获取到计算结果的来源信息。
     * <p>
     * Obtain the calculation source of the expression. Each mathematical expression needs to be calculated by the calculation component. Use this method to obtain the source information of the calculation result.
     */
    public String getSrcName() {
        return this.CalculationNumberResults.getCalculationSourceName();
    }

    /**
     * @return 获取到节点的名称 在这里是数学表达式的公式，是与数学表达式身份息息相关的重要位置
     * <p>
     * The name of the node obtained here is the formula of the mathematical expression and an important position closely related to the identity of the mathematical expression
     */
    @Override
    public String getName() {
        return this.formula;
    }
}
