package eventbus;

/**
 * Created by 1 on 28.11.2016.
 */
public interface Callback {

    void onFail(RuntimeException e);
}