/**
 * PatientStack
 *
 * A LIFO (Last-In, First-Out) stack of ActionRecord objects.
 * Built as a singly-linked chain of internal nodes — no Java utility classes.
 *
 * Every action performed in the ER is pushed here.
 * When the user triggers undo, the most recent action is popped
 * and reversed by EmergencyRoom.
 *
 *   TOP → [ActionRecord 3] → [ActionRecord 2] → [ActionRecord 1] → null
 *            ↑ push / pop here
 *
 * Used by: EmergencyRoom.java
 */
public class PatientStack {


    // ─── Inner Node Class ──────────────────────────────────────────────────

    /**
     * A single link in the stack's internal chain.
     * Holds one ActionRecord and a pointer to the node below it.
     */
    private class StackNode {

        ActionRecord record;
        StackNode    next;

        StackNode(ActionRecord record) {
            this.record = record;
            this.next  = null;
        }
    }


    // ─── Fields ────────────────────────────────────────────────────────────

    /** The node at the top of the stack — most recently pushed action. */
    private StackNode top;

    /** Running count of actions itly stored on the stack. */
    private int size;


    // ─── Constructor ───────────────────────────────────────────────────────

    /**
     * Creates an empty undo stack.
     */
    public PatientStack() {
        this.top  = null;
        this.size = 0;
    }


    // ─── Core Operations ───────────────────────────────────────────────────

    /**
     * Pushes a new ActionRecord onto the TOP of the stack.
     * Called every time a significant ER action occurs.
     *
     * Algorithm:
     *   1. If record is null → print error and return (guard clause)
     *   2. Wrap record in a new StackNode
     *   3. newNode.below = top        (new node points down to old top)
     *   4. top = newNode              (top now points to the new node)
     *   5. Increment size
     *
     * @param record  The action that just occurred
     */
    public void push(ActionRecord record) {
        StackNode p = new StackNode(record);
        p.next = this.top;
        this.top = p;
        this.size++;
    }

    /**
     * Removes and returns the ActionRecord at the TOP of the stack.
     * Called when the user triggers an undo operation.
     *
     * Algorithm:
     *   1. If stack is empty → print "Nothing to undo." and return null
     *   2. Save reference to top.record (this is what you return)
     *   3. Advance top to top.below
     *   4. Decrement size
     *   5. Return the saved record
     *
     * @return  The most recent ActionRecord, or null if the stack is empty
     */
    public ActionRecord pop() {
        if (this.top == null) return null;
        ActionRecord p = this.top.record;
        this.top = this.top.next;
        this.size--;
        return p;
    }

    /**
     * Returns the ActionRecord at the TOP without removing it.
     * Useful for previewing what would be undone before confirming.
     *
     * @return  The top ActionRecord, or null if the stack is empty
     */
    public ActionRecord peek() {
        if (this.top == null) return null;
        ActionRecord p = this.top.record;
        return p;
    }


    // ─── State Checks ──────────────────────────────────────────────────────

    /**
     * Returns true if no actions are stored on the stack.
     */
    public boolean isEmpty() {
        if (this.top == null) return true;
        return false;
    }

    /**
     * Returns the number of actions itly stored on the stack.
     */
    public int size() {
        return this.size;
    }

    /**
     * Removes all records from the stack.
     * Used when a session ends and history should be wiped.
     *
     * Algorithm: top = null, size = 0
     * Note: no need to traverse — Java's garbage collector handles the rest.
     */
    public void clear() {
        this.top = null;
        this.size = 0;
    }


    // ─── Display ───────────────────────────────────────────────────────────

    /**
     * Prints the full action history from most recent (top) to oldest (bottom).
     *
     * Expected output format:
     *   ╔══════════════════════════════════════════╗
     *   ║         ACTION HISTORY — 3 action(s)     ║
     *   ╠══════════════════════════════════════════╣
     *   ║ 1 [MOST RECENT] [14:45:02] DISCHARGE → Maria Lopez (P002)
     *   ║ 2               [14:31:44] ADD_HISTORY → John Smith (P001) | "Allergy: Penicillin"
     *   ║ 3               [14:23:01] ADMIT → John Smith (P001)
     *   ╚══════════════════════════════════════════╝
     *
     * If the stack is empty, print:
     *   "No actions on record."
     *
     * Hint: traverse top → bottom using a StackNode pointer,
     *       call record.toString() for each line.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("No actions on record.");
            return;
        }

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     ACTION HISTORY — " + size + " action(s)          ║");
        System.out.println("╠══════════════════════════════════════════╣");

        StackNode it = top;
        int index = 1;

        while (it != null) {
            if (index == 1) {
                System.out.println("║ " + index + " [MOST RECENT] " + it.record.toString());
            } else {
                System.out.println("║ " + index + "               " + it.record.toString());
            }
            it = it.next;
            index++;
        }

        System.out.println("╚══════════════════════════════════════════╝");
    }
}
