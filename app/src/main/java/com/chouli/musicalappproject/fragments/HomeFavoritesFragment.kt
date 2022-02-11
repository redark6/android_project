package com.chouli.musicalappproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewRecyclerViewAlbumArtistActivityAdapter
import com.chouli.musicalappproject.viewmodels.FavoritesViewModel
import com.chouli.musicalappproject.viewmodels.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home_favorites.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFavoritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: FavoritesViewModel? = null
    private var adapter: MultipleViewRecyclerViewAlbumArtistActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = this.context?.let { MultipleViewRecyclerViewAlbumArtistActivityAdapter(it) }
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        this.context?.let { viewModel!!.init(it) }
        viewModel!!.getFavoritesAritistsAlbumsResponseLiveData().observe(this,
            { result ->
                when(result.status){
                    Status.LOADING ->{
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR  -> {
                        progressBar.visibility = View.GONE
                        var baseView = view?.findViewById<View>(R.id.baseView)
                        if (baseView != null) {
                            Snackbar.make(baseView, resources.getString(R.string.error_get_infos), Snackbar.LENGTH_INDEFINITE)
                                .setAction(resources.getString(R.string.retry)) {
                                    this.getFavorites()
                                }
                                .show()
                        }
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        favoritesRecyclerView.visibility = View.VISIBLE
                        result.data?.let { adapter!!.setValues(it.artistsAlbumsTitle) }
                    }
                }

            })
        this.getFavorites()
    }

    fun getFavorites(){
        viewModel?.getFavoritesAritistsAlbums()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home_favorites, container, false)

        var recyclerView = view.findViewById<View>(R.id.favoritesRecyclerView) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        recyclerView?.adapter = adapter;

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}