package exercises.ex09;


public class MyPriorityTask
{

    public MyPriorityTask(long order, int priority)
    {
        this.order = order;
        this.priority = priority;
    }

    public long getOrder()
    {
        return order;
    }

    public int getPriority()
    {
        return priority;
    }

    private long order;
    private int priority;
}