package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/Api/image")
@CrossOrigin
public class CloudinaryController {
    @Autowired
    private final CloudinaryService service;

    public CloudinaryController(CloudinaryService service) {this.service = service;}

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file){
        try{
            String imageUrl = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("error al subir imagen");
        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImageToFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder
    ){
        try{
            String imageUrl = service.uploadImageFolder(file, folder);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageUrl
            ));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}
