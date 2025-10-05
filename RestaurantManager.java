import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


 //Implementa el patrón Singleton. Gestiona el menú, los clientes y las órdenes de forma centralizada.

public final class RestaurantManager {
    // Instancia única
    private static RestaurantManager instance;

    private Map<String, MenuItem> menuItems;
    private Map<String, Customer> customers;
    private Map<String, Order> orders;

    // Constructor privado para evitar instanciación externa
    private RestaurantManager() {
        this.menuItems = new HashMap<>();
        this.customers = new HashMap<>();
        this.orders = new HashMap<>();
    }
     //Devuelve la única instancia de RestaurantManager.
    public static synchronized RestaurantManager getInstance() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

    //Gestión de Menú
    public void addMenuItem(MenuItem item) {
        if (item == null) throw new IllegalArgumentException("MenuItem nulo");
        menuItems.put(item.getId(), item);
    }

    public MenuItem getMenuItemById(String id) {
        return menuItems.get(id);
    }

    public Collection<MenuItem> getAllMenuItems() {
        return Collections.unmodifiableCollection(menuItems.values());
    }

    public List<MenuItem> topNExpensiveMenuItems(int n) {
        return menuItems.values().stream()
                .sorted(Comparator.comparingDouble(MenuItem::getPrice).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    //Gestión de Clientes
    public void addCustomer(Customer c) {
        if (c == null) throw new IllegalArgumentException("Customer nulo");
        customers.put(c.getId(), c);
    }

    public Collection<Customer> getAllCustomers() {
        return Collections.unmodifiableCollection(customers.values());
    }

    //Gestión de Órdenes
    public void addOrder(Order o) {
        if (o == null) throw new IllegalArgumentException("Order nulo");
        orders.put(o.getId(), o);
    }

    public Collection<Order> getAllOrders() {
        return Collections.unmodifiableCollection(orders.values());
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orders.values().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
    }

    public double averageOrderValue() {
        return orders.values().stream()
                .mapToDouble(Order::getTotal)
                .average()
                .orElse(0.0);
    }

    public double totalRevenueDelivered() {
        return orders.values().stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotal)
                .sum();
    }
}
