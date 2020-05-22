package com.sqlchallenge.databasemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.ResourceView
import com.sqlchallenge.databasemanager.ResourceViewObserver
import com.sqlchallenge.databasemanager.model.ColumnInformation
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class RowListFragment : Fragment() {
    lateinit var tableName: String

    lateinit var rowViewModel: RowListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_row_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableName = arguments?.getString(TABLE_NAME) ?: "Form"
        println(tableName)
        textView.text = tableName
        rowViewModel = ViewModelProvider(this@RowListFragment)[RowListViewModel::class.java]
        rowViewModel.columnsLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getTableView))
        rowViewModel.getColumns(tableName)

        rowViewModel.rowCountLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getRowCount))
        rowViewModel.tableDataLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getTableData))
    }

    private val getTableView = object : ResourceView<List<ColumnInformation>> {
        override fun showData(data: List<ColumnInformation>) {
            rowViewModel.getRowCount(tableName)
        }

        override fun showLoading(isLoading: Boolean) {

        }

        override fun showError(error: Throwable) {
            println("ERROR: ${error.message}")
        }

    }

    private val getRowCount = object : ResourceView<Int> {
        override fun showData(data: Int) {
            println("ROW COUNT: $data")
            rowViewModel.getTableData(tableName)
        }

        override fun showLoading(isLoading: Boolean) {
        }

        override fun showError(error: Throwable) {
            println("ERROR: ${error.message}")
        }

    }

    private val getTableData = object : ResourceView<Any> {
        override fun showData(data: Any) {
            println("TABLE DATA: ${data.toString()}")
        }

        override fun showLoading(isLoading: Boolean) {
        }

        override fun showError(error: Throwable) {
            println("ERROR: ${error.message}")
        }

    }
}