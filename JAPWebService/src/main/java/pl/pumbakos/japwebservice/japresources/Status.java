package pl.pumbakos.japwebservice.japresources;

public class Status {
    public static final byte BAD_EXTENSION = -1;
    public static final byte NO_CONTENT = -2;
    public static final byte INVALID_TITLE = -3;

    public static class Message{
        public static final String NO_CONTENT = "NO_CONTENT";
        public static final String BAD_EXTENSION = "BAD_EXTENSION";
        public static final String INVALID_TITLE = "INVALID_TITLE";
        public static final String ACCEPTED = "ACCEPTED";
        public static final String BAD_REQUEST = "BAD_REQUEST";
        public static final String NOT_FOUND = "NOT_FOUND";
    }

    public static Status status(Status status){
        return status;
    }

    public static Status.Message status(Status.Message status){
        return status;
    }
}
