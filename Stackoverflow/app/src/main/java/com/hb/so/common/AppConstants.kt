package com.hb.so.common

import android.Manifest
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by buihai on 7/29/17.
 */

const val TRANSITION_CARD = "card_transition_"
const val TRANSITION_TOOLBAR = "toolbar_transition"

object AppConstants {

    val PERMISSIONS_IN_APP = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )


    val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

}
