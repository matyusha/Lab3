package un.lab.esa.demo.model;

import un.lab.esa.demo.model.enums.ChangeType;

import javax.persistence.*;

@Entity
@Table(schema = "public", name="changes")
public class Change {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChangeType changeType;

    @Column(name="entity")
    private String entity;

    @Column(name = "entity_id")
    private int entity_id;

    @Column(name = "info")
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChangeType getType() {
        return changeType;
    }

    public void setType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
