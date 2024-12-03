import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DistributedAlgorithms {
    static class Process {
        int id;
        int clock; // Clock time of the process
        boolean isActive;

        Process(int id, int clock) {
            this.id = id;
            this.clock = clock;
            this.isActive = true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.println("Enter the IDs and clock times for each process: ");
        for (int i = 0; i < n; i++) {
            System.out.print("Process ID: ");
            int id = scanner.nextInt();
            System.out.print("Process Clock Time: ");
            int clock = scanner.nextInt();
            processes.add(new Process(id, clock));
        }

        System.out.print("Enter the ID of the process initiating the election: ");
        int initiatorId = scanner.nextInt();

        Process leader = bullyElection(processes, initiatorId);
        if (leader != null) {
            System.out.println("\nLeader elected: Process " + leader.id);
            berkeleySynchronization(processes, leader);
        } else {
            System.out.println("Election failed. No leader could be elected.");
        }
    }

    // Bully Algorithm
    public static Process bullyElection(ArrayList<Process> processes, int initiatorId) {
        Process initiator = null;
        for (Process process : processes) {
            if (process.id == initiatorId) {
                initiator = process;
                break;
            }
        }

        if (initiator == null || !initiator.isActive) {
            System.out.println("Invalid or inactive initiator.");
            return null;
        }

        System.out.println("\nElection initiated by process: " + initiator.id);

        ArrayList<Process> higherProcesses = new ArrayList<>();
        for (Process process : processes) {
            if (process.id > initiator.id && process.isActive) {
                higherProcesses.add(process);
            }
        }

        if (higherProcesses.isEmpty()) {
            System.out.println("No higher processes found. Process " + initiator.id + " becomes the coordinator.");
            return initiator;
        } else {
            System.out.println("Process " + initiator.id + " sends election messages to: ");
            for (Process process : higherProcesses) {
                System.out.println("   Process " + process.id);
            }

            Process newCoordinator = Collections.max(higherProcesses, (p1, p2) -> Integer.compare(p1.id, p2.id));
            System.out.println("\nNew coordinator is process: " + newCoordinator.id);
            return newCoordinator;
        }
    }

    // Berkeley Algorithm
    public static void berkeleySynchronization(ArrayList<Process> processes, Process master) {
        System.out.println("\nClock synchronization initiated by master process ID: " + master.id);
        int totalDifference = 0;
        int numberOfSlaves = 0;

        // Calculate differences
        for (Process process : processes) {
            if (process.id != master.id && process.isActive) {
                int difference = process.clock - master.clock;
                totalDifference += difference;
                numberOfSlaves++;
                System.out.println("Process " + process.id + " reports difference: " + difference);
            }
        }

        // Average adjustment
        int averageAdjustment = totalDifference / (numberOfSlaves + 1);
        System.out.println("\nAverage adjustment calculated by master: " + averageAdjustment);

        // Synchronize clocks
        for (Process process : processes) {
            if (process.isActive) {
                process.clock += averageAdjustment;
                System.out.println("Process " + process.id + " clock adjusted to: " + process.clock);
            }
        }

        System.out.println("\nClock synchronization complete.");
    }
}
