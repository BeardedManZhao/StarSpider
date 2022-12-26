package starSpider.container;

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
     * @return 获取到节点的名称
     * <p>
     * Get the name of the node
     */
    String getName();
}
