package dev.boooiil.historia.mysql;

public class UserData {

    String uuid;
    String displayName;
    String className;
    Integer classLevel;
    Integer classExperience;

    public UserData(String uuid) {
        
    }

    public void setName(String name) {

        displayName = name;

    }

    public void setClass(String name) {

        className = name;

    }

    public void setLevel(Integer level) {

        classLevel = level;

    }

    public Integer getLevel() {

        return classLevel;

    }

    public String getDisplayName() {

        return displayName;

    }

    public String getClassName() {

        return className;

    }

}
