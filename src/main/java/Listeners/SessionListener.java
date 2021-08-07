package Listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener()
public class SessionListener implements HttpSessionListener {

    private final AtomicInteger activeSessions;

    public SessionListener() {
        activeSessions = new AtomicInteger();
    }

    @Override
    public void sessionCreated(HttpSessionEvent se){
        activeSessions.incrementAndGet();
        se.getSession().setAttribute("User",null);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions.decrementAndGet();
    }
    public int getActiveSessions(){
        return activeSessions.get();
    }

}