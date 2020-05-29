package com.sqlchallenge.databasemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.ResourceView
import com.sqlchallenge.databasemanager.ResourceViewObserver
import com.sqlchallenge.databasemanager.viewmodel.TableListViewModel
import kotlinx.android.synthetic.main.fragment_table_list.*

const val TABLE_NAME = "TABLE_NAME"

class TableListFragment : Fragment(), TableRecyclerAdapter.TableOnClick {

    lateinit var viewModel: TableListViewModel
    lateinit var tableAdapter: TableRecyclerAdapter
    lateinit var displayLoader: UICommunicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayLoader = activity as UICommunicator
        viewModel = ViewModelProvider(this@TableListFragment)[TableListViewModel::class.java]
        apply { viewModel.tablesLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getTablesView))
            viewModel.getAllTables() }
    }

    private val getTablesView = object : ResourceView<List<String>> {
        override fun showData(data: List<String>) {
            tableAdapter = TableRecyclerAdapter(data, this@TableListFragment)
            tableRecyclerView.apply {
                layoutManager = GridLayoutManager(this@TableListFragment.context, 3)
                adapter = tableAdapter
            }
        }
        override fun showLoading(isLoading: Boolean) { displayLoader.displayProgress(isLoading) }
        override fun showError(error: Throwable) { handleError(error) }

    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
        Snackbar.make(this@TableListFragment.view!!, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }


    override fun onClick(name: String) {
        val fragment = RowListFragment()
        val bundle = Bundle()
        bundle.putString(TABLE_NAME, name)
        fragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }
}