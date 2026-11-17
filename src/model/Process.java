package model;

public class Process {

    private int processId;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;
    private int remainingTime;

    public Process(int pi, int a, int b, int pr){
        this.processId=pi;
        this.arrivalTime = a;
        this.burstTime = b;
        this.priority = pr;

        this.remainingTime = burstTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }


    public void setCompletionTime(int completionTime) {
    this.completionTime = completionTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override 
    public String toString() {
    return "P" + processId +
           " [Arrival=" + arrivalTime +
           ", Burst=" + burstTime +
           ", Priority=" + priority + "]";
    }
}
