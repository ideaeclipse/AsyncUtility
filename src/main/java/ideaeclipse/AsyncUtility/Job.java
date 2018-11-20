package ideaeclipse.AsyncUtility;

import java.util.Timer;
import java.util.TimerTask;

public class Job<T> {
    private final Async.IU<T> task;

    Job(final Async.IU<T> task, final Long time) {
        this.task = task;
        Timer timer = new Timer();
        timer.schedule(new executable(), 0, time);
    }

    private void execute() {
        Async.blankThread(task);
    }

    private class executable extends TimerTask {

        @Override
        public void run() {
            execute();
        }
    }

}
