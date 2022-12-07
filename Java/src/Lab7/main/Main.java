package Lab7.main;

import Lab7.main.XML_parsel.SalonDOMParsel;
import Lab7.main.XML_parsel.XMLCreator;
import Lab7.main.models.Brand;
import Lab7.main.models.Manufacturer;
import Lab7.main.models.Salon;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "src/Lab7/resources/salon.xml";

        List<Manufacturer> manufacturers = new ArrayList<>();
        List<Brand> brands = new ArrayList<>() {
            {
                add(new Brand(23437, "Brand1"));
                add(new Brand(78433, "Brand2"));
            }
        };
        manufacturers.add(new Manufacturer(32433, "Manufacturer1", brands));
        manufacturers.add(new Manufacturer(42333, "Manufacturer2", brands));
        Salon salon = new Salon(manufacturers);

        XMLCreator.createXML(salon, path);

        SalonDOMParsel parser = new SalonDOMParsel();
        parser.parse(path);
        Salon salon1 = parser.getSalon();

        for (Manufacturer manufacturer : salon1.getSections()) {
            System.out.println(manufacturer.getId());
            System.out.println(manufacturer.getName());
            for (Brand brand : manufacturer.getItems()) {
                System.out.println(brand.getId());
                System.out.println(brand.getName());
            }
        }
    }
}
