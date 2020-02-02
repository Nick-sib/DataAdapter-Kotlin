package ltd.nickolay.listclick.Kt

import android.view.View

/**Class from internet for div Single and Double click */

abstract class OnDoubleClickListenerK : View.OnClickListener {
    private val TIME_DELTA: Long = 300
    private var lastTimeClick: Long = 0
    abstract fun onSingleClick(view: View)
    abstract fun onDoubleClick(view: View)

    override fun onClick(v: View) {
        val currentTimeClick = System.currentTimeMillis()
        if (currentTimeClick - lastTimeClick < TIME_DELTA)
            onDoubleClick(v)
        else
            onSingleClick(v)
        lastTimeClick = currentTimeClick
    }

}