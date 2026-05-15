import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Patient
 *
 * Represents a single patient in the ER system.
 * Owns a singly-linked medical history list built from MedicalHistoryNode.
 *
 * Used by: EmergencyRoom, Queue, HashT_LookUp
 */
public class Patient {

    // ─── Static ID counter and Patient Database ─────────────────────────────

    /** Tracks how many patients are CURRENTLY in the ER. */
    private static int idCounter = 0;

    /** Hash table database storing patient records. */
    /** Key: "name|age", Value: patient ID (e.g., "P001") */
    private static HashT_LookUp<String, String> patientDatabase = new HashT_LookUp<>(16);


    // ─── Fields ───────────────────────────────────────────────────────────

    private String id;               // Unique ID, format: P001, P002, ...
    private String name;             // Full name
    private int    age;              // Age in years
    private int    severity;         // Triage level: 1 (Emergency) to 4 (Expectant)
    private String symptoms;         // Free-text symptom description
    private LocalDateTime arrivalTime;  // Set at construction time

    /** Head of the singly-linked medical history list. Null when empty. */
    private MedicalHistoryNode historyHead;


    // ─── Constructor ───────────────────────────────────────────────────────

    /**
     * Creates a new Patient and auto-assigns a unique ID.
     * @param name      Full name (must not be blank)
     * @param age       Age in years (must be > 0)
     * @param severity  Triage level 1–4 (throws if out of range)
     * @param symptoms  Description of presenting symptoms
     */
    public Patient(String name, int age, int severity, String symptoms) {
        // Sanity checks, Name must be non-blank, Age must be positive, Severity must be 1-4.
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name must not be blank.");
            }
            if (age <= 0) {
                throw new IllegalArgumentException("Age must be a positive integer.");
            }
            if (severity < 1 || severity > 4) {
                throw new IllegalArgumentException("Severity must be between 1 and 4.");
            }

        // Sanity checks completed, assign fields.
        this.id = generateId(name, age);
        this.name = name;
        this.age = age;
        this.severity = severity;
        this.symptoms = symptoms;
        this.arrivalTime = LocalDateTime.now();
        this.historyHead = null;
    }

    /// ─── ID Generation ────────────────────────────────────────────────────

    /**
     * Generates or retrieves a patient ID.
     * 
     * Algorithm:
     *   1. Create a patient key using "name|age" combination
     *   2. Search the hash table for existing patient record
     *   3. If found → return their existing ID (recurring patient)
     *   4. If not found → generate new ID using current idCounter,
     *                     insert into hash table, and return new ID
     *
     * @param name  Patient's full name
     * @param age   Patient's age
     * @return      Patient ID in format "P###"
     */
    private static String generateId(String name, int age) {
        String patientKey = name + "|" + age;
        
        // Search hash table for existing patient
        String existingId = patientDatabase.search(patientKey);
        
        if (existingId != null) {
            // Patient already exists in database, return their ID
            return existingId;
        }
        
        // New patient - generate new ID
        String newId = String.format("P%03d", idCounter);
        idCounter++;

        // Insert into hash table
        patientDatabase.insert(patientKey, newId);

        return newId;
    }


    // ─── Getters ──────────────────────────────────────────────────────────

    public String getId()           { return this.id; }
    public String getName()         { return this.name; }
    public int    getAge()          { return this.age; }
    public int    getSeverity()     { return this.severity; }
    public String getSymptoms()     { return this.symptoms; }
    public LocalDateTime getArrivalTime() { return this.arrivalTime; }


    // ─── Severity label ────────────────────────────────────────────────────

    /**
     * Returns the human-readable triage name for this patient's severity level.
     *
     * 1 → "P1 - Emergency/Immediate"
     * 2 → "P2 - Urgent"
     * 3 → "P3 - Delayed"
     * 4 → "P4 - Expectant"
     * anything else → "Unknown"
        */

    public String getSeverityLabel() {
        int severity = getSeverity();
        if(severity == 1){
            return "P1 - Emergency/Immediate";
        }
        else if(severity == 2){
            return "P2 - Urgent";
        }
        else if(severity == 3){
            return "P3 - Delayed";
        }
        else if(severity == 4){
            return "P4 - Expectant";
        }

        return "Unknown";
    }


    // ─── Medical history (linked list operations) ──────────────────────────

    /**
     * Appends a new medical event to the END of the history list.
     *
     * Algorithm:
     *   1. Create a new MedicalHistoryNode with next = null
     *   2. If historyHead is null → this node becomes the head
     *   3. Otherwise → traverse to the tail (while node.next != null)
     *      and set tail.next = newNode
     *
     * @param entry  Description of the medical event (skip if null or blank)
     */
    public void addMedicalHistory(String entry) {
        if (entry == null || entry.isBlank()) {
            return;
        }

        if (historyHead == null) {
            historyHead = new MedicalHistoryNode(entry, null);
            return;
        }

        MedicalHistoryNode current = historyHead;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new MedicalHistoryNode(entry, null);
    }

    /**
     * Prints the full medical history, numbered from oldest to newest.
     *
     * Expected output format:
     *   === Medical History for [name] ===
     *   1. Allergy: Penicillin
     *   2. Visit 2023-05-15: Chest pain
     *   3. Medication: Lisinopril 10mg
     *
     * If historyHead is null, print:
     *   "No medical history on record."
        */

    public void printMedicalHistory() {

        if (historyHead == null) {
            System.out.println("No medical history on record.");
            return;
        }

        System.out.println("=== Medical History for " + name + " ===");
        MedicalHistoryNode current = historyHead;
        int counter = 1;
        while (current != null) {
            System.out.println(counter + ") " + current.entry);
            current = current.next;
            counter++;
        }
    }

    /**
     * Removes the medical history entry at the given index (0-based).
     *
     * Cases to handle:
     *   - index == 0  → update historyHead = historyHead.next
     *   - index > 0   → traverse to node at (index - 1), then
     *                   prev.next = prev.next.next
     *   - out of range or empty list → print error, do nothing
     *
     * @param index  Zero-based position of the entry to remove
     */
      public void deleteHistoryEntry(int index) {
        if (historyHead == null) {
            throw new IllegalArgumentException("The list is empty");
        }
        if (index == 0) {
            historyHead = historyHead.next;
            return;
        }

        int idx = 0;
        MedicalHistoryNode it = historyHead;
        while (idx < index - 1) {
            it = it.next;
            idx++;
            if (it == null) {
                throw new IllegalArgumentException("The index is out of range");
            }
        }
        if (it.next == null) {
            throw new IllegalArgumentException("The index is out of range");
        }
        it.next = it.next.next;
    }

    /**
     * Returns the number of entries in the medical history.
     *
     * Hint: traverse the list and count nodes. Return 0 if list is empty.
     */
    public int historySize() {
        int size = 0;
        MedicalHistoryNode it = historyHead;
        while (it != null) {
            size++;
            it = it.next;
        }
        return size;
    }

    /**
     * Returns the head node of the medical history list.
     * Used externally when another class needs to traverse the history directly.
     */
    public MedicalHistoryNode getHistoryHead() {
        return historyHead;
    }


    // ─── Display ──────────────────────────────────────────────────────────

    /**
     * Returns a compact one-line summary of this patient.
     *
     * Example:
     *   "[P001] John Smith | Age: 45 | P2 - Urgent | Arrived: 14:23:01"
     *
     * Hint: format arrivalTime with:
     *   DateTimeFormatter.ofPattern("HH:mm:ss")
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + id + "] " + name + " | Age: " + age + " | Severity: " + severity + " | Symptoms: " + symptoms + " | Arrived: " + arrivalTime.format(formatter);
    }

    public void printRecord() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          PATIENT RECORD              ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID       : " + id);
        System.out.println("║ Name     : " + name);
        System.out.println("║ Age      : " + age);
        System.out.println("║ Severity : " + severity);
        System.out.println("║ Symptoms : " + symptoms);
        System.out.println("║ Arrived  : " + arrivalTime.format(formatter));
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Prints a full multi-line patient record to stdout.
     *
     * Example output:
     *   ╔══════════════════════════════╗
     *   ║  PATIENT RECORD              ║
     *   ╠══════════════════════════════╣
     *   ║  ID       : P001             ║
     *   ║  Name     : John Smith       ║
     *   ║  Age      : 45               ║
     *   ║  Severity : P2 - Urgent      ║
     *   ║  Symptoms : Chest pain       ║
     *   ║  Arrived  : 2024-01-15 14:23 ║
     *   ╚══════════════════════════════╝
     *
     * This is called when a doctor "calls" a patient from the queue.
     */
}
