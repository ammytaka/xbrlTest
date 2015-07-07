package tokyo.schole.controller;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import tokyo.schole.meta.MyDataMeta;
import tokyo.schole.model.MyData;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String msg = "何か書いて。";
        if (isPost()){
            msg = asString("msg");
            String name = asString("name");
            String code = asString("code");
            int scode = asInteger("scode");
            MyData mydata = new MyData();
            mydata.setName(name);
            mydata.setCode(code);
            mydata.setScode(scode);
            Datastore.put(mydata);
        }
        List<MyData> mydatas = Datastore.query(MyDataMeta.get()).asList();
        request.setAttribute("msg", msg);
        request.setAttribute("mydatas", mydatas);
        return forward("index.jsp");
    }
}
