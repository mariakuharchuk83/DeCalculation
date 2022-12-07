package Lab7.main.models;

public class Brand {
    private int id;
    private String name;

    public Brand(){
        this.id = 0;
        this.name = "";
    }

    public Brand(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Brand ID: " + id + "\nBrand name: " + name + "\n\n";
    }

}
