package com.chouli.musicalappproject.adaptaters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.core.content.ContextCompat.startActivity
import com.chouli.musicalappproject.activities.ALbumActivity

import com.chouli.musicalappproject.jsonResponse.AlbumSingleHomeResponse
import com.squareup.picasso.Picasso

import androidx.viewbinding.ViewBinding
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.activities.ArtistActivity
import com.chouli.musicalappproject.databinding.*
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.jsonResponse.Artist
import com.chouli.musicalappproject.jsonResponse.Track


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MultipleViewRecyclerViewAlbumArtistActivityAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ViewHolder>() {

    private var results: List<MultipleViewItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            R.layout.title_item -> ViewHolder.TitleViewHolder(
                TitleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.album_item -> ViewHolder.AlbumViewHolder(
                AlbumItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.artist_item -> ViewHolder.ArtistViewHolder(
                ArtistItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.single_item -> ViewHolder.TrackViewHolder(
                SingleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            0 -> ViewHolder.TrackViewHolder(
                SingleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is ViewHolder.TitleViewHolder -> holder.bind(results[position] as MultipleViewItem.Title)
            is ViewHolder.AlbumViewHolder -> holder.bind(results[position] as Album,this.context)
            is ViewHolder.ArtistViewHolder -> holder.bind(results[position] as Artist,this.context)
            is ViewHolder.TrackViewHolder -> holder.bind(results[position] as Track)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(results[position]){
            is MultipleViewItem.Title -> R.layout.title_item
            is Album -> R.layout.album_item
            is Track -> R.layout.single_item
            is Artist -> R.layout.artist_item
            else -> 0
        }
    }

    override fun getItemCount(): Int = results.size

    fun setValues(results: List<MultipleViewItem>) {
        this.results = results
        notifyDataSetChanged();
    }
}

sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: TitleItemBinding) : ViewHolder(binding) {
        fun bind(title: MultipleViewItem.Title) {
            binding.title.text = title.title
        }
    }

    class AlbumViewHolder(private val binding: AlbumItemBinding) : ViewHolder(binding) {
        fun bind(album: Album, context: Context) {
            Picasso.get().load(album.strAlbumThumb).into(binding.albumPicture)
            binding.albumName.text = album.strAlbum
            binding.albumAuthor.text = album.strArtist
            binding.artistCardcell.setOnClickListener {
                var intent = Intent(context, ALbumActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                var albumSend = AlbumSingleHomeResponse(
                    null,
                    album.idAlbum,
                    album.idArtist,
                    null,
                    null,
                    null,
                    null,
                    album.strAlbum,
                    null,
                    album.strAlbumThumb,
                    album.strArtist,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                intent.putExtra("album", albumSend);
                startActivity(context, intent, null)
            }
        }
    }

    class ArtistViewHolder(private val binding: ArtistItemBinding) : ViewHolder(binding) {
        fun bind(artist: Artist, context: Context) {
            Picasso.get().load(artist.strArtistThumb).into(binding.artistPicture)
            binding.artistName.text = artist.strArtist
            binding.artistCardcell.setOnClickListener {
                var intent = Intent(context, ArtistActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                var artistSend = AlbumSingleHomeResponse(
                    null,
                    null,
                    artist.idArtist,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    artist.strArtist,
                    null,
                    artist.strArtistThumb,
                    artist.strCountry,
                    null,
                    null,
                    null,
                    null
                )
                intent.putExtra("artist", artistSend);
                startActivity(context, intent, null)
            }
        }
    }

    class TrackViewHolder(private val binding: SingleItemBinding) : ViewHolder(binding) {
        fun bind(track: Track) {
            binding.itemNumber.text = track.intTrackNumber
            binding.songName.text = track.strTrack
        }
    }
}
