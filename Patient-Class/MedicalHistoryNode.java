/**
 * MedicalHistoryNode
 *
 * A single node in a patient's singly-linked medical history list.
 * Each node stores one medical event (visit record, diagnosis,
 * medication, allergy, etc.) and a pointer to the next node.
 *
 * Used by: Patient.java
 */
public class MedicalHistoryNode {

    // ─── Fields ────────────────────────────────────────────────────────────

    /** The text content of this medical event. */
    String entry;

    /** Pointer to the next node in the list (null if this is the tail). */
    MedicalHistoryNode next;


    // ─── Constructor ───────────────────────────────────────────────────────

    /**
     * Creates a new medical history node.
     *
     * @param entry  The medical event description (e.g. "Allergy: Penicillin")
     * @param next   The next node in the list, or null if this is the last one
     */
    public MedicalHistoryNode(String entry, MedicalHistoryNode next) {
        this.entry = entry;
        this.next = next;
    }


    // ─── Getters ───────────────────────────────────────────────────────────

    /**
     * Returns the text content of this node.
     */
    public String getEntry() {
        return this.entry;
    }

    /**
     * Returns the next node in the list.
     */
    public MedicalHistoryNode getNext() {
        return this.next;
    }

    /**
     * Sets the next pointer. Used during list insertion and deletion.
     */
    public void setNext(MedicalHistoryNode next) {
        this.next = next;
    }


    // ─── Display ───────────────────────────────────────────────────────────

    /**
     * Returns a string representation of this node's content.
     * Example output:  "Allergy: Penicillin"
     */
    @Override
    public String toString() {
        return this.entry;
    }
}
