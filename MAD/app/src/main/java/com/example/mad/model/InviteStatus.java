package com.example.mad.model;

public enum InviteStatus {
    ALL("Tất cả"),
    ACCEPTED("Đã chấp nhận"),
    PENDING("Đang chờ"),
    DECLINED("Từ chối");

    private final String displayName;

    InviteStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
