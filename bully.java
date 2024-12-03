import java.util.Scanner;

public class bully {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter number of processes: ");
        int n = sc.nextInt();
        int[] processId = new int[n];
        boolean[] alive = new boolean[n];
        System.out.print("Enter the process ids: ");
        for (int i = 0; i < n; i++) {
            processId[i] = sc.nextInt();
            alive[i] = true;
        }
        System.out.print("Enter the index of failed process: ");
        int failedProcess = sc.nextInt();
        if(failedIndex >= 0){
            alive[failedProcess] = false;
        }
}
