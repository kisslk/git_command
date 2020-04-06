
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author yue.zhang
 * @Desc
 * @create 2020-03-31 14:19
 **/
@Ignore
public class TestDemoApiTest extends BaseTest {

    /*@Test
    public void create() throws Exception {
        doSubmit("/demo/create", DemoCreateModel.class);
    }

    @Test
    public void update() throws Exception {
        doSubmit("/demo/update", DemoUpdateModel.class);
    }

    @Test
    public void search() throws Exception {
        doSearch("/demo/search", DemoSearchModel.class);
    }

    @Test
    public void getById() throws Exception {
        doGet("/demo/1");
    }*/

    @Test
    public void run() throws Exception {
        runApi("testDemoController");
    }
}
