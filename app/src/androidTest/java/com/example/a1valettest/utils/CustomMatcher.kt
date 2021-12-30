package com.example.a1valettest.utils

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
import android.graphics.drawable.ColorDrawable

import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.test.internal.util.Checks


// Matcher for icons
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


// Matcher for color
fun withBgColor(color: Int): Matcher<View?> {
    Checks.checkNotNull(color)
    return object : BoundedMatcher<View?, CoordinatorLayout>(CoordinatorLayout::class.java) {
        override fun matchesSafely(row: CoordinatorLayout): Boolean {
            return color == (row.background as ColorDrawable).color
        }

        override fun describeTo(description: Description) {
            description.appendText("with text color: ")
        }
    }
}


private fun sameBitmap(
    context: Context,
    drawable: Drawable?,
    resourceId: Int,
    view: View
): Boolean {
    var newDrawable = drawable
    val otherDrawable: Drawable? = ResourcesCompat.getDrawable(context.resources, resourceId, null)
    if (newDrawable == null || otherDrawable == null) {
        return false
    }

    if (newDrawable is StateListDrawable) {
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
        val index = getStateDrawableIndex.invoke(newDrawable, view.drawableState)
        newDrawable = getStateDrawable.invoke(newDrawable, index) as Drawable
    }

    val bitmap = getBitmapFromDrawable(newDrawable)
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
