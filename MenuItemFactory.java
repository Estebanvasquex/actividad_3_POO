class MenuItemFactory {
    public static MenuItem createMenuItem(String type, String id, String name, double price) throws InvalidDataException {
        if (type == null) throw new InvalidDataException("Tipo de item nulo");
        switch (type.toUpperCase()) {
            case "FOOD":
                return new Food(id, name, price);
            case "DRINK":
                return new Drink(id, name, price);
            default:
                throw new InvalidDataException("Tipo desconocido para MenuItem: " + type);
        }
    }
}
