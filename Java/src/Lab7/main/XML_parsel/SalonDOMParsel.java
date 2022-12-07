package Lab7.main.XML_parsel;

import Lab7.main.models.Brand;
import Lab7.main.models.Manufacturer;
import Lab7.main.models.Salon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalonDOMParsel {
    private DocumentBuilder documentBuilder;
    private final Salon salon;

    public SalonDOMParsel() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        salon = new Salon();
    }

    public Salon getSalon() {
        return salon;
    }

    public void parse(String path) {
        try {
            Document document = documentBuilder.parse(path);
            Element root = document.getDocumentElement();
            NodeList sectionsList = root.getElementsByTagName("manufacturer");
            List<Manufacturer> manufacturers = new ArrayList<>();

            for (int i = 0; i < sectionsList.getLength(); i++) {
                Element sectionElement = (Element) sectionsList.item(i);
                Manufacturer manufacturer = buildSection(sectionElement);
                manufacturers.add(manufacturer);
            }

            salon.setSections(manufacturers);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static Manufacturer buildSection(Element sectionElement) {
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setId(Integer.parseInt(sectionElement.getAttribute(XMLComponents.MANUFACTURER_ID.toString())));
        manufacturer.setName(getTextContent(sectionElement, XMLComponents.MANUFACTURER_NAME.toString()));

        Element brandsElement = (Element) sectionElement.getElementsByTagName(XMLComponents.BRANDS.toString())
                .item(0);
        NodeList brandsList = brandsElement.getElementsByTagName("brand");
        List<Brand> brands = new ArrayList<>();

        for (int i = 0; i < brandsList.getLength(); i++) {
            Element itemElement = (Element) brandsList.item(i);
            Brand brand = buildItem(itemElement);
            brands.add(brand);
        }

        manufacturer.setItems(brands);
        return manufacturer;
    }

    private static Brand buildItem(Element itemElement) {
        Brand brand = new Brand();

        brand.setId(Integer.parseInt(itemElement.getAttribute(XMLComponents.BRAND_ID.toString())));
        brand.setName(getTextContent(itemElement, XMLComponents.BRAND_NAME.toString()));

        return brand;
    }

    private static String getTextContent(Element element, String elementName) {
        return element.getElementsByTagName(elementName).item(0).getTextContent();
    }

    static class SimpleErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }
    }
}
