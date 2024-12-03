class Process {
    int processId;
    boolean isLeader;

    Process(int id) {
        this.processId = id;
        this.isLeader = false;
    }
}

public class Ring {

    public static void main(String[] args) {
        // Step 1: Create processes
        Process[] processes = {new Process(1), new Process(3), new Process(5), new Process(2), new Process(4)};

        // Step 2: Start the election
        int leaderId = processes[0].processId; // Initial leader is the first process

        System.out.println("Starting Ring Election...");

        for (int i = 1; i < processes.length; i++) {
            System.out.println("Process " + processes[i - 1].processId + " sends " + leaderId + " to Process " + processes[i].processId);

            // Compare current leader with the next process's ID
            if (processes[i].processId > leaderId) {
                leaderId = processes[i].processId; // Update leader ID if higher
            }
        }

        // Step 3: Announce leader to all processes
        System.out.println("Final leader is Process " + leaderId);
        for (Process process : processes) {
            if (process.processId == leaderId) {
                process.isLeader = true;
                System.out.println("Process " + process.processId + " is elected as the leader.");
            } else {
                System.out.println("Process " + process.processId + " acknowledges the leader is Process " + leaderId + ".");
            }
        }
    }
}