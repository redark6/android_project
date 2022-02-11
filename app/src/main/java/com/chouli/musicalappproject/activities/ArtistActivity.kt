package com.chouli.musicalappproject.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewRecyclerViewAlbumArtistActivityAdapter
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.jsonResponse.AlbumSingleHomeResponse
import com.chouli.musicalappproject.jsonResponse.Artist
import com.chouli.musicalappproject.jsonResponse.ArtistInfoResponse
import com.chouli.musicalappproject.viewmodels.ArtistViewModel
import com.chouli.musicalappproject.viewmodels.Status
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_artist.*
import kotlinx.android.synthetic.main.activity_artist.back
import kotlinx.android.synthetic.main.activity_artist.progressBar
import kotlinx.android.synthetic.main.activity_artist.scroll
import java.util.*

class ArtistActivity : AppCompatActivity() {

    private var viewModel: ArtistViewModel? = null
    private var adapter: MultipleViewRecyclerViewAlbumArtistActivityAdapter? = null
    private var artist: AlbumSingleHomeResponse? = null
    private var isFavorite = false;
    private var artistItem: Artist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        supportActionBar?.hide();
        adapter = applicationContext?.let { MultipleViewRecyclerViewAlbumArtistActivityAdapter(it) }
        viewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        viewModel!!.init(applicationContext)

        viewModel!!.getArtistDetailResponseLiveData()?.observe(this,
            { result ->
                when(result.status){
                    Status.LOADING ->{
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR  -> {
                        progressBar.visibility = View.GONE
                        var baseView = findViewById<View>(R.id.baseView)
                        Snackbar.make(baseView, resources.getString(R.string.error_get_infos), Snackbar.LENGTH_LONG)
                            .setAction(resources.getString(R.string.retry)) {
                                this.getArtist()
                            }
                            .show()
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        scroll.visibility = View.VISIBLE
                        result.data?.let {
                            fillArtistDescription(it.artist)
                            adapter!!.setValues(it.albumTracksTitle)
                            isFavorite = it.isFavorite
                            if(isFavorite){
                                artistIsFavorite.setImageResource(R.drawable.ic_like_on)
                            }else{
                                artistIsFavorite.setImageResource(R.drawable.ic_like_off)
                            }
                        }
                    }
                }

            })
        var recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(this.applicationContext)
        recyclerView?.adapter = adapter;

        this.getArtist()
        this.setButtonQuit()
        this.setButtonFavorite()
    }

    private fun getArtist(){
        var artist = intent.extras?.getParcelable<AlbumSingleHomeResponse>("artist")
        if (artist != null) {
            this.artist = artist
            this.artist!!.idArtist?.let { this.artist!!.strArtist?.let { it1 ->
                viewModel?.getArtistInfo(it,
                    it1
                )
            } }
        };
    }

    private fun fillArtistDescription(artistInfo: ArtistInfoResponse) {
        this.artistItem = artistInfo.artists[0]
        var info = artistInfo.artists[0]
        if (info.strArtistThumb != ""){
            Picasso.get().load(info.strArtistThumb).into(backgroundArtistPicture)
        }
        artistName.text = info.strArtist
        artistGenre.text = info.strGenre
        artistLocation.text = info.strCountry
        var desc = info["strBiography" + Locale.getDefault().language.uppercase()]
        artistDescription.text = if(desc != "" && desc != null) desc else  resources.getString(R.string.no_description)
    }

    fun setButtonQuit(){
        back.setOnClickListener {
            finish()
        }
    }

    fun setButtonFavorite(){
        artistIsFavorite.setOnClickListener {
            if(!isFavorite){
                addInFavorite()
                artistIsFavorite.setImageResource(R.drawable.ic_like_on)
            }else{
                removeInFavorite()
                artistIsFavorite.setImageResource(R.drawable.ic_like_off)
            }
            isFavorite = !isFavorite
        }
    }

    fun addInFavorite(){
        this.artistItem?.let { viewModel?.addFavoriteArtist(it) }
    }

    fun removeInFavorite(){
        this.artistItem?.let { viewModel?.deleteFavoriteArtist(it) }
    }

}