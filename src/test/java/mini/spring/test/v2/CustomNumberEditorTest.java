package mini.spring.test.v2;

import mini.spring.beans.factory.propertyeditors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/10/28.
 */
public class CustomNumberEditorTest {

    /**
     * 写测试用例时:
     * 1、先写正常情况的测试用例,然后由此驱动出基本代码逻辑
     * 2、再写出针对特殊情况的测试用例,再逐步对此调整代码
     */
    @Test
    public void testCustomNumber() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object val = editor.getValue();
        Assert.assertTrue(val instanceof Integer);
        Assert.assertEquals(3, ((Integer) editor.getValue()).intValue());
    }
}