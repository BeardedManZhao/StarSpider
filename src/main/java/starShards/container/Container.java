package starShards.container;

import java.util.HashMap;

/**
 * 数据容器接口，其中提供了与文档直接进行交互的方式，同时还具备与之相关的信息获取函数。
 * <p>
 * Data container interface, which provides a way to directly interact with documents, and also has related information acquisition functions.
 */
public interface Container {

    /**
     * @return 获取到节点的字符串数据，在HTML中的实现为获取到当前节点的文本数据
     * <p>
     * Get the string data to the node
     */
    String getText();

    /**
     * @param split 自定义数据获取的分隔符
     * @return 获取到当前节点以及当前节点所有子节点的数据，其中节点之间的分隔符默认是制表符。
     * <p>
     * Get the data of the current node and all the child nodes of the current node. The separator between nodes is tab by default.
     */
    String getChildrenText(String split);

    /**
     * 以数组形式获取到该节点以及其子类的所有数据
     *
     * @param isNew 如果设置为true代表使用一个新数组存储数据，可以修改新数组而不影响该数据对象，如果设置为false 则直接返回当前节点的数据
     * @return 当前节点以及当前节点的所有子节点的数据，以一个数组的形式返回这些数据。
     * <p>
     * The data of the current node and all its children are returned in an array.
     */
    String[] getChildrenTextArray(boolean isNew);

    /**
     * @return 获取到节点的属性，在HTML中的实现为获取到当前节点的属性数据
     * <p>
     * Get the attributes of the node
     */
    HashMap<String, String> getAttribs();

    /**
     * @param attrName 属性名称
     * @return 获取到节点的属性，在HTML中的实现为获取到当前节点的属性数据
     * <p>
     * Get the attributes of the node
     */
    String getAttrib(String attrName);

    /**
     * @return 获取到节点的名称
     * <p>
     * Get the name of the node
     */
    String getName();
}
