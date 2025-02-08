package com.hilfling.picture_provider.controller

import com.hilfling.picture_provider.service.PhotoProviderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/photos")
class PhotoController(private val photoProviderService: PhotoProviderService) {

    @PostMapping
    fun uploadPhoto(
            @RequestParam("photoFileList", required = true) photoFileList: List<MultipartFile>?,
            @RequestParam("directoryList", required = true) directoryList: List<String>?
    ): ResponseEntity<Any> {
        // Validate input
        if (photoFileList.isNullOrEmpty() || directoryList.isNullOrEmpty()) {
            return ResponseEntity(
                    "Photo files and directory list cannot be empty.",
                    HttpStatus.BAD_REQUEST
            )
        }

        return ResponseEntity(
                photoProviderService.savePhotos(photoFileList, directoryList),
                HttpStatus.CREATED
        )
    }
}
