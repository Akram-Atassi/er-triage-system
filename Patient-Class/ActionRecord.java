import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ActionRecord
 *
 * Represents a single undoable operation performed in the ER system.
 * Every time a significant action occurs, one ActionRecord is created
 * and pushed onto the PatientStack so it can be reversed if needed.
 *
 * Supported action types:
 *   "ADMIT"          → a patient was registered and added to the system
 *   "DISCHARGE"      → a patient was removed from the system
 *   "ADD_HISTORY"    → a medical history entry was appended to a patient
 *   "DELETE_HISTORY" → a medical history entry was removed from a patient
 *
 * Used by: PatientStack.java, EmergencyRoom.java
 */
public class ActionRecord {


    // ─── Fields ────────────────────────────────────────────────────────────

    /** What type of action was performed. One of the four types listed above. */
    private String actionType;

    /** The patient this action was performed on. */
    private Patient patient;

    /**
     * Any extra data needed to reverse the action.
     *
     * Examples:
     *   ADD_HISTORY    → stores the entry string that was added
     *   DELETE_HISTORY → stores the entry string that was removed
     *   ADMIT          → null (reversing admit = just discharge the patient)
     *   DISCHARGE      → null (reversing discharge = re-admit, handled by ER)
     */
    private String detail;

    /** Timestamp of when this action occurred. Set automatically at creation. */
    private LocalDateTime timestamp;


    // ─── Constructor ───────────────────────────────────────────────────────

    /**
     * Creates a new ActionRecord capturing a system event.
     *
     * @param actionType  The type of action (e.g. "ADMIT", "ADD_HISTORY")
     * @param patient     The patient involved
     * @param detail      Extra data needed to reverse the action, or null
     */
    public ActionRecord(String actionType, Patient patient, String detail) {
        this.actionType = actionType;
        this.patient    = patient;
        this.detail     = detail;
        this.timestamp  = LocalDateTime.now();
    }


    // ─── Getters ───────────────────────────────────────────────────────────

    public String  getActionType() { return actionType; }
    public Patient getPatient()    { return patient;    }
    public String  getDetail()     { return detail;     }
    public LocalDateTime getTimestamp() { return timestamp; }


    // ─── Display ───────────────────────────────────────────────────────────

    /**
     * Returns a one-line summary of this action record.
     *
     * Example output:
     *   [14:23:01] ADMIT → [P001] John Smith
     *   [14:31:44] ADD_HISTORY → [P001] John Smith | "Allergy: Penicillin"
     *   [14:45:02] DISCHARGE → [P002] Maria Lopez
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String base = "[" + timestamp.format(formatter) + "] "
                + actionType + " → " + patient.getName()
                + " (" + patient.getId() + ")";

        if (detail != null) {
            return base + " | \"" + detail + "\"";
        }
        return base;
    }
}
