package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PercentageAndFinancialMaths extends Topic {

    // Lesson concepts
    private static final Concept PERCENT_BASICS = new Concept("PF1", "Percentage as Fraction and Decimal");
    private static final Concept PERCENT_CHANGE = new Concept("PF2", "Percentage Increase and Decrease");
    private static final Concept PROFIT_LOSS = new Concept("PF3", "Profit and Loss");
    private static final Concept DISCOUNT = new Concept("PF4", "Discount and Selling Price");
    private static final Concept INTEREST = new Concept("PF5", "Simple and Compound Interest");

    // Prerequisite concepts
    private static final Concept FRACTION_DECIMAL = new Concept("PF6", "Converting Fractions and Decimals");
    private static final Concept MULTIPLY_DIVIDE = new Concept("PF7", "Multiplication and Division");
    private static final Concept RATIO_BASICS = new Concept("PF8", "Ratios");
    private static final Concept DECIMAL_OPERATIONS = new Concept("PF9", "Operations on Decimals");

    public PercentageAndFinancialMaths() {
        super("Class 8", "Percentage & Financial Maths");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "Write 3/4 as a decimal.",
                        Arrays.asList("0.34", "0.25", "0.75", "1.25"),
                        2,
                        FRACTION_DECIMAL
                ),
                new Question(
                        "Write 0.4 as a fraction in simplest form.",
                        Arrays.asList("4/100", "1/4", "4/5", "2/5"),
                        3,
                        FRACTION_DECIMAL
                ),
                new Question(
                        "What is 250 x 4?",
                        Arrays.asList("800", "1000", "1200", "254"),
                        1,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "What is 1200 ÷ 100?",
                        Arrays.asList("120", "1.2", "1200", "12"),
                        3,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "Simplify the ratio 20:50.",
                        Arrays.asList("2:5", "1:2", "4:5", "2:3"),
                        0,
                        RATIO_BASICS
                ),
                new Question(
                        "If two quantities are in the ratio 1:4, the first is what fraction of the total?",
                        Arrays.asList("1/4", "4/5", "1/5", "1/3"),
                        2,
                        RATIO_BASICS
                ),
                new Question(
                        "What is 0.5 x 80?",
                        Arrays.asList("4", "40", "400", "0.4"),
                        1,
                        DECIMAL_OPERATIONS
                ),
                new Question(
                        "What is 12.5 + 7.5?",
                        Arrays.asList("19", "21", "18", "20"),
                        3,
                        DECIMAL_OPERATIONS
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
                        "The words 'per cent' mean:",
                        Arrays.asList("per ten", "per hundred", "per thousand", "per one"),
                        1,
                        PERCENT_BASICS
                ),
                new Question(
                        "Write 35% as a fraction in simplest form.",
                        Arrays.asList("35/10", "3/5", "7/20", "7/10"),
                        2,
                        PERCENT_BASICS
                ),
                new Question(
                        "Find 20% of 150.",
                        Arrays.asList("20", "30", "75", "300"),
                        1,
                        PERCENT_BASICS
                ),
                new Question(
                        "A price rises from Rs. 200 to Rs. 250. What is the percentage increase?",
                        Arrays.asList("20%", "25%", "50%", "5%"),
                        1,
                        PERCENT_CHANGE
                ),
                new Question(
                        "A quantity falls from 80 to 60. What is the percentage decrease?",
                        Arrays.asList("20%", "33%", "25%", "75%"),
                        2,
                        PERCENT_CHANGE
                ),
                new Question(
                        "Percentage increase or decrease is always calculated on the:",
                        Arrays.asList("larger value", "new value", "difference only", "original value"),
                        3,
                        PERCENT_CHANGE
                ),
                new Question(
                        "An article bought for Rs. 400 is sold for Rs. 460. What is the profit?",
                        Arrays.asList("Rs. 40", "Rs. 60", "Rs. 460", "Rs. 860"),
                        1,
                        PROFIT_LOSS
                ),
                new Question(
                        "Which formula gives the profit percentage?",
                        Arrays.asList("(Profit / Selling price) x 100", "(Profit / Cost price) x 100", "(Cost price / Profit) x 100", "(Loss / Cost price) x 100"),
                        1,
                        PROFIT_LOSS
                ),
                new Question(
                        "A shirt bought for Rs. 500 is sold for Rs. 450. What is the loss percentage?",
                        Arrays.asList("5%", "11%", "10%", "50%"),
                        2,
                        PROFIT_LOSS
                ),
                new Question(
                        "A bag marked at Rs. 800 is sold at a 10% discount. What is the selling price?",
                        Arrays.asList("Rs. 80", "Rs. 790", "Rs. 720", "Rs. 880"),
                        2,
                        DISCOUNT
                ),
                new Question(
                        "Discount is calculated on the:",
                        Arrays.asList("cost price", "marked price", "selling price", "profit"),
                        1,
                        DISCOUNT
                ),
                new Question(
                        "A toy marked at Rs. 500 is sold for Rs. 450. What is the discount percentage?",
                        Arrays.asList("10%", "5%", "45%", "50%"),
                        0,
                        DISCOUNT
                ),
                new Question(
                        "Which formula gives simple interest?",
                        Arrays.asList("SI = P x R x T / 100", "SI = P + R + T", "SI = P x R / T", "SI = (P + R) x T / 100"),
                        0,
                        INTEREST
                ),
                new Question(
                        "Find the simple interest on Rs. 2000 at 5% per annum for 3 years.",
                        Arrays.asList("Rs. 100", "Rs. 150", "Rs. 300", "Rs. 600"),
                        2,
                        INTEREST
                ),
                new Question(
                        "What is the amount on Rs. 1000 at 10% per annum compounded annually for 2 years?",
                        Arrays.asList("Rs. 1200", "Rs. 1100", "Rs. 1210", "Rs. 1220"),
                        2,
                        INTEREST
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
                        PERCENT_BASICS,
                        "Per cent means 'per hundred', so x% is the same as x/100. To find a percentage of a "
                                + "quantity, multiply the quantity by x/100.",
                        "x% = x/100     x% of N = (x/100) x N",
                        Arrays.asList(
                                "35% = 35/100 = 7/20",
                                "20% of 150 = (20/100) x 150 = 30"
                        )
                ),
                new LessonContent(
                        PERCENT_CHANGE,
                        "A percentage change compares the change with the ORIGINAL value. It is an increase if the "
                                + "value grows and a decrease if it falls.",
                        "Percentage change = (Change / Original value) x 100",
                        Arrays.asList(
                                "Rs. 200 to Rs. 250: change 50, so (50/200) x 100 = 25% increase",
                                "80 to 60: change 20, so (20/80) x 100 = 25% decrease"
                        )
                ),
                new LessonContent(
                        PROFIT_LOSS,
                        "Cost price (CP) is what you pay and selling price (SP) is what you get. If SP is more than "
                                + "CP there is a profit, and if SP is less there is a loss. Both percentages are worked out "
                                + "on the cost price.",
                        "Profit = SP - CP     Loss = CP - SP     Profit% = (Profit/CP) x 100     Loss% = (Loss/CP) x 100",
                        Arrays.asList(
                                "CP Rs. 400, SP Rs. 460: profit = 60, Profit% = (60/400) x 100 = 15%",
                                "CP Rs. 500, SP Rs. 450: loss = 50, Loss% = (50/500) x 100 = 10%"
                        )
                ),
                new LessonContent(
                        DISCOUNT,
                        "A discount is a reduction on the marked price (MP), the price printed on the article. "
                                + "The discount percentage is worked out on the marked price.",
                        "Discount = MP - SP     Discount% = (Discount/MP) x 100     SP = MP - Discount",
                        Arrays.asList(
                                "MP Rs. 800 with 10% discount: discount = 80, SP = 800 - 80 = Rs. 720",
                                "MP Rs. 500, SP Rs. 450: discount = 50, Discount% = (50/500) x 100 = 10%"
                        )
                ),
                new LessonContent(
                        INTEREST,
                        "Interest is the extra money paid for borrowing or earning on savings. Simple interest is "
                                + "always worked out on the original principal. In compound interest, each year's interest "
                                + "is added to the principal before the next year's interest is calculated.",
                        "SI = (P x R x T)/100     Amount A = P + SI     Compound: A = P (1 + R/100)^n,  CI = A - P",
                        Arrays.asList(
                                "SI on Rs. 2000 at 5% for 3 years = (2000 x 5 x 3)/100 = Rs. 300",
                                "Rs. 1000 at 10% compounded for 2 years: A = 1000 x 1.1 x 1.1 = Rs. 1210, so CI = Rs. 210"
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