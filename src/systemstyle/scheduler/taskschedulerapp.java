package systemstyle.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Interface for a generic Task Scheduler
interface TaskScheduler {
    void registerTask(Task task, ScheduleCondition condition);
    void runTasks(); // Executes all registered tasks
    void showTaskStatus(); // Show current state of registered tasks
}

// Abstract representation of a scheduling condition
abstract class ScheduleCondition {
    abstract void setSchedule();
    abstract void getSchedule();
}

// Simple enum for time units
enum TimeUnit {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

// Time-based scheduling condition
class TimeBasedScheduleCondition extends ScheduleCondition {
    private TimeUnit timeUnit;

    public TimeBasedScheduleCondition(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    void setSchedule() {
        System.out.println("Schedule set to run every " + timeUnit.toString().toLowerCase());
    }

    @Override
    void getSchedule() {
        System.out.println("Scheduled to run every " + timeUnit.toString().toLowerCase());
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}

// Abstract representation of a task
abstract class Task {
    abstract void execute();
    abstract String getName();
}

// Concrete task: HelloWorld
class HelloWorldTask extends Task {
    @Override
    void execute() {
        System.out.println("Hello world task");
    }

    @Override
    String getName() {
        return "HelloWorldTask";
    }
}

// Example: Task that simulates calling an external API
class APICallerTask extends Task {
    @Override
    void execute() {
        System.out.println("Calling external API...");
    }

    @Override
    String getName() {
        return "APICallerTask";
    }
}

// Example: Task that simulates a Python script
class PythonTask extends Task {
    @Override
    void execute() {
        System.out.println("Executing Python script...");
    }

    @Override
    String getName() {
        return "PythonTask";
    }
}

// Example: Task that simulates a Hadoop job
class HadoopTask extends Task {
    @Override
    void execute() {
        System.out.println("Running Hadoop job...");
    }

    @Override
    String getName() {
        return "HadoopTask";
    }
}

// Concrete implementation of TaskScheduler
class TimeBasedTaskScheduler implements TaskScheduler {
    private List<ScheduledTask> taskList = new ArrayList<>();

    @Override
    public void registerTask(Task task, ScheduleCondition condition) {
        condition.setSchedule();
        taskList.add(new ScheduledTask(task, condition));
    }

    @Override
    public void runTasks() {
        System.out.println("Running all scheduled tasks...");
        for (ScheduledTask scheduledTask : taskList) {
            try {
                scheduledTask.setStatus("RUNNING");
                scheduledTask.task.execute();
                scheduledTask.setLastRun(LocalDateTime.now());
                scheduledTask.setStatus("COMPLETED");
            } catch (Exception e) {
                scheduledTask.setStatus("FAILED");
                System.out.println("Task failed: " + scheduledTask.task.getName());
            }
        }
    }

    @Override
    public void showTaskStatus() {
        System.out.println("Current Task States:");
        for (ScheduledTask scheduledTask : taskList) {
            System.out.printf("Task: %s | Status: %s | Last Run: %s | Schedule: %s\n",
                    scheduledTask.task.getName(),
                    scheduledTask.getStatus(),
                    scheduledTask.getLastRun() != null ? scheduledTask.getLastRun().toString() : "Never",
                    ((TimeBasedScheduleCondition) scheduledTask.condition).getTimeUnit());
        }
    }

    // Inner helper class
    private static class ScheduledTask {
        Task task;
        ScheduleCondition condition;
        private LocalDateTime lastRun;
        private String status;

        ScheduledTask(Task task, ScheduleCondition condition) {
            this.task = task;
            this.condition = condition;
            this.status = "PENDING";
        }

        public void setLastRun(LocalDateTime time) {
            this.lastRun = time;
        }

        public LocalDateTime getLastRun() {
            return lastRun;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}

// Main class to demonstrate
public class taskschedulerapp {
    public static void main(String[] args) {
        Task task1 = new HelloWorldTask();
        Task task2 = new PythonTask();
        Task task3 = new APICallerTask();

        ScheduleCondition hourlyCondition = new TimeBasedScheduleCondition(TimeUnit.HOUR);
        ScheduleCondition dailyCondition = new TimeBasedScheduleCondition(TimeUnit.DAY);

        TaskScheduler scheduler = new TimeBasedTaskScheduler();

        scheduler.registerTask(task1, hourlyCondition);
        scheduler.registerTask(task2, dailyCondition);
        scheduler.registerTask(task3, hourlyCondition);

        scheduler.runTasks();
        scheduler.showTaskStatus();
    }
}
