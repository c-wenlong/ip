package duke.kbot;

import java.util.ArrayList;
import java.util.Scanner;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * TaskFileManager class that encapsulates the saving of data onto the local
 * hard drive.
 *
 * @author: CHEN WENLONG
 * @version: CS2103T AY23/24 Semester 2
 */
public class TaskFileManager {

    /** Filepath of where to save to local disk for TASKS */
    private static final String FILEPATH = "src/main/java/duke/memory/output.txt";

    /** Storage format for date. */
    private static final DateTimeFormatter STORAGEFORMAT = DateTimeFormatter.ofPattern("d-M-yy");

    /**
     * Constructor for TaskFileManager.
     */
    public TaskFileManager() {
    }

    /**
     * Implementation to save tasks to file
     * 
     * @param tasks Tasks to be saved to the local drive.
     * @throws IOException Thrown when saving to file and file is not found.
     */
    public static void saveTasksToFile(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(FILEPATH);
        for (Task t : tasks) {
            String taskToStore = t.convertToStorageFormat();
            fw.write(taskToStore + "\n");
        }
        fw.close();
    }

    /**
     * Implementation to load tasks from local drive.
     * 
     * @throws FileNotFoundException Thrown when file cannot be found.
     * @throws IOException           Thrown when there is issue loading the file.
     * @return Tasks which are saved in the local drive as an ArrayList<Task>.
     */
    public static ArrayList<Task> loadTasksFromFile() throws FileNotFoundException, IOException {
        File file = new File(FILEPATH); // create a File for the given file path
        if (!file.exists()) { // Check if the file exists. If not, create a new file.
            file.createNewFile();
        }
        Scanner sc = new Scanner(file); // create a Scanner using the File as the source
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) { // TYPE | MARK | NAME \ BY \ FROM | TO
            String[] taskSegments = sc.nextLine().split(" \\| ", 2);
            String instruction = taskSegments[0];
            String parameter = taskSegments[1];
            tasks.add(loadTask(instruction, parameter));
        }
        sc.close();
        return tasks;
    }

    /**
     * Load the individual Task in the correct format using the instruction String
     * and the information stored in the instruction. Helper function for
     * loadTasksFromFile().
     * 
     * @param ins  String representing the different instructions.
     * @param info String representing the information stored in the Task.
     * @return Returns the Task that is loaded from the given instruction and info.
     */
    public static Task loadTask(String instruction, String parameter) {
        assert instruction != null && instruction.length() > 0 : "File may be corrupted: Task type is not found!";
        Task t = null;
        switch (instruction) {
            case "T":
                String[] todoInput = parameter.split(" \\| ", 2);
                boolean todoIsCompleted = (todoInput[0].trim() != "");
                String tName = todoInput[1];
                t = new ToDo(tName, todoIsCompleted);
                break;
            case "D":
                String[] deadlineInputs = parameter.split(" \\| ", 3);
                boolean deadlineIsCompleted = (deadlineInputs[0].trim() != "");
                String deadlineName = deadlineInputs[1];
                String deadlineDate = deadlineInputs[2];
                try {
                    LocalDate deadline = LocalDate.parse(deadlineDate, STORAGEFORMAT);
                    t = new Deadline(deadlineName, deadline, deadlineIsCompleted);
                } catch (DateTimeParseException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "E":
                String[] eventInputs = parameter.split(" \\| ", 4);
                boolean eventIsCompleted = (eventInputs[0].trim() != "");
                String eventName = eventInputs[1];
                String eventFrom = eventInputs[2];
                String eventTo = eventInputs[3];
                try {
                    LocalDate from = LocalDate.parse(eventFrom, STORAGEFORMAT);
                    LocalDate to = LocalDate.parse(eventTo, STORAGEFORMAT);
                    t = new Event(eventName, from, to, eventIsCompleted);
                } catch (DateTimeParseException f) {
                    System.out.println(f.getMessage());
                }
                break;
        }
        return t;
    }
}
