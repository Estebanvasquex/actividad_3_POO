abstract class MenuItem {
    protected String id;
    protected String name;
    protected double price;

    public MenuItem(String id, String name, double price) throws InvalidDataException {
        if (id == null || id.isEmpty()) throw new InvalidDataException("id inválido");
        if (name == null || name.isEmpty()) throw new InvalidDataException("name inválido");
        if (price <= 0) throw new InvalidDataException("price debe ser mayor que cero");
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f", getType(), name, price);
    }
}
