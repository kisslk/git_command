```java

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yue.zhang
 * @Desc
 * @create 2019-08-08 12:01
 **/
@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes = ServiceApplication.class)
public abstract class BaseTest {

    /**
     * enum-package.properties ignoreResourceNotFound = true
     */

    protected static final String CODE = "$.code";
    protected static final String RESULT_MESSAGE = "$.resultMessage";
    protected static final String DATA = "$.data";
    protected static final Integer SUCCESS = 200;

    @Autowired
    private MockMvc mvc;

    /**
     * post查询
     *
     * @param path
     * @param clazz
     * @throws Exception
     */
    protected void doSearch(String path, Class clazz) throws Exception {
        Object ob = mockBean(clazz);
        doSearch(path, ob);
    }

    /**
     * post查询
     *
     * @param path
     * @param ob
     * @throws Exception
     */
    protected void doSearch(String path, Object ob) throws Exception {
        mvc.perform(
                post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(ob))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(CODE).value(SUCCESS))
                .andExpect(jsonPath(RESULT_MESSAGE).isEmpty())
                .andExpect(jsonPath(DATA).isArray());
    }

    /**
     * post提交
     *
     * @param path
     * @param clazz
     * @throws Exception
     */
    protected void doSubmit(String path, Class clazz) throws Exception {
        Object ob = mockBean(clazz);
        doSubmit(path, ob);
    }

    /**
     * post提交
     *
     * @param path
     * @param ob
     * @throws Exception
     */
    protected void doSubmit(String path, Object ob) throws Exception {
        mvc.perform(
                post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(ob))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(CODE).value(SUCCESS))
                .andExpect(jsonPath(RESULT_MESSAGE).isEmpty())
                .andExpect(jsonPath(DATA).exists());
    }

    /**
     * get请求查询list
     *
     * @param path
     * @throws Exception
     */
    protected void doGetList(String path) throws Exception {
        mvc.perform(
                get(path)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(CODE).value(SUCCESS))
                .andExpect(jsonPath(RESULT_MESSAGE).isEmpty())
                .andExpect(jsonPath(DATA).isArray());

    }

    /**
     * get请求查询model
     *
     * @param path
     * @throws Exception
     */
    protected void doGet(String path) throws Exception {
        mvc.perform(
                get(path)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(CODE).value(SUCCESS))
                .andExpect(jsonPath(RESULT_MESSAGE).isEmpty())
                .andExpect(jsonPath(DATA).exists());

    }

    protected String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    protected Object mockBean(Class clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object ob = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);

            if (field.getType().equals(Integer.class)) {
                field.set(ob, 0);
            } else if (field.getType().equals(String.class)) {
                field.set(ob, "string");
            } else if (field.getType().equals(Byte.class)) {
                field.set(ob, (byte) 1);
            } else if (field.getType().equals(Long.class)) {
                field.set(ob, 0L);
            } else if (field.getType().equals(Boolean.class)) {
                field.set(ob, true);
            } else if (field.getType().equals(BigDecimal.class)) {
                field.set(ob, BigDecimal.valueOf(0L));
            } else {
                log.error("miss type: {}", name);
            }
        }

        if (ob instanceof BaseOperatorModel) {
            ((BaseOperatorModel) ob).setOperatorId(0);
            ((BaseOperatorModel) ob).setOperatorName("string");
        }

        return ob;
    }

}


```
