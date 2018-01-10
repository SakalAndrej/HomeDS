package at.htl.business;

import at.htl.utils.AuthentificationHandler;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class InitBean {

    public void init() {
        AuthentificationHandler.getTOKEN();
    }
}
