# Emergency Room Triage & Patient Management System

A command-line simulation of a hospital emergency room that manages the complete patient lifecycle—from admission through discharge. This system demonstrates real-world application of fundamental data structures (arrays, queues, linked lists, stacks, hash tables, and binary search trees) in a high-stakes operational environment.

## Overview

**What This System Does**

This ER management system handles:
- Patient registration and admission
- FIFO waiting room queue management
- Instant patient lookup by ID
- Complete medical history tracking
- Treatment logging with undo functionality
- Sorted reporting (by name, arrival time, severity)
- Discharge management

**Why It Matters**

Every hospital faces the same operational challenges:
- Patients arrive unpredictably with varying urgency levels
- Medical records must be retrieved instantly during emergencies
- Mistakes (wrong patient discharged, wrong medication) must be undone immediately
- Reports must be generated quickly without re-sorting entire datasets

This system solves these problems by choosing the right data structure for each operation—not based on preference, but on operational necessity.

---

## Data Structures & Their Purpose

| Data Structure | Purpose | Operation |
|---|---|---|
| **Array** | Master patient database with fixed bed capacity | Stores all patients with indexed access |
| **Queue** | ER waiting room (FIFO) | Admit → enqueue; Doctor calls → dequeue |
| **Linked List** | Patient medical history | Chain of past visits, allergies, medications, diagnoses |
| **Stack** | Treatment action log | Every action recorded; undo = pop last action |
| **Hash Table** | Instant patient lookup | Doctor enters patient ID → O(1) retrieval |
| **Binary Search Tree** | Sorted patient records | In-order traversal produces alphabetically sorted list |
| **Sorting Algorithms** | One-time reports | Sort by fields without dedicated tree (severity, time in ER) |

---

## System Architecture

### Core Components

#### 1. **Patient Class**
Represents a single patient with:
- `id`: Unique patient identifier
- `name`: Patient's full name
- `age`: Patient's age
- `severity`: Triage priority (1-4, where 1 = Emergency/Immediate)
- `arrivalTime`: Timestamp of admission
- `medicalHistory`: Linked list of past visits and medical events

#### 2. **Emergency Room Class**
The central hub managing:
- `patientDatabase`: Array of all admitted patients
- `waitingRoom`: Queue of patients awaiting treatment
- `treatmentLog`: Stack of all actions (for undo)
- `patientIndex`: Hash table for O(1) patient lookup by ID
- `patientTree`: Binary search tree for sorted reports

#### 3. **Custom Data Structures**
You build from scratch:
- **Queue**: Enqueue, dequeue, peek operations
- **Linked List**: Insert, traverse, delete nodes
- **Stack**: Push, pop, peek operations
- **Hash Table**: Insert, search, collision resolution
- **Binary Search Tree**: Insert, in-order traversal, delete

---

## Triage Priority Levels

| Level | Name | Color | Description |
|---|---|---|---|
| P1 | Emergency/Immediate | Red | Life-threatening injuries; immediate treatment needed |
| P2 | Urgent | Yellow | Serious but stable; treatment can be delayed briefly |
| P3 | Delayed | Green | Minor injuries; treatment can wait |
| P4 | Expectant | Blue | Extensive injuries; limited resources; comfort care |
| — | Dead | Black | Patients in cardiac arrest; resuscitation not provided |

---

## Command-Line Interface

### Main Menu Operations

```
=== Emergency Room Triage System ===
1. Admit Patient
2. Call Next Patient
3. Look Up Patient by ID
4. View Patient Medical History
5. Undo Last Action
6. Generate Sorted Report
7. Discharge Patient
8. View Current Statistics
9. Exit
```

### Operation Examples

#### **Admit Patient**
```
Enter patient name: John Smith
Enter age: 45
Enter severity level (1-4): 2
Enter symptoms: Chest pain, shortness of breath

→ Patient P001 registered
→ Added to waiting queue
→ Indexed in hash table
→ Indexed in BST (by last name)
```

#### **Call Next Patient**
```
Next patient in queue: John Smith (P001)
Severity: P2 (Urgent)
Medical History:
  - Hypertension (2022)
  - Previous ER visit: 2023-05-15
  - Allergies: Penicillin

Admit to treatment? (y/n)
```

#### **Look Up Patient by ID**
```
Enter patient ID: P001

→ O(1) Hash Table Lookup
Patient Found: John Smith
Age: 45
Severity: P2
Arrival Time: 2024-01-15 14:23:45
```

#### **Generate Sorted Report**
```
Report Options:
1. Sort by Last Name (BST)
2. Sort by Arrival Time (Sorting Algorithm)
3. Sort by Severity (Sorting Algorithm)

→ In-order traversal of BST produces alphabetically sorted list
→ Sorting algorithm rearranges by timestamp or severity

Current Patients in ER:
1. Jane Doe (P001) - P1 - Arrived 14:15
2. John Smith (P002) - P2 - Arrived 14:23
3. Alice Johnson (P003) - P3 - Arrived 14:30
```

#### **Undo Last Action**
```
Last action: Admitted patient P001
Undo? (y/n)

→ Stack.pop() reverses the operation
→ Patient removed from all structures
→ System state restored
```

---

## How to Use

### Prerequisites
- Java 8 or higher
- IDE (IntelliJ, Eclipse, VSCode with Java extension) or command line with javac

### Installation

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd ER-Triage-System
   ```

2. **Compile all Java files**
   ```bash
   javac src/*.java
   ```

3. **Run the main program**
   ```bash
   java -cp src Main
   ```

### Basic Workflow

1. **Start the program** → Main menu appears
2. **Admit patients** → Enter patient details, system assigns ID
3. **Call patients** → Dequeue from waiting room, display full record
4. **Perform operations** → Look up, view history, undo, generate reports
5. **Discharge patients** → Remove from all structures
6. **Exit** → Graceful shutdown

---

## Project Structure

```
ER-Triage-System/
├── src/
│   ├── Main.java                 # Entry point, menu loop
│   ├── Patient.java              # Patient data class
│   ├── EmergencyRoom.java        # Core ER management
│   ├── Queue.java                # Custom Queue implementation
│   ├── Stack.java                # Custom Stack implementation
│   ├── LinkedList.java           # Custom Linked List for medical history
│   ├── HashTable.java            # Custom Hash Table with collision resolution
│   ├── BinarySearchTree.java     # Custom BST for sorted reports
│   ├── SortingAlgorithms.java    # Selection, Bubble, Insertion, Quicksort
│   └── MedicalHistoryNode.java   # Node class for linked list
├── README.md
├── DESIGN.md                     # System design and sketches
├── PSEUDOCODE.md                 # Pseudocode for all operations
└── BENCHMARKS.md                 # Performance analysis
```

---

## Key Features

### ✓ Real Operational Constraints
- Fixed bed capacity (array size limit)
- FIFO fairness in waiting room
- O(1) emergency lookups
- Instant undo for critical errors

### ✓ Complete Patient Lifecycle
- Admission → Waiting → Treatment → Discharge
- Full medical history tracking
- Multiple operation types (admit, lookup, treat, discharge)

### ✓ Educational Value
- Each data structure has a clear purpose
- Sorting algorithms compared empirically
- Hash table collision handling strategies
- BST balancing and traversal

### ✓ Error Handling
- Invalid patient IDs
- Capacity overflow protection
- Duplicate admission prevention
- Safe undo operations

---

## Performance Characteristics

| Operation | Data Structure | Time Complexity | Why |
|---|---|---|---|
| Admit patient | Queue + Hash Table + BST | O(log n) | Hash insert O(1), BST insert O(log n) |
| Call next patient | Queue | O(1) | Dequeue from front |
| Look up by ID | Hash Table | O(1) average | Direct key lookup |
| View history | Linked List | O(h) | Traverse chain of h nodes |
| Undo action | Stack | O(1) | Pop from top |
| Generate sorted report (BST) | Binary Search Tree | O(n) | In-order traversal |
| Generate sorted report (algorithm) | Sorting Algorithm | O(n log n) | Quicksort average case |
| Check if patient exists | Hash Table | O(1) average | Key lookup |

---

## Implementation Details

### Queue Operations
```
enqueue(patient) → Add to rear
dequeue() → Remove from front
peek() → View front without removing
isEmpty() → Check if waiting room empty
```

### Stack Operations
```
push(action) → Record action
pop() → Undo last action
peek() → View last action
isEmpty() → No actions to undo
```

### Hash Table Operations
```
insert(id, patient) → Store with collision resolution
search(id) → O(1) lookup
delete(id) → Remove entry
```

### Linked List Operations (Medical History)
```
addHistory(visit) → Append new visit to end
traverseHistory() → Display full chain from oldest to newest
deleteHistory(index) → Remove specific visit (for corrections)
```

### Binary Search Tree Operations
```
insert(name, patient) → Maintain sorted order
inOrderTraversal() → Generate alphabetically sorted list
delete(name) → Remove patient, maintain tree structure
```

---

## Testing & Validation

### Test Scenarios

1. **Basic Admission & Discharge**
   - Admit 5 patients with varying severity
   - Verify they appear in waiting queue
   - Discharge in order, confirm removal from all structures

2. **Hash Table Lookup**
   - Admit 100 patients
   - Random lookups by ID should return instantly
   - Verify O(1) performance (no scanning)

3. **Undo Functionality**
   - Admit patient P001
   - Admit patient P002
   - Undo (should remove P002)
   - Undo again (should remove P001)
   - Verify system returns to clean state

4. **Sorted Reports**
   - Admit patients: Alice, Bob, Charlie
   - Generate sorted report by last name
   - Verify BST in-order traversal produces: Alice, Bob, Charlie
   - Generate sorted by arrival time
   - Verify sorting algorithm reorders correctly

5. **Capacity Management**
   - Set array capacity to 10
   - Try to admit 11 patients
   - Verify system rejects with capacity error

6. **Medical History Traversal**
   - Add 3 historical visits to patient
   - View history, verify all displayed in order
   - Add new visit, verify appended to end

---

## Benchmarking

The system includes empirical performance testing:

- **Sorting Comparison**: Run Selection, Bubble, Insertion, and Quicksort on same dataset
- **Scaling Analysis**: Measure performance as patient count increases (100 → 1000 → 10000)
- **Hash Table Efficiency**: Collision rate vs. load factor
- **BST Balance**: In-order traversal time vs. unbalanced scenarios

See `BENCHMARKS.md` for detailed results.

---

## Common Issues & Troubleshooting

### "Patient not found" error
- Verify you're using the correct patient ID (format: P### where ### is number)
- Check that patient hasn't been discharged

### "Queue is empty" when calling next patient
- No patients are waiting
- Try admitting some patients first

### "Undo stack is empty"
- No previous actions to undo
- Actions are only undoable from the current session

### Performance slow with large dataset
- Check if hash table load factor is too high (rehash if needed)
- Verify BST isn't severely unbalanced
- Consider using Quicksort instead of Bubble Sort for large reports

### "Duplicate patient ID"
- Patient IDs are auto-generated; this shouldn't occur
- If it does, there's a bug in the ID generation logic

---

## Design Decisions

### Why Array for Patient Database?
Hospitals have physical bed limits. An array with fixed capacity naturally models this constraint. Indexed access allows quick retrieval of patient at bed #5.

### Why Queue for Waiting Room?
FIFO fairness is essential. First patient to arrive should be first to be seen (all else equal). Queue is the only structure that guarantees this.

### Why Hash Table for Lookup?
Emergencies demand instant access. A doctor saying "Give me patient ID P257" needs O(1) retrieval, not O(n) scanning.

### Why Linked List for Medical History?
History is sequential and grows unpredictably. Linked list allows unlimited growth without pre-allocation and natural traversal order.

### Why Stack for Action Log?
Errors must be reversible instantly. Stack's LIFO property makes undo O(1) and conceptually simple.

### Why BST for Reports?
Reports are generated frequently. Maintaining sorted order in a BST avoids re-sorting on every report request.

---

## Extensions & Enhancements

Possible improvements beyond core requirements:

- **Priority Queue**: Replace simple queue with priority queue (dequeue by severity, not arrival)
- **Database Persistence**: Save/load patient records to file
- **Graphical UI**: Replace command-line with JavaFX/Swing interface
- **Multi-room Management**: Track which treatment room each patient is in
- **Advanced Analytics**: Average wait time, peak hours, severity distribution
- **Appointment Scheduling**: Future admissions, not just walk-in
- **Staff Management**: Track nurse/doctor availability and assignment

---

## Learning Outcomes

By completing this project, you'll understand:

✓ How data structures solve real operational problems  
✓ Why hash tables enable emergency lookups  
✓ How stacks power undo functionality  
✓ The role of queues in fairness  
✓ How BSTs maintain sorted order dynamically  
✓ Performance tradeoffs in data structure selection  
✓ Empirical benchmarking of algorithms  
✓ Professional code organization and documentation  



