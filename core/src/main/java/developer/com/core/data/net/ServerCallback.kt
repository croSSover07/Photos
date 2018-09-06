package developer.com.core.data.net

import com.google.gson.JsonParseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ServerCallback<T> : Callback<T> {
    final override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!response.isSuccessful) {
            val error = try {
                gson.fromJson<ServerError>(response.errorBody()?.string(), ServerError::class.java)

            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }

            onFailure(error)

        } else onSuccess(response.body()!!)
    }

    final override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        onFailure(ServerError(t.localizedMessage, null))
    }

    abstract fun onSuccess(response: T)
    abstract fun onFailure(error: ServerError?)
}