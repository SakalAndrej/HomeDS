package at.htl.business;

import at.htl.helper.AuthentificationHandler;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class InitBean {

    @PostConstruct
    public void init() {
        AuthentificationHandler.getTOKEN();
    }
}
