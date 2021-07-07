package pl.pumbakos.japwebservice.service.resource;

public class Defaults {
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/";

    public static class Changeable{
        public static String DIRECTORY = System.getProperty("user.home") + "/Downloads/";
    }
}
