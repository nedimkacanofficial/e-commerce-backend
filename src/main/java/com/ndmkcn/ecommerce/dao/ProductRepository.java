package com.ndmkcn.ecommerce.dao;

import com.ndmkcn.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "products",path = "products")
// Buraya * koyarsak adres farketmeksizin gelen isteklere cevap verir yani tüm herkese cevap döner
// ama biz sadece haberleşmesini istediğimiz uygulama ile haberleştireceğiz. Aynı zamanda
// eğer herkesle haberleşsin istiyorsak o zaman yıldız yerine istersek herhangi bir parantez açmadan
// aşağıdaki gibi bir kullanımda yapabiliriz.
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:4200")
// peki ya sadece iki kaynağa veya daha fazlasına açmak istiyorsak ama sadece bizim belirttiğimiz kaynaklar
// olacaksa bu durumda aşağıdaki gibi bir kullanım mevcuttur.
// @CrossOrigin(origins = "{http://localhost:4200","http://www.ndmkcn.com"})
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategoryId(@RequestParam("id")Long id, Pageable pageable);
    // Burada containing spring data jpadan gelen bir özellik belirtilen parametre değeri ile eşleşen
    // tüm name değerlerini page nesnesi olarak geri döndürür. Yani sanki sql ile like sorgusu yazmak gibi.
    // Ayrıca biz burada Spring Data Rest kullandığımız için buraya yazdığımız endpointlere ulaşmak için
    // arama işlemi yapmışız bu yüzden /api/search/ve metod adımızı yazarız.
    Page<Product> findByNameContaining(@RequestParam("name") String name,Pageable pageable);
}
