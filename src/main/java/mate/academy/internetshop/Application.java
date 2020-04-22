package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ItemService itemService = (ItemService) injector.getInstance(ItemService.class);
        Item hrechka = new Item("Hrechca", 100.00);
        Item rise = new Item("Rise", 80.00);
        Item apple = new Item("Apple", 40.00);

        itemService.create(hrechka);
        itemService.create(rise);
        itemService.create(apple);

        System.out.println(itemService.get(rise.getId()));
        System.out.println(itemService.getAll());
        System.out.println(itemService.delete(hrechka.getId()));

        Item whiskey = new Item("whiskey", 300.00);

        System.out.println(itemService.delete(whiskey.getId()));
        System.out.println(itemService.getAll());

        Item cocaCola = new Item("CocaCola", 22.00);
        itemService.update(cocaCola);

        System.out.println(itemService.getAll());
    }
}
