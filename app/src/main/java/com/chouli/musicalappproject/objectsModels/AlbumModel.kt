package com.chouli.musicalappproject.objectsModels

import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.jsonResponse.AlbumInfoResponse

class AlbumModel(
    val album: AlbumInfoResponse,
    val albumTracksTitle :List<MultipleViewItem>,
    val trackSize: Int,
    val isFavorite: Boolean
    ) {
}