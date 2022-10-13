package com.locotodevteam.seprecempresas.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.adapter.CompanyAdapter
import com.locotodevteam.seprecempresas.databinding.FragmentSearchBinding
import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.view.MainActivity

class SearchFragment : Fragment(), CompanyAdapter.OnItemClickListener {
    lateinit var searchBinding: FragmentSearchBinding
    lateinit var adapter: CompanyAdapter
    val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchBinding = FragmentSearchBinding.bind(view)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchViewModel.firstCall(requireContext())
        initRecyclerView()
        initSearchView()
        initSubscriptions()
    }

    private fun initRecyclerView(){
        searchBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CompanyAdapter(emptyList(), this)
        searchBinding.recyclerView.adapter = adapter
    }

    private fun initSearchView(){
        searchBinding.mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchText -> searchViewModel.searchCompany(searchText, requireContext()) }
                searchBinding.myProgressBar.visibility = View.VISIBLE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initSubscriptions(){
        searchViewModel.companyList.observe(viewLifecycleOwner) { companyList ->
            searchBinding.myProgressBar.visibility = View.GONE
            if(companyList?.datos?.filas?.size == 0) {
                val snack = Snackbar.make(
                    searchBinding.root,
                    "No se han encontrado datos",
                    Snackbar.LENGTH_LONG
                )
                snack.setAnchorView((activity as MainActivity).mainBinding.myBottomNavView)
                snack.show()
            }
            adapter.updateList(companyList?.datos?.filas as List<Company.Datos.Fila>?)
        }

        searchViewModel.loadingFailed.observe(viewLifecycleOwner) { failed ->
            if (failed) {
                val snack = Snackbar.make(searchBinding.root, "No se han encontrado datos", Snackbar.LENGTH_LONG)
                snack.setAnchorView((activity as MainActivity).mainBinding.myBottomNavView)
                snack.show()
                searchBinding.myProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(companyId: String) {
        // navigate to detailFragment
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(companyId)
        findNavController().navigate(action)
    }
}