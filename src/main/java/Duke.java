import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    enum SPECIALTASK {
        bye,
        done,
        list,
        todo,
        deadline,
        event,
        delete
    }
    public static void main(String[] args) throws DukeException {
        String border = "____________________________________________________________";
        Printer printer = new Printer(border);
        printer.PrintIntro();
        Storage storage = new Storage();

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals(SPECIALTASK.bye.name())) {
            String[] splitInput = input.split(" ", 2);
            if (splitInput[0].equals(SPECIALTASK.done.name())) {
                int index = Integer.parseInt(splitInput[1]) - 1;
                String returnString = tasks.get(index).markDone();
                printer.PrintMessage(returnString);
            } else if (input.equals(SPECIALTASK.list.name())) {
                printer.PrintList(tasks);
            } else if (splitInput[0].equals(SPECIALTASK.todo.name())) {
                try {
                    if (splitInput.length < 2 || splitInput[1].equals("") || splitInput[1].equals(" ")) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(splitInput[1]));
                    printer.PrintSpecialTasks(tasks.get(tasks.size() - 1).toString(), tasks.size());
                } catch (DukeException e) {
                    printer.PrintMessage(e.getMessage());
                }
            } else if (splitInput[0].equals(SPECIALTASK.deadline.name())) {
                try {
                    if (splitInput.length < 2) {
                        throw new DukeException("The description of a deadline cannot be empty.");
                    }
                    String[] furtherSplits = splitInput[1].split("/by", 2);
                    if (furtherSplits.length < 2 || furtherSplits[0].equals("")) {
                        throw new DukeException("The description  of a deadline cannot be empty.\n" +
                                "Don't forget to use /by to indicate the deadline.");
                    } else if (furtherSplits[1].equals("") || furtherSplits[1].equals(" ")) {
                        throw new DukeException("Deadline must come with a input date/time for the deadline.");
                    }
                    tasks.add(new Deadline(furtherSplits[0], furtherSplits[1]));
                    printer.PrintSpecialTasks(tasks.get(tasks.size() - 1).toString(), tasks.size());
                } catch (DukeException e) {
                    printer.PrintMessage(e.getMessage());
                }
            } else if (splitInput[0].equals(SPECIALTASK.event.name())) {
                try {
                    if (splitInput.length < 2) {
                        throw new DukeException("The description of a event cannot be empty.");
                    }
                    String[] furtherSplits = splitInput[1].split("/at", 2);
                    if (furtherSplits.length < 2 || furtherSplits[0].equals("")) {
                        throw new DukeException("The description of a event cannot be empty.\n" +
                                "Don't forget to use /at to indicate the event time.");
                    } else if (furtherSplits[1].equals("") || furtherSplits[1].equals(" ")) {
                        throw new DukeException("Event must come with a event date/time.");
                    }
                    tasks.add(new Event(furtherSplits[0], furtherSplits[1]));
                    printer.PrintSpecialTasks(tasks.get(tasks.size() - 1).toString(), tasks.size());
                } catch (DukeException e) {
                    printer.PrintMessage(e.getMessage());
                }
            } else if (splitInput[0].equals(SPECIALTASK.delete.name())) {
                try {
                    if (splitInput.length < 2) {
                        throw new DukeException("We don't know what to delete!");
                    }
                    int index = Integer.parseInt(splitInput[1]) - 1;
                    if (index >= tasks.size() || index <= 0) {
                        throw new DukeException("Task number does not exist!");
                    }
                    Task toDelete = tasks.get(index);
                    tasks.remove(index);
                    printer.PrintDelete(toDelete.toString(), tasks.size());
                } catch (DukeException e1) {
                    printer.PrintMessage(e1.getMessage());
                } catch (NumberFormatException e2) {
                    DukeException newException = new DukeException("Please input a number!");
                    printer.PrintMessage(newException.getMessage());
                }
            } else {
                try {
                    if (input.equals("blah")) {
                        throw new DukeException("I'm sorry, but I don't know what that means :-(");
                    }
                    tasks.add(new Task(input));
                    printer.PrintMessage(String.format("added: %s", input));
                } catch (DukeException e) {
                    printer.PrintMessage(e.getMessage());
                }
            }
            System.out.println(tasks);
            storage.Save(tasks);
            input = sc.nextLine();
        }
        printer.PrintMessage("Bye. Hope to see you again soon!");
    }
}
