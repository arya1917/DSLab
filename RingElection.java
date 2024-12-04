import java.util.Scanner;

class Process {
    int processId;
    boolean isLeader;

    Process(int id) {
        this.processId = id;
        this.isLeader = false;
    }
}

public class RingElection {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get the number of processes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        Process[] processes = new Process[numProcesses];

        // Step 2: Get the process IDs
        System.out.println("Enter the process IDs:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + (i + 1) + " ID: ");
            int id = scanner.nextInt();
            processes[i] = new Process(id);
        }

        // Step 3: Start the election
        int leaderId = processes[0].processId; // Initial leader is the first process

        System.out.println("\nStarting Ring Election...");

        for (int i = 1; i < processes.length; i++) {
            System.out.println("Process " + processes[i - 1].processId + " sends " + leaderId + " to Process " + processes[i].processId);

            // Compare current leader with the next process's ID
            if (processes[i].processId > leaderId) {
                leaderId = processes[i].processId; // Update leader ID if higher
            }
        }

        // Final step: Last process sends the leader ID back to the first process
        System.out.println("Process " + processes[processes.length - 1].processId + " sends " + leaderId + " to Process " + processes[0].processId);

        // Step 4: Announce the leader to all processes
        System.out.println("\nFinal leader is Process " + leaderId);
        for (Process process : processes) {
            if (process.processId == leaderId) {
                process.isLeader = true;
                System.out.println("Process " + process.processId + " is elected as the leader.");
            } else {
                System.out.println("Process " + process.processId + " acknowledges the leader is Process " + leaderId + ".");
            }
        }

        scanner.close();
    }
}