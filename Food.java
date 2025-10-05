class Food extends MenuItem {
    public Food(String id, String name, double price) throws InvalidDataException {
        super(id, name, price);
    }
    @Override
    public String getType() { return "FOOD"; }
}
