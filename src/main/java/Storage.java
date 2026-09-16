package storage;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;


import java.io.BufferedReader;
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

    public static int load(Task[] tasks) {
        int taskCount = 0;

        if (!Files.exists(FILE_PATH)) {
            return 0;
        }

        try {
            BufferedReader reader = Files.newBufferedReader(FILE_PATH);

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                String type = parts[0];
                String status = parts[1];

                if (type.equals("T")) {
                    String description = parts[2];

                    tasks[taskCount] = new Task(description);

                } else if (type.equals("D")) {
                    String description = parts[2];
                    String by = parts[3];

                    tasks[taskCount] = new Deadline(description, by);

                } else if (type.equals("E")) {
                    String description = parts[2];
                    String from = parts[3];
                    String to = parts[4];

                    tasks[taskCount] = new Event(description, from, to);
                }

                if (status.equals("1")) {
                    tasks[taskCount].markAsDone();
                }

                taskCount++;
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }

        return taskCount;
    }
}
