package com.example.projectanmp.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context: Context) {
    val folderName = "HabitTracker"
    val fileName = "habitData.txt"
    // isi json string

    // buat file baru/load file klo sdh ada
    private fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        if (!dir.exists()) {
            dir.mkdirs() // buat folder klo blm ada
        }
        return File(dir, fileName)
    }
    // tulis string ke file
    fun writeToFile(data: String) {
        try {
            val file = getFile()
            FileOutputStream(file, false).use { output ->
                output.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    // Baca string dri file
    fun readFromFile(): String {
        return try {
            val file = getFile()
            file.bufferedReader().useLines { lines ->
                lines.joinToString("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }
    }
    // Hapus file
    fun deleteFile(): Boolean {
        return getFile().delete()
    }
    // Menghasilkan string path menuju file
    fun getFilePath(): String {
        return getFile().absolutePath
    }
}