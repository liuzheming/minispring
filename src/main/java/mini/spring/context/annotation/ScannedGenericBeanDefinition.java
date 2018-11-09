package mini.spring.context.annotation;

import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.core.type.AnnotationMetadata;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/11/1.
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotationBeanDefinition {

    private AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {

        this.metadata = metadata;

        setBeanClassName(metadata.getClassName());
    }

    public AnnotationMetadata getMetadata() {
        return metadata;
    }
}
