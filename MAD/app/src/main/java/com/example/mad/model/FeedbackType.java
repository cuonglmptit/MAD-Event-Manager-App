package com.example.mad.model;

import com.example.mad.R;

public enum FeedbackType {
    ALL("Tất cả", R.drawable.search_icon),
    NEGATIVE("Tiêu cực", R.drawable.angry_face),
    POSITIVE("Tích cực", R.drawable.happy_face),
    NEUTRAL("Bình thường", R.drawable.neutral_face);

    private final String displayName;
    private final int image;

    FeedbackType(String displayName, int image) {
        this.displayName = displayName;
        this.image = image;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "FeedbackType{" +
                "displayName='" + displayName + '\'' +
                ", image=" + image +
                '}';
    }
}
