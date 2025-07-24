package IHC.Portafolio.Service.Plantilla.ResponseObject;
import java.util.ArrayList;
import java.util.List;

import IHC.Portafolio.Service.Generic.ResponseGeneric;

public class ResponseGetAll extends ResponseGeneric {
     public class Response{
        public List<Object> listTPlantilla=new ArrayList<>();
    }
    public Response dto = new Response();
    
}
