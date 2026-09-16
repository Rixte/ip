package storage;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "samsquare.txt");

    public static void save(Task[] tasks, int taskCount) {
        try {
            Files.createDirectories(FILE_PATH.getParent());

            BufferedWriter writer = Files.newBufferedWriter(FILE_PATH);

            for (int i = 0; i < taskCount; i++) {
                Task task = tasks[i];

                String status = task.isDone() ? "1" : "0";

                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;

                    writer.write("D | " + status + " | "
                            + deadline.getDescription() + " | "
                            + deadline.getBy());
                } else if (task instanceof Event) {
                    Event event = (Event) task;

                    writer.write("E | " + status + " | "
                            + event.getDescription() + " | "
                            + event.getFrom() + " | "
                            + event.getTo());
                } else {
                    writer.write("T | " + status + " | "
                            + task.getDescription());
                }

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}