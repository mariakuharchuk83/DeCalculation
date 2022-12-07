package Lab7.main.models;

import java.util.List;

public class Manufacturer {

    private int id;
    private String name;
    private List<Brand> brands;

    public Manufacturer(){
        this.id = 0;
        this.name = "";
        this.brands = null;
    }

    public Manufacturer(int id, String name, List<Brand> brands) {
        this.id = id;
        this.name = name;
        this.brands = brands;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Brand> getItems() {
        return brands;
    }

    public Brand getItem(int id) {
        return brands.stream()
                .filter(item -> item.getId() == id)
                .findAny()
                .orElse(null);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Brand> brands) {
        this.brands = brands;
    }

    public boolean addItem(Brand brand) {
        if (getItem(brand.getId()) != null) {
            return false;
        }
        brands.add(brand);
        return true;
    }

    public boolean deleteItem(int id) {
        Brand brand = getItem(id);
        if (brand == null) {
            return false;
        }
        brands.remove(brand);
        return true;
    }

    public boolean updateItem(int id, String newName) {
        Brand brand = getItem(id);
        if (brand == null) {
            return false;
        }
        brand.setName(newName);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Manufacturer ID: ")
                .append(id)
                .append("\nManufacturer name: ")
                .append(name)
                .append("\nBrands:\n\n");
        for (Brand brand : brands) {
            result.append("   ");
            result.append(brand.toString().replace("\n", "\n   "));
        }

        return result.toString();
    }

}
