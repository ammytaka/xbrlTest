package tokyo.schole.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;

import tokyo.schole.meta.EdinetCodeMeta;
import tokyo.schole.model.EdinetCode;

import com.google.appengine.api.datastore.Key;

public class LoadEdinetCodeController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (isPost()) {
            String clear = request.getParameter("clear");
            if (clear != null && clear.equals("true")) {
                List<Key> keys = Datastore.query().asKeyList();
                Datastore.delete(keys);
            } else {
                FileItem formFile = requestScope("formFile");
                InputStream is = new ByteArrayInputStream(formFile.getData());
                BufferedReader br =
                    new BufferedReader(new InputStreamReader(is, "Windows-31J"));
                String rec = null;
                int line = 0;
                EdinetCodeMeta m = EdinetCodeMeta.get();
                while ((rec = br.readLine()) != null) {
                    line++;
                    if (line < 2)
                        continue;
                    String cols[] =
                        rec.substring(1, rec.length() - 1).split("\",\"");
                    if (cols.length < 12)
                        continue;
                    try {
                        String edinetCode = cols[0];
                        String companyName = cols[6];
                        String industryType = cols[10];
                        String securityCode = cols[11];
                        String jj = cols[2];
                        if (!jj.equals("上場"))
                            continue;
                        List<Key> entryKey =
                            Datastore
                                .query(m)
                                .filter(
                                    EdinetCodeMeta.get().edinetCode
                                        .equal(edinetCode))
                                .asKeyList();
                        EdinetCode entry = null;
                        if (entryKey.size() > 0) {
                            Key key = entryKey.get(0);
                            entry = Datastore.get(m, key);
                        } else {
                            entry = new EdinetCode();
                        }
                        entry.setEdinetCode(edinetCode);
                        entry.setCompanyName(companyName);
                        entry.setIndustryType(industryType);
                        entry.setSecurityCode(securityCode);
                        Datastore.put(entry);

                    } catch (Exception e) {
                    }
                }
                br.close();
                List<EdinetCode> list =
                    Datastore.query(EdinetCodeMeta.get()).asList();
                request.setAttribute("list", list);
            }
        } else {
            request.setAttribute("list", null);
        }
        return forward("LoadEdinetCode.jsp");
    }
}
