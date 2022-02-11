package com.chouli.musicalappproject.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewRecyclerViewAlbumArtistActivityAdapter
import com.chouli.musicalappproject.viewmodels.SearchViewModel
import com.chouli.musicalappproject.viewmodels.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home_research.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeResearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeResearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: SearchViewModel? = null
    private var adapter: MultipleViewRecyclerViewAlbumArtistActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = this.context?.let { MultipleViewRecyclerViewAlbumArtistActivityAdapter(it) }
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        this.context?.let { viewModel!!.init(it) }
        viewModel!!.getSearchResponseLiveData().observe(this,
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
                                    this.search("")
                                }
                                .show()
                        }
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        searchRecyclerView.visibility = View.VISIBLE
                        result.data?.let { adapter!!.setValues(it.artistsAlbumsTitle) }
                    }
                }

            })
        lifecycleScope.launch {
            viewModel?.resutOperation?.collect { value ->
                if (value != null) {
                    search(value)
                }
            }
        }
    }

    private fun search(name: String) {
        searchRecyclerView.visibility = View.GONE
        viewModel?.searchArtistsAlbums(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home_research, container, false)

        var recyclerView = view.findViewById<View>(R.id.searchRecyclerView) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        recyclerView?.adapter = adapter;

        var searchInput = view.findViewById<View>(R.id.searchInput) as EditText?

        searchInput?.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel?.setSearchQuery(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeResearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeResearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}