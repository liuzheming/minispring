package mini.spring.aop;

public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}