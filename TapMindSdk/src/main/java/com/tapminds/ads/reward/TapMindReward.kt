package com.tapminds.ads.reward

interface TapMindReward {

    companion object {
        const val DEFAULT_LABEL = ""
        const val DEFAULT_AMOUNT = 0
    }

    val label: String
    val amount: Int

}