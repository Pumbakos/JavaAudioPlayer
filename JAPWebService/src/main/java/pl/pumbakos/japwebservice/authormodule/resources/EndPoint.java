package pl.pumbakos.japwebservice.authormodule.resources;

public class EndPoint {
    public final static String AUTHOR = "/author";
    public final static String ALL = "/all";

    public static class PathVariable {
        public final static String ID = "/{id}";
        public final static String SONG_ID = "/{songId}";
    }
}
