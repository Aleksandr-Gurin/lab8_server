package ru.ifmo.se.server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SessionsManager {
    private final Set<ClientSession> sessions = new HashSet<ClientSession>();

    public SessionsManager() {}

    public void addSession(ClientSession session) {
        sessions.add(session);
    }

    public void removeSession(ClientSession session) {
        sessions.remove(session);
    }

    public Set<ClientSession> getSessions() {
        return sessions;
    }

    @Override
    public int hashCode(){
        return Objects.hash(sessions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionsManager testClass = (SessionsManager) o;
        return Objects.equals(sessions, testClass.getSessions());
    }

}
