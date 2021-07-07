package pl.pumbakos.audioplayer.audio.resources;

public enum CommandFlag {
    FOLDER_CHOOSE('c'),
    LOOP_OVER_FOLDER("loop");



    private char character;
    private String name;

    CommandFlag(String name){
        this.name = name;
    }

    CommandFlag(char character){
        this.character = character;
    }

    @Override
    public String toString() {
        return name;
    }

    public char getChar(){
        return character;
    }
}
