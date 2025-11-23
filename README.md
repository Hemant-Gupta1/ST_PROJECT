````markdown
# University Management System
### CSE 731: Software Testing | Term I 2025-26  
**IIIT Bangalore**

---

## 👥 Team Members
1. [Hemant Gupta] – [IMT2022030]  
2. [Hemang Seth] – [IMT2022098]

---

## 📝 Project Description
This project implements a comprehensive backend system for a University, featuring Student Enrollment, Faculty Management, Library operations, and Departmental Budgeting.

The system is intentionally designed with complex boolean logic, mathematical accumulation, state management, and boundary conditions to demonstrate the effectiveness of **Mutation Testing**.

---

## 📊 Final Mutation Test Results

![Mutation Test Report](images/mutation_report.png)

```text
* Statistics
  ================================================================================

> > Line Coverage (for mutated classes only): 295/303 (97%)
> > Generated 256 mutations Killed 211 (82%)
> > Mutations with no coverage 8. Test strength 85%
> > Ran 584 tests (2.28 tests per mutation)
````

-----

## 📂 Folder Structure & File Descriptions

```text
UniversityMutationProject/
├── images/
│   └── mutation_report.png    # Mutation test report visualization
├── pom.xml                    # Maven configuration with PITest plugin
├── README.md                  # Project documentation
└── src/
├── main/java/com/university/
│   ├── Main.java              # Entry point
│   ├── Person.java            # Abstract base class
│   ├── Student.java           # GPA, credits, probation logic
│   ├── Faculty.java           # Tenure, salary, service years
│   ├── Course.java            # Capacity, enrollment, cancellation
│   ├── Department.java        # Budget calculation, hiring logic
│   ├── LibraryBook.java       # Book state (damage, availability)
│   ├── LibrarySystem.java     # Overdue fine calculation
│   ├── EnrollmentService.java # Integration of Student–Course rules
│   └── ValidationUtils.java   # Input validation utilities
│
└── test/java/com/university/
    ├── StudentTest.java          # Student GPA, credits, probation tests
    ├── FacultyTest.java          # Tenure, salary, service year tests
    ├── CourseTest.java           # Capacity, enrollment, cancellation tests
    ├── DepartmentTest.java       # Budget calculation, hiring logic tests
    ├── LibraryBookTest.java      # Book state (damage, availability) tests
    ├── LibrarySystemTest.java    # Overdue fine calculation tests
    ├── EnrollmentServiceTest.java# Integration of Student–Course rules tests
    ├── MainTest.java             # System entry point tests
    ├── PersonTest.java           # Abstract base class tests
    ├── ValidationUtilsTest.java  # Input validation utilities tests
    ├── ExceptionBoosterTest.java # Exception handling tests
    ├── BoundarySniperTest.java   # Conditionals boundary tests
    ├── SniperRound2Test.java     # Math and boolean logic tests
    ├── StateAccumulationTest.java# Accumulation of fine tests
    └── OutputCaptureTest.java    # Exception handling tests
```

-----

## 🔬 Testing Levels and Purpose This project uses a multi-level testing approach, aligning directly with professional standards.

### 1\. Unit Testing

This level involves testing individual methods, functions, and classes in isolation.

| File | Focus | Primary Goal |
| :--- | :--- | :--- |
| `StudentTest.java` | `Student` & `Person` logic | Verify correct GPA calculation, credit accumulation, and probation/honor status logic. |
| `FacultyTest.java` | `Faculty` logic | Verify accurate salary raises and the complex boundary condition for tenure eligibility (`> 5` years). |
| `LibraryBookTest.java` | `LibraryBook` state | Verify damage accumulation and the **usability boundary** (`< 80` damage). |
| `ValidationUtilsTest.java` | `ValidationUtils` static methods | Ensure helper functions (e.g., `isValidEmail`, `isValidAge`) correctly identify invalid input formats and boundaries. |

### 2\. Integration Testing

This level verifies that different components of the system work correctly when put together.

| File | Focus | Primary Goal |
| :--- | :--- | :--- |
| `EnrollmentServiceTest.java` | `Student` + `Course` | Verify complex business rules, such as preventing enrollment if a student is on probation or if the credit limit is exceeded. |
| `DepartmentTest.java` | `Faculty` + `Budget` | Verify that the system correctly calculates total salary expense and enforces the budget constraint during a simulated hiring process. |
| `LibrarySystemTest.java` | `Student` + `LibraryBook` | Verify the fine calculation logic, including **complex date-wrapping edge cases** (borrowing in December, returning in January). |

-----

## 🎯 Test Case Design Strategies The final test suite is not just random; it is strategically built using several advanced testing techniques to kill every class of mutant.

### 1\. Boundary Value Analysis (BVA) & Equivalence Partitioning

We explicitly target the edges of valid input ranges, which is essential for mutation testing as it kills the "off-by-one" mutants.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Testing faculty tenure at **5 years** and **6 years** | BVA on the boundary of `> 5` | **Conditional Boundary Mutator** (changing `>` to `>=`). |
| Testing book damage at **79** and **80** | BVA on the boundary of `< 80` usable | **Conditional Boundary Mutator** (changing `<` to `<=`). |
| Testing enrollment at **20 credits** (Success) and **21 credits** (Failure) | BVA on credit limit | **Negate Conditionals Mutator** (inverting the credit limit check). |

### 2\. State Accumulation Testing

Used to kill the most common type of arithmetic mutant in accumulator variables.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Calling `Student.updateAcademicRecord` **twice** | Proves the `+=` operator works | **Assignment Mutator** (changing `totalCredits += X` to `totalCredits = X`). |
| Adding two faculty members with **different salaries** | Proves the loop total is an accumulation | **Math Mutator** (changing `total += salary` to `total = salary`). |

### 3\. Logic & Negation Testing

Specific tests to confirm that partial logic gates or security checks are not being ignored.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Testing email **"name@host"** (no dot) | Proves both `contains("@")` **AND** `contains(".")` are required | **Conditional Mutator** (removing part of a compound boolean check). |
| Testing enrollment of the **same student twice** | Proves the duplicate check (`!list.contains()`) is enforced | **Negation Mutator** (removes the `!` from the condition). |
| Testing `assertThrows` for **negative input** | Proves the exception handler (`if (x < 0) throw...`) is active | **Statement Deletion Mutator** (deleting the error check line). |

### 4\. Output Stream Verification

Used `OutputCaptureTest.java` to test methods that perform logging but don't return a value. This is crucial for killing the "Void Method Call" mutants.

  * **Test Focus:** Verifies that print statements in `EnrollmentService` (e.g., printing "Enrollment failed...") and utility logs in `ValidationUtils` are not silently removed by the mutation tool.

-----

## 🧬 Mutation Operators Summary

1.  **Conditionals Boundary Mutator**: Changes `<` → `<=`, `>=` → `>`, etc.
2.  **Math Mutator**: Replaces arithmetic operations `+`, `-`, `*`, `/`.
3.  **Negate Conditionals Mutator**: Negates boolean conditions (e.g., `if (isProbation)` → `if (!isProbation)`).
4.  **Void Method Call Mutator**: Removes calls to void methods (e.g., deletes `System.out.println`).
5.  **Return Values Mutator**: Changes return types (true/false, numeric defaults, etc.).

-----

## 🚀 How to Run This Project (From Scratch)

### 1\. Install Prerequisites (Java 17 + Maven)

```bash
sudo apt-get update
sudo apt-get install openjdk-17-jdk maven -y
```

### 2\. Compile the Code

```bash
mvn clean compile
```

### 3\. Run the Application (Demo Mode)

```bash
java -cp target/classes com.university.Main
```

Expected output begins with: `=== University System Starting ===`

### 4\. Run All Tests

```bash
mvn test
```

### 5\. Run Mutation Testing (PITest)

```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

### 6\. View Mutation Report

  * Open: `target/pit-reports/index.html`
  * View full mutation matrix and killed/survived mutants.

-----

## 🛠️ Tools & Technologies

  * **Language:** Java (JDK 17)
  * **Build Tool:** Maven
  * **Test Framework:** JUnit 5
  * **Mutation Tool:** [PITest (PIT)](https://pitest.org/) (Version 1.15.0)
  * **IDE:** VS Code

-----

## 🤖 AI Usage Declaration

*In compliance with course rules regarding AI assistance.*

### **Tool Used:** Google Gemini

### **Contribution:**

1.  Generated initial boilerplate structure for `com.university` classes.
2.  Suggested creation of "Sniper" test classes like `BoundarySniperTest` and `OutputCaptureTest`.
3.  Provided PITest configuration snippet for `pom.xml`.

### **Verification:**

All AI-generated content was manually validated, corrected, and expanded by both team members.

```
```