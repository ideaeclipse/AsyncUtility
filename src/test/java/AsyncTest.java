import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.AsyncUtility.WaterfallList;

import java.util.Optional;

public class AsyncTest {
    private AsyncTest() {
        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            System.out.println("Starting 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 1");
            return Optional.of(1);
        });
        list.add(o -> {
            System.out.println("Starting 2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 2");
            return Optional.of(2);
        });
        list.add(o -> {
            System.out.println("Starting 3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 3");
            return Optional.of(3);
        });
        /*
         * Starting 1
         * Starting 2
         * Starting 3
         * Ending 2
         * Ending 1
         * Ending 3
         * [1, 2, 3]
         */
        for (Optional<Integer> o : list.execute().get()) {
            System.out.print(o.isPresent() + " ");
            System.out.println(o.isPresent() ? o.get() : "N/a");
        }
        AsyncList<Integer> list2 = new WaterfallList<>();
        list2.add(o -> Optional.of(1));//1
        list2.add(o -> Optional.of(o+1));//2
        list2.add(o -> Optional.of(o+2));//4
        System.out.println(list2.execute());
        for (Optional<Integer> o : list2.execute().get()) {
            System.out.print(o.isPresent() + " ");
            System.out.println(o.isPresent() ? o.get() : "N/a");
        }
    }

    public static void main(String[] args) {
        new AsyncTest();
    }
}
