package kr.yeon.android.shadow.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import kr.yeon.android.shadow.ShadowView

/**
 * Created by ayeon
 */
class ShadowImageView : AppCompatImageView, ShadowView {

    override val originPadding: Rect = Rect()


    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }


    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        initOriginPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        background = makeShadowDrawable(context, attrs, this)
        optimizePadding()
    }


    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        initOriginPadding(left, top, right, bottom)

        optimizePadding()
    }

    override fun optimizePadding() {
        val backgroundPadding = Rect().apply { background.getPadding(this) }

        super.setPadding(
            originPadding.left + backgroundPadding.left,
            originPadding.top + backgroundPadding.top,
            originPadding.right + backgroundPadding.right,
            originPadding.bottom + backgroundPadding.bottom
        )
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)

        optimizePadding()
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        super.setBackgroundDrawable(background)

        optimizePadding()
    }

    override fun setBackgroundColor(color: Int) {
//        super.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resId: Int) {
//        super.setBackgroundResource(resId)
    }


}