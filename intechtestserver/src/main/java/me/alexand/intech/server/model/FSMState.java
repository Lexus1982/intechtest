package me.alexand.intech.server.model;

import javax.persistence.*;

/**
 * @author asidorov84@gmail.com
 */

@Entity
@Table(name = "states")
public class FSMState {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String message;

    @Column(name = "init_flag")
    private boolean initFlag;

    public FSMState() {
    }

    public FSMState(int id, String name, String message, boolean initFlag) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.initFlag = initFlag;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInitFlag() {
        return initFlag;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (initFlag ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FSMState arg = (FSMState) obj;

        if (id != arg.id) return false;
        if (initFlag != arg.initFlag) return false;
        if (name != null ? !name.equals(arg.name) : arg.name != null) return false;
        return message != null ? message.equals(arg.message) : arg.message == null;
    }

    @Override
    public String toString() {
        return "FSMState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message=" + message +
                ", initFlag=" + initFlag +
                '}';
    }
}
