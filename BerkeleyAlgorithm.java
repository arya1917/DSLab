import java.util.Scanner;

public class BerkeleyAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes (including the server): ");
        int n = scanner.nextInt();

        int[] clocks = new int[n];

        // Input the clock times of all processes
        System.out.println("Enter the clock times of the processes (in seconds):");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " clock time: ");
            clocks[i] = scanner.nextInt();
        }

        // Choose a server process
        System.out.print("Enter the server process index (1 to " + n + "): ");
        int serverIndex = scanner.nextInt() - 1;

        // Perform Berkeley Algorithm
        berkeleyAlgorithm(clocks, serverIndex);

        // Display synchronized clock times
        System.out.println("\nSynchronized clock times:");
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + (i + 1) + " clock: " + clocks[i] + " seconds");
        }
    }

    public static void berkeleyAlgorithm(int[] clocks, int serverIndex) {
        int n = clocks.length;
        int serverTime = clocks[serverIndex];
        int totalDrift = 0;
        int activeProcesses = 0;

        System.out.println("\nServer clock time: " + serverTime + " seconds");

        // Calculate clock differences and collect drifts
        System.out.println("Calculating drifts:");
        for (int i = 0; i < n; i++) {
            if (i != serverIndex) { // All processes except the server
                int drift = clocks[i] - serverTime;
                totalDrift += drift;
                activeProcesses++;
                System.out.println("Process " + (i + 1) + " drift: " + drift + " seconds");
            }
        }

        // Compute average drift
        int averageDrift = totalDrift / (activeProcesses + 1);
        System.out.println("\nAverage drift: " + averageDrift + " seconds");

        // Adjust all clocks
        for (int i = 0; i < n; i++) {
            clocks[i] -= averageDrift;
        }
    }
}
