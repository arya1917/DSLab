import java.util.*;

class Node {
    int id;
    boolean isCoordinator;
    List<Node> nodes;

    public Node(int id) {
        this.id = id;
        this.isCoordinator = false;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void handleElection() {
        System.out.println("Node " + id + " starts an election.");
        boolean higherNodeFound = false;

        for (Node node : nodes) {
            if (node.id > this.id) {
                System.out.println("Node " + id + " sends ELECTION to Node " + node.id);
                higherNodeFound = true;
                node.handleElection(); // Propagate election
            }
        }

        if (!higherNodeFound) {
            becomeCoordinator();
        }
    }

    public void becomeCoordinator() {
        isCoordinator = true;
        System.out.println("Node " + id + " becomes the COORDINATOR.");
        for (Node node : nodes) {
            if (node.id != this.id) {
                System.out.println("Node " + node.id + " acknowledges Node " + id + " as COORDINATOR.");
            }
        }
    }
}

public class Bully1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int n = scanner.nextInt();
        List<Node> nodes = new ArrayList<>();

        System.out.println("Enter the IDs of the nodes: ");
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(scanner.nextInt()));
        }

        // Set the list of nodes for each node
        for (Node node : nodes) {
            node.setNodes(nodes);
        }

        System.out.print("Enter the ID of the node to start the election: ");
        int initiatorId = scanner.nextInt();
        for (Node node : nodes) {
            if (node.id == initiatorId) {
                node.handleElection();
                break;
            }
        }
        scanner.close();
    }
}
