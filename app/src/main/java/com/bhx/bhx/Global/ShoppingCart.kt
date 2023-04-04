package com.bhx.bhx.Global

import com.bhx.bhx.Model.CartItem

class ShoppingCart private constructor() {
    var item: ArrayList<CartItem>? = ArrayList();

    companion object {
        @Volatile private var instance: ShoppingCart? = null;

        fun getInstance(): ShoppingCart {
            if(instance == null) {
                synchronized(this) {
                    if(instance == null) {
                        instance = ShoppingCart();
                    }
                }
            }
            return instance!!;
        }
    }
}