package developer.com.photos.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import developer.com.photos.data.model.Photo
import developer.com.photos.util.Deserializer

val gson: Gson by lazy { GsonBuilder()
    .registerTypeAdapter(Photo::class.java, JsonDeserializer(Deserializer::photo))
    .create() }