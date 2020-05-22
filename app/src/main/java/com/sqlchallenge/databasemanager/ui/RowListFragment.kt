package com.sqlchallenge.databasemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sqlchallenge.databasemanager.R
import com.sqlchallenge.databasemanager.ResourceView
import com.sqlchallenge.databasemanager.ResourceViewObserver
import com.sqlchallenge.databasemanager.model.Form
import com.sqlchallenge.databasemanager.viewmodel.RowListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class RowListFragment : Fragment() {

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
        rowViewModel = ViewModelProvider(this@RowListFragment)[RowListViewModel::class.java]
        rowViewModel.formsLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getFormsView))
        rowViewModel.getForms()
    }

    private val getFormsView = object : ResourceView<List<Form>> {
        override fun showData(data: List<Form>) {
            println("DATA RETURNED: $data")
        }
        override fun showLoading(isLoading: Boolean) {
        }
        override fun showError(error: Throwable) {
            println("ERROR: ${error.message}")
            Toast.makeText(this@RowListFragment.context, error.message, Toast.LENGTH_SHORT).show()
        }
    }
}