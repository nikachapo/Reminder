package com.nikachapo.potoli.task1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val surname: String,
    val email: String,
    val contactNumber: String,
    val address: String
) : Parcelable