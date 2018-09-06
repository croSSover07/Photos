package developer.com.core.extension

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement

private fun <T : Any> JsonElement?.nullOr(getNotNull: JsonElement.() -> T): T? =
    if (this == null || isJsonNull) null else getNotNull()

inline fun <reified T> JsonElement?.deserializeOrNull(context: JsonDeserializationContext): T? =
    if (this == null || isJsonNull) null else context.deserialize(this, T::class.java)

inline fun <reified T> JsonElement?.deserializeOrDefault(
    context: JsonDeserializationContext,
    defaultValue: T
): T = deserializeOrNull<T>(context) ?: defaultValue

val JsonElement?.asOptionalObject get() = nullOr { asJsonObject }
val JsonElement?.asOptionalString get() = nullOr { asString }
val JsonElement?.asOptionalInt get() = nullOr { asInt }
val JsonElement?.asOptionalBoolean get() = nullOr { asBoolean }
val JsonElement?.asOptionalFloat get() = nullOr { asFloat }
val JsonElement?.asOptionalDouble get() = nullOr { asDouble }
val JsonElement?.asOptionalLong get() = nullOr { asLong }
val JsonElement?.asOptionalChar get() = nullOr { asCharacter }