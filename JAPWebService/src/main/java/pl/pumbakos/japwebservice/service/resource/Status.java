package pl.pumbakos.japwebservice.service.resource;

import org.springframework.core.io.Resource;

public class Status {
    public static final byte BAD_EXTENSION = -1;
    public static final byte NO_CONTENT = -2;
    public static final byte INVALID_TITLE = -3;

    public static class Message extends Status{
        public static final String NO_CONTENT = "NO_CONTENT";
        public static final String BAD_EXTENSION = "BAD_EXTENSION";
        public static final String INVALID_TITLE = "INVALID_TITLE";
    }
}
