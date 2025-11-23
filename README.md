# University Management System
### CSE 731: Software Testing | Term I 2025-26  
**IIIT Bangalore**

---

## 👥 Team Members
1. Hemant Gupta – IMT2022030
2. Hemang Seth – IMT2022098

---

## 📝 Project Description
This project implements a comprehensive backend system for a University, featuring Student Enrollment, Faculty Management, Library operations, and Departmental Budgeting.

The system is intentionally designed with complex boolean logic, mathematical accumulation, state management, and boundary conditions. It goes beyond routine testing to **fully demonstrate the power of Mutation Testing (PITest)** as a superior method for test suite quality assurance.

**Project Goal:** As per the course guidelines, this work aims to *"stand out in terms of demonstrating the applicability of a particular technique and one or more tools."*

---

## 🌟 Why This Project Stands Out
This project strategically engineers code and tests to maximize the effectiveness of mutation testing:

1.  **Advanced Applicability of Technique:**
    * **Proof of Test Quality:** While we achieved **97% Line Coverage**, we recognized that traditional coverage only proves a line was executed, not that the test made a valid assertion. Our **82% Mutation Coverage** proves the test suite is rigorous enough to detect even subtle logical errors.
    * **Targeting Logic, Not Just Lines:** We utilized **Boundary Value Analysis** and **State Accumulation Testing** specifically to generate and kill complex mutants hiding in sensitive areas like Financial/Math logic (Department salary summation) and critical boolean checks.

2.  **Clear Differentiation of Testing Levels:**
    * The project clearly applies mutation principles across both Unit and Integration levels, fulfilling a core requirement.

3.  **Comprehensive Tool Demonstration:**
    * We utilized advanced PITest features, such as output interception and specific Maven plugin configurations, to solve real-world testing problems.

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

## 📂 Folder Structure & File Descriptions (\>1000 lines of code)

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

## 🔬 Testing Levels and Purpose

This project uses a multi-level testing approach, aligning directly with professional standards.

### 1\. Unit Testing

This level involves testing individual methods, functions, and classes in isolation.

| File | Focus | Primary Goal |
| :--- | :--- | :--- |
| `StudentTest.java` | `Student` & `Person` logic | Ensure individual components are bug-free. Verifies correct GPA calculation, credit accumulation, and probation/honor status logic. |
| `FacultyTest.java` | `Faculty` logic | Verify accurate salary raises and the complex boundary condition for tenure eligibility (`> 5` years). |
| `LibraryBookTest.java` | `LibraryBook` state | Ensure individual components are bug-free. Verifies damage accumulation and the **usability boundary** (`< 80` damage). |
| `ValidationUtilsTest.java` | `ValidationUtils` static methods | Ensure helper functions (e.g., `isValidEmail`, `isValidAge`) correctly identify invalid input formats and boundaries. |

### 2\. Integration Testing

This level verifies that different components of the system work correctly when put together, ensuring complex cross-component interactions are covered by mutation analysis.

| File | Focus | Primary Goal |
| :--- | :--- | :--- |
| `EnrollmentServiceTest.java` | `Student` + `Course` | **Complex Interactions:** Checks a `Student`'s probation status before allowing enrollment in a `Course`. Enforces rules like preventing enrollment if the credit limit is exceeded. |
| `DepartmentTest.java` | `Faculty` + `Budget` | Verify that the system correctly calculates total salary expense and enforces the budget constraint during a simulated hiring process. |
| `LibrarySystemTest.java` | `Student` + `LibraryBook` | Verify the fine calculation logic, including **complex date-wrapping edge cases** (borrowing in December, returning in January). |

-----

## 🎯 Test Case Design Strategies

The final test suite is not just random; it is strategically built using several advanced testing techniques to kill every class of mutant.

### 1\. Boundary Value Analysis (BVA) & Equivalence Partitioning

We explicitly target the edges of valid input ranges. This is essential for mutation testing as it kills "off-by-one" mutants and **Conditional Boundary Mutators**.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Testing faculty tenure at **5 years** and **6 years** | BVA on the Critical Boundary of **Tenure Eligibility (\> 5 years)** | **Conditional Boundary Mutator** (changing `>` to `>=`). |
| Testing book damage at **79** and **80** | BVA on the boundary of `< 80` usable | **Conditional Boundary Mutator** (changing `<` to `<=`). |
| Testing probation status at **GPA 2.0** | BVA on the **Probation Status (\< 2.0 GPA)** boundary | **Conditional Boundary Mutator**. |
| Testing enrollment at **20 credits** (Success) and **21 credits** (Failure) | BVA on credit limit | **Negate Conditionals Mutator** (inverting the credit limit check). |

### 2\. State Accumulation Testing

Used to kill the most common type of arithmetic mutant in accumulator variables, specifically targeting **Math Mutators**.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Calling `Student.updateAcademicRecord` **twice** | Proves the `+=` operator works | **Assignment Mutator** (changing `totalCredits += X` to `totalCredits = X`). |
| Adding two faculty members with **different salaries** | Proves the loop total is an accumulation (Financial/Math Logic) | **Math Mutator** (changing `total += salary` to `total = salary`). |
| `LibrarySystem` fine calculation | Testing fine summation against Math Mutators | Ensures fine calculation logic is robust. |

### 3\. Logic & Negation Testing

Specific tests to confirm that partial logic gates or security checks are not being ignored.

| Test Case Example | Strategy | Mutant Killed |
| :--- | :--- | :--- |
| Testing email **"name@host"** (no dot) | Proves both `contains("@")` **AND** `contains(".")` are required | **Conditional Mutator** (removing part of a compound boolean check). |
| Testing enrollment of the **same student twice** | Proves the duplicate check (`!list.contains()`) is enforced | **Negation Mutator** (removes the `!` from the condition). |
| Testing `assertThrows` for **negative input** | Proves the exception handler (`if (x < 0) throw...`) is active | **Statement Deletion Mutator** (deleting the error check line). |

-----

## 🛠️ Comprehensive Tool Demonstration (PITest)

We used the advanced features of PITest to solve real testing problems, rather than just running the default configuration.

| Tool Feature Demonstrated | Project Application | Significance |
| :--- | :--- | :--- |
| **Mutation Coverage Reporting** | Generated a formal HTML report that clearly isolates **survived mutants** for triage. | Proves the tool can be used to objectively measure test quality. |
| **JUnit 5 Integration** | Used the necessary `pitest-junit5-plugin` configuration in `pom.xml` to successfully link the mutation engine to our modern test suite. | Demonstrates competence in configuring industry-standard tools for advanced testing techniques. |
| **Output Stream Interception** | Created the specialized `OutputCaptureTest.java` class to redirect and verify console output. | Directly addresses and **kills Void Method Call Mutators** (like deleted `System.out.println` statements) that usually survive, demonstrating a key advanced testing skill. |

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

## 💻 Tools & Technologies

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