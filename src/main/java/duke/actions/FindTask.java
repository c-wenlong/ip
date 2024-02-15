package duke.actions;

import java.util.ArrayList;

import duke.kbot.TaskManager;
import duke.tasks.Task;

/**
 * An find key command that finds you tasks that contains the key.
 * 
 * @author: CHEN WENLONG
 * @version: CS2103T AY23/24 Semester 2
 */
public class FindTask extends Command {
    /** String key that stores what we will be searching for */
    private String key;

    /**
     * Constructor for FindTask.
     * 
     * @param key Key what we will be searching for.
     */
    public FindTask(String key) {
        this.key = key;
    }

    /**
     * Executes the finding of the class
     * 
     * @return Returns the Tasks that contain the key.
     */
    @Override
    public String execute() {
        ArrayList<Task> tasksFound = new ArrayList<>();
        for (Task t : TaskManager.getTasks()) {
            if (t.containsWord(key)) {
                tasksFound.add(t);
            }
        }

        StringBuilder response = new StringBuilder();
        response.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasksFound.size(); i++) {
            response.append((i + 1))
                    .append(". ")
                    .append(tasksFound.get(i))
                    .append("\n");
        }
        return response.toString();
    }
}