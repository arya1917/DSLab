import java.util.*;

class Node {
    int id;
    boolean isCoordinator;
    boolean hasStartedElection;
    List<Node> nodes;
    public Node(int id) {
        this.id = id;
        this.isCoordinator = false;
        this.hasStartedElection = false;
    }
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    public void handleElection() {
        if (hasStartedElection) {
            return; // Prevent duplicate election starts
        }
        hasStartedElection = true;
        System.out.println("Node " + id + " starts an election.");
        boolean higherNodeFound = false;  // Send ELECTION message to nodes with higher IDs
        for (Node node : nodes) {
            if (node.id > this.id) {
                System.out.println("Node " + id + " sends ELECTION to Node " + node.id);
                higherNodeFound = true;
                node.handleElection(); // Trigger election on higher nodes
            }
        }
        if (!higherNodeFound) {
            // If no higher node was found, this node becomes the coordinator
            becomeCoordinator();
        }
    }
    public void becomeCoordinator() {
        isCoordinator = true;
        System.out.println("Node " + id + " becomes the COORDINATOR.");
        for (Node node : nodes) {
            if (node.id != this.id) {
                node.isCoordinator = false; // Ensure only one coordinator exists
                System.out.println("Node " + node.id + " acknowledges Node " + id + " as COORDINATOR.");
            }
        }
        for (Node node : nodes) {
            node.hasStartedElection = false;
        }
    }
}
public class Bully1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);    // Read the number of nodes
        System.out.print("Enter the number of nodes: ");
        int n = scanner.nextInt();
        List<Node> nodes = new ArrayList<>();      // Read the IDs of the nodes
        System.out.println("Enter the IDs of the nodes: ");
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            nodes.add(new Node(id));
        }      // Set the nodes for each node
        for (Node node : nodes) {
            node.setNodes(nodes);
        }      // Start the election from a specified node
        System.out.print("Enter the ID of the node to start the election: ");
        int initiatorId = scanner.nextInt();
        for (Node node : nodes) {
            if (node.id == initiatorId) {
                node.handleElection(); // Handle the election process
                break;
            }
        }
        scanner.close();
    }
}