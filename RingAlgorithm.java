import java.util.Scanner;

public class RingAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] processIds = new int[n];
        boolean[] isActive = new boolean[n];

        // Input process IDs
        System.out.println("Enter the IDs of the processes: ");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " ID: ");
            processIds[i] = scanner.nextInt();
            isActive[i] = true; // All processes are active initially
        }

        // Input the initiator process
        System.out.print("Enter the initiator process index (1 to " + n + "): ");
        int initiatorIndex = scanner.nextInt() - 1;

        // Ring Election
        int currentLeader = ringElection(processIds, isActive, initiatorIndex);
        System.out.println("\nLeader elected: Process with ID " + currentLeader);
    }

    public static int ringElection(int[] processIds, boolean[] isActive, int initiatorIndex) {
        int n = processIds.length;
        int leader = -1;
        int currentIndex = initiatorIndex;
        int maxId = processIds[initiatorIndex];

        System.out.println("\nElection initiated by process ID: " + processIds[initiatorIndex]);

        // Passing the election message around the ring
        do {
            currentIndex = (currentIndex + 1) % n; // Move to the next process in the ring
            if (isActive[currentIndex]) {
                System.out.println("Process ID " + processIds[currentIndex] + " receives election message.");
                if (processIds[currentIndex] > maxId) {
                    maxId = processIds[currentIndex];
                }
            }
        } while (currentIndex != initiatorIndex);

        leader = maxId;
        return leader;
    }
}
