package de.btc.microservice.addressservice;

import de.btc.microservice.addressservice.core.AddressService;
import de.btc.microservice.addressservice.model.Address;
import de.btc.microservice.addressservice.rest.AddressRestService;
import de.btc.microservice.addressservice.rest.dto.AddressDto;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;

public class Main {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        container.fraction(new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("MyDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                })
        );

        // Prevent JPA Fraction from installing it's default datasource fraction
        container.fraction(new JPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/MyDS")
        );

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addResource(AddressRestService.class);
        deployment.addClass(AddressService.class);
        deployment.addClass(Address.class);
        deployment.addClass(AddressDto.class);

        deployment.addAsWebResource(new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");
        deployment.addAsWebResource(new ClassLoaderAsset("index.xhtml", Main.class.getClassLoader()), "index.xhtml");

        deployment.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");
        deployment.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/template.xhtml", Main.class.getClassLoader()), "template.xhtml");

        deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");

        deployment.addAllDependencies();

        container.start().deploy(deployment);
    }
}
