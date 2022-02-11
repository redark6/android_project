package com.chouli.musicalappproject.jsonResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumSingleHomeResponse (
    val dateAdded: String?,
    val idAlbum: String?,
    val idArtist: String?,
    val idTrack: String?,
    val idTrend: String?,
    val intChartPlace: String?,
    val intWeek: String?,
    val strAlbum: String?,
    val strAlbumMBID: String?,
    val strAlbumThumb: String?,
    val strArtist: String?,
    val strArtistMBID: String?,
    val strArtistThumb: String?,
    val strCountry: String?,
    val strTrack: String?,
    val strTrackMBID: String?,
    val strTrackThumb: String?,
    val strType: String?
): Parcelable