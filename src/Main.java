import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));

        Cart cart = new Cart();
        List<Order> orderHistory = new ArrayList<>(); // історія замовлень

        while (true) {
            System.out.println("\n--- Головне меню ---");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Видалити товар з кошика");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Історія замовлень");
            System.out.println("7 - Пошук товарів");
            System.out.println("0 - Вийти");
            System.out.print("Виберіть опцію: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть числове значення.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- Каталог товарів ---");
                    for (Product product : catalog) {
                        System.out.println(product);
                    }
                    break;

                case 2:
                    System.out.print("Введіть ID товару для додавання до кошика: ");
                    try {
                        int addId = Integer.parseInt(scanner.nextLine());
                        Product foundToAdd = null;
                        for (Product p : catalog) {
                            if (p.getId() == addId) {
                                foundToAdd = p;
                                break;
                            }
                        }

                        if (foundToAdd != null) {
                            cart.addProduct(foundToAdd);
                            System.out.println("Товар \"" + foundToAdd.getName() + "\" додано до кошика.");
                        } else {
                            System.out.println("Товар з таким ID не знайдено.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некоректний формат ID.");
                    }
                    break;

                case 3:
                    System.out.println("\n" + cart);
                    break;

                case 4:
                    // видалення товару з кошика
                    if (cart.isEmpty()) {
                        System.out.println("Кошик порожній. Немає чого видаляти.");
                        break;
                    }
                    System.out.println("\n" + cart);
                    System.out.print("Введіть ID товару, який бажаєте видалити: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine());
                        Product toRemove = null;
                        for (Product p : cart.getProducts()) {
                            if (p.getId() == removeId) {
                                toRemove = p;
                                break;
                            }
                        }

                        if (toRemove != null && cart.removeProduct(toRemove)) {
                            System.out.println("Товар видалено з кошика.");
                        } else {
                            System.out.println("Товар з таким ID у кошику не знайдено.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некоректний формат ID.");
                    }
                    break;

                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order newOrder = new Order(cart);
                        orderHistory.add(newOrder);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(newOrder);
                        cart.clear();
                    }
                    break;

                case 6:
                    // перегляд історії замовлень
                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("\n--- Ваша історія замовлень ---");
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.println("№" + (i + 1) + " " + orderHistory.get(i));
                            System.out.println("---------------------------------");
                        }
                    }
                    break;

                case 7:
                    // пошук за назвою або категорією
                    System.out.print("Введіть пошуковий запит (назва або назва категорії): ");
                    String query = scanner.nextLine().trim().toLowerCase();

                    List<Product> searchResults = new ArrayList<>();
                    for (Product p : catalog) {
                        boolean matchName = p.getName().toLowerCase().contains(query);
                        boolean matchCategory = p.getCategory().getName().toLowerCase().contains(query);
                        if (matchName || matchCategory) {
                            searchResults.add(p);
                        }
                    }

                    if (searchResults.isEmpty()) {
                        System.out.println("Товарів за запитом \"" + query + "\" не знайдено.");
                    } else {
                        System.out.println("\n--- Результати пошуку ---");
                        for (Product p : searchResults) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }
}