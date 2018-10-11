package ideaeclipse.AsyncUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class should be the extension of any different type of async execution list type
 * i.e. {@link ForEachList}, {@link WaterfallList}
 * This acts as a list of {@link Async.IU}
 *
 * @param <T>
 * @author Ideaeclipse
 */
public abstract class AsyncList<T> {
    private final List<Async.IU<T>> list;

    public AsyncList() {
        list = new LinkedList<>();
    }

    /**
     * @param index index you wish to receive
     * @return the code block at the index you inputted
     */
    public Async.IU<T> get(final int index) {
        return list.get(index);
    }

    /**
     * @param event event block of code
     * @return object instance to allow for chaining of {@link #add(Async.IU)} methods
     */
    public AsyncList<T>  add(final Async.IU<T> event) {
        list.add(event);
        return this;
    }

    /**
     * @return size of array
     */
    public Integer size() {
        return list.size();
    }

    /**
     * Abstract method
     * @return an option list of optional values with the generic type
     */
    public abstract Optional<List<T>> execute();
}
