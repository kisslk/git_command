
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

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
@SpringBootTest(classes = StockApplication.class)
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
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private ApplicationContext context;

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
                // 有可能数据为空
                //.andExpect(jsonPath(DATA).isArray())
                .andDo(result -> log.info("result: {}", toJson(result.getResponse().getContentAsString())));
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
                .andExpect(jsonPath(DATA).exists())
                .andDo(result -> log.info("result: {}", toJson(result.getResponse().getContentAsString())));
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
                .andExpect(jsonPath(DATA).isArray())
                .andDo(result -> log.info("result: {}", toJson(result.getResponse().getContentAsString())));

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
                .andExpect(jsonPath(DATA).exists())
                .andDo(result -> log.info("result: {}", toJson(result.getResponse().getContentAsString())));

    }

    protected String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    protected Object mockBean(Class clazz) throws IllegalAccessException, InstantiationException {
        Object ob = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object parameter = mockParameter(field.getType());
            if (null != parameter) {
                field.set(ob, parameter);
            }
        }

        if (ob instanceof BaseOperateModel) {
            ((BaseOperateModel) ob).setOperatorKey("0");
            ((BaseOperateModel) ob).setOperatorName("string");
        }

        return ob;
    }

    protected Object mockParameter(Class clazz) {
        if (clazz.equals(String.class)) {
            return "string";
        } else if (clazz.equals(Integer.class)) {
            return 1;
        } else if (clazz.equals(Byte.class)) {
            return (byte) 1;
        } else if (clazz.equals(Long.class)) {
            return 1L;
        } else if (clazz.equals(Boolean.class)) {
            return true;
        } else if (clazz.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(1L);
        } else if (clazz.equals(Short.class)) {
            return (short) 1;
        } else if (clazz.equals(Data.class)) {
            return new Date();
        } else {
            log.warn("miss type: {}", clazz);
            return null;
        }
    }

    protected void runApi(String beanName) throws Exception {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entrySet : map.entrySet()) {
            if (entrySet.getValue().getBean().equals(beanName)) {
                String urlPattern = entrySet.getKey().getPatternsCondition().getPatterns().toArray()[0].toString();
                System.out.println(urlPattern);

                HandlerMethod handlerMethod = entrySet.getValue();
                //handlerMethod.getMethodAnnotation(PostMapping.class);
                PostMapping postMapping = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getMethod(), PostMapping.class);
                if (null != postMapping) {
                    // post请求
                    Class requestClass = handlerMethod.getMethod().getParameterTypes()[0];

                    if (handlerMethod.getMethod().getReturnType().equals(PageListResponse.class)) {
                        // 分页查询
                        doSearch(urlPattern, requestClass);
                    } else {
                        // 不分页查询/提交
                        doSubmit(urlPattern, requestClass);
                    }

                } else {
                    GetMapping getMapping = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getMethod(), GetMapping.class);

                    if (null != getMapping) {
                        // get请求
                        MethodParameter[] parameters = handlerMethod.getMethodParameters();
                        for (MethodParameter parameter : parameters) {

                            PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
                            if (null != pathVariable) {
                                String pathVariableName = pathVariable.name();
                                urlPattern = urlPattern.replace("{" + pathVariableName + "}", mockParameter(parameter.getParameterType()).toString());
                            }

                            RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
                            if (null != requestParam) {
                                // 视作@RequestParam
                                String requestParamName = requestParam.name();
                                if (urlPattern.indexOf("?") < 0) {
                                    urlPattern = urlPattern + "?" + requestParamName + "=" + mockParameter(parameter.getParameterType()).toString();
                                } else {
                                    urlPattern = urlPattern + "&" + requestParamName + "=" + mockParameter(parameter.getParameterType()).toString();
                                }
                            }

                        }

                        doGet(urlPattern);
                    }
                    else {
                        log.warn("class: {}, skip this method: {}", handlerMethod.getBeanType(), handlerMethod.getMethod().getName());
                    }
                }
            }

        }
    }

}
