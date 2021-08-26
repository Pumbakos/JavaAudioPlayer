package pl.pumbakos.japwebservice.songmodule.resource.exception;

public class SongAlreadyExistsException extends RuntimeException {
    public SongAlreadyExistsException(){}

    public SongAlreadyExistsException(String message){
        super(message);
    }
}
