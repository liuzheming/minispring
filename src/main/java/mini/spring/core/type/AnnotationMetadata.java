package mini.spring.core.type;

import mini.spring.core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);

}
