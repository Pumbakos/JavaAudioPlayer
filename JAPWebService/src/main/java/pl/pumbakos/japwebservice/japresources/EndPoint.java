package pl.pumbakos.japwebservice.japresources;

public class EndPoint {
    public final static String AUTHOR = "/author";
    public static final String SONG = "/song";
    public static final String ALBUM = "/album";
    public static final String PRODUCER = "/producer";
    public final static String ALL = "/all";
    public static final String SIZE = "/size";
    public static final String INFO = "/info";

    public static class PathVariable {
        public static final String FILENAME = "/{filename}";
        public final static String ID = "/{id}";
        public final static String SONG_ID = "/{songId}";
    }
}
