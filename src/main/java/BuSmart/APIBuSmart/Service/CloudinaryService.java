package BuSmart.APIBuSmart.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class CloudinaryService {
    //Tamaño maximo de 5mb
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //Extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".png", ".jpeg"};

    //cliente cloudinary inyectado como dependencia
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {this.cloudinary = cloudinary;}

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resource_type", "auto",
                        "quality", "auto:good"
                ));
        return(String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) throw new IllegalArgumentException("el archivo no puede ser nulo");
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("el tamaño de la imagen no puede ser mayor 5mb");
        String originalfilname = file.getOriginalFilename();
        if (originalfilname == null) throw new IllegalArgumentException("nombre del archivo no valido");
        String extension = originalfilname.substring(originalfilname.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se permiten archivos jpg, jpeg o png");
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("el archivo debe ser valido");
    }

    public String uploadImageFolder(MultipartFile file, String folder) throws IOException {
        validateImage(file);
        String originalfilename = file.getOriginalFilename();
        String fileExtensions = originalfilename.substring(originalfilename.lastIndexOf(".")).toLowerCase();
        String uniquefilename = "img_" + UUID.randomUUID() + fileExtensions;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,       // Carpeta de destino
                "public_id", uniquefilename,    // Nombre único para el archivo
                "use_filename", false,          // No usar el nombre original
                "unique_filename", false,       // No generara nombre unico (ya lo hicimos)
                "overwrite", false,             // No sobreescribir archivos existentes
                "resource_type", "auto",
                "quality", "auto:good"
        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }
}
