package me.alexand.intech.client.model;

/**
 * @author asidorov84@gmail.com
 */
public enum ButtonsNames {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    AST("*"),
    ZERO("0"),
    SHARP("#");

    private String name;

    ButtonsNames(String name) {
        this.name = name;
    }

    public static ButtonsNames getByName(String name) {
        switch (name) {
            case "1": return ONE;
            case "2": return TWO;
            case "3": return THREE;
            case "4": return FOUR;
            case "5": return FIVE;
            case "6": return SIX;
            case "7": return SEVEN;
            case "8": return EIGHT;
            case "9": return NINE;
            case "*": return AST;
            case "0": return ZERO;
            case "#": return SHARP;
        }

        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return name;
    }
}
