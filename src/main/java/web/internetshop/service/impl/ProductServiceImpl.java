package web.internetshop.service.impl;

import java.util.List;
import web.internetshop.dao.ProductDao;
import web.internetshop.lib.Inject;
import web.internetshop.lib.Service;
import web.internetshop.model.Product;
import web.internetshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id).get();
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }

}
