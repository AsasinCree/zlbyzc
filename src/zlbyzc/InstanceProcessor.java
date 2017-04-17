package zlbyzc;


import java.util.concurrent.FutureTask;

/**
 * Single instance processor.<br>
 * It allows to run processes on a thread and verify the constraint that only one single instance of
 * a specific process can be queued at a given time.
 */
public class InstanceProcessor extends Processor
{
    /**
     * Create an InstanceProcessor
     */
    public InstanceProcessor(int maxWaiting, int priority)
    {
        super(maxWaiting, 1, priority);

        setThreadName("InstanceProcessor");
    }

    /**
     * Create an InstanceProcessor
     */
    public InstanceProcessor(int priority)
    {
        this(-1, priority);
    }

    /**
     * Create an InstanceProcessor
     */
    public InstanceProcessor()
    {
        this(Processor.NORM_PRIORITY);
    }

    /**
     * Try to submit the specified task for execution and returns a Future representing that task.<br>
     * The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em>
     * completion.<br>
     * Returns a <code>null</code> Future object if processor has already this task pending in queue
     * (in this case the new task is simply ignored)..
     */
    @Override
    protected synchronized <T> FutureTask<T> submit(FutureTaskAdapter<T> task)
    {
        // task already present in queue --> return null (mean the task was ignored)
        if ((task.runnable != null) && hasWaitingTasks(task.runnable))
            return null;
        if ((task.callable != null) && hasWaitingTasks(task.callable))
            return null;

        // add task only if not already present in queue
        return super.submit(task);
    }
}
