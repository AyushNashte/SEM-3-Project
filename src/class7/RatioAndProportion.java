package class7;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RatioAndProportion extends Topic {

    // ---- Lesson concepts ----
    private static final Concept RATIO_MEANING = new Concept("RP1", "Meaning and Simplest Form of a Ratio");
    private static final Concept EQUIVALENT    = new Concept("RP2", "Equivalent Ratios");
    private static final Concept PROPORTION    = new Concept("RP3", "Proportion (Finding the Missing Term)");
    private static final Concept UNITARY       = new Concept("RP4", "Unitary Method");
    private static final Concept SHARING       = new Concept("RP5", "Sharing in a Given Ratio");

    // ---- Prerequisite concepts (Class 6 knowledge) ----
    private static final Concept FACTORS_HCF   = new Concept("RP6", "Common Factors and Simplifying");
    private static final Concept TABLES        = new Concept("RP7", "Multiplication and Division Facts");
    private static final Concept EQUIV_FRAC    = new Concept("RP8", "Equivalent Fractions");
    private static final Concept UNITS         = new Concept("RP9", "Units and Conversion");

    public RatioAndProportion() {
        super("Class 7", "Ratio & Proportion");
    }

    // ==========================================================
    // 1. PREREQUISITE TEST
    // ==========================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("What is the HCF of 12 and 18?",
                        Arrays.asList("3", "6", "9", "12"), 1, FACTORS_HCF),
                new Question("Simplify the fraction 8/12 to its lowest terms.",
                        Arrays.asList("3/4", "4/6", "1/2", "2/3"), 3, FACTORS_HCF),

                new Question("What is 7 x 6?",
                        Arrays.asList("36", "48", "42", "49"), 2, TABLES),
                new Question("What is 72 / 8?",
                        Arrays.asList("9", "8", "7", "12"), 0, TABLES),

                new Question("Which fraction is equal to 1/2?",
                        Arrays.asList("2/3", "2/5", "4/6", "3/6"), 3, EQUIV_FRAC),
                new Question("2/5 = ?/15. What is the missing number?",
                        Arrays.asList("4", "5", "6", "10"), 2, EQUIV_FRAC),

                new Question("How many centimetres are there in 1 metre?",
                        Arrays.asList("10", "100", "1000", "60"), 1, UNITS),
                new Question("Convert 2 hours into minutes.",
                        Arrays.asList("60", "100", "120", "200"), 2, UNITS)
        );
        return new Test(questions);
    }

    // ==========================================================
    // 2. LESSON TEST (3 per concept)
    // ==========================================================
    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                // ---- RP1: Meaning and simplest form ----
                new Question("A class has 12 boys and 18 girls. What is the ratio of boys to girls in simplest form?",
                        Arrays.asList("12 : 18", "3 : 2", "2 : 3", "6 : 9"), 2, RATIO_MEANING),
                // Trap: 6 : 10 and 12 : 20 are equal in value but NOT in simplest form
                new Question("Simplify the ratio 24 : 40 to its simplest form.",
                        Arrays.asList("3 : 5", "6 : 10", "12 : 20", "4 : 7"), 0, RATIO_MEANING),
                // Trap: both quantities must be in the same unit first (2 m = 200 cm)
                new Question("What is the ratio of 50 cm to 2 m in simplest form?",
                        Arrays.asList("25 : 1", "1 : 4", "50 : 2", "1 : 40"), 1, RATIO_MEANING),

                // ---- RP2: Equivalent ratios ----
                new Question("Which ratio is equivalent to 3 : 5?",
                        Arrays.asList("3 : 10", "6 : 8", "6 : 10", "9 : 10"), 2, EQUIVALENT),
                new Question("Fill in the blank: 4 : 7 = 12 : __",
                        Arrays.asList("19", "21", "24", "28"), 1, EQUIVALENT),
                new Question("Which pair of ratios is equivalent?",
                        Arrays.asList("2 : 3 and 4 : 5", "1 : 2 and 3 : 5", "5 : 6 and 10 : 13", "3 : 4 and 9 : 12"), 3, EQUIVALENT),

                // ---- RP3: Proportion ----
                new Question("Four numbers a, b, c, d are in proportion (a : b = c : d) when:",
                        Arrays.asList("a + b = c + d", "a x b = c x d", "a - b = c - d", "a x d = b x c"), 3, PROPORTION),
                new Question("Find x if 3 : 4 = 9 : x.",
                        Arrays.asList("10", "12", "15", "16"), 1, PROPORTION),
                new Question("Are 4, 6, 10, 15 in proportion?",
                        Arrays.asList("No, because 4 + 15 is not 6 + 10",
                                "No, because 4 x 6 is not 10 x 15",
                                "Yes, because 4 x 15 = 6 x 10",
                                "Yes, because 4 + 6 = 10"), 2, PROPORTION),

                // ---- RP4: Unitary method ----
                new Question("5 pens cost Rs 40. What is the cost of 1 pen?",
                        Arrays.asList("Rs 35", "Rs 8", "Rs 200", "Rs 5"), 1, UNITARY),
                new Question("A car goes 150 km in 3 hours at a steady speed. How far will it go in 5 hours?",
                        Arrays.asList("200 km", "300 km", "250 km", "450 km"), 2, UNITARY),
                new Question("12 notebooks cost Rs 180. What do 7 notebooks cost?",
                        Arrays.asList("Rs 90", "Rs 120", "Rs 85", "Rs 105"), 3, UNITARY),

                // ---- RP5: Sharing in a ratio ----
                new Question("Rs 100 is shared between A and B in the ratio 2 : 3. How much does A get?",
                        Arrays.asList("Rs 20", "Rs 60", "Rs 40", "Rs 50"), 2, SHARING),
                new Question("45 sweets are divided in the ratio 4 : 5. How many sweets are in the larger share?",
                        Arrays.asList("20", "25", "30", "36"), 1, SHARING),
                // Trap: shares are Rs 24 and Rs 48, so the DIFFERENCE is 24, not 48
                new Question("Rs 72 is shared in the ratio 1 : 2. What is the difference between the two shares?",
                        Arrays.asList("Rs 48", "Rs 36", "Rs 12", "Rs 24"), 3, SHARING)
        );
    }

    // ==========================================================
    // 3. RETEST
    // ==========================================================
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

    // ==========================================================
    // 4. LESSON CONTENT
    // ==========================================================
    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        RATIO_MEANING,
                        "A ratio compares two quantities of the SAME kind by division. It is written a : b and the "
                                + "order matters: boys : girls is not the same as girls : boys. Both quantities must be in "
                                + "the same unit, so convert first if needed. To simplify a ratio, divide both terms by "
                                + "their HCF until no common factor is left.",
                        "a : b = (a / k) : (b / k)",
                        Arrays.asList(
                                "12 boys : 18 girls = 2 : 3 (divide both by 6)",
                                "24 : 40 = 3 : 5 (divide both by 8, the HCF)",
                                "50 cm : 2 m = 50 cm : 200 cm = 1 : 4 (same unit first)"
                        )
                ),
                new LessonContent(
                        EQUIVALENT,
                        "Equivalent ratios have the same value. You get one by multiplying or dividing BOTH terms of "
                                + "a ratio by the same non-zero number. Two ratios are equivalent if they simplify to the "
                                + "same simplest form.",
                        "a : b = (a x k) : (b x k)",
                        Arrays.asList(
                                "3 : 5 = 6 : 10 = 9 : 15 (multiply by 2, then by 3)",
                                "4 : 7 = 12 : 21 (both terms multiplied by 3)",
                                "3 : 4 and 9 : 12 are equivalent, because 9 : 12 divided by 3 is 3 : 4"
                        )
                ),
                new LessonContent(
                        PROPORTION,
                        "When two ratios are equal, the four numbers are said to be in proportion: a : b = c : d. "
                                + "In a proportion, the product of the outer terms (a x d) equals the product of the inner "
                                + "terms (b x c). Use this to check a proportion or to find a missing term.",
                        "a : b = c : d   means   a x d = b x c",
                        Arrays.asList(
                                "3 : 4 = 9 : x gives 3 x x = 4 x 9, so 3x = 36 and x = 12",
                                "4, 6, 10, 15: 4 x 15 = 60 and 6 x 10 = 60, so they ARE in proportion",
                                "2 : 5 = x : 20 gives 5 x x = 2 x 20, so 5x = 40 and x = 8"
                        )
                ),
                new LessonContent(
                        UNITARY,
                        "In the unitary method you first find the value of ONE unit by dividing, and then multiply to "
                                + "get the value of the number of units you need. It works whenever more items cost "
                                + "proportionally more (direct proportion).",
                        "Value of 1 = total / number,   Value of n = (value of 1) x n",
                        Arrays.asList(
                                "5 pens cost Rs 40, so 1 pen = 40 / 5 = Rs 8 and 9 pens = 8 x 9 = Rs 72",
                                "150 km in 3 h gives 50 km in 1 h, so in 5 h it is 50 x 5 = 250 km",
                                "12 notebooks cost Rs 180, so 1 = Rs 15 and 7 notebooks = 15 x 7 = Rs 105"
                        )
                ),
                new LessonContent(
                        SHARING,
                        "To share an amount in a given ratio: add the ratio terms to get the total number of parts, "
                                + "divide the amount by the total parts to find the value of ONE part, and then multiply "
                                + "each ratio term by that value.",
                        "One part = Amount / (a + b),   Shares = a x part and b x part",
                        Arrays.asList(
                                "Rs 100 in the ratio 2 : 3: parts = 5, one part = Rs 20, so A = Rs 40 and B = Rs 60",
                                "45 sweets in the ratio 4 : 5: parts = 9, one part = 5, so the shares are 20 and 25",
                                "Rs 72 in the ratio 1 : 2: parts = 3, one part = Rs 24, so the shares are Rs 24 and Rs 48"
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

    @Override
    public java.util.Optional<String> getGameId() {
        return java.util.Optional.of("smoothie-rush");
    }
}