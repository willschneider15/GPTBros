package com.example.gptbros.network

import com.squareup.okhttp.RequestBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface WhisperApi {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: multipart/form-data",
            )
    @Multipart
    @POST(
        "v1/audio/translations"
        +""
    )
    suspend fun transcribeAudio(
        @PartMap() partMap: MutableMap<String, RequestBody>
    ) : String
}