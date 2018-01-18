package at.htl.web;

import sun.misc.resources.Messages;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import static com.oracle.tools.packager.linux.LinuxDebBundler.BUNDLE_NAME;

@ManagedBean
@SessionScoped
public class LanguageController implements Serializable {

    static {
        // load message values from bundle file
        final Field[] fieldArray = Messages.class.getDeclaredFields();
        final int len = fieldArray.length;
        for (int i = 0; i < len; i++) {
            final Field field = (Field) fieldArray[i];
            if (field.getType() == java.lang.String.class) {
                if (!field.isAccessible())
                    field.setAccessible(true);
                try {
                    final String rawValue = (String) field.get(null);
                    field.set(null, new String(rawValue.getBytes("ISO-8859-1"),
                            "UTF-8"));
                } catch (Exception e) {
                    // skip field modification
                }
            }
        }
    }

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