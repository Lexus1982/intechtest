package me.alexand.intech.server.service.fsm;

import javafx.util.Pair;
import me.alexand.intech.server.model.FSMEvent;
import me.alexand.intech.server.model.FSMState;
import me.alexand.intech.server.model.Transition;
import me.alexand.intech.server.repository.StateRepository;
import me.alexand.intech.server.repository.TransitionRepository;
import me.alexand.intech.server.util.exceptions.IllegalFSMEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asidorov84@gmail.com
 */
@Component
@Scope("prototype")
public class FSMachineImpl implements FSMachine {
    private final StateRepository stateRepository;
    private final TransitionRepository transitionRepository;

    private final Map<Source, Target> transitions = new HashMap<>();
    private FSMState currentState;

    @Autowired
    public FSMachineImpl(StateRepository stateRepository, TransitionRepository transitionRepository) {
        this.stateRepository = stateRepository;
        this.transitionRepository = transitionRepository;
        init();
    }

    @Override
    public Pair<String, String> sendEvent(FSMEvent event) throws IllegalFSMEvent {
        Target target = transitions.get(new Source(event, currentState));

        if (target == null)
            throw new IllegalFSMEvent(event.toString());

        currentState = target.getState();

        return new Pair<>(target.getState().getMessage(), target.getAction());
    }

    private void init() {
        currentState = stateRepository.getInitState();

        for (Transition transition : transitionRepository.getAll()) {
            transitions.put(new Source(transition.getEvent(), transition.getSourceState()),
                    new Target(transition.getTargetState(), transition.getAction()));
        }
    }

    private class Source {
        private final FSMEvent event;
        private final FSMState state;

        public Source(FSMEvent event, FSMState state) {
            this.event = event;
            this.state = state;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Source arg = (Source) obj;

            if (event != arg.event) return false;
            return state.equals(arg.state);
        }

        @Override
        public int hashCode() {
            int result = event.hashCode();
            result = 31 * result + state.hashCode();
            return result;
        }
    }

    private class Target {
        private final FSMState state;
        private final String action;

        public Target(FSMState state, String action) {
            this.state = state;
            this.action = action;
        }

        public String getAction() {
            return action;
        }

        public FSMState getState() {
            return state;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Target arg = (Target) obj;

            if (!state.equals(arg.state)) return false;
            return action.equals(arg.action);
        }

        @Override
        public int hashCode() {
            int result = state.hashCode();
            result = 31 * result + action.hashCode();
            return result;
        }
    }
}
