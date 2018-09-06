package developer.com.core.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson by lazy { GsonBuilder().create() }