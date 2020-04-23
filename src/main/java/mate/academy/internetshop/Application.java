package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService itemService = (ProductService) injector.getInstance(ProductService.class);
        Product hrechka = new Product("Hrechca", 100.0);
        Product rise = new Product("Rise", 80.0);
        Product apple = new Product("Apple", 40.0);

        itemService.create(hrechka);
        itemService.create(rise);
        itemService.create(apple);

        System.out.println(itemService.get(rise.getId()));
        System.out.println(itemService.getAll());
        System.out.println(itemService.delete(hrechka.getId()));

        Product whiskey = new Product("whiskey", 300.0);

        System.out.println(itemService.delete(whiskey.getId()));
        System.out.println(itemService.getAll());

        Product cocaCola = new Product("CocaCola", 22.0);
        itemService.create(cocaCola);

        System.out.println(itemService.update(cocaCola));
        System.out.println(itemService.getAll());
    }
}
