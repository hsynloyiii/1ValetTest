package com.example.a1valettest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withActionIconDrawable(@DrawableRes resourceId: Int): Matcher<View?> {
    return object : BoundedMatcher<View?, ActionMenuItemView>(ActionMenuItemView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has image drawable resource $resourceId")
        }

        override fun matchesSafely(actionMenuItemView: ActionMenuItemView): Boolean {
            return sameBitmap(
                actionMenuItemView.context,
                actionMenuItemView.itemData.icon,
                resourceId,
                actionMenuItemView
            )
        }
    }
}


private fun sameBitmap(
    context: Context,
    drawable: Drawable?,
    resourceId: Int,
    view: View
): Boolean {
    var drawable = drawable
    val otherDrawable: Drawable? = ResourcesCompat.getDrawable(context.resources, resourceId, null)
    if (drawable == null || otherDrawable == null) {
        return false
    }

    if (drawable is StateListDrawable) {
        val getStateDrawableIndex =
            StateListDrawable::class.java.getMethod(
                "getStateDrawableIndex",
                IntArray::class.java
            )
        val getStateDrawable =
            StateListDrawable::class.java.getMethod(
                "getStateDrawable",
                Int::class.javaPrimitiveType
            )
        val index = getStateDrawableIndex.invoke(drawable, view.drawableState)
        drawable = getStateDrawable.invoke(drawable, index) as Drawable
    }

    val bitmap = getBitmapFromDrawable(drawable)
    val otherBitmap = getBitmapFromDrawable(otherDrawable)
    return bitmap.sameAs(otherBitmap)
}

private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
    val bitmap: Bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
