package mini.spring.core.type.classreading;

import mini.spring.core.type.ClassMetadata;
import mini.spring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
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


    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClass = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
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
