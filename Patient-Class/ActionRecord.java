import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ActionRecord
 *
 * Represents a single ER action that can be stored on the undo stack.
 * Common action types: "ADMIT", "DISCHARGE", "ADD_HISTORY", "DELETE_HISTORY"
 *
 * Used by: PatientStack.java
 */
public class ActionRecord {

    private String        actionType;
    private Patient       patient;
    private LocalDateTime timestamp;
    private String        detail;


    public ActionRecord(String actionType, Patient patient) {
        this.actionType = actionType;
        this.patient    = patient;
        this.timestamp  = LocalDateTime.now();
        this.detail     = null;
    }

    public ActionRecord(String actionType, Patient patient, String detail) {
        this.actionType = actionType;
        this.patient    = patient;
        this.timestamp  = LocalDateTime.now();
        this.detail     = detail;
    }


    public String        getActionType() { return actionType; }
    public Patient       getPatient()    { return patient; }
    public LocalDateTime getTimestamp()  { return timestamp; }
    public String        getDetail()     { return detail; }


    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        String base = "[" + timestamp.format(fmt) + "] "
                    + actionType + " → "
                    + patient.getName() + " (" + patient.getId() + ")";
        if (detail != null && !detail.isBlank()) {
            return base + " | \"" + detail + "\"";
        }
        return base;
    }
}
