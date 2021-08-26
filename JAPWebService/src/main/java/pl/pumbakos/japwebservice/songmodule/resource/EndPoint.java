package pl.pumbakos.japwebservice.songmodule.resource;

public final class EndPoint {
    public static final String SONG = "/song";
    public static final String SIZE = "/size";
    public static final String ALL = "/all";
    public static final String INFO = "/info";

    public static class PathVariable {
        public static final String FILENAME = "/{filename}";
        public static final String ID = "/{id}";
    }
}
