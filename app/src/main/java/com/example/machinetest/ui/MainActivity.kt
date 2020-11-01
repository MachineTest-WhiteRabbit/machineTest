package com.example.machinetest.ui

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Ignore
import com.example.machinetest.R
import com.example.machinetest.ui.adapters.ProductAdapter
import com.example.machinetest.ui.db.PersonDatabase
import com.example.machinetest.ui.models.*
import com.example.machinetest.ui.rest.ApiClient
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var mCompositeDisposable : CompositeDisposable? = null
    var mCount = 0
    var pDatabase : PersonDatabase? = null
    var proList : List<PersonData>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        pDatabase = PersonDatabase.getDatabase(this)
        recyclerProducts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        clickOnMe.setOnClickListener {
            //Fetching the data from url
            showLoader(true)
            fetchDataFromURL()
        }
    }

    /**
     * Fetch data from url
     */

    private fun fetchDataFromURL() {
        try {
            mCompositeDisposable = CompositeDisposable()
            val requestInterface = ApiClient.create()
            mCompositeDisposable?.add(
                requestInterface.getDataFromURL()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
            )
        } catch (e: Exception) {
        }
    }

    private fun handleResponse(resp: Response<JsonArray>) {
        try {
            showLoader(false)
            Log.e("Success :: ","fetch success")
            val responseData = resp.body()
                val dataArray : JsonArray? = responseData

                val gson = GsonBuilder().create()
                val productListType = object :TypeToken<List<PersonData>>() {}.type
                proList = gson.fromJson<List<PersonData>>(dataArray,productListType)
                mCount = proList!!.size
                Log.e("Response :: ",proList!!.size.toString());
                Toast.makeText(this,mCount.toString(),Toast.LENGTH_SHORT).show()
                insertIntoDataBase(0)


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Response  :: ",e.message+"");
        }
    }

    private fun insertIntoDataBase(i:Int) {
//        var cObj = proList?.get(i)?.company
//        var companyObj = Company(0, proList?.get(i)?.id, cObj?.name, cObj?.catchPhrase, cObj?.bs);
            InsertTask(this, proList!![i],i).execute()
    }

    private fun handleError(error: Throwable) {
        try {
            showLoader(false)
            Log.e("Error :: ","Some Error Occurs")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Response  :: ",e.message+"");
        }
    }

    private fun showLoader(b : Boolean){
        if(b)
            spin_kit.visibility = View.VISIBLE
        else
            spin_kit.visibility = View.GONE

        clickOnMe.isEnabled = !b
    }

    /**
     * Inserting into local database
     */
    inner class InsertTask(
        var context: MainActivity,
        var product: PersonData,
        var i: Int
    ) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            var personObj = Person(0,product.id,product.name,product.username,product.email,product.profile_image,product.phone,product.website);
            context.pDatabase!!.prDao().insertProduct(personObj)

            var cObj = product.company
            var companyObj = Company(0,product.id, cObj?.name, cObj?.catchPhrase, cObj?.bs);
            context.pDatabase!!.cmDao().insertCompnay(companyObj)

            var addressObj = Address(0,product.id,
                product.address?.street, product.address?.suite, product.address?.city, product.address?.zipcode
            );
            context.pDatabase!!.addDao().insertAddress(addressObj)

            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
               // InsertTask2(context,proList!![i].company,i).execute()
                if(i<mCount-1) {
                    i=i+1
                    insertIntoDataBase(i)
                }else{
                    GetDataFromDb(context).execute()
                }
            }
        }
    }

    inner class GetDataFromDb(var context: MainActivity) : AsyncTask<Void, Void, List<Person>>() {
        override fun doInBackground(vararg params: Void?): List<Person> {
            return context.pDatabase!!.prDao().getAll()
        }
        override fun onPostExecute(pList: List<Person>?) {
            Toast.makeText(applicationContext, "Get Data :: "+pList?.size.toString(),Toast.LENGTH_SHORT).show()
           setDataToREcycler(pList,context)
        }
    }

    private fun setDataToREcycler(
        pList: List<Person>?,
        context: MainActivity) {
        clickOnMe.visibility = View.GONE
        recyclerProducts.adapter = ProductAdapter(pList!!,context)
        (recyclerProducts.adapter as ProductAdapter).notifyDataSetChanged()
    }

}
