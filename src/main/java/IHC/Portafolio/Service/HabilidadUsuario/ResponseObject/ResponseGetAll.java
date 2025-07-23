package IHC.Portafolio.Service.HabilidadUsuario.ResponseObject;
import java.util.ArrayList;
import java.util.List;

import IHC.Portafolio.Service.Generic.ResponseGeneric;

public class ResponseGetAll extends ResponseGeneric {
    public static class Response {
        public List<Object> listTHabilidad = new ArrayList<>();
    }

    public Response dto = new Response();
    
}
