package com.tapminds.network

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

interface TapMindRestManager {
    suspend fun bidRequest(params: AdRequest): Result<AdData>
}

class AdRestManagerImpl() : TapMindRestManager {

    override suspend fun bidRequest(params: AdRequest): Result<AdData> {
        val response = suspendSafeExecute { TapMindRestAdapterImpl.bidRequest(params) }
        return processResponse(response)
    }

    private fun processResponse(response: Response<AdResponse>?): Result<AdData> {
        return try {
            if (response == null) {
                return Failure(AdError(510, "Network connection error"))
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    if (it.success) {
                        it.data?.let { Success(it) } ?:
                        Failure(AdError(it.error?.code ?: 3, it.error?.message ?: "No fill"))

//                        if (it.ads.isEmpty()) {
//                            Failure(AdError(3, "No fill"))
//                        } else {
//                            Success(it.ads[0].copy(tracking = decodeTrackingUTF8(it.ads[0].tracking)))
//                        }
                    } else {
                        Failure(AdError(it.error?.code ?: 0, it.error?.message ?: "Request failed"))
                    }

                } ?: run {
                    Failure(
                        AdError(response.code(), "Request failed with code: ${response.code()}")
                    )
                }
            } else {
                Failure(AdError(response.code(), "Request failed with code: ${response.code()}"))
            }
        } catch (ex: Exception) {
            Failure(AdError(500, "Error processing response: ${ex.message}"))
        }
    }


//    private fun decodeTrackingUTF8(tracking: Tracking?): Tracking? {
//        if(tracking == null) return null
//        val decodedClicks = tracking.click?.map {
//            URLDecoder.decode(it, "UTF-8")
//        }
//
//        val decodedImpressions = tracking.impression?.map {
//            URLDecoder.decode(it, "UTF-8")
//        }
//
//        return Tracking(decodedClicks, decodedImpressions)
//    }
}

suspend fun <T> suspendSafeExecute(block: suspend () -> T): T? {
    return try {
        block()
    } catch (e: IOException) {
        null
    }
}

sealed class Result<out R : Any>
class Success<out R : Any>(val data: R) : Result<R>()
class Failure(val error: AdError) : Result<Nothing>()

