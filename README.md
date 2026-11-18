# Mini OS Simulator

A Java-based **Mini Operating System Simulator** that demonstrates fundamental operating system concepts through an interactive graphical interface built using Java Swing.

The project simulates CPU scheduling, memory allocation, deadlock avoidance, and multithreading, helping users understand how an operating system manages processes and system resources.

## Features

### 1. CPU Scheduling

Implements three CPU scheduling algorithms:

- **First Come First Serve (FCFS)**
- **Shortest Job First (SJF)** – Non-preemptive
- **Round Robin (RR)** – Configurable time quantum

**Key functionalities:**
- Add processes with Process ID, Arrival Time, Burst Time, and Priority.
- Visualize process execution through a Gantt chart.
- Calculate completion time, turnaround time, and waiting time.
- Display average waiting time and average turnaround time.

### 2. Memory Allocation

Implements three contiguous memory allocation strategies:

- **First Fit**
- **Best Fit**
- **Worst Fit**

**Key functionalities:**
- Add memory blocks with customizable sizes.
- Allocate processes based on the selected strategy.
- Track the remaining free space in each memory block.
- Display allocation history and unallocated processes.
- Reset memory to perform new allocation simulations.

### 3. Deadlock Detection and Avoidance

Implements the **Banker's Algorithm** to determine whether a system is in a safe or unsafe state.

**Key functionalities:**
- Configure the number of processes and resource types.
- Enter Allocation, Maximum, and Available resource matrices.
- Calculate the Need matrix internally.
- Determine whether a safe sequence exists.
- Display the safe execution sequence when one is found.

### 4. Multithreading – Producer-Consumer Problem

Demonstrates Java multithreading through the classic Producer-Consumer synchronization problem.

**Key functionalities:**
- Configurable number of items to produce and consume.
- Configurable shared buffer capacity.
- Adjustable producer and consumer delays.
- Thread synchronization using `wait()` and `notifyAll()`.
- Live output displaying production and consumption events.

## Technologies Used

- **Language:** Java
- **GUI:** Java Swing and AWT
- **Data Structures:** ArrayList, LinkedList, Queue
- **Multithreading:** Thread, Runnable, Synchronization
- **Development Environment:** Visual Studio Code

## Object-Oriented Programming Concepts

The project incorporates several fundamental OOP concepts:

- **Encapsulation:** Classes encapsulate process, memory block, and scheduling data.
- **Abstraction:** Abstract classes and interfaces define common behaviour for scheduling and memory allocation algorithms.
- **Inheritance:** Specific memory allocation strategies extend a common abstract allocator.
- **Polymorphism:** Different scheduling and allocation algorithms implement shared interfaces or abstract methods.
- **Exception Handling:** Input validation and exception handling improve the reliability of simulations.

## Project Structure

```text
MiniOSSimulator/
│
└── src/
    ├── Main.java
    │
    ├── gui/
    │   ├── MainFrame.java
    │   ├── GanttChartPanel.java
    │   ├── SchedulingPanel.java
    │   ├── MemoryPanel.java
    │   ├── DeadlockPanel.java
    │   └── ThreadPanel.java
    │
    ├── model/
    │   ├── Process.java
    │   └── MemoryBlock.java
    │
    ├── scheduler/
    │   ├── Scheduler.java
    │   ├── FCFS.java
    │   ├── SJF.java
    │   ├── RoundRobin.java
    │   ├── GanttChartEntry.java
    │   └── SchedulingResult.java
    │
    ├── memory/
    │   ├── MemoryAllocator.java
    │   ├── FirstFit.java
    │   ├── BestFit.java
    │   └── WorstFit.java
    │
    ├── deadlock/
    │   ├── BankersAlgorithm.java
    │   └── DeadlockResult.java
    │
    └── threads/
        ├── SharedBuffer.java
        ├── Producer.java
        └── Consumer.java
```

## Getting Started

**Requirements:** Java Development Kit (JDK) 17 or later.

1. Clone the repository:

   ```bash
   git clone https://github.com/Shiv-Shriv/MiniOSSimulator.git
   ```

2. Open the project folder in Visual Studio Code or another Java IDE.

3. Run `src/Main.java` to launch the application.

## Learning Objectives

This project was developed to understand and practically implement:

- CPU process scheduling and performance metrics.
- Memory management and allocation strategies.
- Deadlock avoidance using resource allocation algorithms.
- Thread synchronization and inter-thread communication.
- Object-oriented software design and modular Java development.
- Building interactive desktop applications using Swing.

## Future Enhancements

- Additional CPU scheduling algorithms such as Priority Scheduling.
- More detailed memory fragmentation analysis.
- Enhanced visualizations and simulation controls.
- Persistent storage of simulation inputs and results.

## Author

**Shivansh Shrivastava**

B.Tech – Mathematics and Computing

[GitHub Profile](https://github.com/Shiv-Shriv)

---

*Developed as an academic project to demonstrate Operating System concepts through Java and Object-Oriented Programming.*