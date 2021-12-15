package com.decode.nextjob.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import kotlinx.android.synthetic.main.info_job.*

class JobInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_job)

        var bundle = intent.extras
        txvTitleJobInfo.setText(bundle!!.getString("tittle"))
        txvComitmentInfo.setText(bundle!!.getString("commitment"))
        txvCompanyInfo.setText(bundle!!.getString("company"))
        txvLocationInfo.setText(bundle!!.getString("location"))
        txvDescription.setText(bundle!!.getString("description"))



        var urlImg = bundle!!.getString("photo")

        if (urlImg.isNullOrEmpty() || urlImg != null) {
            Glide.with(this).load(urlImg).into(imgCompanyNameInfo)
            Glide.with(this).load(urlImg).into(imgJobInfo)
        }else{
            imgCompanyNameInfo.setImageResource(R.drawable.companylogo)
            imgJobInfo.setImageResource(R.drawable.background)
        }

        btnBack.setOnClickListener{
            onBackPressed()
        }

       txvApply.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(bundle!!.getString("applyUrl")))
                startActivity(browserIntent);



        }

      //  txvComitmentInfo.setText()






    }
}