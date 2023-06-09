package com.bhx.bhx.View.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bhx.bhx.Controller.ProductAdminController
import com.bhx.bhx.Controller.PromotionsAdminController
import com.bhx.bhx.Controller.RetrofitInstance
import com.bhx.bhx.Model.AdminProduct
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.Promotion
import com.bhx.bhx.R
import com.bhx.bhx.View.Admin.Adapter.AdminProductAdapter
import com.bhx.bhx.View.Admin.Adapter.AdminPromotionAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminProductList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminProductList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var edtSearch: EditText?? = null
    private var horizontal_scroll_view: HorizontalScrollView?? =null
    private var isApiCalled: Boolean = false
    private var listProduct : List<AdminProduct> = listOf()
    private var listProductsSearch : List<AdminProduct> = listOf()
    private var btnSearch: Button??=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_product_list, container, false)
        var tableLayout = view.findViewById<TableLayout>(R.id.table_layout)
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view)
        edtSearch = view.findViewById(R.id.edtSearch)
        btnSearch = view.findViewById(R.id.btnSearch)
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAdd)


        btnAdd.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(
                R.id.adminContainer,
                AdminCreateProduct()
            ).commit()
        }

        if(!isApiCalled) {
            var apiProductAdminInstance: ProductAdminController = RetrofitInstance.getInstance().create(ProductAdminController::class.java)

            apiProductAdminInstance.findAll().enqueue(object : Callback<List<AdminProduct>> {
                override fun onResponse(
                    call: Call<List<AdminProduct>>,
                    response: Response<List<AdminProduct>>
                ) {

                    if (response.isSuccessful) {
                        var listOfProduct: List<AdminProduct>? = response.body()

                        if (listOfProduct == null) {
                            listOfProduct = listOf<AdminProduct>()
                        }
                        listProduct = listOfProduct
                        val myAdapter = AdminProductAdapter(requireContext(), listProduct)

                        tableLayout.removeAllViews()

                        for (i in 0 until myAdapter.count) {
                            val rowView = myAdapter.getView(i, null, tableLayout)
                            tableLayout.addView(rowView)
                        }
                        isApiCalled = true
                    }else{
                        Toast.makeText(container!!.context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<AdminProduct>>, t: Throwable) {
                    Toast.makeText(container!!.context,t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        btnSearch!!.setOnClickListener {
            val strSearch = edtSearch!!.text.toString()
            if(strSearch.length!=0) {
                RetrofitInstance.getInstance().create(ProductAdminController::class.java).search(strSearch).enqueue(object :
                    Callback<List<AdminProduct>> {
                    override fun onResponse(call: Call<List<AdminProduct>>, response: Response<List<AdminProduct>>) {
                        if (response.isSuccessful){
                            var listOfProduct: List<AdminProduct>? = response.body()

                            if (listOfProduct == null) {
                                listOfProduct = listOf<AdminProduct>()
                            }
                            listProductsSearch = listOfProduct as List<AdminProduct>
                            val myAdapter = AdminProductAdapter(requireContext(), listProductsSearch)

                            tableLayout.removeAllViews()

                            for (i in 0 until myAdapter.count) {
                                val rowView = myAdapter.getView(i, null, tableLayout)
                                tableLayout.addView(rowView)
                            }
                        }else {
                            Toast.makeText(context, "Tìm kiếm thất bại",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<AdminProduct>>, t: Throwable) {
                        Toast.makeText(context, "Tìm kiếm thất bại",Toast.LENGTH_SHORT).show()
                    }

                })
            }
            else {
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().replace(
                    R.id.adminContainer,
                    AdminProductList()
                ).commit()
            }
        }

        tableLayout.removeAllViews()


        // Khi không cần gọi api thì lấy thẳng cái cũ
        val myAdapter = AdminProductAdapter(requireContext(), listProduct)
        for (i in 0 until myAdapter.count) {
            val rowView = myAdapter.getView(i, null, tableLayout)
            tableLayout.addView(rowView)
        }

        return view
    }
}