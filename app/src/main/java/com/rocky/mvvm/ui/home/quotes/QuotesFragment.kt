package com.rocky.mvvm.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rocky.mvvm.R
import com.rocky.mvvm.data.db.entities.Quote
import com.rocky.mvvm.util.Coroutines
import com.rocky.mvvm.util.hide
import com.rocky.mvvm.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: QuotesViewModel

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
        Coroutines.main {
            val quotes = viewModel.quotes.await()
            /* if (viewLifecycleOwner != null)
                 quotes.observe(viewLifecycleOwner, Observer {
                     context?.toast(it.size.toString())
                 })*/
            bindUI()
        }
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        val quotes = viewModel.quotes.await()
        quotes.observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuotItem())
        })
    }

    private fun initRecyclerView(toQuotItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(toQuotItem)
        }
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }

    private fun List<Quote>.toQuotItem(): List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }

}
