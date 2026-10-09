package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinearEquations extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_LINEAR = new Concept("LE1", "What is a Linear Equation");
    private static final Concept SOLVE_BASIC = new Concept("LE2", "Solving One-Step and Two-Step Equations");
    private static final Concept BOTH_SIDES = new Concept("LE3", "Variable on Both Sides");
    private static final Concept BRACKETS_FRACTIONS = new Concept("LE4", "Equations with Brackets and Fractions");
    private static final Concept WORD_PROBLEMS = new Concept("LE5", "Forming Equations from Word Problems");

    // Prerequisite concepts
    private static final Concept SUBSTITUTION = new Concept("LE6", "Substituting Values into Expressions");
    private static final Concept INVERSE_OPERATIONS = new Concept("LE7", "Inverse (Opposite) Operations");
    private static final Concept INTEGER_OPERATIONS = new Concept("LE8", "Operations on Integers");
    private static final Concept FRACTION_BASICS = new Concept("LE9", "LCM and Adding Fractions");

    public LinearEquations() {
        super("Class 8", "Linear Equations");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "If x = 4, what is the value of 3x + 2?",
                        Arrays.asList("10", "12", "14", "20"),
                        2,
                        SUBSTITUTION
                ),
                new Question(
                        "If y = 5, what is the value of 2y - 3?",
                        Arrays.asList("5", "7", "10", "13"),
                        1,
                        SUBSTITUTION
                ),
                new Question(
                        "What is the inverse (opposite) operation of addition?",
                        Arrays.asList("Multiplication", "Subtraction", "Addition", "Division"),
                        1,
                        INVERSE_OPERATIONS
                ),
                new Question(
                        "What is the inverse operation of multiplying by 5?",
                        Arrays.asList("Multiplying by 5", "Subtracting 5", "Dividing by 5", "Adding 5"),
                        2,
                        INVERSE_OPERATIONS
                ),
                new Question(
                        "What is (-8) + 3?",
                        Arrays.asList("-11", "-5", "5", "11"),
                        1,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-2) x (-6)?",
                        Arrays.asList("-12", "-8", "8", "12"),
                        3,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is the LCM of 2, 3 and 4?",
                        Arrays.asList("6", "9", "12", "24"),
                        2,
                        FRACTION_BASICS
                ),
                new Question(
                        "What is 1/2 + 1/3?",
                        Arrays.asList("2/5", "5/6", "1/6", "2/6"),
                        1,
                        FRACTION_BASICS
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    // Full bank kept separate so the retest can filter it by concept
    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "Which of these is a linear equation in one variable?",
                        Arrays.asList("x² + 3 = 7", "2x + 5 = 11", "x + y = 4", "1/x = 3"),
                        1,
                        WHAT_IS_LINEAR
                ),
                new Question(
                        "The solution (root) of an equation is the value of the variable that:",
                        Arrays.asList("makes both sides equal", "makes the left side zero", "makes the equation longer", "is always positive"),
                        0,
                        WHAT_IS_LINEAR
                ),
                new Question(
                        "Is x = 3 a solution of 2x + 1 = 7?",
                        Arrays.asList("Yes, because both sides equal 7", "No, because the left side is 6", "No, because the left side is 5", "Yes, because x is positive"),
                        0,
                        WHAT_IS_LINEAR
                ),
                new Question(
                        "Solve: x + 7 = 12",
                        Arrays.asList("19", "5", "-5", "7"),
                        1,
                        SOLVE_BASIC
                ),
                new Question(
                        "Solve: 3x = 18",
                        Arrays.asList("6", "15", "21", "54"),
                        0,
                        SOLVE_BASIC
                ),
                new Question(
                        "Solve: 2x + 3 = 11",
                        Arrays.asList("7", "4", "8", "14"),
                        1,
                        SOLVE_BASIC
                ),
                new Question(
                        "Solve: 5x - 3 = 2x + 9",
                        Arrays.asList("2", "3", "4", "6"),
                        2,
                        BOTH_SIDES
                ),
                new Question(
                        "Solve: 6x + 2 = 2x + 22",
                        Arrays.asList("4", "5", "6", "10"),
                        1,
                        BOTH_SIDES
                ),
                new Question(
                        "When you move a term to the other side of an equation (transposition), its sign:",
                        Arrays.asList("stays the same", "changes (+ becomes -, - becomes +)", "becomes zero", "is doubled"),
                        1,
                        BOTH_SIDES
                ),
                new Question(
                        "Solve: 3(x + 2) = 15",
                        Arrays.asList("3", "5", "7", "13"),
                        0,
                        BRACKETS_FRACTIONS
                ),
                new Question(
                        "Solve: x/2 + 1 = 4",
                        Arrays.asList("3", "6", "8", "10"),
                        1,
                        BRACKETS_FRACTIONS
                ),
                new Question(
                        "Solve: 2x/3 = 8",
                        Arrays.asList("12", "16", "24", "11"),
                        0,
                        BRACKETS_FRACTIONS
                ),
                new Question(
                        "A number increased by 9 gives 25. Which equation represents this?",
                        Arrays.asList("x - 9 = 25", "x + 9 = 25", "9x = 25", "x/9 = 25"),
                        1,
                        WORD_PROBLEMS
                ),
                new Question(
                        "The sum of two consecutive numbers is 41. If the smaller number is x, the equation is:",
                        Arrays.asList("x + (x + 1) = 41", "x + (x + 2) = 41", "x(x + 1) = 41", "2x = 42"),
                        0,
                        WORD_PROBLEMS
                ),
                new Question(
                        "A father's age is 3 times his son's age, and together they are 48. How old is the son?",
                        Arrays.asList("9", "12", "16", "36"),
                        1,
                        WORD_PROBLEMS
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return new Test(getLessonQuestionBank());
        }
        return new Test(filtered);
    }

    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        WHAT_IS_LINEAR,
                        "A linear equation in one variable is an equation where the variable has power 1 and "
                                + "appears only once as a letter (no x², no 1/x, no second variable). The solution, or "
                                + "root, is the value of the variable that makes both sides equal.",
                        "ax + b = c, where a ≠ 0",
                        Arrays.asList(
                                "2x + 5 = 11 is linear. Putting x = 3 gives 2(3) + 5 = 11, so x = 3 is the solution",
                                "x² + 3 = 7 is not linear because the power of x is 2"
                        )
                ),
                new LessonContent(
                        SOLVE_BASIC,
                        "To solve an equation, undo what has been done to x using inverse operations, and do the "
                                + "same thing to both sides so the equation stays balanced. Undo addition or subtraction first, "
                                + "then multiplication or division.",
                        "Whatever you do to one side, do to the other",
                        Arrays.asList(
                                "x + 7 = 12 -> x = 12 - 7 = 5",
                                "2x + 3 = 11 -> 2x = 8 -> x = 4"
                        )
                ),
                new LessonContent(
                        BOTH_SIDES,
                        "When x appears on both sides, move all the x terms to one side and all the numbers to the "
                                + "other. Moving a term across the equals sign (transposition) changes its sign.",
                        "Transposition: + becomes -, and - becomes +",
                        Arrays.asList(
                                "5x - 3 = 2x + 9 -> 5x - 2x = 9 + 3 -> 3x = 12 -> x = 4",
                                "6x + 2 = 2x + 22 -> 4x = 20 -> x = 5"
                        )
                ),
                new LessonContent(
                        BRACKETS_FRACTIONS,
                        "Remove brackets first by multiplying them out. For fractions, multiply every term by the "
                                + "LCM of the denominators to clear them, then solve as usual.",
                        "a(b + c) = ab + ac",
                        Arrays.asList(
                                "3(x + 2) = 15 -> 3x + 6 = 15 -> 3x = 9 -> x = 3",
                                "x/2 + 1 = 4 -> x/2 = 3 -> x = 6"
                        )
                ),
                new LessonContent(
                        WORD_PROBLEMS,
                        "To solve a word problem, let the unknown be x, translate each sentence into an equation, "
                                + "solve it, and check the answer against the story.",
                        "Let the unknown = x, form the equation, solve, then verify",
                        Arrays.asList(
                                "A number increased by 9 gives 25: x + 9 = 25, so x = 16",
                                "The sum of two consecutive numbers is 31: x + (x + 1) = 31 -> 2x = 30 -> x = 15, so the numbers are 15 and 16"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}