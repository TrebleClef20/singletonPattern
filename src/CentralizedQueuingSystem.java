import java.util.LinkedList;
import java.util.List;

public class CentralizedQueuingSystem {

    private static CentralizedQueuingSystem instance;

    // Attributes related to helpDesk
    private final int helpDeskCount = 3;
    private String[] helpDesks = new String[helpDeskCount];
    private boolean isFull;

    // Attributes related to Queue
    private List<String> queue = new LinkedList<>();
    private int nextQueueNumber = 1;

    // this prevents instantation outside of the class
    private CentralizedQueuingSystem() {
        for (int i = 0; i < helpDesks.length; i++) {
            this.helpDesks[i] = "";
        }
    }

    // get application instance
    public static synchronized CentralizedQueuingSystem getInstance() {
        if (instance == null)
            instance = new CentralizedQueuingSystem();
        return instance;
    }

    public synchronized int obtainQueueNumber() {
        this.queue.add(nextQueueNumber + "");
        return nextQueueNumber++;
    }

    public synchronized void automateAssign() {
        if (isFull)
            return;

        List<Integer> availables = checkAvailableDesks();

        boolean queueSufficient = true;

        for (Integer i : availables) {
            // last iteration provides
            // false if queue is not enough to fill available slots
            // true if its sufficient and thus slots are full now
            queueSufficient = assignToDesk(i);
        }

        isFull = queueSufficient;
    }

    public synchronized boolean assignToDesk(int deskIndex) {
        if (!queue.isEmpty()) {
            this.helpDesks[deskIndex] = this.queue.remove(0);
            return true;
        }

        // return false if queue already empty
        return false;
    }

    public synchronized List<Integer> checkAvailableDesks() {

        // used List for future integration of many more Desks
        List<Integer> availables = new LinkedList<>();
        for (int i = 0; i < helpDesks.length; i++) {

            // if desk is empty, add the deskNumber to availables
            if (helpDesks[i].equals("")) {
                availables.add(i);
            }
        }
        return availables;
    }

    public synchronized boolean resetNumber(int queueNumber) {

        String numStr = queueNumber + "";

        // check if the number is in the Help Desks
        for (int i = 0; i < helpDesks.length; i++) {
            String numberInDesk = helpDesks[i];

            if (numberInDesk.equals(numStr)) {
                helpDesks[i] = "";
                isFull = false;
                return true;
            }
        }

        // check if the number is in the Queue
        if (!queue.isEmpty()) {
            this.queue.remove(numStr);
            return true;
        }

        // if not found
        return false;
    }

    public synchronized String displayStatus() {
        String forreturn = "\nCentralized Queuing System for Pag-ibig Office";
        forreturn += "\n\n1st Help Desk: " + helpDesks[0];
        forreturn += "\n\n2nd Help Desk: " + helpDesks[1];
        forreturn += "\n\n3rd Help Desk: " + helpDesks[2];
        forreturn += "\n\n          Queue: ";

        for (String strNum : queue) {
            forreturn += strNum + " ";
        }

        return forreturn;
    }
}