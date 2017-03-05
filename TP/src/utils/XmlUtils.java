package utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import model.Template;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class XmlUtils {

    private static XStream xstream;

    public static XStream getXmlUtils(){
        if(xstream == null){
            xstream = new XStream(new StaxDriver());
            xstream.alias("template", Template.class);
            xstream.alias("map",java.util.Map.class);
        }
        return xstream;
    }
}
