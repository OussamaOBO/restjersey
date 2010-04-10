/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.ws.rs.core.Response;

/**
 *
 * @author joaosavio
 */
public class FileUtils {

    public static Response copyFile(File src, String path, String completeName) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File dir = new File(path);
            dir.mkdir(); //cria um novo diretorio na localizacao do upload, caso nao exista
            File file = new File(dir, completeName); //aponta o arquivo para aquele diretorio
            file.createNewFile(); //cria um novo arquivo

            fis = new FileInputStream(src);
            fos = new FileOutputStream(file);
            int bytes = 0;
            byte[] bteFile = new byte[1024];
            while ((bytes = fis.read(bteFile)) != -1) {
                fos.write(bteFile, 0, bytes);
            }
        } catch (FileNotFoundException fnf) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch(IOException ioe) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        catch (Exception e) {
            return Response.serverError().build();
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }
}
