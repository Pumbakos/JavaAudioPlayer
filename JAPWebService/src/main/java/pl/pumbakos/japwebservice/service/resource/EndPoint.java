package pl.pumbakos.japwebservice.service.resource;

public interface EndPoint {
   String FILE = "/file";
   String FILENAME = "/{filename}";
   String DOWNLOAD = "/download";
   String UPLOAD = "/upload";
   String LIST = "/list";
   String SIZE = "/size";
   String TITLES = "/titles";
}
