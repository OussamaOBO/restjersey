/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.resources;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author joaosavio
 */
@Path("/images")
public class ImageResource {

    @GET
    @Path("/{image}")
    @Produces("image/*")
    public Response getImage(@PathParam("image") String image) {
        File dir = new File("D:\\Meus Documentos\\Projetos Java\\Pattern\\web\\fotos\\");
        File f = new File(dir, image);

        if (!f.exists()) {
            return Response.status(404).build();
        }

        String mt = new MimetypesFileTypeMap().getContentType(f);
        return Response.ok(f, mt).build();
    }   
    

}
