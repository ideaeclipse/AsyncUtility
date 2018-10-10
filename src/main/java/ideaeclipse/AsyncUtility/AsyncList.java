package ideaeclipse.AsyncUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AsyncList<T> {
    private final List<Async.IU<T>> list;

    public AsyncList() {
        list = new LinkedList<>();
    }

    public Async.IU<T> get(final int index) {
        return list.get(index);
    }

    public AsyncList<T> add(final Async.IU<T> event) {
        list.add(event);
        return this;
    }

    public Integer size() {
        return list.size();
    }

    public abstract Optional<List<Optional<T>>> execute();
}
