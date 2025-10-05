class Customer extends User {
    private String address;
    private String phone;

    public Customer(String id, String name, String email, String address, String phone) {
        super(id, name, email);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("Customer: %s, %s, %s", name, email, phone);
    }
}
