package cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import data.GetData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class TestChaptersInfo {
    Map<String,String> chapterinfo=null;
    JSONArray jsonElements=null;

    @BeforeClass(alwaysRun = true)
    public void set_up(){
        chapterinfo=new GetData().getChapters();
        jsonElements= JSONObject.parseArray(chapterinfo.get("data"));

    }

    @Test(groups = {"chapter"})
    public void testCode(){
        Assert.assertEquals(chapterinfo.get("code"),"0");
        
    }
    @Test(groups = {"chapters"})
    public void testDataNotNull(){
        Assert.assertNotNull(chapterinfo.get("data"));
    }
}
