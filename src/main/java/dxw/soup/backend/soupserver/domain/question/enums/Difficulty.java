package dxw.soup.backend.soupserver.domain.question.enums;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getMetadata() {
        return ordinal() + 1;
    }
}
