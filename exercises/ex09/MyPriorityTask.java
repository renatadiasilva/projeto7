package exercises.ex09;

public class MyPriorityTask {

    private long order;
    private int priority;

    public MyPriorityTask(long order, int priority) {
        this.order = order;
        this.priority = priority;
    }

    // getters
    public long getOrder() {
        return order;
    }

    public int getPriority() {
        return priority;
    }

}