package com.example.a1valettest.utils.extensions

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.a1valettest.R
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun Context.toast(msg: String) = Toast.makeText(
    this,
    msg,
    Toast.LENGTH_SHORT
).show()

fun View.snackBar(msg: String) = Snackbar.make(
    this,
    msg,
    Snackbar.LENGTH_SHORT
).show()

fun Context.alert(
    title: String,
    message: String,
    positiveButtonText: String,
    positiveButtonAction: (DialogInterface) -> Unit
): AlertDialog =
    MaterialAlertDialogBuilder(this, R.style.Base_Widget_Material3_MaterialAlertDialog)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveButtonAction(dialog)
        }
        .setCancelable(false)
        .show()

fun Context.singleChoiceAlert(
    title: String,
    listItems: Array<out CharSequence>,
    checkedItem: Int,
    onItemClickListener: (DialogInterface, Int) -> Unit
): AlertDialog = MaterialAlertDialogBuilder(this, R.style.Base_Widget_Material3_MaterialAlertDialog)
    .setTitle(title)
    .setSingleChoiceItems(listItems, checkedItem) { dialog, which ->
        onItemClickListener(dialog, which)
    }
    .show()


fun DeviceContent.convertToMyDeviceContent() = MyDeviceContent(
    this.id,
    this.os,
    this.status,
    this.price,
    this.currency,
    this.isFavorite,
    this.imageUrl,
    this.title,
    this.description,
    this.company
)

fun MyDeviceContent.convertToDeviceContent() = DeviceContent(
    this.id,
    this.os,
    this.status,
    this.price,
    this.currency,
    this.isFavorite,
    this.imageUrl,
    this.title,
    this.description,
    this.company
)



