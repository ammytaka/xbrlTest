package tokyo.schole.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class XbrlController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String scode = "";
        if (isPost()){
            scode = asString("scode");
            Edinet edinet = new Edinet();
            String dlParam = edinet.getZipUrl(scode);
            request.setAttribute("dlparam", dlParam);
        } else {
            request.setAttribute("dlparam", "");
        }
        request.setAttribute("scode", scode);
        return forward("xbrl.jsp");
    }
}
