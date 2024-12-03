import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    int id; // Node ID
    boolean isCoordinator; // Is this node the coordinator?
    boolean hasStartedElection; // Prevent repeated election starts
    List<Node> nodes; // List of all nodes in the system

    public Node(int id) {
        this.id = id;
        this.isCoordinator = false;
        this.hasStartedElection = false;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void startElection() {
        if (hasStartedElection) {
            return; // Prevent duplicate election starts
        }

        System.out.println("Node " + id + " starts an election.");
        hasStartedElection = true;

        boolean higherNodeFound = false;

        for (Node node : nodes) {
            if (node.id > this.id) {
                System.out.println("Node " + id + " sends ELECTION to Node " + node.id);
                higherNodeFound = true;
                node.receiveElectionMessage(this);
            }
        }

        if (!higherNodeFound) {
            // No higher node found, become coordinator
            becomeCoordinator();
        }
    }

    public void receiveElectionMessage(Node sender) {
        System.out.println("Node " + id + " received ELECTION from Node " + sender.id);
        if (!hasStartedElection) {
            startElection(); // Start election if not already started
        }
    }

    public void becomeCoordinator() {
        isCoordinator = true;
        System.out.println("Node " + id + " becomes the COORDINATOR.");
        notifyAllNodes();
    }

    public void notifyAllNodes() {
        for (Node node : nodes) {
            if (node.id != this.id) {
                node.receiveCoordinatorMessage(this);
            }
        }

        // Reset election flags for all nodes
        for (Node node : nodes) {
            node.hasStartedElection = false;
        }
    }

    public void receiveCoordinatorMessage(Node coordinator) {
        System.out.println("Node " + id + " acknowledges Node " + coordinator.id + " as COORDINATOR.");
        isCoordinator = false; // Ensure only one coordinator exists
    }
}

public class Bully1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of nodes
        System.out.print("Enter the number of nodes: ");
        int n = scanner.nextInt();

        // Input: Node IDs
        List<Node> nodes = new ArrayList<>();
        System.out.println("Enter the IDs of the nodes: ");
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            nodes.add(new Node(id));
        }

        // Set nodes for each node
        for (Node node : nodes) {
            node.setNodes(nodes);
        }

        // Input: Node to start the election
        System.out.print("Enter the ID of the node to start the election: ");
        int initiatorId = scanner.nextInt();

        // Find the initiating node and start the election
        for (Node node : nodes) {
            if (node.id == initiatorId) {
                node.startElection();
                break;
            }
        }

        scanner.close();
    }
}
