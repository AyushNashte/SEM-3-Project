# EduSnap - Project Requirements

## 1. Project title

EduSnap - Adaptive Mathematics Learning App for Maharashtra SSC (Class 7 to 10)

## 2. Objective

Help students understand Mathematics topics properly. EduSnap checks what the student already
knows, teaches what is missing, tests understanding, and retests weak concepts until the student
passes.

## 3. Target users

- Maharashtra State Board students of Class 7, 8, 9 and 10.
- Students who learn from the Balbharati textbooks and want practice and explanations alongside them.

## 4. Functional requirements

### 4.1 Class and topic selection
- FR1. The student chooses a class (7, 8, 9 or 10) on the home screen.
- FR2. The student chooses a topic from that class.

### 4.2 Pre-requisite test
- FR3. Each topic starts with a pre-requisite test of 5 random questions.
- FR4. A score of 100% marks the topic **Already Mastered** and goes to the report.
- FR5. Any lower score continues to the lesson.

### 4.3 Lesson
- FR6. The lesson shows one concept at a time with an explanation, an optional formula and worked examples.
- FR7. The student can move to the previous and next concept.
- FR8. Topics with a game show a button to play it, and the student returns to the same lesson page afterwards.
- FR9. Class 7 lessons use a Balbharati-style page layout (cover page, "Let's learn.", "Remember :", examples, Activity, Practice Set).

### 4.4 Lesson test and retest
- FR10. The lesson test has 5 random questions, one multiple-choice question at a time.
- FR11. A score of 75% or more marks the topic **Completed**.
- FR12. A lower score lists the weak concepts, which are reviewed and then retested.
- FR13. The retest asks about the weak concepts only and repeats until the student reaches 75%.
- FR14. Every question is tagged with the concept it tests.

### 4.5 Report
- FR15. The report shows the topic, scores, number of attempts, weak concepts and final status
  (Already Mastered, In Progress, Retest Required or Completed).

### 4.6 Games
- FR16. Each listed topic has a mini-game that practises its concepts.
- FR17. JavaFX games share one base class (`ArcadeGame`) for timer, score, states and HUD.

## 5. Content in the current version

| Class | Topics in the app |
|---|---|
| 7 | Integers, Ratio and Proportion |
| 8 | Factorisation, Linear Equations |
| 9 | Real Numbers, Polynomials |
| 10 | Arithmetic Progression, Similarity |

More topic classes are already written (Class 7 Algebraic Expressions, Class 10 Quadratic Equations,
Coordinate Geometry, Trigonometry, Probability) and can be added to the app with one line in `ClassCatalog`.

## 6. Non-functional requirements

- **Usability:** simple screens, readable text, no emoji (they render as boxes on some Windows fonts).
- **Familiarity:** the look of lessons should resemble the Balbharati textbooks (Class 7 first).
- **Performance:** screens open instantly; games run smoothly on a normal student laptop.
- **Maintainability:** one shared engine, class-wise content packages, one registry (`ClassCatalog`).
- **Portability:** runs on any computer with a JDK and the JavaFX SDK. No internet or server needed.
- **Reliability:** question sets are randomised each attempt and scoring follows the fixed thresholds.

## 7. Technology constraints

- Java and JavaFX desktop application.
- Styling done in Java code (no CSS files).
- Data kept in memory only.
- One `Main` class as the entry point.

## 8. Scope

**In scope (current version)**
- Mathematics, Class 7 to 10, two topics per class.
- Pre-requisite check, lessons, lesson test, weak-concept retest, report.
- One mini-game per topic.
- Balbharati-style lesson screen for Class 7.

**Out of scope for now**
- Saving progress between sessions or user accounts.
- Other subjects.
- Marathi-language labels.
- Web, mobile or browser-extension versions.

## 9. Planned improvements

- Kahoot-style test screen (large coloured answer buttons, timer) in place of radio buttons.
- Apply the purple theme or the Balbharati look consistently to test and report screens.
- Extend the Balbharati look to Classes 8, 9 and 10 after the teacher's review of the Class 7 demo.
- Images and diagrams in lessons.
- Move Class 8 games from Swing to JavaFX.
- List the remaining topics in the catalog.
- Save student progress.