package ohi.andre.consolelauncher.managers;

import java.util.Arrays;

/**
 * Built-in terminal color schemes for the `theme` command.
 *
 * These are not wired into the XML theme system yet; they are primarily
 * used for listing and, in the future, could be mapped to XMLPrefs values.
 */
public final class ThemePalette {

    public static final class Scheme {
        public final String name;
        public final String background;
        public final String foreground;
        public final String cursorColor;
        public final String selectionBackground;
        public final String black;
        public final String red;
        public final String green;
        public final String yellow;
        public final String blue;
        public final String purple;
        public final String cyan;
        public final String white;
        public final String brightBlack;
        public final String brightRed;
        public final String brightGreen;
        public final String brightYellow;
        public final String brightBlue;
        public final String brightPurple;
        public final String brightCyan;
        public final String brightWhite;

        public Scheme(String name,
                      String background,
                      String foreground,
                      String cursorColor,
                      String selectionBackground,
                      String black,
                      String red,
                      String green,
                      String yellow,
                      String blue,
                      String purple,
                      String cyan,
                      String white,
                      String brightBlack,
                      String brightRed,
                      String brightGreen,
                      String brightYellow,
                      String brightBlue,
                      String brightPurple,
                      String brightCyan,
                      String brightWhite) {
            this.name = name;
            this.background = background;
            this.foreground = foreground;
            this.cursorColor = cursorColor;
            this.selectionBackground = selectionBackground;
            this.black = black;
            this.red = red;
            this.green = green;
            this.yellow = yellow;
            this.blue = blue;
            this.purple = purple;
            this.cyan = cyan;
            this.white = white;
            this.brightBlack = brightBlack;
            this.brightRed = brightRed;
            this.brightGreen = brightGreen;
            this.brightYellow = brightYellow;
            this.brightBlue = brightBlue;
            this.brightPurple = brightPurple;
            this.brightCyan = brightCyan;
            this.brightWhite = brightWhite;
        }
    }

    // NOTE: values are taken directly from the user-provided JSON definitions.
    private static final Scheme[] SCHEMES = new Scheme[] {
            new Scheme(
                    "Catppuccin",
                    "#1E1E2E", "#D9E0EE", "#C9CBFF", "#C9CBFF",
                    "#6E6C7E", "#F28FAD", "#ABE9B3", "#FAE3B0",
                    "#96CDFB", "#F5C2E7", "#89DCEB", "#C3BAC6",
                    "#988BA2", "#F28FAD", "#ABE9B3", "#FAE3B0",
                    "#96CDFB", "#F5C2E7", "#89DCEB", "#D9E0EE"
            ),
            new Scheme(
                    "Color Scheme 17",
                    "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF",
                    "#0C0C0C", "#C50F1F", "#13A10E", "#C19C00",
                    "#0037DA", "#881798", "#3A96DD", "#CCCCCC",
                    "#767676", "#E74856", "#16C60C", "#F9F1A5",
                    "#3B78FF", "#B4009E", "#61D6D6", "#F2F2F2"
            ),
            new Scheme(
                    "Gruvbox Dark",
                    "#282828", "#EBDBB2", "#FFFFFF", "#FFFFFF",
                    "#282828", "#CC241D", "#98971A", "#D79921",
                    "#458588", "#B16286", "#689D6A", "#A89984",
                    "#928374", "#FB4934", "#B8BB26", "#FABD2F",
                    "#83A598", "#D3869B", "#8EC07C", "#EBDBB2"
            ),
            new Scheme(
                    "Gruvbox Dark 2",
                    "#282828", "#EBDBB2", "#FFFFFF", "#FFFFFF",
                    "#282828", "#CC241D", "#98971A", "#D79921",
                    "#458588", "#B16286", "#689D6A", "#A89984",
                    "#928374", "#FB4934", "#B8BB26", "#FABD2F",
                    "#83A598", "#D3869B", "#8EC07C", "#EBDBB2"
            ),
            new Scheme(
                    "Gruvbox Light",
                    "#FFFFAF", "#3C3836", "#282828", "#282828",
                    "#282828", "#9D0006", "#79740E", "#B57614",
                    "#076678", "#8F3F71", "#427B58", "#A89984",
                    "#A89984", "#9D0006", "#79740E", "#B57614",
                    "#076678", "#8F3F71", "#427B58", "#3C3836"
            ),
            new Scheme(
                    "Gruvbox-Material Hard Dark",
                    "#1D2021", "#D4BE98", "#FFFFFF", "#FFFFFF",
                    "#665C54", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98",
                    "#928374", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98"
            ),
            new Scheme(
                    "Gruvbox-Material Hard Light",
                    "#F9F5D7", "#654735", "#FFFFFF", "#FFFFFF",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98"
            ),
            new Scheme(
                    "Gruvbox-Material Medium Dark",
                    "#282828", "#D4BE98", "#FFFFFF", "#FFFFFF",
                    "#665C54", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98",
                    "#928374", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98"
            ),
            new Scheme(
                    "Gruvbox-Material Medium Light",
                    "#FBF1C7", "#654735", "#FFFFFF", "#FFFFFF",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98"
            ),
            new Scheme(
                    "Gruvbox-Material Soft Dark",
                    "#32302F", "#D4BE98", "#FFFFFF", "#FFFFFF",
                    "#665C54", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98",
                    "#928374", "#EA6962", "#A9B665", "#D8A657",
                    "#7DAEA3", "#D3869B", "#89B482", "#D4BE98"
            ),
            new Scheme(
                    "Gruvbox-Material Soft Light",
                    "#F2E5BC", "#654735", "#FFFFFF", "#FFFFFF",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98",
                    "#504945", "#C14A4A", "#6C782E", "#B47109",
                    "#45707A", "#945E80", "#4C7A5D", "#D4BE98"
            ),
            new Scheme(
                    "Monokai Night",
                    "#1F1F1F", "#F8F8F8", "#FFFFFF", "#FFFFFF",
                    "#1F1F1F", "#F92672", "#A6E22E", "#E6DB74",
                    "#6699DF", "#AE81FF", "#E69F66", "#F8F8F2",
                    "#75715E", "#F92672", "#A6E22E", "#E6DB74",
                    "#66D9EF", "#AE81FF", "#E69F66", "#F8F8F2"
            ),
            new Scheme(
                    "test",
                    "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF",
                    "#0C0C0C", "#0C0C0C", "#0C0C0C", "#0C0C0C",
                    "#0C0C0C", "#0C0C0C", "#0C0C0C", "#0C0C0C",
                    "#767676", "#E74856", "#16C60C", "#F9F1A5",
                    "#3B78FF", "#B4009E", "#61D6D6", "#F2F2F2"
            )
    };

    public static Scheme[] getSchemes() {
        return Arrays.copyOf(SCHEMES, SCHEMES.length);
    }

    /**
     * Returns a copy of all schemes sorted by name (case-insensitive).
     * This ordering is used both for listing (-view/-viewe) and
     * for numeric selection with -apply.
     */
    public static Scheme[] getSchemesSortedByName() {
        Scheme[] copy = Arrays.copyOf(SCHEMES, SCHEMES.length);
        Arrays.sort(copy, (a, b) -> a.name.compareToIgnoreCase(b.name));
        return copy;
    }

    public static String[] getSchemeNames() {
        Scheme[] schemes = getSchemesSortedByName();
        String[] names = new String[schemes.length];
        for (int i = 0; i < schemes.length; i++) {
            names[i] = schemes[i].name;
        }
        return names;
    }
}
