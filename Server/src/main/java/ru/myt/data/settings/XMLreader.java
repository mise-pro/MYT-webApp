package ru.myt.data.settings;

import java.awt.Color;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLreader {

    public static XMLreader instance;
    private boolean debugMode;
    private String yaKey;

    public static XMLreader getInstance() {
        if (instance == null) {
            instance = new XMLreader();
        }
        return instance;
    }

    private XMLreader() {
        try {
            System.out.println("Processing XML settings file");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FileToInputStreamConverter.accessFileInsideArch("envSettings.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("debugMode");
            Element elem = (Element) nodeLst.item(0);
            if (((Node) elem.getChildNodes().item(0)).getNodeValue().equalsIgnoreCase("true")) {
                debugMode = true;
            } else {
                debugMode = false;
            }

            if (EnvSettings.DEBUG_MODE) {
                System.out.println("DebugMode enabled");
            }

            nodeLst = doc.getElementsByTagName("yaAPIkey");
            elem = (Element) nodeLst.item(0);
            yaKey = (elem.getChildNodes().item(0)).getNodeValue();


        } catch (Exception errorReadingXMLfile) {
            System.out.println("Processing XML settings file FAILED, exit immediately - " + errorReadingXMLfile);
            System.exit(0);
        }
            System.out.println("End processing XML settings file successfully");
    }

    public boolean isDebugMode() {
        return debugMode;
    }
    public String getYaKey() {
        return yaKey;
    }

    private Color getColorFromString(String colorAsString) {
        try {
            String delims = "[,]";
            String[] colorIngredients = colorAsString.split(delims);

            int rValue = Integer.parseInt(colorIngredients[0]);
            int gValue = Integer.parseInt(colorIngredients[1]);
            int bValue = Integer.parseInt(colorIngredients[2]);

            return new Color(rValue, gValue, bValue);
        } catch (Exception e) {
            System.out.println("Unable to parse following color from config, replaced with default value: " + colorAsString);
            return new Color(255, 255, 255);
        }
    }
}
