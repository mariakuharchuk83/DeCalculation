package Lab7.main.XML_parsel;

import Lab7.main.models.Brand;
import Lab7.main.models.Manufacturer;
import Lab7.main.models.Salon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;

public class XMLCreator {
    public static void createXML(Salon salon, String path) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            Element rootElement = document.createElementNS("http://www.example.com/salon",
                    "salon");
            rootElement.setPrefix("cns");
            document.appendChild(rootElement);

            for (Manufacturer manufacturer : salon.getSections()) {
                Element sectionElement = document.createElement("manufacturer");
                sectionElement.setAttribute("id", String.valueOf(manufacturer.getId()));

                Element sectionNameElement = document.createElement("name");
                sectionNameElement.appendChild(document.createTextNode(manufacturer.getName()));
                sectionElement.appendChild(sectionNameElement);

                Element itemsElement = document.createElement("items");

                for (Brand brand : manufacturer.getItems()) {
                    Element itemElement = document.createElement("brand");
                    itemElement.setAttribute("id", String.valueOf(brand.getId()));

                    Element itemNameElement = document.createElement("name");
                    itemNameElement.appendChild(document.createTextNode(brand.getName()));
                    itemElement.appendChild(itemNameElement);

                    itemsElement.appendChild(itemElement);
                }

                if (manufacturer.getItems().isEmpty()) {
                    sectionElement.appendChild(document.createTextNode("<brands></brands>"));
                } else {
                    sectionElement.appendChild(itemsElement);
                }
                rootElement.appendChild(sectionElement);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "salon.dtd");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(path));
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
