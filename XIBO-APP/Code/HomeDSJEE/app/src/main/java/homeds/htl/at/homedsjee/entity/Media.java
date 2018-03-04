package homeds.htl.at.homedsjee.entity;

/**
 * Created by Felix on 04.03.2018.
 */

public class Media {

    Long id;
    String name;
    String description;


    public Media(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Media() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
