package com.chouli.musicalappproject.objectsModels

import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.jsonResponse.AlbumInfoResponse
import com.chouli.musicalappproject.jsonResponse.ArtistInfoResponse

class ArtistModel(
    val artist: ArtistInfoResponse,
    val albumTracksTitle :List<MultipleViewItem>,
    val isFavorite: Boolean
    ) {
}