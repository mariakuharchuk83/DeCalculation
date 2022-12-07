package Lab7.main.XML_parsel;

public enum XMLComponents {
    SALON("cns:salon"),
    MANUFACTURERS("manufacturers"),
    MANUFACTURER("manufacturer"),
    MANUFACTURER_ID("id"),
    MANUFACTURER_NAME("name"),
    BRANDS("brands"),
    BRAND("brand"),
    BRAND_ID("id"),
    BRAND_NAME("name");

    private final String name;

    XMLComponents(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
