package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product hrechka = new Product("Hrechca", 100.0);
        Product rise = new Product("Rise", 80.0);
        Product apple = new Product("Apple", 40.0);

        productService.create(hrechka);
        productService.create(rise);
        productService.create(apple);

        System.out.println(productService.get(rise.getId()));
        System.out.println(productService.getAll());
        System.out.println(productService.delete(hrechka.getId()));

        Product whiskey = new Product("whiskey", 300.0);

        System.out.println(productService.delete(whiskey.getId()));
        System.out.println(productService.getAll());

        Product cocaCola = new Product("CocaCola", 22.0);
        productService.create(cocaCola);

        System.out.println(productService.update(cocaCola));
        System.out.println(productService.getAll());

        System.out.println("_______TEST_ORDER______");
        User vasia = new User("vasia", "1234", "verySecretPassword");
        Order order = new Order(productService.getAll(), vasia);
        System.out.println(order.getProducts());
        System.out.println(order.getUser());
    }
}
