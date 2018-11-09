package mini.spring.core.type.classreading;

import mini.spring.core.io.Resource;
import mini.spring.core.type.AnnotationMetadata;
import mini.spring.core.type.ClassMetadata;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();

}
