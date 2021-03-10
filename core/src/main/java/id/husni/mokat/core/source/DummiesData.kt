package id.husni.mokat.core.source

import com.google.gson.Gson
import id.husni.mokat.core.source.remote.response.MovieResponse
import java.io.IOException
import java.io.InputStream


object DummiesData {
    private var gson = Gson()
    fun getMoviesData() =
        gson.fromJson(jsonFile("MoviesDummy"), MovieResponse::class.java).results


    private fun jsonFile(source: String): String? {
        var json: String? = null
        try {
            val input: InputStream = this.javaClass.classLoader!!.getResourceAsStream(source)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = String(buffer, charset("UTF-8"))

        } catch (ex: IOException) {

        }
        return json
    }
}