package at.htl.web;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LanguageController implements Serializable {

    public void countryLocaleCodeChanged() {

        if (FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(Locale.ENGLISH)) {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.GERMANY);
        } else {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.ENGLISH);
        }
    }
}