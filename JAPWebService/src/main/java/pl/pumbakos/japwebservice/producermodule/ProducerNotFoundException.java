package pl.pumbakos.japwebservice.producermodule;

public class ProducerNotFoundException extends RuntimeException{
    public ProducerNotFoundException(long id){
        super("Could not find producer " + id);
    }
}
