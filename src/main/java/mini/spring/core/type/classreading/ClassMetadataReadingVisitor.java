package mini.spring.core.type.classreading;

import mini.spring.core.type.ClassMetadata;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.SpringAsmInfo;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-30 .
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String[] interfaces;

    private String superClass;

    private String className;

    private boolean isAbstract;

    private boolean isInterface;

    private boolean isFinal;


    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }


    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean hasSuperClass() {
        return (superClass != null) && (superClass.length() > 0);
    }

    @Override
    public String getSuperClassName() {
        return superClass;
    }

    @Override
    public String[] getInterfaceNames() {
        return interfaces;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
