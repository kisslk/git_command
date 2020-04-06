
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author yue.zhang
 * @Desc
 * @create 2020-03-31 14:19
 **/
@Ignore
public class FullTestDemo extends BaseTest {

    @Test
    public void saveOrUpdateRelation() throws Exception {
        FullTestDemoCreateModel model = (FullTestDemoCreateModel) mockBean(FullTestDemoCreateModel.class);
        FullTestDemoCreateModel.Detail detail = (FullTestDemoCreateModel.Detail) mockBean(FullTestDemoCreateModel.Detail.class);
        model.setDetails(Arrays.asList(detail));
        doSubmit("/full-test-demo/save-or-update", model);
    }

    @Test
    public void saveOrUpdateNode() throws Exception {
        SaveOrUpdateNodeModel model = (SaveOrUpdateNodeModel) mockBean(SaveOrUpdateNodeModel.class);
        SaveOrUpdateNodeModel.WorkflowNode node = (SaveOrUpdateNodeModel.WorkflowNode) mockBean(SaveOrUpdateNodeModel.WorkflowNode.class);
        node.setId(null);
        node.setApprovalId(5982);
        model.setNodeList(Arrays.asList(node));
        doSubmit("/full-test-demo/node/save-or-update", model);
    }

    @Test
    public void disable() throws Exception {
        doSubmit("/full-test-demo/disable", FullTestDemoDisableModel.class);
    }

    @Test
    public void enable() throws Exception {
        doSubmit("/full-test-demo/enable", FullTestDemoEnableModel.class);
    }

    @Test
    public void search() throws Exception {
        doSearch("/full-test-demo/search", FullTestDemoSearchModel.class);
    }

    @Test
    public void getById() throws Exception {
        doGet("/full-test-demo/1?includeNode=true");
    }

    @Test
    public void listByVendorId() throws Exception {
        doGet("/full-test-demo/list-by-vendor?vendorId=1&includeNode=true");
    }

    @Test
    public void getFullTestDemoLog() throws Exception {
        doGet("/full-test-demo/log/relation?cfgFullTestDemoId=1");
    }

}
