
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantApp {
    public static void main(String[] args) {
        RestaurantManager manager = RestaurantManager.getInstance();

        Customer alice = new Customer("C001", "Alice Pérez", "alice@mail.com", "Cll 12 #34-56", "3120001111");
        Customer bob = new Customer("C002", "Bob Gómez", "bob@mail.com", "Cll 90 #10-20", "3120002222");
        manager.addCustomer(alice);
        manager.addCustomer(bob);

        try {
            MenuItem m1 = MenuItemFactory.createMenuItem("FOOD", "M001", "Hamburguesa clásica", 12.50);
            MenuItem m2 = MenuItemFactory.createMenuItem("FOOD", "M002", "Ensalada César", 8.00);
            MenuItem m3 = MenuItemFactory.createMenuItem("DRINK", "M003", "Gaseosa 500ml", 2.50);
            MenuItem m4 = MenuItemFactory.createMenuItem("DRINK", "M004", "Jugo natural", 3.20);

            manager.addMenuItem(m1);
            manager.addMenuItem(m2);
            manager.addMenuItem(m3);
            manager.addMenuItem(m4);
        } catch (InvalidDataException e) {
            System.err.println("Error creando menú: " + e.getMessage());
        }

        Order o1 = new Order("O1001", alice);
        try {
            o1.addItem(new OrderItem(manager.getMenuItemById("M001"), 2)); 
            o1.addItem(new OrderItem(manager.getMenuItemById("M003"), 2)); 
        } catch (InvalidDataException e) {
            System.err.println("Error creando pedido o1: " + e.getMessage());
        }
        manager.addOrder(o1);

        Order o2 = new Order("O1002", bob);
        try {
            o2.addItem(new OrderItem(manager.getMenuItemById("M002"), 1)); 
            o2.addItem(new OrderItem(manager.getMenuItemById("M004"), 1));
        } catch (InvalidDataException e) {
            System.err.println("Error creando pedido o2: " + e.getMessage());
        }
        o2.setStatus(OrderStatus.DELIVERED);
        manager.addOrder(o2);

        List<String> pricey = manager.getAllMenuItems().stream()
                .filter(mi -> mi.getPrice() > 3.0)
                .map(MenuItem::getName)
                .collect(Collectors.toList());

        System.out.println("Items del menú con precio > 3.0: " + pricey);

        double avgOrder = manager.getAllOrders().stream()
                .mapToDouble(Order::getTotal)
                .average()
                .orElse(0.0);

        System.out.printf("Valor promedio de pedidos: %.2f%n", avgOrder);

        Map<OrderStatus, List<Order>> pedidosPorEstado = manager.getAllOrders().stream()
                .collect(Collectors.groupingBy(Order::getStatus));

        System.out.println("Pedidos agrupados por estado:");
        pedidosPorEstado.forEach((status, list) -> {
            System.out.println(" - " + status + ": " + list.size() + " pedido(s)");
        });

        double ingresosEntregados = manager.getAllOrders().stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotal)
                .sum();

        System.out.printf("Ingresos por pedidos entregados: %.2f%n", ingresosEntregados);

        List<MenuItem> top3 = manager.getAllMenuItems().stream()
                .sorted(Comparator.comparingDouble(MenuItem::getPrice).reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Top 3 items más costosos del menú:");
        top3.forEach(mi -> System.out.printf(" * %s (%.2f)%n", mi.getName(), mi.getPrice()));

        Scanner sc = new Scanner(System.in);
        System.out.println(" Validación: intenta agregar un nuevo item al menú.");
        System.out.print("Ingrese precio sugerido para 'Item Prueba' (ej: 4.5). Para probar un error ingrese 'abc': ");
        try {
            String input = sc.nextLine();
            double precio = Double.parseDouble(input);
            MenuItem prueba = MenuItemFactory.createMenuItem("FOOD", "M999", "Item Prueba", precio);
            manager.addMenuItem(prueba);
            System.out.println("Item agregado correctamente: " + prueba);
        } catch (NumberFormatException nfe) {
            System.err.println("Precio inválido: no se pudo convertir a número. Detalle: " + nfe.getMessage());
        } catch (InvalidDataException ide) {
            System.err.println("Error de validación: " + ide.getMessage());
        } finally {
            sc.close();
            System.out.println("Fin de la sección de validación (scanner cerrado).");
        }

        System.out.println(" --- Estado final del sistema ---");
        System.out.println("Clientes registrados: " + manager.getAllCustomers().size());
        System.out.println("Items en menú: " + manager.getAllMenuItems().size());
        System.out.println("Pedidos registrados: " + manager.getAllOrders().size());
    }
}
