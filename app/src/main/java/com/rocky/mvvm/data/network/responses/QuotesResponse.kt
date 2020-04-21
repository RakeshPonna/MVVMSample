package com.rocky.mvvm.data.network.responses

import com.rocky.mvvm.data.db.entities.Quote

data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)