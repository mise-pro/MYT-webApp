package ru.myt.client.tray.settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.myt.data.settings.FileToInputStreamConverter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class XMLreader {

    protected static XMLreader instance;
    private boolean debugMode;
    private String deviceId;
    private String connectionType;
    private String connectionEndpoint;

    protected static XMLreader getInstance() {
        if (instance == null) {
            instance = new XMLreader();
        }
        return instance;
    }

    private XMLreader() {
        //File configFile = new File("trayConfig.xml");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FileToInputStreamConverter.accessFileInsideArch("trayConfig.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("debugMode");
            Element elem = (Element) nodeLst.item(0);

            if (elem.getChildNodes().item(0).getNodeValue().equalsIgnoreCase("true")) {
                debugMode = true;
            } else {
                debugMode = false;
            }

            if (TraySettings.DEBUG_MODE) {
                System.out.println("Begin processing XML settings file");
            }

            nodeLst = doc.getElementsByTagName("deviceId");
            elem = (Element) nodeLst.item(0);
            deviceId = ((Node) elem.getChildNodes().item(0)).getNodeValue();

            nodeLst = doc.getElementsByTagName("connectionType");
            elem = (Element) nodeLst.item(0);
            connectionType = ((Node) elem.getChildNodes().item(0)).getNodeValue();

            nodeLst = doc.getElementsByTagName("connectionEndpoint");
            elem = (Element) nodeLst.item(0);
            connectionEndpoint = ((Node) elem.getChildNodes().item(0)).getNodeValue();

        } catch (Exception errorReadingXMLfile) {
            if (TraySettings.DEBUG_MODE) {
                System.out.println("Processing XML settings file FAILED, exit immediately" + errorReadingXMLfile);
            }
            System.exit(0);
        }
        if (TraySettings.DEBUG_MODE) {
            System.out.println("End processing XML settings file successfully");
        }
    }

    protected boolean isDebugMode() {
        return debugMode;
    }
    protected String getDeviceId() {
        return deviceId;
    }

    protected String getConnectionType() {
        return connectionType;
    }

    protected String getConnectionEndpoint() {
        return connectionEndpoint;
    }
}
