package com.chouli.musicalappproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.layoutManagers.HomeTabLayoutFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home_ranking.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeRankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeRankingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val tabsTitles = arrayOf("Titres", "Albums")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home_ranking, container, false)


        var homeTabLayoutFragmentAdapter = HomeTabLayoutFragmentAdapter(requireActivity());
        var tabLayout =  view.findViewById<TabLayout>(R.id.homeRankingTabLayout)
        var viewPager2 = view.findViewById<ViewPager2>(R.id.homeRankingViewPager)
        if (viewPager2 != null && tabLayout != null) {
            viewPager2.adapter = homeTabLayoutFragmentAdapter
            TabLayoutMediator(tabLayout,viewPager2) { tab, position ->
                tab.text = tabsTitles[position]
            }.attach()
        };

        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeRankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeRankingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}