package com.decode.nextjob.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.decode.nextjob.helpers.helpers
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

        btnShareLink.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Welcome to your NextJob, apply on this role\n\nPosition: "+txvTitleJobInfo.text+"\n"+"Company: "+txvCompanyInfo.text+
                "\nCommitment: "+txvComitmentInfo.text+"\n\nApply now on\n"+bundle!!.getString("applyUrl")+"\n\n\nTo find more jobs download the NextJob on playstore https://bit.ly/decodeinc")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }





        if(!helpers.isInternetAvailable()){
            showdialog(this)
        }
    }
    fun showdialog(c: Context){
        val alert= AlertDialog.Builder(c);
        alert.setMessage("Check out your internet connection and find out your next jobs")
            .setCancelable(false)
            .setPositiveButton("Check again", DialogInterface.OnClickListener { dialogInterface, i ->
                if (!helpers.isInternetAvailable()){
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }

            }).setNegativeButton("Exit", DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            }).setTitle("Ups, Connection")
            .setIcon(R.drawable.no_internet).create()

        alert.show()
    }
}