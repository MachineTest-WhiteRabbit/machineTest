package com.example.machinetest.ui

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.machinetest.R
import com.example.machinetest.ui.db.PersonDatabase
import com.example.machinetest.ui.models.Address
import com.example.machinetest.ui.models.Company
import kotlinx.android.synthetic.main.activity_detaild_page.*

class DetaildPage : AppCompatActivity() {

    var id : String = "";
    var pDatabase : PersonDatabase? = PersonDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaild_page)

        id = intent.getStringExtra("id");
        pName.text = intent.getStringExtra("name")
        email.text = intent.getStringExtra("email")
        phone.text = intent.getStringExtra("phone")
        website.text = intent.getStringExtra("website");

        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)

        Glide.with(this).load(intent.getStringExtra("image")).apply(requestOptions).into(pImage)

        GetCompanyDataFromDb(this).execute()
    }


    inner class GetCompanyDataFromDb(var context: DetaildPage) : AsyncTask<Void, Void, List<Company>>() {
        override fun doInBackground(vararg params: Void?): List<Company> {
            return context.pDatabase!!.cmDao().getSelected(id)
        }
        override fun onPostExecute(pList: List<Company>?) {
            company.text = "Company :: "+pList?.get(0)?.name +" : "+pList?.get(0)?.catchPhrase+" : "+pList?.get(0)?.bs
        }
    }

    inner class GetAddressFromDb(var context: DetaildPage) : AsyncTask<Void, Void, List<Address>>() {
        override fun doInBackground(vararg params: Void?): List<Address> {
            return context.pDatabase!!.addDao().getSelected(id)
        }
        override fun onPostExecute(pList: List<Address>?) {
            address.text=  "Address :: "+pList?.get(0)?.city +" : "+pList?.get(0)?.street +" : "+pList?.get(0)?.zipcode +" : "
        }
    }

    private fun setDataToREcycler() {

    }
}
