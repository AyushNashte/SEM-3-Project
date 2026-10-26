# EduSnap - System Architecture

## 1. Overview

EduSnap is a **standalone Java desktop application**. It runs entirely on the student's computer.
There is no server, no database and no browser extension.

| Layer | What it contains |
|---|---|
| GUI | JavaFX screens in `common/gui/` (styled in Java code) |
| Engine | Flow, scoring and reporting in `common/` |
| Content | Concepts, lessons, question banks and games in `class7/` to `class10/` |

## 2. Technologies

| Item | Choice |
|---|---|
| Language | Java |
| GUI toolkit | JavaFX |
| Storage | In memory, per session |
| IDE | IntelliJ IDEA |
| Version control | Git and GitHub |

## 3. The shared engine (`common/`)

| Class | Role |
|---|---|
| `Concept` | An id and a name for one unit of knowledge, for example `INT3 Addition of Integers`. |
| `Question` | Question text, options, correct answer and the `Concept` it tests. |
| `Test` | A list of questions. Can be shuffled and cut down to a random subset. |
| `TestResult` | Score percentage and the list of weak concepts. |
| `LessonContent` | Explanation, optional formula and worked examples for one concept. |
| `TopicStatus` | `ALREADY_MASTERED`, `IN_PROGRESS`, `RETEST_REQUIRED`, `COMPLETED`. |
| `StudentReport` | Scores, attempts, weak concepts and final status. |
| `Topic` | Abstract flow controller. Every topic extends it. |

### `Topic` is a template

A topic class (for example `class10/ArithmeticProgression`) extends `Topic` and supplies only content:
its concepts, pre-requisite questions, lesson content and lesson test. It can also override
`getGameId()` to name its game. The inherited flow does the rest:

- Each attempt uses **5 random questions** from the question bank.
- Pre-requisite test: 100% gives `ALREADY_MASTERED`.
- Lesson test and retest: pass mark **75%**.
- A failed test records weak concepts. The retest asks only about those concepts.

## 4. Learning flow

```
Select Class -> Select Topic -> Pre-requisite Test
   100%  -> Already Mastered -> Report
   <100% -> Lesson -> Lesson Test
               >= 75% -> Completed -> Report
               <  75% -> Weak-concept review -> Retest -> (until >= 75%) -> Report
```

## 5. GUI structure

`Main` starts JavaFX and creates `AppLauncher`, which owns the window (1150 x 720, minimum 900 x 600)
and swaps the scene for each step.

| Screen | Purpose |
|---|---|
| `HomeScreen` | Choose a class. |
| `ClassTopicsScreen` | Choose a topic (list comes from `ClassCatalog.all()`). |
| `PrerequisiteTestScreen` | Pre-requisite test. |
| `LessonScreen` | Original lesson screen: one concept per page, Play Game button. Used for Classes 8, 9, 10. |
| `book/BookLessonScreen` | Balbharati-style lesson screen. Used for Class 7. |
| `LessonTestScreen` | Lesson test. |
| `RetestScreen` | Retest on weak concepts. |
| `ReportScreen` | Final report. |
| `QuestionTestScreen` | Shared question-paging screen used by the three test screens. |

Styling: `Theme` (purple theme for most screens) and `book/BookTheme` (Balbharati colours and fonts).
`UiStyle` is an older style helper kept for compatibility.

`ClassCatalog` is the single registry of classes, topics and accent colours. Adding a topic to the
app is one `topic(...)` line there.

## 6. Balbharati-style lesson screen

Package `common/gui/book/`:

| File | Role |
|---|---|
| `BookTheme` | Colour and font constants, page width, desk and shadow helpers. |
| `BookParts` | Static builders: cloud tag, headings, body text, "Remember :" box, example block, activity block, chapter banner. |
| `BookLessonScreen` | Keeps a page number (0 = cover, 1 to n = concepts), builds each page from `LessonContent`, draws the footer strip, opens the game and the Practice Set. |

`AppLauncher.showLesson()` chooses the screen: Class 7 uses `BookLessonScreen`, other classes use
`LessonScreen`. Both read the same `LessonContent`, so content is unchanged. `LessonScreen.resolveGame(id)`
is public and static so both screens share one game lookup.

The cover image `class7_cover.jpg` is loaded as a resource from the same package folder. If it is
missing a plain drawn cover is used.

## 7. Games

A topic returns a game id from `getGameId()`. The lesson screen shows a Play button and
`LessonScreen.resolveGame(id)` maps the id to a game.

- **JavaFX games** extend `common/gui/games/ArcadeGame`. It provides the canvas, the animation loop,
  score, timer, lives-style game states (playing, round won, game over) and the HUD. A game only supplies
  its title, prompt, round set-up, drawing and click handling. Examples: Integer Hopper, Hill Climb,
  Similarity Defence.
- **Class 8 games** are Swing windows started through `Topic.launchGame(Runnable)`.

| Game id | Topic |
|---|---|
| `integer-hopper` | Class 7 Integers |
| `smoothie-rush` | Class 7 Ratio and Proportion |
| `term-catcher`, `fraction-fisher` | Class 7 topics not yet in the catalog |
| `factor-breaker`, `equation-duel` | Class 8 (Swing) |
| `upside-down-lights` | Class 9 Real Numbers |
| `borderland-games` | Class 9 Polynomials |
| `ap-express` (Hill Climb) | Class 10 Arithmetic Progression |
| `similarity-lab` (Similarity Defence) | Class 10 Similarity |
| `root-radar`, `coordinate-radar` | Class 10 topics not yet in the catalog |

## 8. Data

All content is written in Java code. Questions, lessons and reports live in memory for the length of
a session. Nothing is saved to disk.

## 9. Package map

```
common/            engine classes
common/gui/        screens, catalog, themes
common/gui/book/   Balbharati-style lesson screen
common/gui/games/  ArcadeGame base class
class7/ ... class10/  topic content, with games/ subfolders
```