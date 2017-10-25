package me.alexand.intech.server.model;

import javax.persistence.*;

/**
 * @author asidorov84@gmail.com
 */

@Entity
@Table(name = "transitions")
public class Transition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Enumerated(EnumType.STRING)
    private FSMEvent event;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id")
    private FSMState sourceState;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id")
    private FSMState targetState;

    @Column
    private String action;

    public Transition() {
    }

    public Transition(int id, FSMEvent event, FSMState sourceState, FSMState targetState, String action) {
        this.id = id;
        this.event = event;
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public FSMEvent getEvent() {
        return event;
    }

    public FSMState getSourceState() {
        return sourceState;
    }

    public FSMState getTargetState() {
        return targetState;
    }

    public String getAction() {
        return action;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (sourceState != null ? sourceState.hashCode() : 0);
        result = 31 * result + (targetState != null ? targetState.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Transition arg = (Transition) obj;

        if (id != arg.id) return false;
        if (event != arg.event) return false;
        if (sourceState != null ? !sourceState.equals(arg.sourceState) : arg.sourceState != null) return false;
        if (targetState != null ? !targetState.equals(arg.targetState) : arg.targetState != null) return false;
        return action != null ? action.equals(arg.action) : arg.action == null;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "id=" + id +
                ", event=" + event +
                ", sourceState=" + sourceState +
                ", targetState=" + targetState +
                ", action='" + action + '\'' +
                '}';
    }
}
