package edu.kit.recipe.recipebackend.controller.api.v1.image;


import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.repository.ImageDataInfo;
import edu.kit.recipe.recipebackend.repository.ImageRepository;
import edu.kit.recipe.recipebackend.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageRepository imageRepository;


    @PostMapping
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file)  {
		if (file == null || file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		 try {
			 imageRepository.save(ImageData.builder()
					 .name(file.getOriginalFilename())
					 .type(file.getContentType())
					 .image(ImageUtils.compressImage(file.getBytes())).build());
		 }
		 catch (IOException e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName){
		Optional<ImageData> dbImageData = imageRepository.findByName(fileName);

		if(dbImageData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		byte[] imageData=ImageUtils.decompressImage(dbImageData.get().getImage());
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);
	}

	@GetMapping
	public ResponseEntity<List<ImageDataInfo>> listAllImageIds() {
		return ResponseEntity.ok(imageRepository.findAllProjectedBy());
	}
}
