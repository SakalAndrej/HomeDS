package homeds.htl.at.homedsjee.entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Felix on 20.02.2018.
 */

public class Structure implements Serializable {

    private String name;
    private int subStructuresCount;
    private LinkedList<Structure> subStructures;
    private String description;


    public Structure() {
    }

    public Structure(String name, int subStructuresCount, LinkedList<Structure> subStructures, String description) {
        this.name = name;
        this.subStructuresCount = subStructuresCount;
        this.subStructures = subStructures;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubStructuresCount() {
        return subStructuresCount;
    }

    public void setSubStructuresCount(int subStructuresCount) {
        this.subStructuresCount = subStructuresCount;
    }

    public LinkedList<Structure> getSubStructures() {
        return subStructures;
    }

    public void setSubStructures(LinkedList<Structure> subStructures) {
        this.subStructures = subStructures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
