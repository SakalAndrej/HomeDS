package at.htl.business;

import at.htl.facades.DataSetFieldFacade;
import at.htl.xiboClient.DataSetApi;
import at.htl.xiboClient.helper.AuthentificationHandler;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class InitBean {

    @PostConstruct
    public void init() {
        AuthentificationHandler.getTOKEN();
    }
}
