import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.AsyncUtility.WaterfallList;

import java.util.Optional;

public class AsyncTest {
   private AsyncTest(){
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
        System.out.println(list.execute());
    /*
        AsyncList<Integer> parentList = new WaterfallList<>();
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
        parentList.add(o-> {
            list.execute().get().forEach(a->{
                System.out.print(a.isPresent() + " ");
                System.out.println(a.isPresent() ? a.get() : "N/a");
            });
            return Optional.of(4);
        });
        parentList.add(Optional::of);
        parentList.execute().get().forEach(o->{
            System.out.print(o.isPresent() + " ");
            System.out.println(o.isPresent() ? o.get() : "N/a");
        });
        */
    }

    public static void main(String[] args) {
        new AsyncTest();
    }
}
