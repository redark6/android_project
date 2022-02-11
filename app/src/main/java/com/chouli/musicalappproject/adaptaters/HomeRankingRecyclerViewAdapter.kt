package com.chouli.musicalappproject.adaptaters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import com.chouli.musicalappproject.activities.ALbumActivity
import com.chouli.musicalappproject.activities.ArtistActivity

import com.chouli.musicalappproject.databinding.HomeRankingSingleAlbumItemBinding
import com.chouli.musicalappproject.jsonResponse.AlbumSingleHomeResponse
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeRankingRecyclerViewAdapter(
    private val values: List<AlbumSingleHomeResponse>,
    private val context: Context,
    private val mode: String
) : RecyclerView.Adapter<HomeRankingRecyclerViewAdapter.ViewHolder>() {

    private var results: List<AlbumSingleHomeResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeRankingSingleAlbumItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = results[position]
        holder.bindPosition(position,item)
        holder.setClickAction(item)
    }

    override fun getItemCount(): Int = results.size

    fun setValues(results: List<AlbumSingleHomeResponse?>) {
        this.results = results as List<AlbumSingleHomeResponse>
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: HomeRankingSingleAlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val itemNumber: TextView = binding.itemNumber
        val singleAlbumName: TextView = binding.singleAlbumName
        val singleAlbumAuthor: TextView = binding.singleAlbumAuthor
        val singleAlbumPicture: ImageView = binding.singleAlbumPicture
        val singleAlbumCardcell: CardView = binding.singleAlbumCardcell

        fun bindPosition(position: Int, single: AlbumSingleHomeResponse) {
            itemNumber.text = (position+1).toString()
            singleAlbumAuthor.text = single.strArtist
            if(mode == "album"){
                singleAlbumName.text = single.strAlbum
                Picasso.get().load(single.strAlbumThumb).into(singleAlbumPicture)
            }
            // we assume its single by default
            else{
                singleAlbumName.text = single.strTrack
                Picasso.get().load(single.strTrackThumb).into(singleAlbumPicture)
            }

        }

        fun setClickAction(albumSingle: AlbumSingleHomeResponse){
            singleAlbumCardcell.setOnClickListener{
                var intent : Intent? = null
                if(mode == "album"){
                    intent = Intent(context,ALbumActivity::class.java)
                    intent.putExtra("album", albumSingle)
                }else{
                    intent =  Intent(context,ArtistActivity::class.java)
                    intent.putExtra("artist",albumSingle)
                }
                startActivity(context,intent, null)
            }
        }
    }

}