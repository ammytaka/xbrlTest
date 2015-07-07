package tokyo.schole.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Edinet {
    protected String searchHost = "https://disclosure.edinet-fsa.go.jp" ;
    protected String searchUrl = searchHost + "/E01EW/BLMainController.jsp?uji.verb=W1E63011CXW1E6A011DSPSch&uji.bean=ee.bean.parent.EECommonSearchBean&TID=W1E63011&PID=&SESSIONKEY=&lgKbn=2&pkbn=0&skbn=1&dskb=&askb=&dflg=0&iflg=0&preId=1&fls=on&cal=1&era=H&yer=&mon=&pfs=4&row=100&idx=0&str=&kbn=1&flg=&syoruiKanriNo=&mul=";

    public String getZipUrl(String scode){
        String zipUrl = "";
        String srchUrl = searchUrl + scode ;

        try {
            Document document = Jsoup.connect(srchUrl).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:38.0) Gecko/20100101 Firefox/38.0").validateTLSCertificates(false).ignoreHttpErrors(true).get();
            Elements resTable = document.select("table.resultTable");
            Elements resRows  = resTable.first().getElementsByTag("tr");
            for (Element rowElement : resRows) {
                Elements colElements = rowElement.getElementsByTag("td");
                Element titleCol = colElements.eq(1).first();
                Element aTag = titleCol.child(0);
                String title = aTag.text();
                if(title.contains("有価証券報告書")){
                    Element xbrlCol = colElements.eq(6).first();
                    Element dlAtag = xbrlCol.getElementsByTag("a").first();
                    String onclickAttr = dlAtag.attr("onclick");
                    String wr = onclickAttr.split("\\(")[1] ;
                    String wl = wr.split("\\)")[0];
                    String wcsv = wl.replace(" ", "").replace("'","");
                    String dlParams[] = wcsv.split(",");
                    HashMap<String, String> queryParams = new HashMap<String, String>();
                    Elements formItems = document.select("form[name=thisForm]");
                    Elements inputTags = formItems.first().select("input[type=hidden]");
                    for(Element inputTag : inputTags){
                        String k = inputTag.attr("name");
                        String v = inputTag.attr("value");
                        queryParams.put(k, v);
                    }
                    queryParams.put("uji.verb", dlParams[0]);
                    queryParams.put("uji.bean", dlParams[1]);
                    //queryParams.put("PID", dlParams[2]);
                    //queryParams.put("TID", dlParams[3]);
                    String otherParams[] = dlParams[4].split("&");
                    for(int i=0; i<otherParams.length; i++){
                        String  oParam[] = otherParams[i].split("=");
                        queryParams.put(oParam[0], oParam[1]) ;
                    }
                    zipUrl = searchHost + dlParams[5];
                    Set<Entry<String, String>> qp =queryParams.entrySet() ;
                    Iterator<Entry<String, String>> qpi = qp.iterator();
                    while(qpi.hasNext()){
                        Entry<String, String> entry = qpi.next();
                        zipUrl = zipUrl + "&" + entry.getKey() + "=" + entry.getValue();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return zipUrl ;
    }

    public String getXbrlZip(String zipUrl){
        try {

            URL url = new URL(zipUrl);

            HttpURLConnection conn =
              (HttpURLConnection) url.openConnection();
            conn.setAllowUserInteraction(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; rv:38.0) Gecko/20100101 Firefox/38.0");
            conn.connect();

            int httpStatusCode = conn.getResponseCode();

            if(httpStatusCode != HttpURLConnection.HTTP_OK){
              throw new Exception();
            }

            // Input Stream
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            ZipInputStream  zipin = new ZipInputStream(dis);
            ZipEntry ze = zipin.getNextEntry();
            do{
                if(ze.isDirectory()) continue;
                String fname = ze.getName();
                ze = zipin.getNextEntry();
            }while(ze != null);
            // Close Stream
            dis.close();

          }  catch (ProtocolException e) {
            e.printStackTrace();
          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
        return "";
    }


}
