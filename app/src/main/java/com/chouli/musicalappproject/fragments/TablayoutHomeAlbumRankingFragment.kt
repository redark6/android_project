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
import com.chouli.musicalappproject.adaptaters.HomeRankingRecyclerViewAdapter
import com.chouli.musicalappproject.viewmodels.HomeAlbumRankingViewModel
import com.chouli.musicalappproject.viewmodels.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tablayout_home_album_ranking.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TablayoutHomeAlbumRankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TablayoutHomeAlbumRankingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: HomeAlbumRankingViewModel? = null
    private var adapter: HomeRankingRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = this.context?.let { HomeRankingRecyclerViewAdapter(ArrayList(), it, "album") }
        viewModel = ViewModelProvider(this).get(HomeAlbumRankingViewModel::class.java)
        this.context?.let { viewModel!!.init(it) }
        viewModel!!.getTrendingResponseLiveData().observe(this,
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
                                    this.getTopItuneAlbums()
                                }
                                .show()
                        }
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        homeAlBumRankingRecyclerView.visibility = View.VISIBLE
                        result.data?.let { adapter!!.setValues(it.trending) }
                    }
                }

            })

        this.getTopItuneAlbums()
    }

    private fun getTopItuneAlbums(){
        viewModel?.getTopItuneAlbums()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_tablayout_home_album_ranking, container, false)

        var recyclerView = view.findViewById<View>(R.id.homeAlBumRankingRecyclerView) as RecyclerView?
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
         * @return A new instance of fragment TablayoutHomeAlbumRankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TablayoutHomeAlbumRankingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

