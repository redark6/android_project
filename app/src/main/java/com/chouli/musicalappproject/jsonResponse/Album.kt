package com.chouli.musicalappproject.jsonResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewItem

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val idAlbum: String,
    @ColumnInfo(name = "idArtist") val idArtist: String?,
    @ColumnInfo(name = "idLabel") val idLabel: String?,
    @ColumnInfo(name = "intLoved") val intLoved: String?,
    @ColumnInfo(name = "intSales") val intSales: String?,
    @ColumnInfo(name = "intScore") val intScore: String?,
    @ColumnInfo(name = "intScoreVotes") val intScoreVotes: String?,
    @ColumnInfo(name = "intYearReleased") val intYearReleased: String?,
    @ColumnInfo(name = "strAlbum") val strAlbum: String?,
    @ColumnInfo(name = "strAlbum3DCase") val strAlbum3DCase: String?,
    @ColumnInfo(name = "strAlbum3DFace") val strAlbum3DFace: String?,
    @ColumnInfo(name = "strAlbum3DFlat") val strAlbum3DFlat: String?,
    @ColumnInfo(name = "strAlbum3DThumb") val strAlbum3DThumb: String?,
    @ColumnInfo(name = "strAlbumCDart") val strAlbumCDart: String?,
    @ColumnInfo(name = "strAlbumSpine") val strAlbumSpine: String?,
    @ColumnInfo(name = "strAlbumStripped") val strAlbumStripped: String?,
    @ColumnInfo(name = "strAlbumThumb") val strAlbumThumb: String?,
    @ColumnInfo(name = "strAlbumThumbBack") val strAlbumThumbBack: String?,
    @ColumnInfo(name = "strAlbumThumbHQ") val strAlbumThumbHQ: String?,
    @ColumnInfo(name = "strAllMusicID") val strAllMusicID: String?,
    @ColumnInfo(name = "strAmazonID") val strAmazonID: String?,
    @ColumnInfo(name = "strArtist") val strArtist: String?,
    @ColumnInfo(name = "strArtistStripped") val strArtistStripped: String?,
    @ColumnInfo(name = "strBBCReviewID") val strBBCReviewID: String?,
    @ColumnInfo(name = "strDescriptionCN") val strDescriptionCN: String?,
    @ColumnInfo(name = "strDescriptionDE") val strDescriptionDE: String?,
    @ColumnInfo(name = "strDescriptionEN") val strDescriptionEN: String?,
    @ColumnInfo(name = "strDescriptionES") val strDescriptionES: String?,
    @ColumnInfo(name = "strDescriptionFR") val strDescriptionFR: String?,
    @ColumnInfo(name = "strDescriptionHU") val strDescriptionHU: String?,
    @ColumnInfo(name = "strDescriptionIL") val strDescriptionIL: String?,
    @ColumnInfo(name = "strDescriptionIT") val strDescriptionIT: String?,
    @ColumnInfo(name = "strDescriptionJP") val strDescriptionJP: String?,
    @ColumnInfo(name = "strDescriptionNL") val strDescriptionNL: String?,
    @ColumnInfo(name = "strDescriptionNO") val strDescriptionNO: String?,
    @ColumnInfo(name = "strDescriptionPL") val strDescriptionPL: String?,
    @ColumnInfo(name = "strDescriptionPT") val strDescriptionPT: String?,
    @ColumnInfo(name = "strDescriptionRU") val strDescriptionRU: String?,
    @ColumnInfo(name = "strDescriptionSE") val strDescriptionSE: String?,
    @ColumnInfo(name = "strDiscogsID") val strDiscogsID: String?,
    @ColumnInfo(name = "strGeniusID") val strGeniusID: String?,
    @ColumnInfo(name = "strGenre") val strGenre: String?,
    @ColumnInfo(name = "strItunesID") val strItunesID: String?,
    @ColumnInfo(name = "strLabel") val strLabel: String?,
    @ColumnInfo(name = "strLocation") val strLocation: String?,
    @ColumnInfo(name = "strLocked") val strLocked: String?,
    @ColumnInfo(name = "strLyricWikiID") val strLyricWikiID: String?,
    @ColumnInfo(name = "strMood") val strMood: String?,
    @ColumnInfo(name = "strMusicBrainzArtistID") val strMusicBrainzArtistID: String?,
    @ColumnInfo(name = "strMusicBrainzID") val strMusicBrainzID: String?,
    @ColumnInfo(name = "strMusicMozID") val strMusicMozID: String?,
    @ColumnInfo(name = "strRateYourMusicID") val strRateYourMusicID: String?,
    @ColumnInfo(name = "strReleaseFormat") val strReleaseFormat: String?,
    @ColumnInfo(name = "strReview") val strReview: String?,
    @ColumnInfo(name = "strSpeed") val strSpeed: String?,
    @ColumnInfo(name = "strStyle") val strStyle: String?,
    @ColumnInfo(name = "strTheme") val strTheme: String?,
    @ColumnInfo(name = "strWikidataID") val strWikidataID: String?,
    @ColumnInfo(name = "strWikipediaID") val strWikipediaID: String?,
) : MultipleViewItem() {
    operator fun get(s: String): CharSequence? {
        when (s) {
            "strDescriptionCN" -> return strDescriptionCN
            "strDescriptionDE" -> return strDescriptionDE
            "strDescriptionEN" -> return strDescriptionEN
            "strDescriptionES" -> return strDescriptionES
            "strDescriptionFR" -> return strDescriptionFR
            "strDescriptionHU" -> return strDescriptionHU
            "strDescriptionIL" -> return strDescriptionIL
            "strDescriptionIT" -> return strDescriptionIT
            "strDescriptionJP" -> return strDescriptionJP
            "strDescriptionNL" -> return strDescriptionNL
            "strDescriptionNO" -> return strDescriptionNO
            "strDescriptionPL" -> return strDescriptionPL
            "strDescriptionPT" -> return strDescriptionPT
            "strDescriptionRU" -> return strDescriptionRU
            "strDescriptionSE" -> return strDescriptionSE
            else -> return strDescriptionEN

        }
    }
}