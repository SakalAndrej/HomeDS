package at.htl.business;

import at.htl.xiboClient.helper.AuthentificationHandler;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class InitBean {

    public void init() {
        AuthentificationHandler.getTOKEN();
    }
}
