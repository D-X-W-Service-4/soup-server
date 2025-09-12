package dxw.soup.backend.soupserver.domain.user.enums;

public enum Grade {
    M1, M2, M3;

    public String getGradeText() {
        return (ordinal() + 1) + "학년";
    }
}
