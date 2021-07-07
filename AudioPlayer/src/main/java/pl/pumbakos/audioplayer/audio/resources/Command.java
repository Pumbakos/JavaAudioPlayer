package pl.pumbakos.audioplayer.audio.resources;

public enum Command {
    PLAY("play"),
    STOP("stop"),
    PAUSE("pause"),
    RESUME("resume"),
    NEXT("next"),
    PREVIOUS("previous"),
    LIST("list"),
    FOLDER("folder"),
    EXIT("exit");

    private String name;

    Command(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
