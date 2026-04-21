package com.radio.jams

import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class GithubApi(
    private val client: OkHttpClient,
    private val json: Json,
) {

    suspend fun fetchRepositories(since: Long? = null): List<GitRepository> = withContext(Dispatchers.IO) {
        val urlBuilder = HttpUrl.Builder()
            .scheme("https")
            .host("api.github.com")
            .addPathSegment("repositories")

        if (since != null) {
            urlBuilder.addQueryParameter("since", since.toString())
        }

        val request = Request.Builder()
            .url(urlBuilder.build())
            .header("accept", "application/vnd.github.v3+json")
            .header("user-agent", "AndroidInterviewTemplate1")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("GitHub request failed: HTTP ${response.code}")
            }

            val body = response.body?.string()
                ?: throw IOException("GitHub request failed: empty body")

            json.decodeFromString(body)
        }
    }
}