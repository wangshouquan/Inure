package app.simple.inure.decorations.checkbox

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import app.simple.inure.R
import app.simple.inure.decorations.switchview.SwitchCallbacks
import app.simple.inure.util.ColorUtils.animateColorChange
import app.simple.inure.util.ColorUtils.resolveAttrColor
import app.simple.inure.util.ViewUtils

@SuppressLint("ClickableViewAccessibility")
class CheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CheckBoxFrameLayout(context, attrs, defStyleAttr) {

    private var thumb: ImageView
    private var switchCallbacks: SwitchCallbacks? = null

    private val tension = 3.5F
    private val thumbWidth = context.resources.getDimensionPixelOffset(R.dimen.switch_thumb_dimensions)

    private var isChecked: Boolean = false

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.radio_button_view, this, true)

        thumb = view.findViewById(R.id.switch_thumb)

        clipChildren = false
        clipToPadding = false
        clipToOutline = false

        ViewUtils.addShadow(this)

        view.setOnClickListener {
            isChecked = if (isChecked) {
                animateUnchecked()
                switchCallbacks?.onCheckedChanged(false)
                false
            } else {
                animateChecked()
                switchCallbacks?.onCheckedChanged(true)
                true
            }
        }

        requestLayout()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                thumb.animate()
                    .scaleY(1.5F)
                    .scaleX(1.5F)
                    .setInterpolator(DecelerateInterpolator(1.5F))
                    .setDuration(500L)
                    .start()
            }
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP,
            -> {
                thumb.animate()
                    .scaleY(1.0F)
                    .scaleX(1.0F)
                    .setInterpolator(DecelerateInterpolator(1.5F))
                    .setDuration(500L)
                    .start()
            }
        }

        return super.onTouchEvent(event)
    }

    fun setChecked(boolean: Boolean) {
        isChecked = if (boolean) {
            animateChecked()
            boolean
        } else {
            animateUnchecked()
            boolean
        }
    }

    private fun animateUnchecked() {
        thumb.animate()
            .scaleX(0F)
            .scaleY(0F)
            .alpha(0F)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(500L)
            .start()

        animateColorChange(ContextCompat.getColor(context, R.color.switch_off))
        animateElevation(0F)
    }

    private fun animateChecked() {
        thumb.animate()
            .scaleX(1F)
            .scaleY(1F)
            .alpha(1F)
            .setInterpolator(OvershootInterpolator(tension))
            .setDuration(500L)
            .start()

        animateColorChange(context.resolveAttrColor(R.attr.colorAppAccent))
        animateElevation(25F)
    }

    private fun animateElevation(elevation: Float) {
        val valueAnimator = ValueAnimator.ofFloat(this.elevation, elevation)
        valueAnimator.duration = 500L
        valueAnimator.interpolator = LinearOutSlowInInterpolator()
        valueAnimator.addUpdateListener {
            this.elevation = it.animatedValue as Float
        }
        valueAnimator.start()
    }

    fun setOnCheckedChangeListener(switchCallbacks: SwitchCallbacks) {
        this.switchCallbacks = switchCallbacks
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        thumb.clearAnimation()
    }

    /**
     * Inverts the switch's checked status. If the switch is checked then
     * it will be unchecked and vice-versa
     */
    fun invertCheckedStatus() {
        isChecked = if (isChecked) {
            animateUnchecked()
            switchCallbacks?.onCheckedChanged(false)
            false
        } else {
            animateChecked()
            switchCallbacks?.onCheckedChanged(true)
            true
        }
    }
}