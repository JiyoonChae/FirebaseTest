package com.jy.firebaseremoteconfig

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotePagerAdapter (private val quotes: List<Quote>, private val isName: Boolean): RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    inner class QuoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val quoteTextView= itemView.findViewById<TextView>(R.id.quoteTextView)
        private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)

        fun bind(quote:Quote){
            quoteTextView.text = quote.quote
            nameTextView.text = quote.name
        }
    }
}