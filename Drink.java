class Drink extends MenuItem {
    public Drink(String id, String name, double price) throws InvalidDataException {
        super(id, name, price);
    }
    @Override
    public String getType() { return "DRINK"; }
}
