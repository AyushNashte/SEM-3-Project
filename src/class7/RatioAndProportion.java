package class7;
import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class 7 - Ratio & Proportion
 * Framed around recipes, maps, and sharing money/prizes so scaling
 * quantities up or down feels intuitive.
 */
public class RatioAndProportion extends Topic {

    private static final Concept RATIO_BASICS = new Concept("RP1", "Understanding Ratios");
    private static final Concept EQUIVALENT_RATIOS = new Concept("RP2", "Equivalent Ratios & Simplifying");
    private static final Concept PROPORTION = new Concept("RP3", "Proportion");
    private static final Concept UNITARY_METHOD = new Concept("RP4", "Unitary Method");
    private static final Concept SHARING = new Concept("RP5", "Dividing a Quantity in a Given Ratio");

    public RatioAndProportion() {
        super("Class 7", "Ratio & Proportion");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is 12 ÷ 4?",
                        Arrays.asList("2", "3", "4", "6"),
                        1,
                        UNITARY_METHOD
                ),
                new Question(
                        "Simplify the fraction 4/8.",
                        Arrays.asList("1/2", "2/4", "1/4", "4/8 cannot simplify"),
                        0,
                        EQUIVALENT_RATIOS
                ),
                new Question(
                        "What is the HCF of 6 and 9?",
                        Arrays.asList("1", "2", "3", "6"),
                        2,
                        EQUIVALENT_RATIOS
                ),
                new Question(
                        "If 1 pen costs ₹10, what do 3 pens cost?",
                        Arrays.asList("₹13", "₹20", "₹30", "₹40"),
                        2,
                        UNITARY_METHOD
                ),
                new Question(
                        "What is 15 x 2?",
                        Arrays.asList("17", "25", "30", "32"),
                        2,
                        PROPORTION
                ),
                new Question(
                        "In a class of 20 students, 12 are boys. How many are girls?",
                        Arrays.asList("6", "8", "10", "12"),
                        1,
                        RATIO_BASICS
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "A fruit basket has 6 apples and 4 oranges. What is the ratio of apples to oranges?",
                        Arrays.asList("2:3", "3:2", "4:6", "6:10"),
                        1,
                        RATIO_BASICS
                ),
                new Question(
                        "A recipe needs flour and sugar in the ratio 3:1. If you use 9 cups of flour, how many cups of sugar?",
                        Arrays.asList("1", "2", "3", "4"),
                        2,
                        RATIO_BASICS
                ),
                new Question(
                        "Which ratio is equivalent to 2:5?",
                        Arrays.asList("4:10", "5:2", "2:10", "4:5"),
                        0,
                        EQUIVALENT_RATIOS
                ),
                new Question(
                        "Simplify the ratio 18:24 to its lowest terms.",
                        Arrays.asList("6:8", "9:12", "3:4", "2:3"),
                        2,
                        EQUIVALENT_RATIOS
                ),
                new Question(
                        "Are 2:3 and 8:12 in proportion?",
                        Arrays.asList("Yes", "No", "Cannot determine", "Only if numbers are equal"),
                        0,
                        PROPORTION
                ),
                new Question(
                        "If 4 : 6 :: x : 9, find x.",
                        Arrays.asList("5", "6", "7", "8"),
                        1,
                        PROPORTION
                ),
                new Question(
                        "A map shows a scale where 2 cm represents 50 km. Two cities are 6 cm apart on the map. What is the real distance?",
                        Arrays.asList("100 km", "150 km", "200 km", "300 km"),
                        1,
                        PROPORTION
                ),
                new Question(
                        "If 5 chocolates cost ₹75, what is the cost of 1 chocolate?",
                        Arrays.asList("₹10", "₹12", "₹15", "₹20"),
                        2,
                        UNITARY_METHOD
                ),
                new Question(
                        "A car travels 240 km in 4 hours at constant speed. How far does it travel in 1 hour?",
                        Arrays.asList("40 km", "50 km", "60 km", "80 km"),
                        2,
                        UNITARY_METHOD
                ),
                new Question(
                        "If 8 workers can build a wall in 6 days, how many days for 1 worker (same rate, working alone)?",
                        Arrays.asList("14 days", "36 days", "48 days", "56 days"),
                        2,
                        UNITARY_METHOD
                ),
                new Question(
                        "₹600 is to be shared between two friends in the ratio 2:3. How much does the first friend get?",
                        Arrays.asList("₹200", "₹240", "₹300", "₹360"),
                        1,
                        SHARING
                ),
                new Question(
                        "A prize of ₹1000 is split among 3 winners in the ratio 5:3:2. How much does the winner with the largest share get?",
                        Arrays.asList("₹200", "₹300", "₹500", "₹600"),
                        2,
                        SHARING
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
                        RATIO_BASICS,
                        "A ratio compares two quantities of the same kind, showing how much of one thing "
                                + "there is compared to another. It is written as a : b, and read as 'a to b'.",
                        "Ratio of a to b = a : b = a/b",
                        Arrays.asList(
                                "A team has 5 boys and 3 girls -> ratio of boys to girls = 5:3",
                                "Mixing 2 parts water with 1 part juice -> ratio = 2:1"
                        )
                ),
                new LessonContent(
                        EQUIVALENT_RATIOS,
                        "Equivalent ratios represent the same comparison, just scaled up or down -- multiply "
                                + "or divide both terms by the same number. A ratio is in its simplest form when "
                                + "both terms have no common factor other than 1 (divide both by their HCF).",
                        null,
                        Arrays.asList(
                                "2:3 = 4:6 = 6:9 (all multiplied by the same number)",
                                "Simplify 12:18 by dividing both by their HCF (6) -> 2:3"
                        )
                ),
                new LessonContent(
                        PROPORTION,
                        "A proportion says that two ratios are equal. Four numbers a, b, c, d are in "
                                + "proportion if a:b = c:d, which is also written a:b :: c:d. A quick way to "
                                + "check: the product of the extremes equals the product of the means.",
                        "a:b :: c:d  is true when a x d = b x c",
                        Arrays.asList(
                                "Check if 3:4 and 9:12 are in proportion: 3x12 = 36 and 4x9 = 36 -> yes, proportional",
                                "Find x if 5:x :: 10:8 -> 5x8 = x x10 -> x = 4"
                        )
                ),
                new LessonContent(
                        UNITARY_METHOD,
                        "The unitary method finds the value of a single unit first, then uses it to find the "
                                + "value of any other number of units. Step 1: find the value for 1 unit "
                                + "(divide). Step 2: multiply by however many units you need.",
                        null,
                        Arrays.asList(
                                "6 pens cost ₹90 -> 1 pen costs ₹15 -> 4 pens cost ₹60",
                                "A car covers 180 km in 3 hours -> 1 hour = 60 km -> 5 hours = 300 km"
                        )
                ),
                new LessonContent(
                        SHARING,
                        "To divide a quantity in a given ratio, add up the parts of the ratio to find the "
                                + "total number of shares, find the value of one share, then multiply by each "
                                + "person's number of parts.",
                        null,
                        Arrays.asList(
                                "Share ₹500 in the ratio 2:3 -> total parts = 5, 1 part = ₹100 -> shares are ₹200 and ₹300",
                                "Split 24 sweets in the ratio 1:2:3 -> total parts = 6, 1 part = 4 sweets -> 4, 8, 12 sweets"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts in Ratio & Proportion:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}