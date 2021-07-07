package pl.pumbakos.audioplayer.audio.resources;

import org.intellij.lang.annotations.RegExp;

public class Regex {
    @RegExp
    public static final String COMMAND_REGEX = "^([A-Za-z]+)|([\\\\-]{1,2}[A-Za-z]+)$";
}
