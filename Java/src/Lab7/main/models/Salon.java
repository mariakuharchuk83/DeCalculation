package Lab7.main.models;

import java.util.ArrayList;
import java.util.List;

public class Salon {
    private List<Manufacturer> manufacturers;

    public Salon() {
        manufacturers = new ArrayList<>();
    }

    public Salon(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<Manufacturer> getSections() {
        return manufacturers;
    }

    public Manufacturer getSection(int id) {
        return manufacturers.stream()
                .filter(section -> section.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Brand getItem(int id) {
        for (Manufacturer manufacturer : manufacturers) {
            for (Brand brand : manufacturer.getItems()) {
                if (brand.getId() == id) {
                    return brand;
                }
            }
        }
        return null;
    }

    public Manufacturer getSectionByItem(int id) {
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getItem(id) != null) {
                return manufacturer;
            }
        }
        return null;
    }

    public void setSections(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public boolean addSection(Manufacturer manufacturer) {
        if (getSection(manufacturer.getId()) != null) {
            return false;
        }
        manufacturers.add(manufacturer);
        return true;
    }

    public boolean deleteSection(int id) {
        Manufacturer manufacturer = getSection(id);
        if (manufacturer == null) {
            return false;
        }
        manufacturers.remove(manufacturer);
        return true;
    }

    public boolean updateSection(int id, String newName) {
        Manufacturer manufacturer = getSection(id);
        if (manufacturer == null) {
            return false;
        }
        manufacturer.setName(newName);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Manufacturer manufacturer : manufacturers) {
            result.append(manufacturer.toString());
        }

        return result.toString();
    }

}
