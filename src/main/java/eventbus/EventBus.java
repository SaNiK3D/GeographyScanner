package eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Александр on 12.11.2016.
 */
public class EventBus {

    private Map<Class, Set<Handler>> handlers;

    public EventBus() {
        handlers = new HashMap<>();
    }

    public <T extends Event> void addHandler(Class<T> handlerClass, Handler<T> handler) {
        Set<Handler> chosenByClassHandlers = handlers.computeIfAbsent(handlerClass, k -> new HashSet<>());
        chosenByClassHandlers.add(handler);
    }

    public <T extends Event> void deleteHandler(Class<T> handlerClass, Handler<T> handlerToDelete) {
        Set<Handler> destinationHandlers = handlers.get(handlerClass);
        if(destinationHandlers != null){
            destinationHandlers.remove(handlerToDelete);
        }
    }

    public <T extends Event> void post(T event) {
        Set<Handler> handlersToPostOn = handlers.get(event.getClass());
        if(handlersToPostOn != null){
            for (Handler handler : handlersToPostOn) {
                //noinspection unchecked
                handler.handle(event);
            }
        }
    }
}