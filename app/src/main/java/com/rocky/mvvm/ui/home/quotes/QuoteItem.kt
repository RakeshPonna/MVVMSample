package com.rocky.mvvm.ui.home.quotes

import com.rocky.mvvm.R
import com.rocky.mvvm.data.db.entities.Quote
import com.rocky.mvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}
