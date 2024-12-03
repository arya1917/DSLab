import java.util.Scanner;

public class BullyAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] processIds = new int[n];
        boolean[] isAlive = new boolean[n];

        // Input process IDs and their status
        System.out.println("Enter the IDs of the processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " ID: ");
            processIds[i] = scanner.nextInt();
            isAlive[i] = true; // All processes are initially alive
        }

        // Input the failed process index
        System.out.print("Enter the index of the failed process (1 to " + n + ", or 0 if none): ");
        int failedIndex = scanner.nextInt() - 1;
        if (failedIndex >= 0) {
            isAlive[failedIndex] = false; // Mark the process as failed
        }

        // Input the initiator process
        System.out.print("Enter the initiator process index (1 to " + n + "): ");
        int initiatorIndex = scanner.nextInt() - 1;

        // Perform Bully Algorithm
        int leader = bullyAlgorithm(processIds, isAlive, initiatorIndex);
        System.out.println("\nLeader elected: Process with ID " + leader);
    }

    public static int bullyAlgorithm(int[] processIds, boolean[] isAlive, int initiatorIndex) {
        int n = processIds.length;
        int initiatorId = processIds[initiatorIndex];
        int leader = initiatorId;

        System.out.println("\nElection initiated by process ID: " + initiatorId);

        // Election process: Notify higher ID processes
        for (int i = initiatorIndex + 1; i < n; i++) {
            if (isAlive[i]) {
                System.out.println("Process ID " + processIds[i] + " responds to election message.");
                leader = processIds[i];
            }
        }

        // If no higher ID process responds, initiator becomes the leader
        if (leader == initiatorId) {
            System.out.println("No higher ID process responded. Initiator becomes the leader.");
        } else {
            System.out.println("Process ID " + leader + " becomes the leader.");
        }

        return leader;
    }
}
