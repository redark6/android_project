package com.chouli.musicalappproject.jsonResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chouli.musicalappproject.adaptaters.MultipleViewItem

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val idArtist: String,
    @ColumnInfo(name = "idLabel") val idLabel: String?,
    @ColumnInfo(name = "intBornYear") val intBornYear: String?,
    @ColumnInfo(name = "intCharted") val intCharted: String?,
    @ColumnInfo(name = "intDiedYear") val intDiedYear: String?,
    @ColumnInfo(name = "intFormedYear") val intFormedYear: String?,
    @ColumnInfo(name = "intMembers") val intMembers: String?,
    @ColumnInfo(name = "strArtist") val strArtist: String?,
    @ColumnInfo(name = "strArtistAlternate") val strArtistAlternate: String?,
    @ColumnInfo(name = "strArtistBanner") val strArtistBanner: String?,
    @ColumnInfo(name = "strArtistClearart") val strArtistClearart: String?,
    @ColumnInfo(name = "strArtistCutout") val strArtistCutout: String?,
    @ColumnInfo(name = "strArtistFanart") val strArtistFanart: String?,
    @ColumnInfo(name = "strArtistFanart2") val strArtistFanart2: String?,
    @ColumnInfo(name = "strArtistFanart3") val strArtistFanart3: String?,
    @ColumnInfo(name = "strArtistFanart4") val strArtistFanart4: String?,
    @ColumnInfo(name = "strArtistLogo") val strArtistLogo: String?,
    @ColumnInfo(name = "strArtistStripped") val strArtistStripped: String?,
    @ColumnInfo(name = "strArtistThumb") val strArtistThumb: String?,
    @ColumnInfo(name = "strArtistWideThumb") val strArtistWideThumb: String?,
    @ColumnInfo(name = "strBiographyCN") val strBiographyCN: String?,
    @ColumnInfo(name = "strBiographyDE") val strBiographyDE: String?,
    @ColumnInfo(name = "strBiographyEN") val strBiographyEN: String?,
    @ColumnInfo(name = "strBiographyES") val strBiographyES: String?,
    @ColumnInfo(name = "strBiographyFR") val strBiographyFR: String?,
    @ColumnInfo(name = "strBiographyHU") val strBiographyHU: String?,
    @ColumnInfo(name = "strBiographyIL") val strBiographyIL: String?,
    @ColumnInfo(name = "strBiographyIT") val strBiographyIT: String?,
    @ColumnInfo(name = "strBiographyJP") val strBiographyJP: String?,
    @ColumnInfo(name = "strBiographyNL") val strBiographyNL: String?,
    @ColumnInfo(name = "strBiographyNO") val strBiographyNO: String?,
    @ColumnInfo(name = "strBiographyPL") val strBiographyPL: String?,
    @ColumnInfo(name = "strBiographyPT") val strBiographyPT: String?,
    @ColumnInfo(name = "strBiographyRU") val strBiographyRU: String?,
    @ColumnInfo(name = "strBiographySE") val strBiographySE: String?,
    @ColumnInfo(name = "strCountry") val strCountry: String?,
    @ColumnInfo(name = "strCountryCode") val strCountryCode: String?,
    @ColumnInfo(name = "strDisbanded") val strDisbanded: String?,
    @ColumnInfo(name = "strFacebook") val strFacebook: String?,
    @ColumnInfo(name = "strGender") val strGender: String?,
    @ColumnInfo(name = "strGenre") val strGenre: String?,
    @ColumnInfo(name = "strISNIcode") val strISNIcode: String?,
    @ColumnInfo(name = "strLabel") val strLabel: String?,
    @ColumnInfo(name = "strLastFMChart") val strLastFMChart: String?,
    @ColumnInfo(name = "strLocked") val strLocked: String?,
    @ColumnInfo(name = "strMood") val strMood: String?,
    @ColumnInfo(name = "strMusicBrainzID") val strMusicBrainzID: String?,
    @ColumnInfo(name = "strStyle") val strStyle: String?,
    @ColumnInfo(name = "strTwitter") val strTwitter: String?,
    @ColumnInfo(name = "strWebsite") val strWebsite: String?,
): MultipleViewItem(){
    operator fun get(s: String): CharSequence? {
        when (s) {
            "strBiographyCN" -> return strBiographyCN
            "strBiographyDE" -> return strBiographyDE
            "strBiographyEN" -> return strBiographyEN
            "strBiographyES" -> return strBiographyES
            "strBiographyFR" -> return strBiographyFR
            "strBiographyHU" -> return strBiographyHU
            "strBiographyIL" -> return strBiographyIL
            "strBiographyIT" -> return strBiographyIT
            "strBiographyJP" -> return strBiographyJP
            "strBiographyNL" -> return strBiographyNL
            "strBiographyNO" -> return strBiographyNO
            "strBiographyPL" -> return strBiographyPL
            "strBiographyPT" -> return strBiographyPT
            "strBiographyRU" -> return strBiographyRU
            "strBiographySE" -> return strBiographySE
            else -> return strBiographyEN

        }
    }
}