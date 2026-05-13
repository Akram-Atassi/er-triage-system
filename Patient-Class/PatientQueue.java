/**
 * PatientQueue
 *
 * A FIFO (First-In, First-Out) queue of Patient objects.
 * Built as a singly-linked chain of internal nodes — no Java utility classes.
 *
 * Patients enter at the REAR  (enqueue → new arrival joins the line)
 * Patients leave from the FRONT (dequeue → next patient gets called)
 *
 *   FRONT → [P001] → [P002] → [P003] → REAR
 *             ↑                            ↑
 *          dequeue()                   enqueue()
 *
 * Used by: EmergencyRoom.java
 */
public class PatientQueue {


    // ─── Inner Node Class ──────────────────────────────────────────────────

    /**
     * A single link in the queue's internal chain.
     * Holds one Patient and a pointer to the node behind it in line.
     */
    private class QueueNode {

        Patient   patient;
        QueueNode next;

        QueueNode(Patient patient) {
            this.patient = patient;
            this.next    = null;
        }
    }


    // ─── Fields ────────────────────────────────────────────────────────────

    /** The node at the front of the line — next to be dequeued. */
    private QueueNode front;

    /** The node at the back of the line — last to have been enqueued. */
    private QueueNode back;

    /** Running count of patients currently waiting. */
    private int size;


    // ─── Constructor ───────────────────────────────────────────────────────

    /**
     * Creates an empty waiting room queue.
     */
    public PatientQueue() {
        this.front = null;
        this.back  = null;
        this.size  = 0;
    }


    // ─── Core Operations ───────────────────────────────────────────────────

    /**
     * Adds a patient to the REAR of the queue (new arrival joins the line).
     *
     * Algorithm:
     *   1. If patient is null → print error and return (guard clause)
     *   2. Wrap patient in a new QueueNode
     *   3. If the queue is empty → front AND rear both point to the new node
     *   4. Otherwise → rear.next = newNode, then move rear forward to newNode
     *   5. Increment size
     *
     * @param patient  The arriving patient to add to the queue
     */
    public void enqueue(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient value is null");
            
        }
        QueueNode p = new QueueNode(patient);
        if (this.front == null) {
            this.front = p;
            this.back = p;
        } else {
            this.back.next = p;
            this.back = p;
        }
        this.size++;
    }

    /**
     * Removes and returns the patient at the FRONT of the queue.
     * This is called when a doctor is ready for the next patient.
     *
     * Algorithm:
     *   1. If queue is empty → print "Waiting room is empty." and return null
     *   2. Save reference to front.patient (this is what you will return)
     *   3. Advance front to front.next
     *   4. If front is now null → the queue is empty, so rear must also be null
     *   5. Decrement size
     *   6. Return the saved patient
     *
     * @return  The next patient in line, or null if the queue is empty
     */
    public Patient dequeue() {
        if (this.front == null) {
            throw new IllegalArgumentException("Line is empty");
            return null;
        }
        Patient p = this.front.patient;
        this.front = this.front.next;
        this.size--;
        return p;
    }

    /**
     * Returns the patient at the FRONT without removing them.
     * Useful for displaying who is next without actually calling them.
     *
     * @return  The front patient, or null if the queue is empty
     */
    public Patient peek() {
        if (this.front == null) return "No patients in queue";
        return this.front.patient;
    }


    // ─── State Checks ──────────────────────────────────────────────────────

    /**
     * Returns true if no patients are currently waiting.
     */
    public boolean isEmpty() {
        if (this.front == null) return true;
        return false;
    }

    /**
     * Returns the number of patients currently in the queue.
     */
    public int size() {
        return this.size;
    }


    // ─── Display ───────────────────────────────────────────────────────────

    /**
     * Prints every patient in the queue from front to rear, numbered by position.
     *
     * Expected output format:
     *   ╔══════════════════════════════════════════╗
     *   ║       WAITING ROOM — 3 patient(s)        ║
     *   ╠══════════════════════════════════════════╣
     *   ║ 1 [NEXT] [P001] John Smith  | P1 - Emergency | 14:23:01
     *   ║ 2        [P002] Maria Lopez | P2 - Urgent    | 14:25:17
     *   ║ 3        [P003] James Wu    | P3 - Delayed   | 14:31:44
     *   ╚══════════════════════════════════════════╝
     *
     * If the queue is empty, print:
     *   "Waiting room is empty."
     *
     * Hint: traverse front → rear using a QueueNode pointer,
     *       call patient.toString() for each line.
     */
    public void display() {
        if (this.front == null) {
            throw new IllegalArgumentException("Waiting room is empty");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║           WAITING ROOM — " + size + " patient(s)           ║");
        System.out.println("╠══════════════════════════════════════════════════╣");

        QueueNode it = this.front;
        int count = 1;
        while (it != null) {
            if (count == 1) {
                System.out.println("║ " + count + " [NEXT] " + it.patient.toString() + " ║");
            } else {
                System.out.println("║ " + count + "        " + it.patient.toString() + " ║");
            }
            it = it.next;
            count++;
        }

        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
