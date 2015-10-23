package us.lessig2016.android.api.models;

/**
 * Created by Allen on 10/21/15.
 */
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String picture;
    private String fullPicture;
    private String message;
    private String link;
    private String name;
    private String type;
    private String caption;
    private From from;
    private Date updatedTime;
    private String id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     *
     * @param picture
     * The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     *
     * @return
     * The fullPicture
     */
    public String getFullPicture() {
        return fullPicture;
    }

    /**
     *
     * @param fullPicture
     * The full_picture
     */
    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     *
     * @return
     * The from
     */
    public From getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     *
     * @return
     * The updatedTime
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     *
     * @param updatedTime
     * The updated_time
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public class From {

        private String name;
        private String id;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}