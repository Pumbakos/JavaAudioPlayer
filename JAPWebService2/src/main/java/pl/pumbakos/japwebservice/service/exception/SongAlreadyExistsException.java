package pl.pumbakos.japwebservice2.service.exception;

public class SongAlreadyExistsException extends RuntimeException {
    public SongAlreadyExistsException(){}

    public SongAlreadyExistsException(String message){
        super(message);
    }
}
