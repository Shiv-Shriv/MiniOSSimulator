package scheduler;
import java.util.List;
import model.Process;

public interface Scheduler {
    void schedule(List<Process> processes);
    
}
