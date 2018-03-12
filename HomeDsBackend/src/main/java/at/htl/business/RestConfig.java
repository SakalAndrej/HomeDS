package at.htl.business;

import at.htl.rest.*;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rs")
public class RestConfig extends Application {

    public RestConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.2.0");
        beanConfig.setTitle("HomeDSBackend");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("vm59.htl-leonding.ac.at:8080");
        beanConfig.setBasePath("/homeds/rs");
        beanConfig.setResourcePackage("at.htl.rest");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<>();

        set.add(CrossOriginResourceSharingFilter.class);
        set.add(RestConfig.class);
        set.add(DataSetDataFieldEndpoint.class);
        set.add(CrawlerEndpoint.class);
        set.add(MediaEndpoint.class);
        set.add(StatusEndpoint.class);
        set.add(DisplayEndpoint.class);

        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return set;
    }
}
