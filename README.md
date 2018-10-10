# AsyncUtility
* This utility allows you to added code blocks into a list and execute them asynchronously on multiple different threads
* You can also synchronously execute code to free up the main thread
## Methods
* There are two main methods for asnyc execution
    * AsyncList
        * For asynchronous execution
        * Instances are (ForEachList, WaterfallList)
    * Async.queue()
        * For synchronous execution
## Examples
* Synchronous execution
```java
Integer a = Async.queue(() -> {
        System.out.println("Synchronous execution");
        return Optional.of(1);
    }, "TestThread");
System.out.println(a);
```
* This method returns whatever the code block you provided returns
```java
/*
* Return:
* Synchronous execution
* Optional[1]
*/
```
* Asynchronous execution
```java
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
    System.out.println(list.execute());
}
```
* This execute method executes which ever method is next in the set and starts executing it
* As you can see from the code The method with the shortest thread sleep time ends first and the longest ends last
* Even though the code blocks get execute asynchronously the return values get returned in order you added them to the list in 
```java
/*
* Result:
* Starting 3
* Starting 2
* Starting 1
* Ending 2
* Ending 1
* Ending 3
* Optional[[Optional[1], Optional[2], Optional[3]]]
*/
```
#### Projects
* If you want to see projects that incorporate this utility check out my other projects DiscordAPI, reflectionListener, and jsonUtilities