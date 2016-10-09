package com.netopyr.reduxfx.vscenegraph.property;

public enum VPropertyType {
    ID("id"),
    PADDING("padding"),
    SPACING("spacing"),
    TEXT("text"),
    SELECTED("selected"),
    DISABLE("disable"),
    ITEMS("items"),
    TOGGLE_GROUP("toggleGroup"),
    COLUMNS("columns"),
    TABLE_CELL("tableCell"),
    ALIGNMENT("alignment"),
    STYLE_CLASS("styleClass"),
    MNEMONIC_PARSING("mnemonicParsing"),
    PROMPT_TEXT("promptText"),
    STYLESHEETS("stylesheets"),
    H_GROW("hgrow"),
    MIN_WIDTH("minWidth"),
    MAX_WIDTH("maxWidth"),
    MIN_HEIGHT("minHeight"),
    MAX_HEIGHT("maxHeight"),
    TOP_ANCHOR("topAnchor"),
    RIGHT_ANCHOR("rightAnchor"),
    BOTTOM_ANCHOR("bottomAnchor"),
    LEFT_ANCHOR("leftAnchor");

    private final String name;

    VPropertyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
