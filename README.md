PROJECT: University Management System
COURSE: CSE 731 - Software Testing (Term I 2025'26)
TEAM MEMBERS: 
1. [Your Name] - [Your Roll Number]
2. [Partner Name] - [Partner Roll Number]

-----------------------------------------------------------------------
PROJECT DESCRIPTION:
This project implements a backend system for a University, featuring 
Student Enrollment, Faculty Management, Library operations, and Budgeting.
The system is designed with complex boolean logic and mathematical 
operations to demonstrate the effectiveness of Mutation Testing.

TOTAL SOURCE LINES OF CODE (SLOC): ~1000+ (excluding documentation)

-----------------------------------------------------------------------
TESTING STRATEGY: Mutation Testing
We used Mutation Testing to evaluate the quality of our test suite. 
Unlike traditional code coverage, which only checks if code was executed, 
Mutation Testing checks if our tests are strong enough to detect changes 
(bugs) injected into the code.

MUTATION OPERATORS USED:
Per the project requirements, we targeted specific logic to ensure 
at least 3 operators were active at Unit and Integration levels:
1. Conditionals Boundary Mutator (changed < to <=, etc.)
2. Math Mutator (changed + to -, * to /, etc.)
3. Negate Conditionals Mutator (changed boolean return values)

-----------------------------------------------------------------------
TOOLS USED:
1. Java JDK 17
2. Maven (Build Tool)
3. JUnit 5 (Test Framework)
4. PIT (PITest) Mutation Testing Plugin (Version 1.15.0)

-----------------------------------------------------------------------
AI USAGE DECLARATION:
In compliance with the project guidelines regarding AI tools:
- Tool Used: Google Gemini
- Purpose: 
  1. Generating the initial boilerplate structure for the University domain classes.
  2. Suggesting complex mathematical logic (e.g., Library fine calculations) to provide 
     better targets for mutation operators.
  3. Assisting in the configuration of the 'pom.xml' for the PIT plugin.
- Verification: All AI-generated code was manually reviewed, debugged, and 
  extended by the team members to ensure correctness and adherence to 
  Java best practices.

-----------------------------------------------------------------------
HOW TO RUN:
1. Compile and Run Application:
   Run 'com.university.Main.main()' to see the system flow.

2. Run Mutation Tests:
   Execute command: mvn org.pitest:pitest-maven:mutationCoverage
   
3. View Reports:
   Open 'target/pit-reports/index.html' in a web browser.