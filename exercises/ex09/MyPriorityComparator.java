package exercises.ex09;

import java.util.Comparator;

public class MyPriorityComparator implements Comparator<MyPriorityTask> {

    public int compare(MyPriorityTask mpt1, MyPriorityTask mpt2)
    {
    	// the higher priority the better
        int diffP = mpt2.getPriority() - mpt1.getPriority();

       	// the lower order the better
        if (diffP == 0) return (int) (mpt1.getOrder() - mpt2.getOrder());
        
        return diffP;
    }

 }