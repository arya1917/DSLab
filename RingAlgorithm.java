import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RingAlgorithm {
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

        Collections.sort(processes, (a, b) -> a.id - b.id); // Sort by ID for the ring structure

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

        ringElection(processes, initiator);
    }

    public static void ringElection(ArrayList<Process> processes, Process initiator) {
        int n = processes.size();
        ArrayList<Integer> electionList = new ArrayList<>();

        int startIndex = processes.indexOf(initiator);
        int currentIndex = startIndex;

        System.out.println("Election initiated by process: " + initiator.id);

        do {
            Process currentProcess = processes.get(currentIndex);
            if (currentProcess.isActive) {
                electionList.add(currentProcess.id);
                System.out.println("Process " + currentProcess.id + " passes the message to process "
                        + processes.get((currentIndex + 1) % n).id);
            }
            currentIndex = (currentIndex + 1) % n;
        } while (currentIndex != startIndex);

        int newCoordinatorId = Collections.max(electionList);
        System.out.println("Election list: " + electionList);
        System.out.println("New coordinator is process: " + newCoordinatorId);

        // Announce the new coordinator
        currentIndex = startIndex;
        do {
            Process currentProcess = processes.get(currentIndex);
            if (currentProcess.isActive) {
                System.out.println("Process " + currentProcess.id + " announces the new coordinator as process "
                        + newCoordinatorId + " to process " + processes.get((currentIndex + 1) % n).id);
            }
            currentIndex = (currentIndex + 1) % n;
        } while (currentIndex != startIndex);
    }
}
