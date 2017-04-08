package utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import model.Link;
import model.OperatorDTO;
import model.Template;

import java.awt.*;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class XmlUtils {

    private static XStream xstream;

    public static XStream getXmlUtils() {
        if (xstream == null) {
            xstream = new XStream(new StaxDriver());
            xstream.alias("dto", OperatorDTO.class);
            xstream.alias("template", Template.SaveableTemplate.class);
            xstream.alias("link", Link.class);
            xstream.alias("map", java.util.Map.class);
            xstream.alias("rectangle", Rectangle.class);
        }
        return xstream;
    }
}
