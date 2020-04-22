package mate.academy.internetshop.model;

public class Item {
    private static Long idGenerator = 0L;
    private final Long id;
    private String name;
    private Double price;
    private int count;

    public Item(String name, Double price, int amount) {
        this.name = name;
        this.price = price;
        this.count = amount;
        id = ++idGenerator;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return "name: " + name
                + ", price: " + price
                + ", id: " + id;
    }
}
