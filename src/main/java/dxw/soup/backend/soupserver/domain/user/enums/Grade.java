package dxw.soup.backend.soupserver.domain.user.enums;

public enum Grade {
    ONE, TWO, THREE;

    public String getGradeText() {
        return (ordinal() + 1) + "학년";
    }
}
