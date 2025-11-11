package dxw.soup.backend.soupserver.domain.user.enums;

import lombok.Getter;

public enum Soup {
    TOMATO(10, 20, null),
    CORN(20, 30, null),
    MUSHROOM(30, 40, null),
    PUMPKIN(40, 50, null),
    SWEET_POTATO(Integer.MAX_VALUE, Integer.MAX_VALUE, null);

    private final int min;

    private final int max;

    @Getter
    private Soup nextSoup;

    static {
        TOMATO.nextSoup = CORN;
        CORN.nextSoup = MUSHROOM;
        MUSHROOM.nextSoup = PUMPKIN;
        PUMPKIN.nextSoup = SWEET_POTATO;
    }

    Soup(int min, int max, Soup nextSoup) {
        this.min = min;
        this.max = max;
        this.nextSoup = nextSoup;
    }

    public boolean shouldUpgrade(int count) {
        return count >= min && count < max;
    }
}
