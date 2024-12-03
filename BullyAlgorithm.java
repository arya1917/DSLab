import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BullyAlgorithm {
    static class Process {
        int id;
        boolean isActive;

        Process(int id) {
            this.id = id;
            this.isActive = true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.println("Enter the IDs of the processes: ");
        for (int i = 0; i < n; i++) {
            processes.add(new Process(scanner.nextInt()));
        }

        System.out.print("Enter the ID of the process initiating the election: ");
        int initiatorId = scanner.nextInt();

        Process initiator = null;
        for (Process process : processes) {
            if (process.id == initiatorId) {
                initiator = process;
                break;
            }
        }

        if (initiator == null) {
            System.out.println("Invalid initiator ID.");
            return;
        }

        bullyElection(processes, initiator);
    }

    public static void bullyElection(ArrayList<Process> processes, Process initiator) {
        System.out.println("Election initiated by process: " + initiator.id);

        ArrayList<Integer> higherProcesses = new ArrayList<>();
        for (Process process : processes) {
            if (process.id > initiator.id && process.isActive) {
                higherProcesses.add(process.id);
            }
        }

        if (higherProcesses.isEmpty()) {
            System.out.println("No higher processes found. Process " + initiator.id + " becomes the coordinator.");
        } else {
            System.out.println("Process " + initiator.id + " sends election messages to: " + higherProcesses);

            int newCoordinatorId = Collections.max(higherProcesses);
            System.out.println("New coordinator is process: " + newCoordinatorId);

            for (Process process : processes) {
                if (process.isActive) {
                    System.out.println("Process " + newCoordinatorId + " announces itself as coordinator to process "
                            + process.id);
                }
            }
        }
    }
}
