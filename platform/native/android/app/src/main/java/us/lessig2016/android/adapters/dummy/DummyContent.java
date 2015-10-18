package us.lessig2016.android.adapters.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample title for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    private static final String TAG = "DummyContent";

    /**
     * An array of sample (dummy) items.
     */
    public static List<PostItem> ITEMS = new ArrayList<PostItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, PostItem> ITEM_MAP = new HashMap<String, PostItem>();

    static {
        // Add dummy post items
        for(int i = 0; i < 10; i++) {
            String id = "" + i;
            String title = "Item " + i;
            String description = "Here is the Item " + i + " description. \nMore description.";
            String footer = "Footer for Item " + i;
            String imageResName = (i % 2 == 0) ? "dummy_lessig_face" : "dummy_facebook_avatar";
            addItem(new PostItem(id, title, description, footer, imageResName));
        }
    }

    private static void addItem(PostItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of title.
     */
    public static class PostItem {
        private String id;
        private String title;
        private String description;
        private String footer;

        private String imageResName;

        public PostItem(String id, String title, String description, String footer) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.footer = footer;
        }

        public PostItem(String id, String title, String description, String footer, String imageResName) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.footer = footer;
            this.imageResName = imageResName;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getFooter() {
            return footer;
        }

        public String getImageResName() {
            return imageResName;
        }
    }
}
