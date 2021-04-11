//package com.example.dailyjournalapp.dummy;
//
//import android.database.Cursor;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Helper class for providing sample content for user interfaces created by
// * Android template wizards.
// * <p>
// * TODO: Replace all uses of this class before publishing your app.
// */
//public class DummyContent {
//
//    public static final List<JournalItem> ITEMS = new ArrayList<JournalItem>();
//
//    public static final Map<String, JournalItem> ITEM_MAP = new HashMap<String, JournalItem>();
//
////    static {
////
////        // Add some sample items.
////        for (int i = 1; i <= COUNT; i++) {
////            addItem(createDummyItem(i));
////        }
////    }
//
//    private static void addItem(JournalItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }
//
//    private static JournalItem createDummyItem(int position) {
//        return new JournalItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }
//
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }
//
//    /**
//     * A dummy item representing a piece of content.
//     */
//    public static class JournalItem {
//        public int id;
//        public String title;
//
//        public JournalItem(int id, String title) {
//            this.id = id;
//            this.title = title;
//        }
//
//        @Override
//        public String toString() {
//            return title;
//        }
//    }
//}