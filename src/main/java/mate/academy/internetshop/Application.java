package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

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

        System.out.println("_____TEST_USER_____");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = userService.create(new User("Vasya", "vasiliy", "secretPassword"));
        System.out.println(user.toString());
        user.setPassword("VerySecretPassword");
        userService.update(user);
        System.out.println(user.toString());

        System.out.println("_______TEST_SHOPPING_CART______");
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCartService.addProduct(shoppingCart, hrechka); // test failed
        shoppingCartService.addProduct(shoppingCart, whiskey);
        shoppingCartService.getAllProducts(shoppingCart)
                .forEach(prod -> System.out.println(prod.toString()));

        System.out.println("_______TEST_ORDER______");
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getAllProducts(shoppingCart), user);
        orderService.getUserOrders(user).forEach(order -> System.out.println(order.toString()));
    }
}
