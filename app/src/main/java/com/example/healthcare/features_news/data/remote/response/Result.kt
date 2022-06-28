package com.example.healthcare.features_news.data.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity

data class Result(
    val name: String,
    val dose: String,
    val strength: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    fun toResult():HealthCareEntity {
        return HealthCareEntity(
            id = 0,
            name = name,
            dose = dose,
            strength = strength
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(dose)
        parcel.writeString(strength)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}