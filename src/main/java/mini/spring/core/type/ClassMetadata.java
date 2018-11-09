package mini.spring.core.type;

public interface ClassMetadata {


    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();

    String getClassName();

}
