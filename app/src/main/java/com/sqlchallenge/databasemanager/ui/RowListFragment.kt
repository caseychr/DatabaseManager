package com.sqlchallenge.databasemanager.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.ResourceView
import com.sqlchallenge.databasemanager.ResourceViewObserver
import com.sqlchallenge.databasemanager.model.ColumnData
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.android.synthetic.main.fragment_row_list.*
import kotlinx.android.synthetic.main.layout_table_info.view.*

class RowListFragment : Fragment() {

    lateinit var tableName: String
    lateinit var rowViewModel: RowListViewModel
    lateinit var displayLoader: UICommunicator

    lateinit var columnInfoAdapter: ColumnInfoRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_row_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayLoader = activity as UICommunicator
        tableName = arguments?.getString(TABLE_NAME) ?: "Form"
        subscribeObservers()
        tableInfoLayout.tableNameTextView.text = tableName
    }

    private fun subscribeObservers() {
        rowViewModel = ViewModelProvider(this@RowListFragment)[RowListViewModel::class.java]
        rowViewModel.apply {
            columnsLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getTableView))
            rowCountLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getRowCount))
            tableDataLiveDataTest.observe(viewLifecycleOwner, ResourceViewObserver(getTableDataTest))
        }
        // Fetch Column info of table and row count
        rowViewModel.getColumns(tableName)
        rowViewModel.getRowCount(tableName)
    }

    private val getTableView = object : ResourceView<List<ColumnData>> {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun showData(data: List<ColumnData>) { rowViewModel.getTableData(data, tableName) }
        override fun showLoading(isLoading: Boolean) { displayLoader.displayProgress(isLoading) }
        override fun showError(error: Throwable) { handleError(error) }

    }

    private val getRowCount = object : ResourceView<Int> {
        override fun showData(data: Int) {
            tableInfoLayout.rowsNumberTextView.text = "(${data.toString()} Rows)"
        }
        override fun showLoading(isLoading: Boolean) { displayLoader.displayProgress(isLoading) }
        override fun showError(error: Throwable) { handleError(error) }

    }

    private val getTableDataTest = object : ResourceView<List<ColumnData>> {
        override fun showData(data: List<ColumnData>) {
            columnInfoAdapter = ColumnInfoRecyclerAdapter(data)
            columnRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@RowListFragment.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = columnInfoAdapter
            }
        }
        override fun showLoading(isLoading: Boolean) { displayLoader.displayProgress(isLoading) }
        override fun showError(error: Throwable) { handleError(error) }

    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
        Snackbar.make(this@RowListFragment.view!!, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }


}