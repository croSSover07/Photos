package developer.com.photos.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import developer.com.core.extension.asOptionalInt
import developer.com.core.extension.asOptionalObject
import developer.com.core.extension.asOptionalString
import developer.com.core.extension.deserializeOrNull
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Key
import java.lang.reflect.Type

object Deserializer {
    fun photo(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Photo {
        val obj = json.asJsonObject

        return Photo(
            obj[Key.ID].asString,
            obj[Key.Photo.CREATED_AT].asOptionalString,
            obj[Key.Photo.WIDTH].asInt,
            obj[Key.Photo.HEIGHT].asInt,
            obj[Key.Photo.LIKES].asInt,
            obj[Key.Photo.DESCRIPTION].asOptionalString,
            obj[Key.Photo.URLS].deserializeOrNull(ctx),
            obj[Key.Photo.LINKS].asJsonObject[Key.Photo.LINK_DOWNLOAD].asString,
            obj[Key.Photo.LOCATION].asOptionalObject?.get(Key.Photo.LOCATION_TITLE)?.asOptionalString,
            obj[Key.Photo.USER].asOptionalObject?.get(Key.Photo.INSTAGRAM_NAME)?.asOptionalString,
            obj[Key.Photo.VIEWS].asOptionalInt,
            obj[Key.Photo.DOWNLOADS].asOptionalInt
        )
    }
}