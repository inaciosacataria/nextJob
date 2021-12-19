package com.decode.nextjob.helpers

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class CustomAlertDialog {
   fun showdialog(c: Context){
       val alert= AlertDialog.Builder(c);
       alert.setMessage("Check out your internet connection and find out your next jobs")
           .setCancelable(false)
           .setPositiveButton("Check again", DialogInterface.OnClickListener { dialogInterface, i ->
            //   if (helpers.isInternetAvailable())

           }).setNegativeButton("exirt", DialogInterface.OnClickListener { dialogInterface, i ->  })
    }
}