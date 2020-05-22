package com.sqlchallenge.databasemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.ResourceView
import com.sqlchallenge.databasemanager.ResourceViewObserver
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.android.synthetic.main.fragment_table_list.*

class TableListFragment : Fragment(), TableRecyclerAdapter.TableOnClick {

    lateinit var rowViewModel: RowListViewModel
    lateinit var tableAdapter: TableRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rowViewModel = ViewModelProvider(this@TableListFragment)[RowListViewModel::class.java]
        rowViewModel.tablesLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getTablesView))
        rowViewModel.getAllTables()
    }

    private val getTablesView = object : ResourceView<List<String>> {
        override fun showData(data: List<String>) {
            tableAdapter = TableRecyclerAdapter(data, this@TableListFragment)
            tableRecyclerView.apply {
                layoutManager = GridLayoutManager(this@TableListFragment.context, 3)
                adapter = tableAdapter
            }

        }
        override fun showLoading(isLoading: Boolean) {
        }

        override fun showError(error: Throwable) {
            println("ERROR: ${error.message}")
            Toast.makeText(this@TableListFragment.context, error.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(name: String) {
        Toast.makeText(this@TableListFragment.context, name, Toast.LENGTH_SHORT).show()
    }
}