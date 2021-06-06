package homeword1.definition;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 方式4：自定义标签
 *
 * @author LinJn
 * @since 2021/6/6 22:10
 */
public class LinJnHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new LinJnBeanDefinitionParser());
    }

}
