package com.example.onlinecinemaapp.extensions

import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.onlinecinemaapp.R
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

// helper для adapterDelegates установка данных в recycler
fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

// helper отвязывает adapter если view отвязывается от экрана
fun RecyclerView.setAdapterAndCleanupOnDetachFromWindow(recyclerViewAdapter: RecyclerView.Adapter<*>) {
    adapter = recyclerViewAdapter
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            adapter = null
            removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) {
        }
    })
}

// helper для either
inline fun <reified T> attempt(func: () -> T): Either<Throwable, T> = try {
    Either.Right(func.invoke())
} catch (e: Throwable) {
    Either.Left(e)
}

// скрыть статус бар api 30
fun FragmentActivity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        window.insetsController?.hide(WindowInsets.Type.navigationBars())
    }
}

// показать статус бар api 30
fun FragmentActivity.showStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars())
        window.insetsController?.show(WindowInsets.Type.navigationBars())
    }
}

// скрыть статус бар api <30
@Suppress("DEPRECATION")
fun FragmentActivity.hideNavigationBar() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

// показать статус бар api <30
@Suppress("DEPRECATION")
fun FragmentActivity.showNavigationBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

// конвертация dp в пиксели
fun Resources.dpToPx(dip: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        displayMetrics
    )
}

// загрузка изображения с помощью glide
fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.color.grey_900)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

// установить view видимой
fun View.visible() {
    visibility = View.VISIBLE
}

// установить view скрытой
fun View.gone() {
    visibility = View.GONE
}