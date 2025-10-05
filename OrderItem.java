class OrderItem {
    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) throws InvalidDataException {
        if (item == null) throw new InvalidDataException("MenuItem nulo en OrderItem");
        if (quantity <= 0) throw new InvalidDataException("Quantity debe ser > 0");
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return item.getPrice() * quantity; }

    @Override
    public String toString() {
        return String.format("%s x%d => %.2f", item.getName(), quantity, getSubtotal());
    }
}
