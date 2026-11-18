package scheduler;
import java.util.List;
import model.Process;

public interface Scheduler {
    List<GanttChartEntry> schedule(List<Process> processes);
    
}
