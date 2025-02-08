package com.hilfling.picture_provider.service

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoProviderService {

    fun savePhotos(
            photoFileList: List<MultipartFile>,
            directoryList: List<String>,
    ): List<String> {
        if (photoFileList.size != directoryList.size) {
            throw IllegalArgumentException("Photo file list size must match directory list size.")
        }

        return photoFileList.mapIndexed { index, multipartFile ->
            try {
                // Validate and create directory
                val directoryPath = Paths.get(directoryList[index])
                Files.createDirectories(directoryPath) // Always ensure the directory exists

                // Define file path
                val fileName = multipartFile.originalFilename ?: "default_name.jpg"
                val filePath = directoryPath.resolve(fileName)

                // Save the file (overwrite if exists)
                multipartFile.inputStream.use { inputStream ->
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
                }

                filePath.toString()
            } catch (e: java.nio.file.AccessDeniedException) {
                throw RuntimeException("Access denied to directory: ${e.message}")
            } catch (e: java.io.IOException) {
                throw RuntimeException("Failed to save file due to IO error: ${e.message}")
            }
        }
    }

    fun savePhoto(
        photoFile: MultipartFile,
        directory: String,
    ): String {
        try {
            val directoryPath = Paths.get(directory)
            Files.createDirectories(directoryPath)
            val fileName = photoFile.originalFilename ?: "default_name.jpg"
            val filePath = directoryPath.resolve(fileName)
            photoFile.inputStream.use { inputStream ->
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            }
        }catch (e: java.nio.file.AccessDeniedException) {
            throw RuntimeException("Access denied to directory: ${e.message}")
        }catch (e: java.io.IOException) {
            throw RuntimeException("Failed to save file due to IO error: ${e.message}")
        }
        return directory
    }
}
