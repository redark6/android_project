package com.chouli.musicalappproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewRecyclerViewAlbumArtistActivityAdapter
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.jsonResponse.AlbumInfoResponse
import com.chouli.musicalappproject.jsonResponse.AlbumSingleHomeResponse
import com.chouli.musicalappproject.viewmodels.AlbumViewModel
import com.chouli.musicalappproject.viewmodels.Status
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_album.*
import java.util.*

class ALbumActivity : AppCompatActivity() {

    private var viewModel: AlbumViewModel? = null
    private var adapter: MultipleViewRecyclerViewAlbumArtistActivityAdapter? = null
    private var album: AlbumSingleHomeResponse? = null
    private var isFavorite = false;
    private var albumItem: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        supportActionBar?.hide();

        adapter = applicationContext?.let { MultipleViewRecyclerViewAlbumArtistActivityAdapter(it) }
        viewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        viewModel!!.init(applicationContext)

        viewModel!!.getArtistAlbumDetailResponseLiveData()?.observe(this,
            { result ->
                when(result.status){
                    Status.LOADING ->{
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR  -> {
                        progressBar.visibility = View.GONE
                        var baseView = findViewById<View>(R.id.baseView)
                        Snackbar.make(baseView, resources.getString(R.string.error_get_infos), Snackbar.LENGTH_INDEFINITE)
                            .setAction(resources.getString(R.string.retry)) {
                                this.getAlbum()
                            }
                            .show()
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        scroll.visibility = View.VISIBLE
                        result.data?.let {
                            fillAlbumDescription(it.album)
                            setSongNumber(it.trackSize)
                            adapter!!.setValues(it.albumTracksTitle)
                            isFavorite = it.isFavorite
                            if(isFavorite){
                                albumIsFavorite.setImageResource(R.drawable.ic_like_on)
                            }else{
                                albumIsFavorite.setImageResource(R.drawable.ic_like_off)
                            }
                        }
                    }
                }

            })

        var recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(this.applicationContext)
        recyclerView?.adapter = adapter;

        this.getAlbum()
        this.setButtonQuit()
        this.setButtonFavorite()
    }

    private fun getAlbum(){
        var album = intent.extras?.getParcelable<AlbumSingleHomeResponse>("album");
        if (album != null) {
            this.album = album
            this.album!!.idAlbum?.let { viewModel?.getAlbumInfo(it) }
        };
    }

    private fun setSongNumber(tracks: Int) {
        if(tracks == 0){
            albumSongNumber.text = resources.getString(R.string.tracks_subtitle,0)
        } else {
            albumSongNumber.text = resources.getString(R.string.tracks_subtitle,tracks)
        }
    }

    private fun fillAlbumDescription(albumInfo: AlbumInfoResponse) {
        this.albumItem = albumInfo.album[0]
        var info = albumInfo.album[0]
        if (info.strAlbumThumb != ""){
            Picasso.get().load(info.strAlbumThumb).into(backgroundAlbumPicture)
            Picasso.get().load(info.strAlbumThumb).into(albumPicture)
        }
        albumAuthor.text = info.strArtist
        albumTitle.text = info.strAlbum
        votes.text = info.intScoreVotes

        if(info.intScoreVotes == null){
            votes.text =  resources.getString(R.string.votes,0)
        } else {
            votes.text = resources.getString(R.string.votes, info.intScoreVotes!!.toInt())
        }

        if(info.intScore == null){
            rate.text = "0"
        } else {
            rate.text = info.intScore
        }
        var desc = info["strDescription" + Locale.getDefault().language.uppercase()]
        albumDescription.text = if(desc != "" && desc != null) desc else  resources.getString(R.string.no_description)
    }

    fun setButtonQuit(){
        back.setOnClickListener {
            finish()
        }
    }

    fun setButtonFavorite(){
        albumIsFavorite.setOnClickListener {
            if(!isFavorite){
                addInFavorite()
                albumIsFavorite.setImageResource(R.drawable.ic_like_on)
            }else{
                removeInFavorite()
                albumIsFavorite.setImageResource(R.drawable.ic_like_off)
            }
            isFavorite = !isFavorite
        }
    }

    fun addInFavorite(){
        this.albumItem?.let { viewModel?.addFavoriteAlbum(it) }
    }

    fun removeInFavorite(){
        this.albumItem?.let { viewModel?.deleteFavoriteAlbum(it) }
    }

}