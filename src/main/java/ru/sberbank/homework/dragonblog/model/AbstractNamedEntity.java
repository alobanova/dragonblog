package ru.sberbank.homework.dragonblog.model;

/**
 * Created by Mart
 * 28.06.2019
 **/
public class AbstractNamedEntity extends AbstractBaseEntity {
    private String uniqueName;

    public AbstractNamedEntity() {
    }

    public AbstractNamedEntity(Long id, String name) {
        super(id);
        this.uniqueName = name;
    }

    public String getName() {
        return uniqueName;
    }

    public void setName(String name) {
        this.uniqueName = name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), id, uniqueName);
    }
}
