package com.bhx.bhx.View.HomeFragment

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.R
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Model.Product
import com.bhx.bhx.View.ProductOfCateFragment.ProductOfCateFragment

class ProductAdapter(private val categories: MutableList<ReviewCategory>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCateName: TextView = itemView.findViewById(R.id.tvCateName)
        val btnSeeMore: Button = itemView.findViewById(R.id.btnSeeMore)
        val revListProduct: RecyclerView = itemView.findViewById(R.id.revListProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.tvCateName.text = categories[position].name
        holder.btnSeeMore.text = "Xem tất cả " + (categories[position].countProducts) + " sản phẩm"

        holder.btnSeeMore.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.container,
                ProductOfCateFragment(categories[position])
            ).addToBackStack(null).commit()
        }
        var adapter = ListProductAdapter(categories[position].products as MutableList<Product>, context)
        holder.revListProduct.adapter = adapter
        holder.revListProduct.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}