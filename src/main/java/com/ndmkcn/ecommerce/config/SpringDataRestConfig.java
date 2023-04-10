package com.ndmkcn.ecommerce.config;

import com.ndmkcn.ecommerce.entity.Country;
import com.ndmkcn.ecommerce.entity.Product;
import com.ndmkcn.ecommerce.entity.ProductCategory;
import com.ndmkcn.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {
    @Value("${allowed.origins}")
    private String[] theallowedOrigins;
    private EntityManager entityManager;
    @Autowired
    public SpringDataRestConfig(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    // Burada Spring Data Rest tarafından oluşturulacak ekleme güncelleme silme işlemlerini
    // iptal ederek manuel olarak kendimiz yapacağız dedik.
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions={HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.PATCH};
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class,config, theUnsupportedActions);
        disableHttpMethods(Order.class,config, theUnsupportedActions);
        exposeIds(config);
        // CONFİGURE CORS MAPPİNG
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theallowedOrigins);
    }
    // Burayı sağ tıkladık ve refactor extract ile method haline getirdik çünkü tekrar eden bir yapı oldu.
    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    // Bu method Spring Data Rest bize kategory idsini vermediği için yazdık burad abize entity üzerinden
    // category id vermesi için bir takım ayarlamalar yaptık.
    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();
        List<Class> entityClasses=new ArrayList<>();
        for (EntityType entity:entities) {
            entityClasses.add(entity.getJavaType());
        }
        Class[] domainTypes=entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
