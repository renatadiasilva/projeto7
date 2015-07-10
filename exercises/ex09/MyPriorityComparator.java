package exercises.ex09;

import java.util.Comparator;

// Referenced classes of package exercises.ex09:
//            MyPriorityTask

public class MyPriorityComparator
    implements Comparator<MyPriorityTask>
{

    public int compare(MyPriorityTask mpt1, MyPriorityTask mpt2)
    {
        int diffP = mpt2.getPriority() - mpt1.getPriority();
        if(diffP == 0)
            return (int)(mpt1.getOrder() - mpt2.getOrder());
        else
            return diffP;
    }

 }