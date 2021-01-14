package org.litespring.util;

import java.util.ArrayList;
import java.util.List;

public class MessageTracker {
    private static List<String> TRACKER_MESSAGES = new ArrayList<>();

    public static void addMsg(String msg) {
        TRACKER_MESSAGES.add(msg);
    }

    public static void clearMsgs() {
        TRACKER_MESSAGES.clear();
    }

    public static List<String> getMsgs() {
        return TRACKER_MESSAGES;
    }
}
